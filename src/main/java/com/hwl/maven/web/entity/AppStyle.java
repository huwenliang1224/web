package com.hwl.maven.web.entity;

public class AppStyle {

    private int adCount;

    private String adSlotId;

    private String appId;

    private String codeId;

    private int imgCount;

    private Integer platForm;

    private String size;

    private Integer style;

    private boolean online;

    public int getAdCount() {
        return adCount;
    }

    public String getAdSlotId() {
        return adSlotId;
    }

    public String getAppId() {
        return appId;
    }

    public String getCodeId() {
        return codeId;
    }

    public int getImgCount() {
        return imgCount;
    }

    public Integer getPlatForm() {
        return platForm;
    }

    public String getSize() {
        return size;
    }

    public Integer getStyle() {
        return style;
    }

    public void setAdCount(int adCount) {
        this.adCount = adCount;
    }

    public void setAdSlotId(String adSlotId) {
        this.adSlotId = adSlotId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public void setImgCount(int imgCount) {
        this.imgCount = imgCount;
    }

    public void setPlatForm(Integer platForm) {
        this.platForm = platForm;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
