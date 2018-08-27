package com.platform.dao;

import com.platform.entity.SysRegionEntity;
import com.platform.vo.TreeVo;
import com.platform.vo.WeixinTreeVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @date 2017-11-04 11:19:31
 */

@Repository
public interface SysRegionDao extends BaseDao<SysRegionEntity> {
    List<TreeVo> queryAllRegion();

    List<WeixinTreeVo> queryAllRegionForWeixin(SysRegionEntity region);

    //查询所有的根节点
    List<TreeVo> queryRegionSimple(SysRegionEntity region);
}
