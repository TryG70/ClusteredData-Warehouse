package com.progresssoft.clustereddatawarehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progresssoft.clustereddatawarehouse.dto.DealDto;
import com.progresssoft.clustereddatawarehouse.response.APIResponse;
import com.progresssoft.clustereddatawarehouse.service.DealService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(DealController.class)
class DealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealService mockDealService;

    @Test
    void testSaveDeal() throws Exception {

        DealDto dealDto = new DealDto("TFGHU789YH","USD", "EUR", BigDecimal.valueOf(10.0));

        final APIResponse<?> apiResponse = new APIResponse<>("message", LocalDateTime.now(), dealDto);
        doReturn(apiResponse).when(mockDealService).saveFXDeal(
                new DealDto("TFGHU789YH", "USD", "EUR", BigDecimal.valueOf(10.0)));

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/deals/save")
                        .content(mapper.writeValueAsString(dealDto)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("message"))
                .andExpect(jsonPath("$.dto.dealUniqueId").value(dealDto.getDealUniqueId()))
                .andExpect(jsonPath("$.dto.fromCurrencyISOCode").value(dealDto.getFromCurrencyISOCode()))
                .andExpect(jsonPath("$.dto.toCurrencyISOCode").value(dealDto.getToCurrencyISOCode()))
                .andExpect(jsonPath("$.dto.dealAmount").value(dealDto.getDealAmount()))
                .andReturn().getResponse();

    }

    @Test
    void testSaveDeal_InvalidISOCode() throws Exception {

        DealDto dealDto = new DealDto("TFGHU789YH","DDD", "EUR", BigDecimal.valueOf(10.0));

        final APIResponse<?> apiResponse = new APIResponse<>("message", LocalDateTime.now(), dealDto);
        doReturn(apiResponse).when(mockDealService).saveFXDeal(
                new DealDto("TFGHU789YH", "USD", "EUR", BigDecimal.valueOf(10.0)));

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/deals/save")
                        .content(mapper.writeValueAsString(dealDto)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error while saving deal"))
                .andExpect(jsonPath("$.dto").value(new ArrayList<>(List.of("Invalid From Currency ISO Code"))))
                .andReturn().getResponse();
    }

    @Test
    void testSaveDeal_lessThanMinDealAmount() throws Exception {

        DealDto dealDto = new DealDto("TFGHU789YH","USD", "EUR", BigDecimal.valueOf(5.0));

        final APIResponse<?> apiResponse = new APIResponse<>("message", LocalDateTime.now(), dealDto);
        doReturn(apiResponse).when(mockDealService).saveFXDeal(
                new DealDto("TFGHU789YH", "USD", "EUR", BigDecimal.valueOf(10.0)));

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/deals/save")
                        .content(mapper.writeValueAsString(dealDto)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error while saving deal"))
                .andExpect(jsonPath("$.dto").value(new ArrayList<>(List.of("Deal Amount can not be less than 10"))))
                .andReturn().getResponse();
    }

    @Test
    void testSaveDeal_dealUniqueIdIsRequired() throws Exception {

        DealDto dealDto = new DealDto(" ","USD", "EUR", BigDecimal.valueOf(50.0));

        final APIResponse<?> apiResponse = new APIResponse<>("message", LocalDateTime.now(), dealDto);
        doReturn(apiResponse).when(mockDealService).saveFXDeal(
                new DealDto("TFGHU789YH", "USD", "EUR", BigDecimal.valueOf(10.0)));

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/deals/save")
                        .content(mapper.writeValueAsString(dealDto)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error while saving deal"))
                .andExpect(jsonPath("$.dto").value(new ArrayList<>(List.of("Deal Id is required"))))
                .andReturn().getResponse();
    }

    @Test
    void testRetrieveFxDeal() throws Exception {

        DealDto dealDto = new DealDto(" ","USD", "EUR", BigDecimal.valueOf(50.0));

        final APIResponse<?> apiResponse = new APIResponse<>("message", LocalDateTime.now(), dealDto);
        doReturn(apiResponse).when(mockDealService).retrieveFXDeal("fxDealId");

        mockMvc.perform(get("/api/v1/deals/retrieve/{fxDealId}", "fxDealId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.message").value("message"))
                .andExpect(jsonPath("$.dto.dealUniqueId").value(dealDto.getDealUniqueId()))
                .andExpect(jsonPath("$.dto.fromCurrencyISOCode").value(dealDto.getFromCurrencyISOCode()))
                .andExpect(jsonPath("$.dto.toCurrencyISOCode").value(dealDto.getToCurrencyISOCode()))
                .andExpect(jsonPath("$.dto.dealAmount").value(dealDto.getDealAmount()))
                .andReturn().getResponse();
    }

}
