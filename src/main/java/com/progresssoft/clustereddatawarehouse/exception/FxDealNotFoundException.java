package com.progresssoft.clustereddatawarehouse.exception;

import lombok.Data;

@Data
public class FxDealNotFoundException extends RuntimeException {

    public FxDealNotFoundException(String message) {
        super(message);
    }
}
