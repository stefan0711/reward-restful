package com.moyi.rewardrestful.dao;

import com.moyi.rewardrestful.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Zhipeng Yin
 * @date 2023-02-07 15:27
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    void deleteByCustomerId(Long customerId);
    Page<Transaction> findAllByCustomerId(Long customerId, Pageable pageable);
    List<Transaction> findAllByCustomerId(Long customerId);
    List<Transaction> findAllByCustomerIdAndCreateDateTimeBetween(Long customerId, LocalDateTime from, LocalDateTime to);
}
