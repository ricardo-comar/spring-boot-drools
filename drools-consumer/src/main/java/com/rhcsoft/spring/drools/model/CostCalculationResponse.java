package com.rhcsoft.spring.drools.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CostCalculationResponse {

    @NotBlank
    @Pattern(regexp = "^[{]?[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}[}]?$", message = "Invalid UUID format")
    private String id;

    @NotBlank
    private BigDecimal quantity;

    @NotBlank
    private BigDecimal costValue;

    private String errorMessage;

}
