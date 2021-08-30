package com.yubo.mapper;

import com.yubo.my.mapper.MyMapper;
import com.yubo.pojo.OrderStatus;
import com.yubo.pojo.Orders;
import com.yubo.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper extends MyMapper<Orders> {

    public List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String, Object> map);

    public int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);

    public List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map);


}