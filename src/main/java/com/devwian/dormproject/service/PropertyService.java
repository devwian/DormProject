package com.devwian.dormproject.service;

import com.devwian.dormproject.entity.Property;
import com.devwian.dormproject.mapper.PropertyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class PropertyService {

    @Autowired
    PropertyMapper propertyMapper;

    @Autowired
    SDService sdService;

    /**
     * 插入财产
     *
     * @param property 财产
     */
    public int insertProperty(Property property){
        if (propertyMapper.getPropertyById(property.getPropertyId())!=null){
            System.out.println("exist property "+ property);
            return 9;
        }
        System.out.println("insert "+ property);
        try{
            if (property.getPropertyId().isEmpty()||property.getPropertyName().isEmpty()){
                throw new Exception();
            }
            propertyMapper.insert(property);
            return 0;
        }catch (Exception e){
            return 1;
        }
    }

    public List<Property> studentGetProperty(String studentId){
        return propertyMapper.studentGetProperty(sdService.getDormIdByStudentId(studentId));
    }

    /**
     * 查询属性
     *
     * @return {@link List<Property>}
     */
    public List<Property> queryProperty(){
        //System.out.println("PropertyService queryProperty");
        try{
            return propertyMapper.queryProperty();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 删除财产
     *
     * @param propertyId 属性id
     * @return boolean
     */
    public boolean delProperty(String propertyId){
        return propertyMapper.delPropertyById(propertyId);
    }

    /**
     * 编辑属性
     *
     * @param property 财产
     * @return boolean
     */
    public boolean editProperty(Property property){
        return propertyMapper.updateProperty(property);
    }
}
