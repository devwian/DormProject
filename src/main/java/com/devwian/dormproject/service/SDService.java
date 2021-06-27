package com.devwian.dormproject.service;

import com.devwian.dormproject.entity.SD;
import com.devwian.dormproject.mapper.SDMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class SDService {
    @Autowired
    SDMapper sdMapper;

    @Autowired
    DormService dormService;

    /**
     * 得到sd
     *
     * @return {@link List<SD>}
     */
    public List<SD> getSD(){
        try{
            List<SD> list = sdMapper.queryAll();
            if (list!=null){
                //按照宿舍号排序
                list.sort((o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getDormId(),o2.getDormId()));
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加sd
     *
     * @param dormId    宿舍id
     * @param studentId 学号
     * @return 返回结果
     */
    public String addSD(String dormId,String studentId){
        try{
            if (dormService.getDormById(dormId)==null||dormId==null||studentId==null){
                return "null";
            }
            if (sdMapper.getDormPeople(dormId)>=dormService.getDormNum(dormId)){
                return "full";
            }
            SD sd = new SD();
            sd.setDormId(dormId);
            sd.setStudentId(studentId);
            sdMapper.addSD(sd);
            return "true";
        }catch (Exception e){
            //e.printStackTrace();
            return "false";
        }

    }

    /**
     * 删除sd信息
     *
     * @param studentId 学生号
     * @return boolean
     */
    public boolean delSD(String studentId){
        try {
            sdMapper.delSD(studentId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 通过学号获取宿舍id
     *
     * @param studentId 学号
     * @return {@link String}
     */
    public String getDormIdByStudentId(String studentId){
        try{
            return sdMapper.getDormId(studentId);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 编辑sd
     *
     * @param sd sd
     * @return boolean
     */
    public boolean editSD(SD sd){
        return sdMapper.editSD(sd);
    }
}
