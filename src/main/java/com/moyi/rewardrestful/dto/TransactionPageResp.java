package com.moyi.rewardrestful.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * @author Zhipeng Yin
 * @date 2023-02-10 10:27
 */
@JsonPropertyOrder({ "last", "pageNo", "pageSize", "totalElements", "totalPages", "content" })
public class TransactionPageResp {
    private List<TransactionDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    @JsonProperty("isLast")
    private boolean last;



    public List<TransactionDto> getContent() {
        return content;
    }

    public void setContent(List<TransactionDto> content) {
        this.content = content;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

}
