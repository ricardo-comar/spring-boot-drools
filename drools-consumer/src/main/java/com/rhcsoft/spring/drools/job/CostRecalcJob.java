package com.rhcsoft.spring.drools.job;

import java.math.BigDecimal;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rhcsoft.spring.drools.model.CostCalcResult;
import com.rhcsoft.spring.drools.service.CostCalculatorService;

@Component
public class CostRecalcJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CostRecalcJob.class);

    @Autowired
    private CostCalculatorService service;

    @Autowired
    private KieContainer kieContainer;

    @Scheduled(cron = "0 * * * * *")
    public void runRecalculation() {
        LOGGER.info("Running cost recalculation job");

        service.getCostCalculationIds().forEach(id -> {
            LOGGER.info("Recalculating cost for id: " + id);
            recalcById(id);
        });

        LOGGER.info("Finished cost recalculation job");
    }

    @Async
    public void recalcById(String id) {

        service.getCostCalculationById(id).ifPresentOrElse(model -> {
            LOGGER.info("Cost model found for id: " + id);

            CostCalcResult result = new CostCalcResult();
            result.setCostId(id);
            result.setCostFactor(BigDecimal.ONE);

            KieSession kieSession = kieContainer.newKieSession();
            kieSession.setGlobal("result", result);
            kieSession.insert(model);
            kieSession.fireAllRules();
            kieSession.dispose();

            service.recalculateCost(result);
            LOGGER.info("Cost recalculated for id: " + id);

        }, () -> {
            LOGGER.error("Cost model not found for id: " + id);
        });

    }

}
