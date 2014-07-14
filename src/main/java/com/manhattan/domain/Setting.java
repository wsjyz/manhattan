package com.manhattan.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by lk.zh on 2014/7/15 0015.
 */
@Entity
@Table(name = "t_mht_setting")
public class Setting {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "setting_id")
    private String settingId;
    @Column(name = "vip_cost") String vipCost;
    @Column(name = "credit") String credit;
    @Column(name = "monthly_cost") String monthlyCost;

    public String getSettingId() {
        return settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }

    public String getVipCost() {
        return vipCost;
    }

    public void setVipCost(String vipCost) {
        this.vipCost = vipCost;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getMonthlyCost() {
        return monthlyCost;
    }

    public void setMonthlyCost(String monthlyCost) {
        this.monthlyCost = monthlyCost;
    }
}
