package com.progresssoft.clustereddatawarehouse.serviceImpl;

import com.progresssoft.clustereddatawarehouse.dto.DealDto;
import com.progresssoft.clustereddatawarehouse.entity.Deal;
import com.progresssoft.clustereddatawarehouse.exception.DealAlreadyExistsException;
import com.progresssoft.clustereddatawarehouse.exception.FxDealNotFoundException;
import com.progresssoft.clustereddatawarehouse.exception.SameISOCodeException;
import com.progresssoft.clustereddatawarehouse.repository.DealRepository;
import com.progresssoft.clustereddatawarehouse.response.APIResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock
    private DealRepository mockDealRepository;

    private DealServiceImpl dealServiceImplUnderTest;

    private LocalDateTime time;

    @BeforeEach
    void setUp() {
        dealServiceImplUnderTest = new DealServiceImpl(mockDealRepository);
        time = LocalDateTime.of(2023, 1, 21, 4, 22, 10);
    }

    @Test
    void failed_testSaveFXDeal_dealUniqueId_alreadyExists() {

        final DealDto dealDTO = new DealDto("TFGHU789YH", "EUR", "USD",
                new BigDecimal("50.00"));

        final Deal savedDeal = new Deal(1L, "TFGHU789YH", Currency.getInstance("EUR"), Currency.getInstance("USD"), time, new BigDecimal("50.00"));

        when(mockDealRepository.findByDealUniqueId("TFGHU789YH")).thenReturn(Optional.of(savedDeal));

        assertThrows(DealAlreadyExistsException.class, () -> dealServiceImplUnderTest.saveFXDeal(dealDTO));
        verify(mockDealRepository, times(1)).findByDealUniqueId(dealDTO.getDealUniqueId());

    }


    @Test
    void failed_testSaveFXDeal_sameISOCodeException() {

        final DealDto dealDTO = new DealDto("TFGHU789YH", "EUR", "EUR",
                new BigDecimal("50.00"));

        assertThrows(SameISOCodeException.class, () -> dealServiceImplUnderTest.saveFXDeal(dealDTO));
    }

    @Test
    void succesful_testSaveFXDeal() {

        final DealDto dealDTO = new DealDto("TFGHU789YH", "EUR", "USD",
                new BigDecimal("50.00"));

        when(mockDealRepository.findByDealUniqueId("TFGHU789YH")).thenReturn(Optional.empty());

        final Deal deal = Deal.builder()
                .dealUniqueId("TFGHU789YH")
                .fromCurrencyISOCode(Currency.getInstance("EUR"))
                .toCurrencyISOCode(Currency.getInstance("USD"))
                .dealAmount(new BigDecimal("50.00")).build();

        final Deal savedDeal = new Deal(1L, "TFGHU789YH", Currency.getInstance("EUR"), Currency.getInstance("USD"), time, new BigDecimal("50.00"));

        when(mockDealRepository.save(deal)).thenReturn(savedDeal);

        var actual = dealServiceImplUnderTest.saveFXDeal(dealDTO);

        assertThat(actual.getDto().getDealUniqueId()).isEqualTo(dealDTO.getDealUniqueId());
        assertThat(actual.getDto().getFromCurrencyISOCode()).isEqualTo(dealDTO.getFromCurrencyISOCode());
        assertThat(actual.getDto().getToCurrencyISOCode()).isEqualTo(dealDTO.getToCurrencyISOCode());
        assertThat(actual.getDto().getDealAmount()).isEqualTo(dealDTO.getDealAmount());
        verify(mockDealRepository, times(1)).findByDealUniqueId(dealDTO.getDealUniqueId());
        verify(mockDealRepository, times(1)).save(deal);
    }

    @Test
    void testGetDealByDealUniqueId() {

        final Deal expectedResult = new Deal(1L, "TFGHU789YH", Currency.getInstance("EUR"),
                Currency.getInstance("USD"), time, new BigDecimal("50.00"));

        final Optional<Deal> optionalDeal = Optional.of(
                new Deal(1L, "TFGHU789YH", Currency.getInstance("EUR"), Currency.getInstance("USD"),
                        time, new BigDecimal("50.00")));
        when(mockDealRepository.findByDealUniqueId("TFGHU789YH")).thenReturn(optionalDeal);


        final Deal result = dealServiceImplUnderTest.getDealByDealUniqueId("TFGHU789YH");

        assertThat(result).isEqualTo(expectedResult);
        verify(mockDealRepository, times(1)).findByDealUniqueId("TFGHU789YH");
    }

    @Test
    void testGetDealByDealUniqueId_DealRepositoryReturnsAbsent() {

        when(mockDealRepository.findByDealUniqueId("TFGHU789YH")).thenReturn(Optional.empty());

        assertThrows(FxDealNotFoundException.class, () -> dealServiceImplUnderTest.getDealByDealUniqueId("TFGHU789YH"));
        verify(mockDealRepository, times(1)).findByDealUniqueId("TFGHU789YH");
    }

    @Test
    void testRetrieveFXDeal() {

        final APIResponse<DealDto> expectedResult = new APIResponse<>("FX Deal retrieved successfully", time,
                new DealDto("TFGHU789YH", "EUR", "USD", new BigDecimal("50.00")));


        final Optional<Deal> optionalDeal = Optional.of(
                new Deal(1L, "TFGHU789YH", Currency.getInstance("EUR"), Currency.getInstance("USD"),
                        time, new BigDecimal("50.00")));
        when(mockDealRepository.findByDealUniqueId("TFGHU789YH")).thenReturn(optionalDeal);

        final APIResponse<DealDto> result = dealServiceImplUnderTest.retrieveFXDeal("TFGHU789YH");

        assertThat(result.getMessage()).isEqualTo(expectedResult.getMessage());
        assertThat(result.getDto()).isEqualTo(expectedResult.getDto());
        verify(mockDealRepository, times(1)).findByDealUniqueId("TFGHU789YH");
    }

    @Test
    void testRetrieveFXDeal_DealRepositoryReturnsAbsent() {

        when(mockDealRepository.findByDealUniqueId("TFGHU789YH")).thenReturn(Optional.empty());

        assertThrows(FxDealNotFoundException.class, () -> dealServiceImplUnderTest.retrieveFXDeal("TFGHU789YH"));
        verify(mockDealRepository, times(1)).findByDealUniqueId("TFGHU789YH");
    }
}
