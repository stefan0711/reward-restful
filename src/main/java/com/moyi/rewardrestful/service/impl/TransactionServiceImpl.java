package com.moyi.rewardrestful.service.impl;

import com.moyi.rewardrestful.dao.CustomerRepository;
import com.moyi.rewardrestful.dao.TransactionRepository;
import com.moyi.rewardrestful.dto.TransactionDto;
import com.moyi.rewardrestful.entity.Customer;
import com.moyi.rewardrestful.entity.Transaction;
import com.moyi.rewardrestful.exception.ResourceNotFoundException;
import com.moyi.rewardrestful.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zhipeng Yin
 * @date 2023-02-08 17:56
 */
@Service("TransactionService")
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository repository, CustomerRepository customerRepository) {
        this.transRepository = repository;
        this.customerRepository = customerRepository;

    }


    @Override
    public List<Transaction> getAllTransByCusId(Long CustomerId) {
        return null;
    }

    @Override
    public TransactionDto createTrans(Long customerId, TransactionDto transactionDto) {
         Customer customer =customerRepository.findById(customerId)
                 .orElseThrow(()->new ResourceNotFoundException("customer","customerId",customerId));
         //calculate reward
         double reward = 0;
         double purchase = transactionDto.getPurchase();
         if (purchase>100){
             reward = reward + 50;
             reward = 2 *  (purchase - 100) + reward;
         }else if(purchase>50){
             reward = reward+50;
         }
         Transaction save = mapToEntity(transactionDto);
         save.setCustomer(customer);
         save.setReward(reward);

         Transaction saved = transRepository.save(save);
         return mapToDto(saved);
    }

    @Override
    public TransactionDto updateTrans(Long customerId, Long id, TransactionDto transactionDto) {
        return null;
    }

    @Override
    public void deleteTrans(Long customerId, Long id) {

    }

    private Transaction mapToEntity(TransactionDto transactionDto){
        Transaction transaction = new Transaction();
        transaction.setPurchase(transactionDto.getPurchase());
        transaction.setReward(transactionDto.getReward());
        return  transaction;

    }
    private TransactionDto mapToDto(Transaction transaction){
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setPurchase(transaction.getPurchase());
        transactionDto.setReward(transaction.getReward());
        transactionDto.setId(transaction.getId());
        transactionDto.setCustomerId(transaction.getCustomer().getId());
        transactionDto.setCustomerName(transaction.getCustomer().getName());

        return transactionDto;
    }


}
