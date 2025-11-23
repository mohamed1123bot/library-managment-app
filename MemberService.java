package com.example.project10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member login(MemberDTO dto) {
        return memberRepository.findByEmailAndPhoneAndPassword(
                dto.getEmail(),
                dto.getPhone(),
                dto.getPassword()
        ).orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }


    public Member register(MemberDTO dto) {
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        if (memberRepository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Phone already in use");
        }

        Member member = new Member();
        member.setEmail(dto.getEmail());
        member.setPhone(dto.getPhone());
        member.setPassword(dto.getPassword());
        member.setJoinDate(LocalDate.now());

        return memberRepository.save(member);
    }


    public Member updateMember(Long id, Member updatedMember) {
        Member member = memberRepository.findById(id).orElseThrow();
        member.setName(updatedMember.getName());
        member.setEmail(updatedMember.getEmail());
        member.setPhone(updatedMember.getPhone());
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public List<Member> searchMembers(String keyword) {
        return memberRepository.findByNameContainingIgnoreCase(keyword);
  }
    public List<Member> getAllMembersWithLoans() {
        return memberRepository.fetchAllWithLoans();
    }
    @GetMapping("/loans")
    public ResponseEntity<List<Member>> getMembersWithLoans() {
        return ResponseEntity.ok(memberRepository.fetchAllWithLoans());
    }
}
//
//@Transactional(readOnly = true)
//public List<Member> getAllMembers() {
//    return memberRepository.fetchAllWithLoans();
//}
