package com.moyi.rewardrestful.dto;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;


/**
 * @author Zhipeng Yin
 * @date 2023-02-08 18:24
 */

public class TransactionDto {
    private Long id;

    private Long customerId;
    private String customerName;
    @NotBlank
    @PositiveOrZero   // value of purchase must be either positive or 0
    private Double purchase;
    private Double reward;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getPurchase() {
        return purchase;
    }

    public void setPurchase(Double purchase) {
        this.purchase = purchase;
    }

    public Double getReward() {
        return reward;
    }

    public void setReward(Double reward) {
        this.reward = reward;
    }
}
