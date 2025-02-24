package com.rhcsoft.spring.drools.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rhcsoft.spring.drools.entity.CostEntity;

@Repository
public interface CostEntityRepository extends JpaRepository<CostEntity, String> {

    @Query("select p.id from #{#entityName} p")
    List<String> findAllIds();

}
