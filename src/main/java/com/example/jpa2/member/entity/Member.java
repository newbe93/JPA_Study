package com.example.jpa2.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Member(String name){
        this.name = name;
    }

    public Member() {

    }

    public void updateMember(String name){
        this.name = name;
    }
}
