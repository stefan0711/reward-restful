package com.moyi.rewardrestful.controller;

import com.moyi.rewardrestful.dto.CustomerPageResp;
import com.moyi.rewardrestful.dto.CustomersDto;
import com.moyi.rewardrestful.service.CustomerService;
import com.moyi.rewardrestful.service.TransactionService;
import com.moyi.rewardrestful.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zhipeng Yin
 * @date 2023-02-07 15:29
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final TransactionService transactionService;

    @Autowired
    public CustomerController(CustomerService customerService, TransactionService transactionService) {
        this.customerService = customerService;
        this.transactionService = transactionService;
    }
    @GetMapping
    public CustomerPageResp getAllCustomers(
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        return customerService.getAllCus(pageNo, pageSize, sortBy, sortDir);
    }


    @PostMapping
    public ResponseEntity<CustomersDto> createCustomer(@Validated @RequestBody CustomersDto customersDto){
        CustomersDto cusResponse = customerService.createCus(customersDto);
        return new ResponseEntity<>(cusResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomersDto> updateCustomer(@Validated @RequestBody CustomersDto customersDto, @PathVariable(name = "id") long id) {
        CustomersDto cusResponse = customerService.updateCus( id,customersDto);
        return new ResponseEntity<>(cusResponse, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable(name = "id") long id) {
        customerService.deleteCusById(id);
        transactionService.deleteTransByCustomerId(id);
        return new ResponseEntity<>("customer and transaction deleted successfully.", HttpStatus.OK);
    }


}
