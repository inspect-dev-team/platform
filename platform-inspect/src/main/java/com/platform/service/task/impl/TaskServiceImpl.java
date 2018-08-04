package com.platform.service.task.impl;

import com.platform.dao.task.TaskDao;
import com.platform.entity.SysUserEntity;
import com.platform.entity.notice.NoticeEntity;
import com.platform.entity.task.TaskEntity;
import com.platform.entity.task.vo.TaskStatisticsVo;
import com.platform.service.notice.INoticeService;
import com.platform.service.task.TaskGroupMaterialService;
import com.platform.service.task.TaskService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 任务表Service实现类
 *
 * @author admin
 *  
 * @date 2018-07-21 10:58:54
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskDao taskDao;

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private TaskGroupMaterialService taskGroupMaterialService;

    @Override
    public TaskEntity queryObject(Integer id) {
        return taskDao.queryObject(id);
    }

    @Override
    public List<TaskEntity> queryList(Map<String, Object> map) {
//        SysUserEntity sysUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        SysUserEntity sysUser = new SysUserEntity();
        sysUser.setUserId(1L);
        map.put("userId",sysUser.getUserId());
        List<TaskEntity> taskList = taskDao.queryList(map);

        List<TaskStatisticsVo> statistics;
        for (TaskEntity taskEntity : taskList) {
            //taskEntity.setChekArea(sysUser.getRegion());
            taskEntity.setProgressRate(calcProgressRate(taskEntity.getId()));
            statistics = taskGroupMaterialService.queryMaterialStatisticsByTaskId(Long.valueOf(taskEntity.getId()));
            taskEntity.setStatistics(statistics);
        }
        return taskDao.queryList(map);
    }

    /**
     * 计算任务完成进度
     * @return
     */
    private String calcProgressRate(Integer taskId){
        String progressRate = "0/0";
        if (taskId == null) {
            return progressRate;
        }
        TaskStatisticsVo statisticsVo = taskDao.selectTaskProgressRate(taskId);
        Integer finish;
        Integer total;
        if (statisticsVo != null) {
            finish = statisticsVo.getFinish() == null ? 0 : statisticsVo.getFinish();
            total = statisticsVo.getTotal() == null ? 0 : statisticsVo.getTotal();
            progressRate =  finish + "/" + total;
        }
        return progressRate;
    }

    /**
     * 查询任务消息的状态名称
     * @param userId 当前登录用户Id
     * @param taskId 任务Id
     * @return
     */
    private String getNoticeStatus(Long userId,Long taskId){
        NoticeEntity condition = new NoticeEntity();
        condition.setUserId(userId);
        condition.setTaskId(taskId);
        NoticeEntity notice = noticeService.queryObjectByCondition(condition);
        if (notice != null) {
            return notice.getName().equals("")?"":notice.getName();
        }
        return "";
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return taskDao.queryTotal(map);
    }

    @Override
    public List<TaskEntity> queryTaskList(Map<String, Object> map) {
        return taskDao.selectTaskList(map);
    }

    @Override
    public int queryTaskTotal(Map<String, Object> map) {
        return taskDao.selectTaskTotal(map);
    }

    @Override
    public int save(TaskEntity task) {
        return taskDao.save(task);
    }

    @Override
    public int update(TaskEntity task) {
        return taskDao.update(task);
    }

    @Override
    public int delete(Integer id) {
        return taskDao.delete(id);
    }

    @Override
    public int deleteBatch(Integer[]ids) {
        return taskDao.deleteBatch(ids);
    }
}
