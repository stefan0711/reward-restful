package com.moyi.rewardrestful.dao;

import com.moyi.rewardrestful.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Zhipeng Yin
 * @date 2023-02-07 15:26
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
