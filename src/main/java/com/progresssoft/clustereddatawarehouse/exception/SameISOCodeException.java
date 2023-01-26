package com.progresssoft.clustereddatawarehouse.exception;

import lombok.Data;

@Data
public class SameISOCodeException extends RuntimeException {

    public SameISOCodeException(String message) {
        super(message);
    }
}
