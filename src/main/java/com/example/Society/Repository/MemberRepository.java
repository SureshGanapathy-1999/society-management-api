package com.example.Society.Repository;

import com.example.Society.DTO.MemberResponseDTO;
import com.example.Society.Model.Flat;
import com.example.Society.Model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional <Member> findByFlatsContaining(Flat flat);

}
