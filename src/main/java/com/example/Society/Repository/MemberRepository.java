package com.example.Society.Repository;

import com.example.Society.Model.Flat;
import com.example.Society.Model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    List<Flat> findByFlat(Flat flat);

}
