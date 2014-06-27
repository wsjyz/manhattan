package com.manhattan.service;

import com.manhattan.domain.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lk.zh on 2014/5/22.
 */
public interface WalletService {
    List<Wallet> getWalletByUserId(String userId);

    Wallet saveWallet(String userId, BigDecimal money);

    Page<Wallet> getRecordList(Pageable pageAble, String userId);
}
