package com.rhcsoft.spring.drools.job;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rhcsoft.spring.drools.service.CostCalculatorService;

@Component
public class CostRecalcJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CostRecalcJob.class);

    @Autowired
    private CostCalculatorService service;

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
        Optional<String> idRecalculated = service.recalculateCost(id);
        LOGGER.info("Cost recalculated for id: " + idRecalculated.orElse(null));
    }

}
