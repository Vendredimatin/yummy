package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.PreferenceRepository;
import com.j2ee.yummy.model.canteen.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @program: yummy
 * @description: 优惠类的dao
 * @author: Liu Hanyi
 * @create: 2019-02-27 18:45
 **/

@Repository
public class PreferenceDao {

    @Autowired
    PreferenceRepository preferenceRepository;

    public Preference insert(Preference preference){
        return preferenceRepository.save(preference);
    }
}
