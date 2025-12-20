package com.example.CICD.test;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {
    // Neo4jRepository를 상속받는 것만으로 count(), save(), findAll() 등을 사용할 수 있다.
    // 연결 테스트를 위해 이 인터페이스의 메서드를 호출하는 것이다.
}
