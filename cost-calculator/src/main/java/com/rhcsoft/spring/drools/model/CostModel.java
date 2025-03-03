package com.rhcsoft.spring.drools.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CostModel {

    @NotBlank
    @Pattern(regexp = "^[{]?[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}[}]?$", message = "Invalid UUID format")
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private BigDecimal unitCost;

    @NotBlank
    private BigDecimal freightCost;

    private Boolean isFragile;

    transient BigDecimal totalCost;

    private String description;

    private String comments;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime calculatedAt;

}
