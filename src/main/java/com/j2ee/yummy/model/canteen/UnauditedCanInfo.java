package com.j2ee.yummy.model.canteen;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: yummy
 * @description: 申请修改的餐厅信息
 * @author: Liu Hanyi
 * @create: 2019-02-22 23:09
 **/


@Getter
@Setter
public class UnauditedCanInfo extends Canteen{

    private boolean isAudited = false;

    public UnauditedCanInfo() {
    }

}
