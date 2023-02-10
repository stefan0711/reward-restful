package com.moyi.rewardrestful.service.impl;

import com.moyi.rewardrestful.dao.CustomerRepository;
import com.moyi.rewardrestful.dao.TransactionRepository;
import com.moyi.rewardrestful.dto.RewardDto;
import com.moyi.rewardrestful.dto.TransactionDto;
import com.moyi.rewardrestful.dto.TransactionPageResp;
import com.moyi.rewardrestful.entity.Customer;
import com.moyi.rewardrestful.entity.Transaction;
import com.moyi.rewardrestful.exception.ResourceNotFoundException;
import com.moyi.rewardrestful.exception.TransactionException;
import com.moyi.rewardrestful.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public TransactionPageResp getAllTransByCusId(Long customerId,int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        Page<Transaction> pageTransactions = transRepository.findAllByCustomerId(customerId,pageRequest);
        List<Transaction> transactionList = pageTransactions.getContent();
        List<TransactionDto> transactionDtos = transactionList.stream().map(p->mapToDto(p)).collect(Collectors.toList());
        TransactionPageResp transactionPageResp = new TransactionPageResp();

        transactionPageResp.setContent(transactionDtos);
        transactionPageResp.setPageNo(pageTransactions.getNumber());
        transactionPageResp.setPageSize(pageTransactions.getSize());
        transactionPageResp.setTotalElements(pageTransactions.getTotalElements());
        transactionPageResp.setTotalPages(pageTransactions.getTotalPages());
        transactionPageResp.setLast(pageTransactions.isLast());
        return transactionPageResp;
    }

    public RewardDto getTotalAndMonthlyByCusId(Long customerId){
        //calculate monthly reward
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        LocalDateTime lastSecondMonth = LocalDateTime.now().minusMonths(2);
        LocalDateTime lastThirdMonth = LocalDateTime.now().minusMonths(3);
        //monthly all the transactions
        List<Transaction> lastOneMonthTrans = transRepository.
                findAllByCustomerIdAndCreateDateTimeBetween(customerId, lastMonth, LocalDateTime.now());
        List<Transaction> lastTwoMonthTrans = transRepository
                .findAllByCustomerIdAndCreateDateTimeBetween(customerId, lastSecondMonth, lastMonth);
        List<Transaction> lastThreeMonthTrans = transRepository
                .findAllByCustomerIdAndCreateDateTimeBetween(customerId, lastThirdMonth, lastSecondMonth);

        double lastOneMonthReward = lastOneMonthTrans.stream().mapToDouble(Transaction::getReward).sum();
        double lastTwoMonthReward = lastTwoMonthTrans.stream().mapToDouble(Transaction::getReward).sum();
        double lastThreeMonthReward = lastThreeMonthTrans.stream().mapToDouble(Transaction::getReward).sum();
        //calculate total purchase and reward
        double totalReward = transRepository.findAllByCustomerId(customerId).stream().mapToDouble(Transaction::getReward).sum();
        double totalPurchase = transRepository.findAllByCustomerId(customerId).stream().mapToDouble(Transaction::getPurchase).sum();

        return new RewardDto(totalPurchase,totalReward,lastOneMonthReward,lastTwoMonthReward,lastThreeMonthReward);
    }



    @Override
    public TransactionDto createTrans(Long customerId, TransactionDto transactionDto) {
         Customer customer =customerRepository.findById(customerId)
                 .orElseThrow(()->new ResourceNotFoundException("customer","customerId",customerId));
         double purchase = transactionDto.getPurchase();
        //calculate reward
         double reward = calculateReward(purchase);
         Transaction save = mapToEntity(transactionDto);
         save.setCustomer(customer);
         save.setReward(reward);

         Transaction saved = transRepository.save(save);
         return mapToDto(saved);
    }

    @Override
    public TransactionDto updateTrans(Long customerId, Long id, TransactionDto transactionDto) {
        // retrieve customer entity by id
        Customer customer =customerRepository.findById(customerId)
                .orElseThrow(()->new ResourceNotFoundException("customer","customerId",customerId));

        Transaction transaction = transRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Transaction","transactionId",id));
        if(!Objects.equals(transaction.getCustomer().getId(), customer.getId())){
            throw new TransactionException(HttpStatus.BAD_REQUEST,"Transaction does not belong to customer");
        }
        double reward = calculateReward(transactionDto.getPurchase());
        transaction.setPurchase(transactionDto.getPurchase());
        transaction.setReward(reward);

        Transaction updateTrans = transRepository.save(transaction);
        return mapToDto(updateTrans);
    }

    @Override
    public void deleteTrans(Long customerId, Long id) {
        // retrieve customer entity by id
        Customer customer =customerRepository.findById(customerId)
                .orElseThrow(()->new ResourceNotFoundException("customer","customerId",customerId));

        Transaction transaction = transRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Transaction","transactionId",id));
        if(!Objects.equals(transaction.getCustomer().getId(), customer.getId())){
            throw new TransactionException(HttpStatus.BAD_REQUEST,"Transaction does not belong to customer");
        }
        transRepository.delete(transaction);

    }

    @Override
    public void deleteTransByCustomerId(Long customerId) {
        boolean customer =customerRepository.findById(customerId).isEmpty();
        if(!customer){
            return;
        }
        transRepository.deleteByCustomerId(customerId);
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

    //calculate
    private double calculateReward(double purchase){
        double reward = 0;
        if (purchase>100){
            reward = reward + 50;
            reward = 2 *  (purchase - 100) + reward;
        }else if(purchase>50){
            reward = reward+50;
        }
        return reward;
    }


}
