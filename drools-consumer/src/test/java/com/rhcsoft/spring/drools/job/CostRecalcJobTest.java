package com.rhcsoft.spring.drools.job;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.rhcsoft.spring.drools.config.CostSaveRuleConfig;
import com.rhcsoft.spring.drools.model.CostCalcResult;
import com.rhcsoft.spring.drools.model.CostModel;
import com.rhcsoft.spring.drools.service.CostCalculatorService;

@SpringBootTest(classes = CostRecalcJob.class)
@ContextConfiguration(classes = CostSaveRuleConfig.class)
public class CostRecalcJobTest {

    @MockitoBean
    CostCalculatorService service;

    @Autowired
    CostRecalcJob job;

    @Test
    public void testDefault() {

        when(service.getCostCalculationById(anyString())).thenReturn(Optional.of(new CostModel() {
            {
                setId("1234");
                setIsFragile(false);
            }
        }));
        when(service.recalculateCost(any())).thenReturn(Optional.of("1234"));

        job.recalcById("1234");

        verify(service).recalculateCost(new CostCalcResult() {
            {
                setCostId("1234");
                setCostFactor(new BigDecimal(1.0));
            }
        });

    }

    @Test
    public void testFragile() {

        when(service.getCostCalculationById(anyString())).thenReturn(Optional.of(new CostModel() {
            {
                setId("1234");
                setIsFragile(true);
            }
        }));
        when(service.recalculateCost(any())).thenReturn(Optional.of("1234"));

        job.recalcById("1234");

        verify(service).recalculateCost(new CostCalcResult() {
            {
                setCostId("1234");
                setCostFactor(new BigDecimal(1.5));
            }
        });

    }

}
