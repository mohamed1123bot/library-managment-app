package com.example.project10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;
    private MemberRepository memberRepository;



    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        return ResponseEntity.ok(memberService.updateMember(id, member));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @GetMapping("all")
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchMembers(@RequestParam String keyword) {
        return ResponseEntity.ok(memberService.searchMembers(keyword));
  }
    // تسجيل عضو جديد
    @PostMapping("/signup")
    public Member signup(@RequestBody Member member) {
        member.setJoinDate(LocalDate.now());
        return memberRepository.save(member);
    }

    // تسجيل الدخول
    @PostMapping("/login")
    public String login(@RequestBody Member loginMember) {
        Optional<Member> memberOpt = memberRepository.findByEmail(loginMember.getEmail());
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            if (member.getPassword().equals(loginMember.getPassword())) {
                return "مرحباً " + member.getName();
            } else {
                return "كلمة المرور غير صحيحة";
            }
        } else {
            return "البريد الإلكتروني غير موجود";
        }
    }
}