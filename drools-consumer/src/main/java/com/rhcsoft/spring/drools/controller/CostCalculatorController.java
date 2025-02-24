package com.rhcsoft.spring.drools.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhcsoft.spring.drools.model.CostCalculationRequest;
import com.rhcsoft.spring.drools.model.CostCalculationResponse;
import com.rhcsoft.spring.drools.model.CostDataRequest;
import com.rhcsoft.spring.drools.model.CostDataResponse;
import com.rhcsoft.spring.drools.model.CostModel;
import com.rhcsoft.spring.drools.service.CostCalculatorService;
import com.rhcsoft.spring.drools.service.exception.BusinessException;

@RestController
@RequestMapping("/cost-calculator/calculator")
public class CostCalculatorController {

    @Autowired
    private CostCalculatorService costCalculatorService;

    @PutMapping
    public ResponseEntity<CostDataResponse> saveCostCalculation(@RequestBody CostDataRequest request)
            throws BusinessException {
        CostModel costModel = costCalculatorService.saveCostCalculation(request);
        CostDataResponse response = new CostDataResponse() {
            {
                setId(costModel.getId());
                setCostDetails(costModel);
            }
        };

        return ResponseEntity.accepted().body(response);
    }

    @PostMapping
    public ResponseEntity<CostCalculationResponse> calculateCost(@RequestBody CostCalculationRequest request)
            throws BusinessException {

        Optional<CostModel> costResponse = costCalculatorService.calculateCost(request.getCostId(),
                request.getQuantity());

        return costResponse.map(cost -> {
            CostCalculationResponse response = new CostCalculationResponse() {
                {
                    setId(request.getCostId());
                    setQuantity(request.getQuantity());
                    setCostValue(cost.getTotalCost());
                }
            };
            return ResponseEntity.ok(response);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CostDataResponse> getCostCalculationById(@PathVariable String id) {
        Optional<CostModel> costModel = costCalculatorService.getCostCalculationById(id);
        return costModel.map(cost -> {
            CostDataResponse response = new CostDataResponse() {
                {
                    setId(id);
                    setCostDetails(cost);
                }
            };
            return ResponseEntity.ok(response);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCostCalculationById(@PathVariable String id) {
        Optional<String> deletedId = costCalculatorService.deleteCostCalculationById(id);
        return deletedId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}