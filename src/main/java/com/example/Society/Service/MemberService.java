package com.example.Society.Service;


import com.example.Society.Model.Flat;
import com.example.Society.Model.Member;
import com.example.Society.Repository.FlatRepository;
import com.example.Society.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    private FlatRepository flatRepository;

    // Create Member
    public Member createMember(Long flatId, Member member) {
        Flat flat = flatRepository.findById(flatId).orElseThrow(()->new RuntimeException("flat not found"));
        member.setFlat(flat);
        return memberRepository.save(member);
    }

    // Get All member
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // Get member by ID
    public Member getMemberByID(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(()->new RuntimeException("member not found"));
    }

    // Update Member
    public Member updateMember(Long memberId, Member Updatedmember) {
        Member existing =  memberRepository.findById(memberId).orElseThrow(()->new RuntimeException("member not found"));
        existing.setFirstName(Updatedmember.getFirstName());
        existing.setLastName(Updatedmember.getLastName());
        existing.setEmail(Updatedmember.getEmail());
        existing.setMobileNumber(Updatedmember.getMobileNumber());
        existing.setVehicleNumber(Updatedmember.getVehicleNumber());
        existing.setOwnership(Updatedmember.getOwnership());

        return memberRepository.save(existing);
    }

    // Delete Member
    public String deleteMember(Long memberId) {
        Member existing = memberRepository.findById(memberId).orElseThrow(()->new RuntimeException("member not found"));
        memberRepository.delete(existing);

        return "member successfully deleted";
    }

    // Get Member by Flat
    public List<Flat> getMemberByFlat(Long flatID) {
        Flat flat = flatRepository.findById(flatID).orElseThrow(()->new RuntimeException("flat not found"));
        return memberRepository.findByFlat(flat);
    }

}
