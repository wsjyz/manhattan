package com.manhattan.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2014/5/21 0021.
 */
@Entity
@Table(name = "t_mht_wallet")
public class Wallet {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "wallet_id")
    private String walletId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "money")
    private Float money;
    @Column(name = "pay_status")
    private String payStatus;
    @Column(name = "opt_time")
    private Timestamp optTime;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Timestamp getOptTime() {
        return optTime;
    }

    public void setOptTime(Timestamp optTime) {
        this.optTime = optTime;
    }
}
