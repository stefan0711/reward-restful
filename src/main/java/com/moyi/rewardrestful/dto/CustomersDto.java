package com.moyi.rewardrestful.dto;


import javax.validation.constraints.NotBlank;

/**
 * @author Zhipeng Yin
 * @date 2023-02-08 18:15
 */

public class CustomersDto {
    private Long id;
    @NotBlank
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
