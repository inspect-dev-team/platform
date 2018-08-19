package com.platform.service.stat;


import com.platform.entity.SysRegionEntity;
import com.platform.entity.stat.StaTaskDayEntity;

import java.util.List;
import java.util.Map;

/**
 * 按天统计任务执行情况Service接口
 *
 * @author admin
 *  
 * @date 2018-08-06 19:30:48
 */
public interface StaTaskDayService {

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
    StaTaskDayEntity queryObject(Integer id);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<StaTaskDayEntity> queryList(Map<String, Object> map);

    /**
     * 分页统计总数
     *
     * @param map 参数
     * @return 总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存实体
     *
     * @param staTaskDay 实体
     * @return 保存条数
     */
    int save(StaTaskDayEntity staTaskDay);

    /**
     * 根据主键更新实体
     *
     * @param staTaskDay 实体
     * @return 更新条数
     */
    int update(StaTaskDayEntity staTaskDay);

    /**
     * 根据主键删除
     *
     * @param id
     * @return 删除条数
     */
    int delete(Integer id);

    /**
     * 根据主键批量删除
     *
     * @param ids
     * @return 删除条数
     */
    int deleteBatch(Integer[] ids);



    List<StaTaskDayEntity> statTask(Map<String, Object> map , List<SysRegionEntity> districtList);
}
