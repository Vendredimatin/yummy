package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.UnauditedCanInfoRepository;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: yummy
 * @description: dao
 * @author: Liu Hanyi
 * @create: 2019-03-05 11:52
 **/

@Repository
public class UnauditedCanInfoDao {
    @Autowired
    UnauditedCanInfoRepository unauditedCanInfoRepository;

    public List<UnauditedCanInfo> getAllUnaudite(){
        return unauditedCanInfoRepository.getAllByIsAudited(0);
    }

    public boolean insertUnauditedCanInfo(UnauditedCanInfo unauditedCanInfo){
        try{
            unauditedCanInfoRepository.save(unauditedCanInfo);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public UnauditedCanInfo getUnauditedInfoByCanID(long canteenID){
        return unauditedCanInfoRepository.getByCanteenID(canteenID);
    }

    public void delete(long id){
        unauditedCanInfoRepository.deleteById(id);
    }
}
