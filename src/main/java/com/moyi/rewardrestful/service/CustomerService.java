package com.moyi.rewardrestful.service;

import com.moyi.rewardrestful.dto.CustomersDto;

/**
 * @author Zhipeng Yin
 * @date 2023-02-08 17:54
 */
public interface CustomerService {

    CustomersDto createCus(CustomersDto customersDto);

    CustomersDto updateCus(Long id,CustomersDto customersDto);

    void deleteCusById(Long id);


}
