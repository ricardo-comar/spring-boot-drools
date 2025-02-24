package com.rhcsoft.spring.drools.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CostCalculatorServiceImpl.class);

    @Autowired
    private EntityModelMapper mapper;

    @Autowired
    private CostEntityRepository repo;

    @Override
    public CostModel saveCostCalculation(CostDataRequest request) throws BusinessException {
        LOGGER.info("Saving cost calculation");

        CostEntity entity = new CostEntity() {
            {
                setCostFactor(BigDecimal.ONE);
            }
        };
        if (request.getId() != null) {
            entity = repo.findById(request.getId())
                    .orElseThrow(() -> new BusinessException("Cost entity not found for id: " + request.getId()));
        }

        mapper.updateEntityFromModel(request.getCostDetails(), entity);
        repo.save(entity);

        LOGGER.info("Calculation saved: {}", entity.getId());
        return mapper.entityToModel(entity);
    }

    @Override
    public Optional<CostModel> calculateCost(String costId, BigDecimal quantity) throws BusinessException {
        LOGGER.info("Calculating cost for costId: {} and quantity: {}", costId, quantity);

        return repo.findById(costId).map(entity -> {
            CostModel model = mapper.entityToModel(entity);
            model.setTotalCost(calculateTotalCost(entity, quantity));

            LOGGER.info("Cost calculated: {}", model.getTotalCost());
            return Optional.of(model);
        }).orElse(Optional.empty());

    }

    @Override
    public Optional<CostModel> getCostCalculationById(String id) {
        LOGGER.info("Getting cost calculation by id: {}", id);

        return repo.findById(id).map(mapper::entityToModel);
    }

    @Override
    public Optional<String> deleteCostCalculationById(String id) {
        LOGGER.info("Deleting cost calculation by id: {}", id);

        return repo.findById(id).map(entity -> {
            repo.delete(entity);
            return Optional.of(id);
        }).orElse(Optional.empty());
    }

    @Override
    public List<String> getCostCalculationIds() {
        LOGGER.info("Getting all cost calculation ids");

        return repo.findAllIds();
    }

    protected BigDecimal calculateTotalCost(CostEntity entity, BigDecimal quantity) {
        return entity.getUnitCost().multiply(entity.getCostFactor()).multiply(quantity).add(entity.getFreigCost());
    }
}
