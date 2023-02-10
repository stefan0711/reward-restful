package com.moyi.rewardrestful.service;

import com.moyi.rewardrestful.dto.TransactionDto;
import com.moyi.rewardrestful.entity.Transaction;

import java.util.List;

/**
 * @author Zhipeng Yin
 * @date 2023-02-08 17:54
 */
public interface TransactionService {


    List<Transaction> getAllTransByCusId(Long customerId);

    TransactionDto createTrans(Long customerId,TransactionDto transactionDto);
    // id = transactionId
    TransactionDto updateTrans(Long customerId,Long id,TransactionDto transactionDto);

    void  deleteTrans(Long customerId,Long id);
}
