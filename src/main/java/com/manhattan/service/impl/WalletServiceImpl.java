package com.manhattan.service.impl;

import com.manhattan.dao.IUserDAO;
import com.manhattan.dao.UserDAO;
import com.manhattan.dao.WalletDao;
import com.manhattan.domain.User;
import com.manhattan.domain.Wallet;
import com.manhattan.service.WalletService;
import com.manhattan.util.MhtConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by lk.zh on 2014/5/22.
 */
@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletDao walletDao;
    @Autowired
    private IUserDAO iUserDao;
    @Autowired
    private UserDAO userDao;
    @Override
    public List<Wallet> getWalletByUserId(String userId) {
        return walletDao.findByUserId(userId);
    }

    @Override
    public Wallet saveWallet(String userId, BigDecimal money) {
        Wallet wallet=new Wallet();
        wallet.setUserId(userId);
        wallet.setMoney(money);
        wallet.setPayStatus(MhtConstant.PAY_STATUS_SUCCESS);
        wallet.setOptTime(new Timestamp(new Date().getTime()));
        Wallet wallet1=walletDao.save(wallet);
        User user=iUserDao.load(userId);
        BigDecimal walletMoney=user.getWallet();
        if (walletMoney == null) {
            walletMoney = new BigDecimal(0);
        }
        user.setWallet(walletMoney.add(money));
        userDao.save(user);
        return wallet1;
    }
}
