package com.progresssoft.clustereddatawarehouse.serviceImpl;

import com.progresssoft.clustereddatawarehouse.dto.DealDto;
import com.progresssoft.clustereddatawarehouse.entity.Deal;
import com.progresssoft.clustereddatawarehouse.exception.DealAlreadyExistsException;
import com.progresssoft.clustereddatawarehouse.exception.FxDealNotFoundException;
import com.progresssoft.clustereddatawarehouse.exception.SameISOCodeException;
import com.progresssoft.clustereddatawarehouse.mapper.DealMapper;
import com.progresssoft.clustereddatawarehouse.repository.DealRepository;
import com.progresssoft.clustereddatawarehouse.response.APIResponse;
import com.progresssoft.clustereddatawarehouse.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DealServiceImpl implements DealService {


    private final DealRepository dealRepository;

    @Autowired
    public DealServiceImpl(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }


    @Override
    public APIResponse<DealDto> saveFXDeal(DealDto dealDTO) {

        if (!dealDTO.getToCurrencyISOCode().equals(dealDTO.getFromCurrencyISOCode())) {

            Optional<Deal> optionalDeal = dealRepository.findByDealUniqueId(dealDTO.getDealUniqueId());

            if(optionalDeal.isEmpty()) {

                Deal deal = DealMapper.dealDtoToDealMapper(dealDTO);
                Deal savedDeal = dealRepository.save(deal);

                return APIResponse.<DealDto>builder()
                        .message("FX Deal saved successfully")
                        .time(LocalDateTime.now())
                        .dto(DealMapper.dealToDealDtoMapper(savedDeal))
                        .build();

            } else {
                throw new DealAlreadyExistsException("Deal with dealUniqueId: " + dealDTO.getDealUniqueId() + " already exists");
            }

        } else {
            throw new SameISOCodeException("From and To currency codes should not be the same");
        }
    }

    @Override
    public APIResponse<DealDto> retrieveFXDeal(String fxDealId) {
        Deal deal = getDealByDealUniqueId(fxDealId);

        return APIResponse.<DealDto>builder()
                .message("FX Deal retrieved successfully")
                .time(LocalDateTime.now())
                .dto(DealMapper.dealToDealDtoMapper(deal))
                .build();
    }

    public Deal getDealByDealUniqueId(String dealUniqueId) {
        return dealRepository.findByDealUniqueId(dealUniqueId).orElseThrow(() -> new FxDealNotFoundException("Deal with dealUniqueId: " + dealUniqueId + " not found"));
    }
}
