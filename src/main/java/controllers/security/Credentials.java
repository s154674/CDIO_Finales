package controllers.security;

import java.io.Serializable;

/**
 * Created by emilbonnekristiansen on 07/06/2017.
 */
public class Credentials implements Serializable {
    private int oprId;
    private String password;

    public int getOprId() {
        return oprId;
    }

    public void setOprId(int oprId) {
        this.oprId = oprId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
