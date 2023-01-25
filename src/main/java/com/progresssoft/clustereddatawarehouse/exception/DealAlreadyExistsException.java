package com.progresssoft.clustereddatawarehouse.exception;

import lombok.Data;

@Data
public class DealAlreadyExistsException extends RuntimeException {

    public DealAlreadyExistsException(String message) {
        super(message);
    }
}
