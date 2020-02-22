package enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnums {
    NEW(0,"新订单"),FINISHED(1,"已完成"),CANCEl(2,"已取消");
    private Integer code;
    private String message;

     OrderStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
