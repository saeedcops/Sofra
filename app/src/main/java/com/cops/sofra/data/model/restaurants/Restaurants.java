
package com.cops.sofra.data.model.restaurants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurants {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RestaurantPagination data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RestaurantPagination getData() {
        return data;
    }

    public void setData(RestaurantPagination data) {
        this.data = data;
    }

}
