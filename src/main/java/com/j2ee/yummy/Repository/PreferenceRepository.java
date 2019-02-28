package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.canteen.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference,Long> {
}
