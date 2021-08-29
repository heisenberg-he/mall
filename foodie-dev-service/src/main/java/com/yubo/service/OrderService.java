package com.yubo.service;

import com.yubo.pojo.OrderStatus;
import com.yubo.pojo.bo.SubmitOrderBO;
import com.yubo.pojo.vo.OrderVO;

public interface OrderService {
    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     */
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    public OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时订单
     * @param
     * @return
     */
    public void colseOrder();
}
