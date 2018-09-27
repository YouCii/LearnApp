package com.youcii.mvplearn.model;

/**
 * @author YouCii
 * @date 2016/8/15
 */
public class RecyclerBean {
    private String id = "";
    private String content = "";
    private BeanType beanType = BeanType.TEXT;

    public RecyclerBean(String id, String content, BeanType beanType) {
        this.id = id;
        this.content = content;
        this.beanType = beanType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BeanType getBeanType() {
        return beanType;
    }

    public void setBeanType(BeanType beanType) {
        this.beanType = beanType;
    }

    public static enum BeanType {
        TEXT, IMAGE
    }
}
