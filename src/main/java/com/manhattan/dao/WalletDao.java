package com.manhattan.dao;

import com.manhattan.domain.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Administrator on 2014/5/22 0022.
 */
public interface WalletDao extends JpaRepository<Wallet, String> {

    List<Wallet> findByUserId(String userId);

    Page<Wallet> findByUserId(String userId, Pageable pageAble);
}
