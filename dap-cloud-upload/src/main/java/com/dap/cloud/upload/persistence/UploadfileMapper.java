/*
 * Powered By sunchengrui
 * Since 2014 - 2015
 */
package com.dap.cloud.upload.persistence;

import com.dap.cloud.base.domain.EasyUIDataGrid;
import com.dap.cloud.upload.domain.Uploadfile;

import java.util.List;

/**
 * @author sunchengrui
 * @version 1.0
 * @since 1.0
 */
public interface UploadfileMapper {

    /**
     * 根据ID查询单条数据
     * @param id
     * @return
     */
    public Uploadfile findUploadfileById(String id);

    /**
     * 查询数据集合
     * @param uploadfile
     * @return
     */
    public List<Uploadfile> findUploadfileList(Uploadfile uploadfile);

    /**
     * 查询数据总数
     * @return
     */
    public EasyUIDataGrid findUploadfileListTotal(Uploadfile uploadfile);

    /**
     * 数据新增
     * @param uploadfile
     */
    public void saveUploadfile(Uploadfile uploadfile);

    /**
     * 数据删除
     * @param listId
     */
    public void deleteUploadfile(List<String> listId);
}