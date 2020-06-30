package com.zb.pojo;

import java.io.Serializable;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/15
 * @Version V1.0
 */
public class Advert implements Serializable {
    private Integer id;
    private Integer category_id;
    private Integer status;
    private Integer sort_order;
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort_order() {
        return sort_order;
    }

    public void setSort_order(Integer sort_order) {
        this.sort_order = sort_order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
