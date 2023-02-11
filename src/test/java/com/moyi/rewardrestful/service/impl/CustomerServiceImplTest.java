package com.moyi.rewardrestful.service.impl;

import com.moyi.rewardrestful.dao.CustomerRepository;
import com.moyi.rewardrestful.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


/**
 * @author Zhipeng Yin
 * @date 2023-02-10 15:31
 */
@SpringBootTest
class CustomerServiceImplTest {
    @Autowired
    CustomerRepository customerRepository;


    @Test
    void createCus() {
        Customer customer = new Customer();
        customer.setName("test");

        Customer save = customerRepository.save(customer);
        Assertions.assertNotNull(save);
        Assertions.assertEquals(customer.getName(), save.getName());
    }

    @Test
    void getAllCus() {
        createCus();
        int pageNo = 0;
        int pageSize = 5;
        Sort sort = Sort.by("name").ascending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        Page<Customer> list = customerRepository.findAll(pageRequest);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.getContent().size());

    }

    @Test
    void updateCus() {
        Customer customer = new Customer();
        customer.setId(8L);
        customer.setName("updateTest");
        Customer update = customerRepository.save(customer);

        Assertions.assertNotNull(update);
        Assertions.assertEquals(customer.getName(), update.getName());
        Assertions.assertEquals(customer.getId(), update.getId());
    }

    @Test
    void deleteCusById() {
        Long id = 8L;
        customerRepository.deleteById(id);
        boolean customer = customerRepository.findById(id).isPresent();
        Assertions.assertFalse(customer);
    }
}