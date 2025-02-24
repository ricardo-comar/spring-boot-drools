package com.rhcsoft.spring.drools.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CostCalcResult {

    private String costId;

    private BigDecimal costFactor;
}
