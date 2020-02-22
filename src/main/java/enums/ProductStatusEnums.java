package enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnums {
    UP(0,"在架"),
    DOWN(1,"下架");

   private Integer code;
   private String message;

    ProductStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
