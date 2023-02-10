package com.moyi.rewardrestful.controller;

import com.moyi.rewardrestful.dto.RewardDto;
import com.moyi.rewardrestful.dto.TransactionDto;
import com.moyi.rewardrestful.dto.TransactionPageResp;
import com.moyi.rewardrestful.service.TransactionService;
import com.moyi.rewardrestful.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zhipeng Yin
 * @date 2023-02-07 15:34
 */
@RestController
@RequestMapping("api/v1")
public class TransactionController {
    public final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * 分页 paging
     * If you have more and more data in the future, finding all the data at once can cause problems,
     * long wait times, or memory leaks, so paging
     */
    @GetMapping("/customers/{customerId}/transactions")
    public TransactionPageResp getAllByCustomerId(
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIR, required = false) String sortDir,
            @PathVariable Long customerId){

        return transactionService.getAllTransByCusId(customerId,pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/customers/{customerId}/transactions/summary")
    public ResponseEntity<RewardDto> getTotalAndMonthly(@PathVariable(value = "customerId") long id){
         RewardDto rewardDto = transactionService.getTotalAndMonthlyByCusId(id);
         return new ResponseEntity<>(rewardDto,HttpStatus.OK);
    }


    @PostMapping("/customers/{customerId}/transactions")
    public ResponseEntity<TransactionDto> createTransaction(@PathVariable(value = "customerId") long id,
                                                        @Validated @RequestBody TransactionDto transactionDto) {
        return new ResponseEntity<>(transactionService.createTrans(id, transactionDto), HttpStatus.CREATED);
    }

    @PutMapping("/customers/{customerId}/transactions/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable(value = "customerId") Long customerId,
                                                    @PathVariable(value = "id") Long transactionId,
                                                    @Validated @RequestBody TransactionDto transactionDto) {
        TransactionDto update = transactionService.updateTrans( customerId,transactionId, transactionDto);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/customers/{customerId}/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable(value = "customerId") Long customerId,
                                                @PathVariable(value = "id") Long transactionId) {
        transactionService.deleteTrans(customerId, transactionId);

        return new ResponseEntity<>("Transaction deleted Successfully", HttpStatus.OK);
    }

}
