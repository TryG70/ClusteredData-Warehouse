package com.progresssoft.clustereddatawarehouse.controller;

import com.progresssoft.clustereddatawarehouse.dto.DealDto;
import com.progresssoft.clustereddatawarehouse.response.APIResponse;
import com.progresssoft.clustereddatawarehouse.service.DealService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@Scope
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "api/v1/deals")
public class DealController {

    private final DealService dealService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> register(@Valid @RequestBody DealDto dealDto, Errors error) {
        if(error.hasErrors()){
            log.error("Error while saving deal: {}", Objects.requireNonNull(error.getFieldError()).getDefaultMessage());
            return new ResponseEntity<>((APIResponse.<List<String>>builder()
                            .message("Error while saving deal")
                            .time(LocalDateTime.now())
                            .dto(error.getFieldErrors().stream().map(e -> e.getDefaultMessage()).toList())
                    .build()), BAD_REQUEST);
        }

        log.info("DealController.register() dealDto: {}", dealDto);
        return new ResponseEntity<>(dealService.saveFXDeal(dealDto), CREATED);
    }
}
