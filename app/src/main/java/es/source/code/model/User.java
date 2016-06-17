package es.source.code.model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/6/15.
 */
public class User implements Serializable {
    private String userName;
    private String password;
    private Boolean oldUser;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getOldUser() {
        return oldUser;
    }

    public void setOldUser(Boolean oldUser) {
        this.oldUser = oldUser;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
