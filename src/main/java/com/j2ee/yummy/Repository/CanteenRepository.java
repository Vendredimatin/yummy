package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.canteen.Canteen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanteenRepository extends JpaRepository<Canteen,Long> {

    public Canteen findCanteenByIdAndPassword(long id,String password);
}
