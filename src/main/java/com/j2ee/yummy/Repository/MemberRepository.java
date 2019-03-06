package com.j2ee.yummy.Repository;

import com.j2ee.yummy.PO.MemberPO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberPO,Long> {

    public MemberPO findByEmailAndPassword(String email,String passowrd);


}
