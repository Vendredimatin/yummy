package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.canteen.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    public List<Menu> findAllByCanteenID(long canteenID);
}
