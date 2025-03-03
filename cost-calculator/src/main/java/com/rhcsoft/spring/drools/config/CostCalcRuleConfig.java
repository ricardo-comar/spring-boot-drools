package com.rhcsoft.spring.drools.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CostCalcRuleConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CostCalcRuleConfig.class);
    private static final String COST_CALC_RULES_DRL = "rules/cost-calc.drl";

    @Bean
    public KieContainer costSaveRuleContainer() {
        final KieServices KIE_SERVICES = KieServices.Factory.get();
        KieFileSystem kieFileSystem = KIE_SERVICES.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(COST_CALC_RULES_DRL));
        KieBuilder kb = KIE_SERVICES.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        KieContainer kieContainer = KIE_SERVICES.newKieContainer(kieModule.getReleaseId());
        LOGGER.info("Cost save rule container created: ", kieContainer);
        return kieContainer;
    }
}
