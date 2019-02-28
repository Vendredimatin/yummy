package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.canteen.Combo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComboRepository extends JpaRepository<Combo,Long> {
}
