package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    public Member findByEmailAndPassword(String email,String passowrd);

    public boolean existsByEmail(String email);
}
