package com.moyi.rewardrestful.service;

import com.moyi.rewardrestful.dto.CustomerPageResp;
import com.moyi.rewardrestful.dto.CustomersDto;

import java.util.List;

/**
 * @author Zhipeng Yin
 * @date 2023-02-08 17:54
 */
public interface CustomerService {

    CustomerPageResp getAllCus(int pageNo, int pageSize, String sortBy, String sortDir);

    CustomersDto createCus(CustomersDto customersDto);

    CustomersDto updateCus(Long id,CustomersDto customersDto);

    void deleteCusById(Long id);


}
