package com.moyi.rewardrestful.service;

import com.moyi.rewardrestful.dto.RewardDto;
import com.moyi.rewardrestful.dto.TransactionDto;
import com.moyi.rewardrestful.dto.TransactionPageResp;
import com.moyi.rewardrestful.entity.Transaction;

import java.util.List;

/**
 * @author Zhipeng Yin
 * @date 2023-02-08 17:54
 */
public interface TransactionService {


    TransactionPageResp getAllTransByCusId(Long customerId,int pageNo, int pageSize, String sortBy, String sortDir);

    TransactionDto createTrans(Long customerId,TransactionDto transactionDto);
    // id = transactionId
    TransactionDto updateTrans(Long customerId,Long id,TransactionDto transactionDto);

    RewardDto getTotalAndMonthlyByCusId(Long customerId);

    void  deleteTrans(Long customerId,Long id);

    void deleteTransByCustomerId(Long customerId);


}
