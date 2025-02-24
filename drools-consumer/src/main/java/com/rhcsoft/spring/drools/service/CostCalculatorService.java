package com.rhcsoft.spring.drools.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.rhcsoft.spring.drools.model.CostDataRequest;
import com.rhcsoft.spring.drools.model.CostModel;
import com.rhcsoft.spring.drools.service.exception.BusinessException;

public interface CostCalculatorService {

    CostModel saveCostCalculation(CostDataRequest request) throws BusinessException;

    Optional<CostModel> calculateCost(String costId, BigDecimal quantity) throws BusinessException;

    Optional<CostModel> getCostCalculationById(String id);

    Optional<String> deleteCostCalculationById(String id);

    List<String> getCostCalculationIds();

}
