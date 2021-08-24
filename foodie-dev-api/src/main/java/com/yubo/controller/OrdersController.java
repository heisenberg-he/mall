package com.yubo.controller;

import com.yubo.enums.PayMethod;
import com.yubo.pojo.bo.SubmitOrderBO;
import com.yubo.pojo.vo.OrderVO;
import com.yubo.service.OrderService;
import com.yubo.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单相关",tags = {"订单相关Api接口"})
@RequestMapping("orders")
@RestController
public class OrdersController {
    @Autowired
    private OrderService orderService;


    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type
                && submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type ) {
            return IMOOCJSONResult.errorMsg("支付方式不支持！");
        }

        // 1. 创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();

        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品
        // TODO 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
//        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);
//
//        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
//        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
//        merchantOrdersVO.setReturnUrl(payReturnUrl);
//
//        // 为了方便测试购买，所以所有的支付金额都统一改为1分钱
//        merchantOrdersVO.setAmount(1);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("imoocUserId","imooc");
//        headers.add("password","imooc");
//
//        HttpEntity<MerchantOrdersVO> entity =
//                new HttpEntity<>(merchantOrdersVO, headers);
//
//        ResponseEntity<IMOOCJSONResult> responseEntity =
//                restTemplate.postForEntity(paymentUrl,
//                        entity,
//                        IMOOCJSONResult.class);
//        IMOOCJSONResult paymentResult = responseEntity.getBody();
//        if (paymentResult.getStatus() != 200) {
//            logger.error("发送错误：{}", paymentResult.getMsg());
//            return IMOOCJSONResult.errorMsg("支付中心订单创建失败，请联系管理员！");
//        }

        return IMOOCJSONResult.ok(orderId);
    }
}
