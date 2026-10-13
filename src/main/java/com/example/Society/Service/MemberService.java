package com.example.Society.Service;


import com.example.Society.Config.MemberStatus;
import com.example.Society.DTO.MemberRequestDTO;
import com.example.Society.DTO.MemberResponseDTO;
import com.example.Society.Exception.DuplicateResourceException;
import com.example.Society.Exception.ResourceNotFoundException;
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
        Flat flat = flatRepository.findById(flatId).orElseThrow(()->new ResourceNotFoundException("flat not found with id: "+flatId));

        if (flat.getOwner() != null) {
            throw new DuplicateResourceException("Flat already has an owner");
        }

        if (memberRepository.existsByMobileNumber(memberRequestDTO.getMobileNumber())) {
            throw new DuplicateResourceException(
                    "Mobile number already exists: " + memberRequestDTO.getMobileNumber());
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
        Member member = memberRepository.findById(memberId).orElseThrow(()->new ResourceNotFoundException("Member not found with id: "+memberId));

        return convertDTOResponse(member);
    }

    // Update Member
    public MemberResponseDTO updateMember(Long memberId, MemberRequestDTO Updatedmember) {
        Member existing =  memberRepository.findById(memberId).orElseThrow(()->new ResourceNotFoundException("Member not found with id: "+memberId));

        String mobileNumber = Updatedmember.getMobileNumber();

        if (!existing.getMobileNumber().equals(mobileNumber)
                && memberRepository.existsByMobileNumber(mobileNumber)) {
            throw new DuplicateResourceException(
                    "Mobile number already exists: " + mobileNumber);
        }

        existing.setFirstName(Updatedmember.getFirstName());
        existing.setLastName(Updatedmember.getLastName());
        existing.setMobileNumber(mobileNumber);

        Member savedMember = memberRepository.save(existing);

        return convertDTOResponse(savedMember);
    }

    // Delete Member
    public String deleteMember(Long memberId) {

        Member existing = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Member not found with id: " + memberId));

        if (flatRepository.existsByOwnerId(memberId)) {
            throw new DuplicateResourceException(
                    "Cannot delete member because they still own one or more flats");
        }

        memberRepository.delete(existing);

        return "Member deleted successfully";
    }

    // Get Member by Flat
    public MemberResponseDTO getMemberByFlat(Long flatID) {

        Flat flat = flatRepository.findById(flatID)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Flat not found with id: " + flatID));

        Member owner = flat.getOwner();

        if (owner == null) {
            throw new ResourceNotFoundException(
                    "No owner found for flat id: " + flatID);
        }

        return convertDTOResponse(owner);
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
