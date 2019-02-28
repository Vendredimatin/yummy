package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.canteen.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish,Long> {
}
