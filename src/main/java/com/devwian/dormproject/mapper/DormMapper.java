package com.devwian.dormproject.mapper;

import com.devwian.dormproject.entity.Dorm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DormMapper {
    int deleteByPrimaryKey(String dormId);

    int insert(Dorm record);

    int insertSelective(Dorm record);

    Dorm selectByPrimaryKey(String dormId);

    int getDormNum(String dormId);

    int updateByPrimaryKeySelective(Dorm record);

    int updateByPrimaryKey(Dorm record);

    List<Dorm> queryAll();
}