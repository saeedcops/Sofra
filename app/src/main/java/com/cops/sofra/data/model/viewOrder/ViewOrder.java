
package com.cops.sofra.data.model.viewOrder;

import com.cops.sofra.data.model.myOrder.MyOrderData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewOrder {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private MyOrderData data;

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

    public MyOrderData getData() {
        return data;
    }

    public void setData(MyOrderData data) {
        this.data = data;
    }

}
