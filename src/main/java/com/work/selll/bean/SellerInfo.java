package com.work.selll.bean;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class SellerInfo {
    @Id
    private String sellerId;

    private String username;

    private String password;
    //微信openid
    private String openid;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
}
