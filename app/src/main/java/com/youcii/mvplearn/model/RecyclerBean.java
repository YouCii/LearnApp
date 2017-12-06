package com.youcii.mvplearn.model;

/**
 * @author YouCii
 * @date 2016/8/15
 */
public class RecyclerBean {
    private String id = "";
    private String content = "";

    public RecyclerBean(String content, String id) {
        this.content = content;
        this.id = id;
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
}
