package com.zb.service;

import com.zb.pojo.Ordertemp;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderTempService  {
    public int getTempCount(Integer id);

    public void CurriculumToRedis();

    public Ordertemp findOnlyOne(Integer id, Integer uid);

    /**
     * 修改临时表状态信息
     * @param ordertemp
     * @return
     */
    public Integer updateOrdertemp(Ordertemp ordertemp);

    /**
     * 查询课程剩余名额
     * @param id
     * @return
     */
    public int checkStore(Integer id);

    /**
     * 添加临时记录
     * @param id
     * @param uid
     * @return
     */
    public int lockRoomStock(Integer id, Integer uid);

    /**
     * 轮循redis中数据，检查抢购有没有成功
     * @param subjectId
     * @return
     */
    public String qgWhile(Integer subjectId);
}
