package com.rhcsoft.spring.drools.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhcsoft.spring.drools.entity.CostEntity;

@Repository
public interface CostEntityRepository extends JpaRepository<CostEntity, String> {

}
