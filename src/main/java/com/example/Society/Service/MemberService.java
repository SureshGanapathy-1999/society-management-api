package com.example.Society.Service;


import com.example.Society.Config.MemberStatus;
import com.example.Society.DTO.MemberRequestDTO;
import com.example.Society.DTO.MemberResponseDTO;
import com.example.Society.Model.Flat;
import com.example.Society.Model.Member;
import com.example.Society.Repository.FlatRepository;
import com.example.Society.Repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private FlatRepository flatRepository;

    public MemberService(MemberRepository memberRepository, FlatRepository flatRepository) {
        this.memberRepository = memberRepository;
        this.flatRepository = flatRepository;
    }

    // Create Member
    public MemberResponseDTO createMember(Long flatId, MemberRequestDTO memberRequestDTO) {
        Flat flat = flatRepository.findById(flatId).orElseThrow(()->new RuntimeException("flat not found"));

        if (flat.getOwner() != null) {
            throw new RuntimeException("Flat already has an owner");
        }

        Member member = new Member();

        member.setFirstName(memberRequestDTO.getFirstName());
        member.setLastName(memberRequestDTO.getLastName());
        member.setMobileNumber(memberRequestDTO.getMobileNumber());
        member.setMemberStatus(MemberStatus.ACTIVE);

        Member savedMember =  memberRepository.save(member);

        flat.setOwner(savedMember);
        flatRepository.save(flat);

        return convertDTOResponse(savedMember);
    }

    // Get All member
    public List<MemberResponseDTO> getAllMembers() {
        return memberRepository.findAll().stream().map(this::convertDTOResponse).toList();
    }

    // Get member by ID
    public MemberResponseDTO getMemberByID(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new RuntimeException("member not found"));

        return convertDTOResponse(member);
    }

    // Update Member
    public MemberResponseDTO updateMember(Long memberId, MemberRequestDTO Updatedmember) {
        Member existing =  memberRepository.findById(memberId).orElseThrow(()->new RuntimeException("member not found"));
        existing.setFirstName(Updatedmember.getFirstName());
        existing.setLastName(Updatedmember.getLastName());
        existing.setMobileNumber(Updatedmember.getMobileNumber());

        Member savedMember = memberRepository.save(existing);

        return convertDTOResponse(savedMember);
    }

    // Delete Member
    public String deleteMember(Long memberId) {
        Member existing = memberRepository.findById(memberId).orElseThrow(()->new RuntimeException("member not found"));
        memberRepository.delete(existing);

        return "Member deleted successfully";
    }

    // Get Member by Flat
    public MemberResponseDTO getMemberByFlat(Long flatID) {

        Flat flat = flatRepository.findById(flatID)
                .orElseThrow(() -> new RuntimeException("flat not found"));

        return memberRepository.findByFlatsContaining(flat)
                .map(this::convertDTOResponse)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
    }

    //created for response conversion

    private MemberResponseDTO convertDTOResponse(Member member) {
        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();

        memberResponseDTO.setId(member.getId());
        memberResponseDTO.setFirstName(member.getFirstName());
        memberResponseDTO.setLastName(member.getLastName());
        memberResponseDTO.setMobileNumber(member.getMobileNumber());
        memberResponseDTO.setMemberStatus(member.getMemberStatus());

        return memberResponseDTO;
    }

}
