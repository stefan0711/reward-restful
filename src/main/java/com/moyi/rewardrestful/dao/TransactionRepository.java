package com.moyi.rewardrestful.dao;

import com.moyi.rewardrestful.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Zhipeng Yin
 * @date 2023-02-07 15:27
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
