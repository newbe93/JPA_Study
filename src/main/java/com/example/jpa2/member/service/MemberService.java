package com.example.jpa2.member.service;

import com.example.jpa2.member.entity.Member;
import com.example.jpa2.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional
    public Long join(Member member){
        Member savedMember = memberRepository.save(member);
        return savedMember.getId();
    }
}
