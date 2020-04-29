/*
 * Powered By sunchengrui
 * Since 2014 - 2015
 */
package com.dap.cloud.upload.domain;

import com.dap.cloud.base.domain.DataEntity;

/**
 * @author sunchengrui
 * @version 1.0
 * @since 1.0
 */
public class Uploadfile extends DataEntity<Uploadfile> {
    private static final long serialVersionUID = 5454155825314635342L;

    //文件原名
    private String viewname;
    //文件存储名
    private String realname;
    //WEB访问路径
    private String url;
    //服务器真实路径
    private String realurl;
    //后缀名
    private String suffix;
    //文件大小
    private String filesize;
    //图片宽度
    private int imgWidth = 0;
    //图片高度
    private int imgHeight = 0;

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public void setViewname(String viewname) {
        this.viewname = viewname;
    }

    public String getViewname() {
        return this.viewname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRealname() {
        return this.realname;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setRealurl(String realurl) {
        this.realurl = realurl;
    }

    public String getRealurl() {
        return this.realurl;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getFilesize() {
        return this.filesize;
    }
}