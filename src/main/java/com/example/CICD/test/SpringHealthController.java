package com.example.CICD.test;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/spring")
@RequiredArgsConstructor
public class SpringHealthController {

    private final JdbcTemplate jdbcTemplate; // PostgreSQL 체크용
    private final PersonRepository personRepository; // Neo4j 체크용 (미리 생성 가정)
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/check-all")
    public Map<String, Object> checkAll() {
        Map<String, Object> status = new HashMap<>();

        // 1. PostgreSQL 체크
        try {
            jdbcTemplate.execute("SELECT 1");
            status.put("postgresql", "OK");
        } catch (Exception e) {
            status.put("postgresql", "FAIL: " + e.getMessage());
        }

        // 2. Neo4j 체크
        try {
            personRepository.count();
            status.put("neo4j", "OK");
        } catch (Exception e) {
            status.put("neo4j", "FAIL: " + e.getMessage());
        }

        // 3. FastAPI 통신 체크
        try {
            String fastApiUrl = "http://[FASTAPI_PRIVATE_IP]:8000/api/fastapi/health";
            String response = restTemplate.getForObject(fastApiUrl, String.class);
            status.put("fastapi_link", "OK (" + response + ")");
        } catch (Exception e) {
            status.put("fastapi_link", "FAIL: " + e.getMessage());
        }

        return status;
    }

    @GetMapping("/health")
    public String health() { return "Spring is Alive"; }
}