package com.example.Society.Controller;


import com.example.Society.Model.Flat;
import com.example.Society.Model.Member;
import com.example.Society.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    //Create Member for a Flat
    @PostMapping("/flat/{flatId}")
    public Member createMember(@PathVariable Long flatId, @RequestBody Member member) {
        return memberService.createMember(flatId,member);
    }

    //Get All Members
    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    //Get Member by ID
    @GetMapping("/{memberId}")
    public Member getMemberById(@PathVariable Long memberId) {
        return memberService.getMemberByID(memberId);
    }

    //Update Member
    @PutMapping("/{memberId}")
    public Member updateMember(@PathVariable Long memberId, @RequestBody Member member) {
        return memberService.updateMember(memberId, member);
    }

    //Delete Member
    @DeleteMapping("/{memberId}")
    public String deleteMember(@PathVariable Long memberId) {
        return memberService.deleteMember(memberId);
    }

    // Get Member By Flat
    @GetMapping("/flat/{flatID}")
    public List<Flat> getMemberByFlat(@PathVariable Long flatID) {
        return memberService.getMemberByFlat(flatID);
    }

}
