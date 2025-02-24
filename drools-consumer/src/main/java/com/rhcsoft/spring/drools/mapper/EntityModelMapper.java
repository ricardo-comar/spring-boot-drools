package com.rhcsoft.spring.drools.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.rhcsoft.spring.drools.entity.CostEntity;
import com.rhcsoft.spring.drools.model.CostModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface EntityModelMapper {

    public CostModel entityToModel(CostEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "calculatedAt", ignore = true)
    public CostEntity modelToEntity(CostModel model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "calculatedAt", ignore = true)
    public void updateEntityFromModel(CostModel model, @MappingTarget CostEntity entity);

}
