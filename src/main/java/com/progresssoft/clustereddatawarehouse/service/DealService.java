package com.progresssoft.clustereddatawarehouse.service;

import com.progresssoft.clustereddatawarehouse.dto.DealDto;
import com.progresssoft.clustereddatawarehouse.response.APIResponse;

public interface DealService {

    APIResponse<?> saveFXDeal(DealDto dealDTO);
    APIResponse<?> retrieveFXDeal(String fxDealId);
}
