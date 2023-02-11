package com.moyi.rewardrestful.service.impl;

import com.moyi.rewardrestful.dao.CustomerRepository;
import com.moyi.rewardrestful.dao.TransactionRepository;
import com.moyi.rewardrestful.entity.Customer;
import com.moyi.rewardrestful.entity.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



/**
 * @author Zhipeng Yin
 * @date 2023-02-10 15:36
 */
@SpringBootTest
class TransactionServiceImplTest {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CustomerRepository customerRepository;


    @Test
    void createTrans() {
        Transaction transaction = new Transaction();
        transaction.setCustomer(customerRepository.findById(1L).get());
        transaction.setPurchase(200.00);

        Transaction save = transactionRepository.save(transaction);

        Assertions.assertNotNull(save);
        Assertions.assertEquals(transaction.getPurchase(), save.getPurchase());
        Assertions.assertEquals(transaction.getCustomer(), save.getCustomer());
    }

    @Test
    void updateTrans() {
        Transaction transaction = new Transaction();
        transaction.setCustomer(customerRepository.findById(1L).get());
        transaction.setPurchase(300.00);
        transaction.setId(1L);
        Transaction save = transactionRepository.save(transaction);

        Assertions.assertNotNull(save);
        Assertions.assertEquals(transaction.getPurchase(), save.getPurchase());
    }

    @Test
    void deleteTrans() {
        Long id = 8L;
        transactionRepository.deleteById(id);
        boolean transaction = transactionRepository.findById(id).isPresent();
        Assertions.assertFalse(transaction);

    }

    @Test
    void deleteTransByCustomerId() {
        Long customerId = 1L;
        transactionRepository.deleteByCustomerId(customerId);
        boolean transaction = transactionRepository.findAllByCustomerId(customerId).isEmpty();
        Assertions.assertFalse(transaction);
    }
}