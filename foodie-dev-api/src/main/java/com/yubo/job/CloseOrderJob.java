package com.yubo.job;

import com.yubo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**定时任务：关闭超时未支付的订单*/
//@Component
public class CloseOrderJob {
    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0/5 * * * * ? ")
    public void CloseOrder(){
         orderService.colseOrder();
    }
}
