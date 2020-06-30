package com.zb.service;

import com.zb.pojo.Material;
import com.zb.util.PageUtil;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/12
 * @Version V1.0
 */
public interface MaterialService {
    //按照年级id和科目id分页查询
    public PageUtil<Material> findMaterialPage(Integer index,Integer size,Integer gradeId,Integer subjectId);
    //按照id维一查询
    public Material findMaterialById(Long id);
    //上传资料（添加）
    public int insertMaterialById(Material material);
}
