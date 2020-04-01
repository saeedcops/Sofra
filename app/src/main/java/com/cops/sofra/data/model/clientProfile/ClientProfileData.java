
package com.cops.sofra.data.model.clientProfile;

import com.cops.sofra.data.model.restaurantLogin.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientProfileData {

    @SerializedName("user")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
