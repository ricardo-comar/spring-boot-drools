package com.rhcsoft.spring.drools.mapper;

import org.mapstruct.Mapper;

import com.rhcsoft.spring.drools.entity.CostEntity;
import com.rhcsoft.spring.drools.model.CostModel;

@Mapper(componentModel = "spring")
public interface EntityModelMapper {

    public CostModel entityToModel(CostEntity entity);

    public CostEntity modelToEntity(CostModel model);

}
