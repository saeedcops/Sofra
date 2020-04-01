
package com.cops.sofra.data.model.myOffer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOffer {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private MyOfferPagination data;

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

    public MyOfferPagination getData() {
        return data;
    }

    public void setData(MyOfferPagination data) {
        this.data = data;
    }

}
