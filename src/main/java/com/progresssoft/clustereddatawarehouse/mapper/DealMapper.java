package com.progresssoft.clustereddatawarehouse.mapper;

import com.progresssoft.clustereddatawarehouse.dto.DealDto;
import com.progresssoft.clustereddatawarehouse.entity.Deal;

import java.util.Currency;

public class DealMapper {

    public static DealDto dealToDealDtoMapper(Deal deal) {

        return DealDto.builder()
                .dealUniqueId(deal.getDealUniqueId())
                .fromCurrencyISOCode(String.valueOf(deal.getFromCurrencyISOCode()))
                .toCurrencyISOCode(String.valueOf(deal.getToCurrencyISOCode()))
                .dealAmount(deal.getDealAmount())
                .build();
    }

    public static Deal dealDtoToDealMapper(DealDto dealDto) {

        return Deal.builder()
                .dealUniqueId(dealDto.getDealUniqueId())
                .fromCurrencyISOCode(Currency.getInstance(dealDto.getFromCurrencyISOCode()))
                .toCurrencyISOCode(Currency.getInstance(dealDto.getToCurrencyISOCode()))
                .dealAmount(dealDto.getDealAmount())
                .build();
    }
}
