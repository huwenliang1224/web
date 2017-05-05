package com.hwl.maven.web.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * app代码位管理实体类
 * 
 * @author huwenl
 *
 */
public class AppCode {

    private String ownerId;
    private String codeId;

    private List<AppStyle> styleList = new ArrayList<>();

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public List<AppStyle> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<AppStyle> styleList) {
        this.styleList = styleList;
    }

}
