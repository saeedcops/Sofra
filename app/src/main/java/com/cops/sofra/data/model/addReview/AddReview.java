
package com.cops.sofra.data.model.addReview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddReview {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private AddReviewData data;

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

    public AddReviewData getData() {
        return data;
    }

    public void setData(AddReviewData data) {
        this.data = data;
    }

}
