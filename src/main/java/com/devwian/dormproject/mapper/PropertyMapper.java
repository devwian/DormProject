package com.devwian.dormproject.mapper;

import com.devwian.dormproject.entity.Property;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PropertyMapper {
    void insert(Property property);

    List<Property> queryProperty();

    boolean delPropertyById(String propertyId);

    boolean updateProperty(Property property);

    List<Property> studentGetProperty(String dormId);

    Property getPropertyById(String propertyId);
}
