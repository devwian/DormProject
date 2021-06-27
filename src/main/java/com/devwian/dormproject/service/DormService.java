package com.devwian.dormproject.service;

import com.devwian.dormproject.entity.Dorm;
import com.devwian.dormproject.mapper.DormMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class DormService {
    @Autowired
    DormMapper dormMapper;

    /**
     * 添加宿舍
     *
     * @param dormId    宿舍id
     * @param dormNum   宿舍num
     * @param dormTel   宿舍电话
     * @param className 类名
     */
    public void addDorm(String dormId,int dormNum,String dormTel,String className){
        Dorm dorm = new Dorm();
        dorm.setDormId(dormId);
        dorm.setDormNum(dormNum);
        dorm.setDormTel(dormTel);
        dorm.setClassName(className);
        //System.out.println(dorm);
        dormMapper.insert(dorm);
    }

    /**
     * 通过Id获取宿舍
     *
     * @param dormId 宿舍id
     * @return {@link Dorm}
     */
    Dorm getDormById(String dormId){
        try{
            return dormMapper.selectByPrimaryKey(dormId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 查询所有
     * 获取全部宿舍
     *
     * @return {@link List<Dorm>}
     */
    public List<Dorm> queryAll(){
        return dormMapper.queryAll();
    }

    public void editDorm(Dorm dorm){
        try{
            dormMapper.updateByPrimaryKey(dorm);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 删除宿舍
     *
     * @param dormId 宿舍id
     */
    public void delDorm(String dormId){
       try{
           dormMapper.deleteByPrimaryKey(dormId);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    /**
     *
     */
    public int getDormNum(String dormId){
        try {
            return dormMapper.getDormNum(dormId);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
