package com.rhcsoft.spring.drools.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhcsoft.spring.drools.entity.CostEntity;
import com.rhcsoft.spring.drools.mapper.EntityModelMapper;
import com.rhcsoft.spring.drools.model.CostDataRequest;
import com.rhcsoft.spring.drools.model.CostModel;
import com.rhcsoft.spring.drools.repository.CostEntityRepository;
import com.rhcsoft.spring.drools.service.CostCalculatorService;
import com.rhcsoft.spring.drools.service.exception.BusinessException;

@Service
public class CostCalculatorServiceImpl implements CostCalculatorService {

    @Autowired
    private CostEntityRepository repo;

    @Autowired
    private EntityModelMapper mapper;

    @Override
    public CostModel saveCostCalculation(CostDataRequest request) throws BusinessException {

        CostEntity entity = new CostEntity();
        if (request.getId() != null) {
            entity = repo.findById(request.getId())
                    .orElseThrow(() -> new BusinessException("Cost entity not found for id: " + request.getId()));
        }
        return mapper.entityToModel(entity);
    }

    @Override
    public Optional<CostModel> calculateCost(String costId, BigDecimal quantity) throws BusinessException {

        return repo.findById(costId).map(entity -> {
            CostModel model = mapper.entityToModel(entity);
            model.setTotalCost(model.getUnitCost().multiply(quantity));
            return Optional.of(model);
        }).orElse(Optional.empty());

    }

    @Override
    public Optional<CostModel> getCostCalculationById(String id) {
        return repo.findById(id).map(mapper::entityToModel);
    }

    @Override
    public Optional<String> deleteCostCalculationById(String id) {
        return repo.findById(id).map(entity -> {
            repo.delete(entity);
            return Optional.of(id);
        }).orElse(Optional.empty());
    }

}
