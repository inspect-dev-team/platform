package com.platform.entity;

import com.platform.validator.group.AddGroup;
import com.platform.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 * @author admin
 *
 * @date 2016年9月18日 上午9:28:55
 */
public class SysUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 用户名账户 */
    @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /*真实名字*/
    private String realname;

    /** 密码 */
    private transient String password;

    /** 邮箱 */
//    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
//    @Email(message = "邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    /** 手机号 */
    private String mobile;

    /** 状态  0：禁用   1：正常 */
    private Integer status;

    private Integer roleId;

    /** 角色ID列表 */
    private List<Long> roleIdList;

    /** 创建者ID */
    private Long createUserId;

    /** 创建时间 */
    private Date createTime;

    private Long deptId;

    /** 部门名称 */
    private String deptName;


    /************************* 巡检自定义 字段  begin *********************/



    /** 身份 */
    private Integer identify;

    /** 所属区域Id */
    private Integer regionId;

    /** 所属区域 */
    private String region;

    /** 上级 */
    private String superior;

    private String superiorStr;

    /** 安全证url */
    private String certificateUrl;

    /** 最后修改时间 */
    private Date updateTime;

    /** 最后修改人 */
    private Long updateUserId;

    /** 用户所属企业 */
    private Integer enterpriseId;

    /**
     * 设置：
     *
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取：
     *
     * @return Long
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置：用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取：用户名
     *
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置：密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取：密码
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置：邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取：邮箱
     *
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置：手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取：手机号
     *
     * @return String
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置：状态  0：禁用   1：正常
     *
     * @param status 状态  0：禁用   1：正常
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：状态  0：禁用   1：正常
     *
     * @return Integer
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     *
     * @return Date
     */
    public Date getCreateTime() {
        return createTime;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getIdentify() {
        return identify;
    }

    public void setIdentify(Integer identify) {
        this.identify = identify;
    }

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
    }

    public String getCertificateUrl() {
        return certificateUrl;
    }

    public void setCertificateUrl(String certificateUrl) {
        this.certificateUrl = certificateUrl;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getSuperiorStr() {
        return superiorStr;
    }

    public void setSuperiorStr(String superiorStr) {
        this.superiorStr = superiorStr;
    }
}
