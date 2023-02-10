package com.moyi.rewardrestful.dto;

/**
 * @author Zhipeng Yin
 * @date 2023-02-10 13:51
 */
public class RewardDto {
    private double totalPurchase;
    private double totalReward;
    private double lastOneMonth;
    private double lastTwoMonth;
    private double lastThreeMonth;

    public RewardDto(double totalPurchase, double totalReward, double lastOneMonth, double lastTwoMonth, double lastThreeMonth) {
        this.totalPurchase = totalPurchase;
        this.totalReward = totalReward;
        this.lastOneMonth = lastOneMonth;
        this.lastTwoMonth = lastTwoMonth;
        this.lastThreeMonth = lastThreeMonth;
    }

    public double getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(double totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public double getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(double totalReward) {
        this.totalReward = totalReward;
    }

    public double getLastOneMonth() {
        return lastOneMonth;
    }

    public void setLastOneMonth(double lastOneMonth) {
        this.lastOneMonth = lastOneMonth;
    }

    public double getLastTwoMonth() {
        return lastTwoMonth;
    }

    public void setLastTwoMonth(double lastTwoMonth) {
        this.lastTwoMonth = lastTwoMonth;
    }

    public double getLastThreeMonth() {
        return lastThreeMonth;
    }

    public void setLastThreeMonth(double lastThreeMonth) {
        this.lastThreeMonth = lastThreeMonth;
    }

}
