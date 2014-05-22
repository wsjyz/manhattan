package com.manhattan.service;

import com.manhattan.domain.Wallet;

import java.util.List;

/**
 * Created by lk.zh on 2014/5/22.
 */
public interface WalletService {
    List<Wallet> getWalletByUserId(String userId);
}
