package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.ManagerDao;
import com.j2ee.yummy.dao.UnauditedCanInfoDao;
import com.j2ee.yummy.model.Balance;
import com.j2ee.yummy.model.Manager;

import com.j2ee.yummy.model.Member;
import com.j2ee.yummy.model.canteen.Canteen;
import com.j2ee.yummy.model.canteen.UnauditedCanInfo;
import com.j2ee.yummy.model.order.Order;
import com.j2ee.yummy.model.order.stateDesignPattern.OrderState;
import com.j2ee.yummy.yummyEnum.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @program: yummy
 * @description: manager 的业务逻辑实现类
 * @author: Liu Hanyi
 * @create: 2019-02-22 22:53
 */

@Service
public class ManagerServiceImpl {
    @Autowired
    ManagerDao managerDao;
    @Autowired
    UnauditedCanInfoDao unauditedCanInfoDao;
    @Autowired
    CanteenServiceImpl canteenService;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    MemberServiceImpl memberService;
    @Autowired
    BalanceServiceImpl balanceService;

    private static final double INIT_BALANCE = 1000;

    public Manager login(long id, String password) {
        return managerDao.login(id, password);
    }

    public boolean saveUnauditedCanInfo(UnauditedCanInfo canteenInfo) {
        return unauditedCanInfoDao.insertUnauditedCanInfo(canteenInfo);
    }

    public List<Manager> getAllMs() {
        return managerDao.selectAllMs();
    }

    public List<UnauditedCanInfo> getAllUnaudited() {
        return unauditedCanInfoDao.getAllUnaudite();
    }

    public void pass(long canteenID) {
        UnauditedCanInfo unauditedCanInfo = unauditedCanInfoDao.getUnauditedInfoByCanID(canteenID);

        Canteen canteen = canteenService.getCanteenByID(canteenID);
        canteen.setAddress(unauditedCanInfo.getAddress());
        canteen.setPhone(unauditedCanInfo.getPhone());
        canteen.setLandlordName(unauditedCanInfo.getLandlordName());
        canteen.setCanteenName(unauditedCanInfo.getCanteenName());
        canteen.setCategories(unauditedCanInfo.getCategories());

        unauditedCanInfoDao.delete(unauditedCanInfo.getId());
    }

    public void reject(long canteenID) {
        UnauditedCanInfo unauditedCanInfo = unauditedCanInfoDao.getUnauditedInfoByCanID(canteenID);
        unauditedCanInfoDao.delete(unauditedCanInfo.getId());

    }

    public Map<String, Object> getStatistics() {
        List<Balance> memBalance = balanceService.getBalances(UserType.Member);
        double[] costTable = {100, 500, 1000, 5000};
        String[] costKey = {"100以下","100~500","500~1000","1000~5000","5000以上"};
        Map<String, Integer> memCostMap = new HashMap<>();

        memBalance.stream().forEach(m -> {
            double cost = INIT_BALANCE - m.getBalance();
            boolean isAdd = false;
            for (int i = 0; i < costTable.length; i++) {
                if (cost < costTable[i]){
                    int num = memCostMap.getOrDefault(costKey[i],0);
                    memCostMap.put(costKey[i],num+1);
                    isAdd = true;
                    break;
                }
            }

            if (!isAdd){
                int num = memCostMap.getOrDefault(costKey[4],0);
                memCostMap.put(costKey[4],num+1);
            }
        });

        double[] sellTable = {50, 100, 500};
        String[] sellKey = {"50以下","50~100","100~500","500以上"};
        Map<String,Integer> canSellMap = new HashMap<>();
        List<Long> canteenIDs = canteenService.getAll().stream().map(Canteen::getId).collect(Collectors.toList());

        canteenIDs.stream().forEach(canteenID -> {
            List<Order> orders = orderService.getOrdersByCanID(canteenID);
            int totalOrderNums = (int) orders.stream().filter(order -> order.getOrderState().equals(OrderState.完成)).count();

            boolean isAdd = false;
            for (int i = 0; i < sellTable.length; i++) {
                if (totalOrderNums < sellTable[i]){
                    isAdd = true;
                    int num = canSellMap.getOrDefault(sellKey[i],0);
                    canSellMap.put(sellKey[i],num+1);
                    break;
                }
            }

            if (!isAdd){
                int num = canSellMap.getOrDefault(sellKey[3],0);
                canSellMap.put(sellKey[3],num+1);
            }
        });

       /* List<Order> orders = orderService.getAll().stream().filter(o -> o.getOrderState() == OrderState.完成).collect(Collectors.toList());
        Map<Month,List<Order>> tmp= orders.stream().collect(Collectors.groupingBy(order->order.getTime().getMonth()));
        Map<Month,>*/
        double yummyProfit = balanceService.getBalance(0).getBalance();

        long memberNums = memberService.count();
        long canteenNums = canteenService.count();
        Map<String,Object> res = new HashMap<>();
        res.put("memberNums",memberNums);
        res.put("canteenNums",canteenNums);
        res.put("yummyProfit",yummyProfit);
        res.put("memCostMap",memCostMap);
        res.put("canSellMap",canSellMap);

        return res;
    }


}
