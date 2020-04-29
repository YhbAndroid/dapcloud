package com.dap.cloud.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 数据Entity类
 * @author SeeYoui
 * @version 2014-05-16
 */
@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页号
     */
    protected int page;
    /**
     * 每页行数
     */
    protected int rows;
    protected int limit;
    /**
     * 当前行号
     */
    protected int row;

    @JsonIgnore
    public int getPage() {
        if ((this.page - 1) * this.limit < 0) {
            return 0;
        }
        return (this.page - 1) * this.limit;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @JsonIgnore
    public int getRows() {
        if (this.rows == 0) {
            return 20;
        }
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @JsonIgnore
    public int getLimit() {
        if (this.limit == 0) {
            return 30;
        }
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @JsonIgnore
    public int getRow() {
        if ((this.page - 1) * this.rows < 0) {
            return 0;
        }
        return (this.page - 1) * this.rows;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    /**
     * 删除标记（1：正常；0：删除；2：审核；）
     */
    public static final String DEL_FLAG_NORMAL = "1";
    public static final String DEL_FLAG_DELETE = "0";
    public static final String DEL_FLAG_AUDIT = "2";

}