package com.rhcsoft.spring.drools.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    private String errorMessage;
}
