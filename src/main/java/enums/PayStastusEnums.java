package enums;

import lombok.Getter;

@Getter
public enum  PayStastusEnums {
    WAIT(0,"等待支付"),
    SUCCESS(1,"已支付")
    ;

    private Integer code;
    private String message;

    PayStastusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
