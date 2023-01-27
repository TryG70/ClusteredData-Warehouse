package com.progresssoft.clustereddatawarehouse.serviceImpl;

import com.progresssoft.clustereddatawarehouse.dto.DealDto;
import com.progresssoft.clustereddatawarehouse.entity.Deal;
import com.progresssoft.clustereddatawarehouse.exception.DealAlreadyExistsException;
import com.progresssoft.clustereddatawarehouse.exception.SameISOCodeException;
import com.progresssoft.clustereddatawarehouse.repository.DealRepository;
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
        // Setup
        final DealDto dealDTO = new DealDto("TFGHU789YH", "EUR", "USD",
                new BigDecimal("50.00"));

        final Deal savedDeal = new Deal(1L, "TFGHU789YH", Currency.getInstance("EUR"), Currency.getInstance("USD"), time, new BigDecimal("50.00"));

        when(mockDealRepository.findByDealUniqueId("TFGHU789YH")).thenReturn(Optional.of(savedDeal));


        assertThrows(DealAlreadyExistsException.class, () -> dealServiceImplUnderTest.saveFXDeal(dealDTO));
        verify(mockDealRepository, times(1)).findByDealUniqueId(dealDTO.getDealUniqueId());

    }


    @Test
    void failed_testSaveFXDeal_sameISOCodeException() {
        // Setup
        final DealDto dealDTO = new DealDto("TFGHU789YH", "EUR", "EUR",
                new BigDecimal("50.00"));

        assertThrows(SameISOCodeException.class, () -> dealServiceImplUnderTest.saveFXDeal(dealDTO));


    }

    @Test
    void succesful_testSaveFXDeal() {
        // Setup
        final DealDto dealDTO = new DealDto("TFGHU789YH", "EUR", "USD",
                new BigDecimal("50.00"));

        when(mockDealRepository.findByDealUniqueId("TFGHU789YH")).thenReturn(Optional.empty());

        // Configure DealRepository.save(...).
        final Deal deal = Deal.builder()
                .dealUniqueId("TFGHU789YH")
                .fromCurrencyISOCode(Currency.getInstance("EUR"))
                .toCurrencyISOCode(Currency.getInstance("USD"))
                .dealAmount(new BigDecimal("50.00")).build();

        final Deal savedDeal = new Deal(1L, "TFGHU789YH", Currency.getInstance("EUR"), Currency.getInstance("USD"), time, new BigDecimal("50.00"));

        when(mockDealRepository.save(deal)).thenReturn(savedDeal);

        // Run the test
        var actual = dealServiceImplUnderTest.saveFXDeal(dealDTO);



        // Verify the results
        assertThat(actual.getDto().getDealUniqueId()).isEqualTo(dealDTO.getDealUniqueId());
        assertThat(actual.getDto().getFromCurrencyISOCode()).isEqualTo(dealDTO.getFromCurrencyISOCode());
        assertThat(actual.getDto().getToCurrencyISOCode()).isEqualTo(dealDTO.getToCurrencyISOCode());
        assertThat(actual.getDto().getDealAmount()).isEqualTo(dealDTO.getDealAmount());
        verify(mockDealRepository, times(1)).findByDealUniqueId(dealDTO.getDealUniqueId());
        verify(mockDealRepository, times(1)).save(deal);
    }
}
