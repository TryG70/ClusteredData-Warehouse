package com.progresssoft.clustereddatawarehouse.dto;



import com.progresssoft.clustereddatawarehouse.validator.ValidCurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DealDto {


    @NotNull(message = "Deal Id is required")
    private String dealUniqueId;


    @NotNull(message = "From Currency is required")
    @ValidCurrencyCode(message = "Invalid From Currency ISO Code")
    private String fromCurrencyISOCode;


    @NotNull(message = "To Currency is required")
    @ValidCurrencyCode(message = "Invalid To Currency ISO Code")
    private String toCurrencyISOCode;


    @NotNull(message = "Deal Amount is required")
    @Min(value = 10, message = "Deal Amount can not be less than 0")
    private BigDecimal dealAmount;

}
