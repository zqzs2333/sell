package com.work.selll.Controller;

import com.work.selll.DTO.OrderDTO;
import com.work.selll.Util.ResultVoUtil;
import com.work.selll.VO.ResultVo;
import com.work.selll.bean.OrderDetail;
import com.work.selll.exception.SellException;
import com.work.selll.form.OrderForm;
import com.work.selll.service.OrderService;
import enums.ResultEnums;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/list")
    public ResultVo<List<OrderDTO>> findAll(@RequestParam(value = "page" ,defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size)
    {
        PageRequest of = PageRequest.of(page-1, size);

        Page<OrderDTO> orderDTOPage = orderService.findList(of);
        List<OrderDTO> content = orderDTOPage.getContent();
        return ResultVoUtil.success(orderDTOPage.getContent());
        

    }

    @GetMapping("/cancel")
    public ResultVo cancel(@RequestParam("orderId") String orderId)
    {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            throw new SellException(ResultEnums.order_not_exist);
        }
        orderService.cancel(orderDTO);
        return ResultVoUtil.success();
    }
    @GetMapping("detail")
    public ResultVo<OrderDTO> detail(@RequestParam("orderId") String orderId,
                                              @RequestParam("openid") String openid)
    {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (!orderDTO.getBuyerOpenid().equals(openid)) {
            throw new SellException(ResultEnums.openid_is_error);
        }
        return ResultVoUtil.success(orderDTO);
    }
    public ResultVo finish(@RequestParam("orderId") String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null){
            throw new SellException(ResultEnums.order_not_exist);
        }
        orderService.finish(orderDTO);
        return  ResultVoUtil.success();
    }
//    public ResultVo modify(@Valid OrderForm orderForm,)
}
