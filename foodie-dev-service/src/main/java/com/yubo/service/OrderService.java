package com.yubo.service;

import com.yubo.pojo.bo.SubmitOrderBO;
import com.yubo.pojo.vo.OrderVO;

public interface OrderService {
    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     */
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);

}
