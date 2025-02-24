package com.rhcsoft.spring.drools.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rhcsoft.spring.drools.service.CostCalculatorService;
import com.rhcsoft.spring.drools.service.CostRecalcService;

@Component
public class CostRecalcJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CostRecalcJob.class);

    @Autowired
    private CostCalculatorService service;

    @Autowired
    private CostRecalcService recalcService;

    @Scheduled(cron = "0 * * * * *")
    public void runRecalculation() {
        LOGGER.info("Running cost recalculation job");

        service.getCostCalculationIds().forEach(id -> {
            LOGGER.info("Recalculating cost for id: " + id);
            recalcService.recalcById(id);
        });

        LOGGER.info("Finished cost recalculation job");
    }

}
