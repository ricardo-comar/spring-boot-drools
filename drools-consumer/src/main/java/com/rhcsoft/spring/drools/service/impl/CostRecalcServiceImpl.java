package com.rhcsoft.spring.drools.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rhcsoft.spring.drools.model.CostCalcResult;
import com.rhcsoft.spring.drools.repository.CostEntityRepository;
import com.rhcsoft.spring.drools.service.CostRecalcService;

@Service
public class CostRecalcServiceImpl implements CostRecalcService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CostRecalcServiceImpl.class);

    @Autowired
    private CostEntityRepository repo;

    @Autowired
    private KieContainer kieContainer;

    @Async
    @Override
    public void recalcById(String id) {

        repo.findById(id).ifPresentOrElse(entity -> {
            LOGGER.info("Cost model found for id: " + id);

            CostCalcResult result = new CostCalcResult();
            result.setCostId(id);
            result.setCostFactor(BigDecimal.ONE);

            KieSession kieSession = kieContainer.newKieSession();
            kieSession.setGlobal("result", result);
            kieSession.insert(entity);
            kieSession.fireAllRules();
            kieSession.dispose();

            entity.setCostFactor(result.getCostFactor());
            entity.setCalculatedAt(LocalDateTime.now());
            repo.save(entity);
            LOGGER.info("Cost recalculated for id: " + id);

        }, () -> {

            LOGGER.error("Cost model not found for id: " + id);
        });

    }

}
