package com.manhattan.service.impl;

import com.manhattan.dao.WalletDao;
import com.manhattan.domain.Wallet;
import com.manhattan.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lk.zh on 2014/5/22.
 */
@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletDao walletDao;
    @Override
    public List<Wallet> getWalletByUserId(String userId) {
        return walletDao.findByUserId(userId);
    }
}
