package com.moyi.rewardrestful.service.impl;

import com.moyi.rewardrestful.dao.CustomerRepository;
import com.moyi.rewardrestful.dto.CustomersDto;
import com.moyi.rewardrestful.entity.Customer;
import com.moyi.rewardrestful.exception.ResourceNotFoundException;
import com.moyi.rewardrestful.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Zhipeng Yin
 * @date 2023-02-08 17:55
 */
@Service("CustomerService")
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public CustomersDto createCus(CustomersDto customersDto) {
        Customer customer = mapToEntity(customersDto);
        Customer savedCus = customerRepository.save(customer);
        return mapToDTO(savedCus);
    }

    @Override
    public CustomersDto updateCus(Long id,CustomersDto customersDto) {
        //It checks whether customers exist，Throw an exception if the customer does not exist
        Customer checkCus = customerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("customer","id",id));
        checkCus.setName(customersDto.getName());
        Customer updatedCus = customerRepository.save(checkCus);
        return mapToDTO(updatedCus);
    }

    @Override
    public void deleteCusById(Long id) {
        Customer checkCus = customerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("customer","id",id));
        customerRepository.delete(checkCus);
    }

    private Customer mapToEntity(CustomersDto customersDto){
        Customer customer = new Customer();
        customer.setName(customersDto.getName());
        return customer;
    }

    private CustomersDto mapToDTO(Customer customer) {
        CustomersDto customersDto = new CustomersDto();
        customersDto.setId(customer.getId());
        customersDto.setName(customer.getName());
        return customersDto;
    }
}
