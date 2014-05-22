package com.manhattan.dao;

import com.manhattan.domain.Wallet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Administrator on 2014/5/22 0022.
 */
public interface WalletDao extends CrudRepository<Wallet, String> {

    List<Wallet> findByUserId(String userId);
}
