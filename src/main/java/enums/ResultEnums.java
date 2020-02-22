package enums;

import lombok.Getter;

@Getter
public enum  ResultEnums {
    product_not_exist(10,"商品不存在"),
    product_stock_error(11,"库存不足"),
    order_not_exist(12,"订单不存在"),
    orderDetail_not_exist(13,"订单不存在"),
    orderStutas_is_error(14,"订单状态不正确"),
    orderStutas_update_error(15,"订单状态更新失败"),
    order_detail_empty(16,"订单状态更新失败"),
    payStutas_is_error(17,"支付状态不正确"),
    payStutas_update_error(18,"支付状态更新失败"),
    pay_detail_empty(19,"支付状态更新失败"),
    param_error(1,"参数不正确"),
    cart_is_empty(20,"购物车是空的"),
    openid_is_error(21,"不是本的openId"),
    wx_mp_error(22,"wx公众号错误"),
    wxpay_notify_money_error(23,"微信支付异步通知金额校验不通过"),
    seller_product_status_error(24,"商品状态不正确")

    ;
    private Integer code;
    private String message;

    ResultEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
