package com.devwian.dormproject.mapper;

import com.devwian.dormproject.entity.SD;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * sdmapper
 *
 * @author devwian
 * @date 2021/06/14
 */
@Mapper
@Repository
public interface SDMapper {
    /**
     * 通过ID得到宿舍信息
     *
     * @param dormId 宿舍id
     * @return {@link SD}
     */
    SD getSDByDormId(String dormId);

    /**
     * 编辑sd
     *
     * @param sd sd
     * @return boolean
     */
    boolean editSD(SD sd);

    /**
     * 得到宿舍id
     *
     * @param studentId 学号
     * @return {@link String}
     */
    String getDormId(String studentId);

    /**
     * 查询所有
     *
     * @return {@link List<SD>}
     */
    List<SD> queryAll();

    /**
     * 添加住宿信息
     *
     * @param sd sd
     */
    void addSD(SD sd);

    /**
     * 删除住宿信息
     *
     * @param studentId 学生证
     */
    void delSD(String studentId);

    /**
     * 获取宿舍人数上限
     *
     * @param dormId 宿舍id
     * @return int
     */
    int getDormPeople(String dormId);
}
