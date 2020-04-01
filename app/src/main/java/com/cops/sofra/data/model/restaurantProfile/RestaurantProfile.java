
package com.cops.sofra.data.model.restaurantProfile;

import com.cops.sofra.data.model.restaurantLogin.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantProfile {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private  RestaurantProfileData data;

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

    public RestaurantProfileData getData() {
        return data;
    }

    public void setData(RestaurantProfileData data) {
        this.data = data;
    }

}
