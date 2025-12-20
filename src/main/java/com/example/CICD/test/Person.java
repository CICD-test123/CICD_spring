package com.example.CICD.test;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

@Node("Person") // Neo4j에서 'Person' 레이블을 가진 노드임을 명시한다.
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue
    private Long id; // 내부적으로 생성되는 고유 ID이다.

    private String name;

    // 기본 생성자가 반드시 필요하다.
    public Person() {}

    public Person(String name) {
        this.name = name;
    }
}
