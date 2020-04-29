package com.dap.cloud.base.domain;

import com.dap.cloud.utils.GeneratorUUID;
import com.dap.cloud.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 数据Entity类
 * @author SeeYoui
 * @version 2014-05-16
 */

@MappedSuperclass
public abstract class DataEntity<T> extends BaseEntity<T> {

    private static final long serialVersionUID = 1L;
    /**
     * 实体编号（唯一标识）
     */
    @Id
    protected String id;
    protected String remarks; // 备注
    protected String createUser; // 创建者
    protected Date createTime; // 创建日期
    protected String updateUser; // 更新者
    protected Date updateTime; // 更新日期
    protected String delFlag; // 删除标记（1：正常；0：删除；2：审核）
    protected String bindId; // 流程相关绑定id
    protected String sort; //排序字段
    protected String order; //排序方式


    /**
     * 插入之前执行方法，需要手动调用
     */
    public void preInsert() {
        if (StringUtils.isBlank(this.id)) {
            this.id = GeneratorUUID.getId();
        }
//		this.createUser = "";
        this.updateTime = new Date();
        this.createTime = this.updateTime;
    }

    /**
     * 更新之前执行方法，需要手动调用
     */
    public void preUpdate() {
//		this.updateUser = "";
        this.updateTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Length(min = 0, max = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Length(min = 1, max = 1)
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @JsonIgnore
    public String getSort() {
        return StringUtils.toUnderScoreCase(sort);
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @JsonIgnore
    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
