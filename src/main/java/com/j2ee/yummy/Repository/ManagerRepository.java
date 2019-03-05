package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    public Manager findByIdAndAndPassword(long id, String password);
}
