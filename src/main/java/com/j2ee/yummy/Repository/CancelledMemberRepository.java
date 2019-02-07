package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.CancelledMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancelledMemberRepository extends JpaRepository<CancelledMember,Long> {
}
