package com.progresssoft.clustereddatawarehouse.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Currency;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DealDto {


    @NotNull(message = "Deal Id is required")
    private String dealUniqueId;


    @NotNull(message = "From Currency is required")
    @Currency(value = "ISO4217", message = "Invalid currency code")
    private String fromCurrencyISOCode;


    @NotNull(message = "To Currency is required")
    @Currency(value = "ISO4217", message = "Invalid currency code")
    private String toCurrencyISOCode;


    @NotNull(message = "Deal Amount is required")
    @Min(value = 10, message = "Deal Amount can not be less than 0")
    private BigDecimal dealAmount;

}
