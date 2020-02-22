package com.work.selll.Controller;

import com.lly835.bestpay.model.PayResponse;
import com.work.selll.DTO.OrderDTO;
import com.work.selll.exception.SellException;
import com.work.selll.service.OrderService;
import com.work.selll.service.PayService;
import enums.ResultEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("pay")
public class PayController {

    @Autowired
    OrderService orderService;
    @Autowired
    PayService payService;
    @GetMapping("create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map)
    {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnums.order_not_exist);
        }
        //发起支付
        PayResponse payResponse = payService.create(orderDTO);

        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        return  new ModelAndView("/pay/create");
    }
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData)
    {
        payService.notify(notifyData);
        //返回微信处理结果
        return new ModelAndView("pay/success");
    }
}
