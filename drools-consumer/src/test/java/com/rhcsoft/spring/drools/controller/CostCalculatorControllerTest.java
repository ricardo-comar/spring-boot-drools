package com.rhcsoft.spring.drools.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhcsoft.spring.drools.model.CostCalculationRequest;
import com.rhcsoft.spring.drools.model.CostDataRequest;
import com.rhcsoft.spring.drools.model.CostModel;
import com.rhcsoft.spring.drools.service.CostCalculatorService;
import com.rhcsoft.spring.drools.service.CostRecalcService;
import com.rhcsoft.spring.drools.service.exception.BusinessException;

@WebMvcTest(CostCalculatorController.class)
public class CostCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CostCalculatorService costCalculatorService;

    @MockitoBean
    private CostRecalcService costRecalcService;

    @Autowired
    private ObjectMapper objectMapper;

    private CostModel costModel;

    @BeforeEach
    void setUp() {
        costModel = new CostModel();
        costModel.setId("1");
        costModel.setTotalCost(new BigDecimal(100.0));

        doNothing().when(costRecalcService).recalcById(anyString());
    }

    @Test
    void testSaveCostCalculation() throws Exception {
        CostDataRequest request = new CostDataRequest();
        when(costCalculatorService.saveCostCalculation(any(CostDataRequest.class))).thenReturn(costModel);

        mockMvc.perform(put("/cost-calculator/calculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted());
    }

    @Test
    void testSaveCostCalculationServerBusiness() throws Exception {
        CostDataRequest request = new CostDataRequest();
        when(costCalculatorService.saveCostCalculation(any(CostDataRequest.class)))
                .thenThrow(new BusinessException("test error"));

        mockMvc.perform(put("/cost-calculator/calculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void testSaveCostCalculationServerError() throws Exception {
        CostDataRequest request = new CostDataRequest();
        when(costCalculatorService.saveCostCalculation(any(CostDataRequest.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/cost-calculator/calculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    void testCalculateCost() throws Exception {
        CostCalculationRequest request = new CostCalculationRequest();
        request.setCostId("1");
        request.setQuantity(new BigDecimal(10));
        when(costCalculatorService.calculateCost(anyString(), any())).thenReturn(Optional.of(costModel));

        mockMvc.perform(post("/cost-calculator/calculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testCalculateCostBusinessException() throws Exception {
        CostCalculationRequest request = new CostCalculationRequest();
        request.setCostId("1");
        request.setQuantity(new BigDecimal(10));
        when(costCalculatorService.calculateCost(anyString(), any())).thenThrow(new BusinessException("test error"));

        mockMvc.perform(post("/cost-calculator/calculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCalculateCostServerError() throws Exception {
        CostCalculationRequest request = new CostCalculationRequest();
        request.setCostId("1");
        request.setQuantity(new BigDecimal(10));
        when(costCalculatorService.calculateCost(anyString(), any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/cost-calculator/calculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetCostCalculationById() throws Exception {
        when(costCalculatorService.getCostCalculationById(anyString())).thenReturn(Optional.of(costModel));

        mockMvc.perform(get("/cost-calculator/calculator/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCostCalculationByIdNotFound() throws Exception {
        when(costCalculatorService.getCostCalculationById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/cost-calculator/calculator/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCostCalculationById() throws Exception {
        when(costCalculatorService.deleteCostCalculationById(anyString())).thenReturn(Optional.of("1"));

        mockMvc.perform(delete("/cost-calculator/calculator/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCostCalculationByIdNotFound() throws Exception {
        when(costCalculatorService.deleteCostCalculationById(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/cost-calculator/calculator/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
