package com.example.Society.Controller;

import com.example.Society.DTO.MemberRequestDTO;
import com.example.Society.DTO.MemberResponseDTO;
import com.example.Society.Service.MemberService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //Create Member for a Flat
    @PostMapping("/flat/{flatId}")
    public MemberResponseDTO createMember(@PathVariable Long flatId, @Valid @RequestBody MemberRequestDTO memberRequestDTO) {
        return memberService.createMember(flatId,memberRequestDTO);
    }

    //Get All Members
    @GetMapping
    public List<MemberResponseDTO> getAllMembers() {
        return memberService.getAllMembers();
    }

    //Get Member by ID
    @GetMapping("/{memberId}")
    public MemberResponseDTO getMemberById(@PathVariable Long memberId) {
        return memberService.getMemberByID(memberId);
    }

    //Update Member
    @PutMapping("/{memberId}")
    public MemberResponseDTO updateMember(@PathVariable Long memberId, @Valid @RequestBody MemberRequestDTO memberRequestDTO) {
        return memberService.updateMember(memberId, memberRequestDTO);
    }

    //Delete Member
    @DeleteMapping("/{memberId}")
    public String deleteMember(@PathVariable Long memberId) {
        return memberService.deleteMember(memberId);
    }

    // Get Member By Flat
    @GetMapping("/flat/{flatId}")
    public MemberResponseDTO getMemberByFlat(@PathVariable Long flatId) {
        return memberService.getMemberByFlat(flatId);
    }

}
