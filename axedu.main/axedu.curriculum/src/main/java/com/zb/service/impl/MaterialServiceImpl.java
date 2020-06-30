package com.zb.service.impl;

import com.zb.mapper.MaterialMapper;
import com.zb.pojo.Material;
import com.zb.service.MaterialService;
import com.zb.util.PageUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/12
 * @Version V1.0
 */
@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired(required = false)
    private MaterialMapper materialMapper;

    @Override
    public PageUtil<Material> findMaterialPage(Integer index, Integer size, Integer gradeid, Integer subjectid) {
        PageUtil<Material> page = new PageUtil<>();
        Map<String,Object> param = new HashMap<>();
        param.put("beginPos",(index-1)*size);
        param.put("pageSize",size);
        param.put("subjectid",subjectid);
        param.put("gradeid",gradeid);
        try {
            List<Material> listByMap = materialMapper.getMaterialListByMap(param);
            int count = materialMapper.getMaterialCountByMap(param);
            page.setData(listByMap);
            page.setSize(size);
            page.setCount(count);
            page.setIndex(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
    public Material findMaterialById(Long id){
        try {
            return materialMapper.getMaterialById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insertMaterialById(Material material) {
        try {
            return materialMapper.insertMaterial(material);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
