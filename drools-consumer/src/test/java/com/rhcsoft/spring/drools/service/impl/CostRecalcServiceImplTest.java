package com.rhcsoft.spring.drools.service.impl;

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

import com.rhcsoft.spring.drools.config.CostCalcRuleConfig;
import com.rhcsoft.spring.drools.entity.CostEntity;
import com.rhcsoft.spring.drools.repository.CostEntityRepository;

@SpringBootTest(classes = CostRecalcServiceImpl.class)
@ContextConfiguration(classes = CostCalcRuleConfig.class)
public class CostRecalcServiceImplTest {

    @Autowired
    private CostRecalcServiceImpl service;

    @MockitoBean
    private CostEntityRepository repo;

    @Test
    public void testDefault() {

        when(repo.findById(anyString())).thenReturn(Optional.of(new CostEntity() {
            {
                setId("1234");
                setIsFragile(false);
            }
        }));
        when(repo.save(any())).thenReturn(null);

        service.recalcById("1234");

        verify(repo).save(new CostEntity() {
            {
                setId("1234");
                setIsFragile(false);
                setCostFactor(new BigDecimal(1.0));
            }
        });

    }

    @Test
    public void testFragile() {

        when(repo.findById(anyString())).thenReturn(Optional.of(new CostEntity() {
            {
                setId("1234");
                setIsFragile(true);
            }
        }));
        when(repo.save(any())).thenReturn(null);

        service.recalcById("1234");

        verify(repo).save(new CostEntity() {
            {
                setId("1234");
                setIsFragile(true);
                setCostFactor(new BigDecimal(1.5));
            }
        });

    }

}
