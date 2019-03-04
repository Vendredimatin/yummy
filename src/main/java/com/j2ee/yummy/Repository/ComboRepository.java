package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.canteen.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ComboRepository extends JpaRepository<Combo,Long> {
}
