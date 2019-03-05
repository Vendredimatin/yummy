package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.Manager;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @program: yummy
 * @description: unauditedCanInfo 的数据层类
 * @author: Liu Hanyi
 * @create: 2019-02-22 23:11
 **/


public interface UnauditedCanInfoRepository extends JpaRepository<UnauditedCanInfo,Long> {

    public List<UnauditedCanInfo> getAllByIsAudited(int isAudited);

    public UnauditedCanInfo getByCanteenID(long canteenID);
}
