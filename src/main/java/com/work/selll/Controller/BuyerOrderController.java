package com.work.selll.Controller;

import com.work.selll.DTO.OrderDTO;
import com.work.selll.Util.ResultVoUtil;
import com.work.selll.VO.ResultVo;
import com.work.selll.converter.OrderForm2OrderDTO;
import com.work.selll.exception.SellException;
import com.work.selll.form.OrderForm;
import com.work.selll.service.OrderService;
import enums.ResultEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/buyer/order")
@RestController
@Slf4j
public class BuyerOrderController {
    @Autowired
    OrderService orderService;
    //创建订单
    @PostMapping("/create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            log.error("创建订单-参数不正确 orderForm={}",orderForm);
            throw new SellException(ResultEnums.param_error.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("创建订单-购物车不能为空");
            throw  new  SellException(ResultEnums.cart_is_empty);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String,String> map= new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVoUtil.success(map);
}
    @GetMapping("/list")
    public ResultVo<List<OrderDTO>> list(@RequestParam(value = "openid" ) String openid,
    @RequestParam(value = "page",defaultValue = "0") Integer page, @RequestParam(value = "size",defaultValue = "10") Integer size)
    {
        if (StringUtils.isEmpty(openid)) {
            log.error("查询订单-openid为空");
            throw new SellException(ResultEnums.param_error);
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findAll(openid, pageRequest);
        return ResultVoUtil.success(orderDTOPage.getContent());
    }
    @GetMapping("/detail")
    public ResultVo<OrderDTO> detial(@RequestParam(value = "openid" ) String openid,
                                     @RequestParam(value = "orderid") String orderid)
    {
        //todo
        OrderDTO orderDTO = orderService.findOne(orderid);
        if (orderDTO == null) {
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("传入的openid和数据库中的openid不一致");
            throw new SellException(ResultEnums.openid_is_error);
        }
        return ResultVoUtil.success(orderDTO);
    }
    @GetMapping("/cancel")
    public ResultVo<OrderDTO> cancel(@RequestParam(value = "openid" ) String openid,
                                     @RequestParam(value = "orderid") String orderid)
    {
        //todo
        OrderDTO orderDTO = orderService.findOne(orderid);
        if (orderDTO == null) {
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error("传入的openid和数据库中的openid不一致");
            throw new SellException(ResultEnums.openid_is_error);
        }
        orderService.cancel(orderDTO);
        return ResultVoUtil.success();
    }
}
