
package com.cops.sofra.data.model.newOffer;

import com.cops.sofra.data.model.myOffer.MyOfferData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewOffer {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private MyOfferData data;

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

    public MyOfferData getData() {
        return data;
    }

    public void setData(MyOfferData data) {
        this.data = data;
    }

}
