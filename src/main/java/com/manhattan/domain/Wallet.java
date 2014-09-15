package com.manhattan.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal money;
    @Column(name = "pay_status")
    private String payStatus;
    @Column(name = "opt_time")
    private Timestamp optTime;
    @Column(name = "subject")
    private String subject;

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
