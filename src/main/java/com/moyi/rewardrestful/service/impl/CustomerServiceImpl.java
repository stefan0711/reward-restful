package com.moyi.rewardrestful.service.impl;

import com.moyi.rewardrestful.dao.CustomerRepository;
import com.moyi.rewardrestful.dto.CustomerPageResp;
import com.moyi.rewardrestful.dto.CustomersDto;
import com.moyi.rewardrestful.entity.Customer;
import com.moyi.rewardrestful.exception.ResourceNotFoundException;
import com.moyi.rewardrestful.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public CustomerPageResp getAllCus(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        Page<Customer> customerPage = customerRepository.findAll(pageRequest);
        List<Customer> customers = customerPage.getContent();
        List<CustomersDto> customersDtos = customers.stream().map(p->mapToDTO(p)).collect(Collectors.toList());

        CustomerPageResp customerPageResp = new CustomerPageResp();
        customerPageResp.setContent(customersDtos);
        customerPageResp.setPageNo(customerPage.getNumber());
        customerPageResp.setPageSize(customerPage.getSize());
        customerPageResp.setTotalElements(customerPage.getTotalElements());
        customerPageResp.setTotalPages(customerPage.getTotalPages());
        customerPageResp.setLast(customerPage.isLast());

        return customerPageResp;
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
