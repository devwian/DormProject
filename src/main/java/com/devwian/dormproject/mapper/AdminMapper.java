package com.devwian.dormproject.mapper;

import com.devwian.dormproject.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminMapper {
    /**
     * 获取管理员信息
     *
     * @param adminId
     * @return
     */
    Admin getAdminById(String adminId);

    /**
     * 注册
     *
     * @param admin
     */
    void insertInto(Admin admin);
}