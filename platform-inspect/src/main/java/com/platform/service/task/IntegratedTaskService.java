package com.platform.service.task;


import com.platform.entity.task.IntegratedTaskEntity;

import java.util.List;
import java.util.Map;

/**
 * 综合任务Service接口
 *
 * @author admin
 *  
 * @date 2018-09-01 10:28:46
 */
public interface IntegratedTaskService {

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体
     */
    IntegratedTaskEntity queryObject(Integer id);

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<IntegratedTaskEntity> queryList(Map<String, Object> map);

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
     * @param integratedTask 实体
     * @return 保存条数
     */
    int save(IntegratedTaskEntity integratedTask);

    /**
     * 根据主键更新实体
     *
     * @param integratedTask 实体
     * @return 更新条数
     */
    int update(IntegratedTaskEntity integratedTask);

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
}