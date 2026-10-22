package com.example.Society.Controller;

import com.example.Society.DTO.MemberRequestDTO;
import com.example.Society.DTO.MemberResponseDTO;
import com.example.Society.Service.MemberService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/flat/{flatId}")
    public MemberResponseDTO createMember(@PathVariable Long flatId, @Valid @RequestBody MemberRequestDTO memberRequestDTO) {
        return memberService.createMember(flatId,memberRequestDTO);
    }

    //Get All Members
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<MemberResponseDTO> getAllMembers() {
        return memberService.getAllMembers();
    }

    //Get Member by ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{memberId}")
    public MemberResponseDTO getMemberById(@PathVariable Long memberId) {
        return memberService.getMemberByID(memberId);
    }

    //Update Member
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{memberId}")
    public MemberResponseDTO updateMember(@PathVariable Long memberId, @Valid @RequestBody MemberRequestDTO memberRequestDTO) {
        return memberService.updateMember(memberId, memberRequestDTO);
    }

    //Delete Member
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{memberId}")
    public String deleteMember(@PathVariable Long memberId) {
        return memberService.deleteMember(memberId);
    }

    // Get Member By Flat
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/flat/{flatId}")
    public MemberResponseDTO getMemberByFlat(@PathVariable Long flatId) {
        return memberService.getMemberByFlat(flatId);
    }

}
