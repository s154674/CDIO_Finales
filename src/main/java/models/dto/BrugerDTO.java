package models.dto;

import java.util.List;

/**
 * Created by emilbonnekristiansen on 06/06/2017.
 */
public class BrugerDTO
{
    int oprId;
    String oprNavn;
    String ini;
    String cpr;
    String password;
    List<String> roller;

    public BrugerDTO() {}
    public BrugerDTO(int oprId, String oprNavn, String ini, String cpr, String password, List<String> roller) {
        this.oprId = oprId;
        this.oprNavn = oprNavn;
        this.ini = ini;
        this.cpr = cpr;
        this.password = password;
        this.roller = roller;
    }

    public int getOprId() {
        return oprId;
    }
    public void setOprId(int oprId) {
        this.oprId = oprId;
    }
    public String getOprNavn() {
        return oprNavn;
    }
    public void setOprNavn(String oprNavn) {
        this.oprNavn = oprNavn;
    }
    public String getIni() {
        return ini;
    }
    public void setIni(String ini) {
        this.ini = ini;
    }
    public String getCpr() {
        return cpr;
    }
    public void setCpr(String cpr) {
        this.cpr = cpr;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<String> getRoller() {
        return roller;
    }
    public void setRoller(List<String> roller) {
        this.roller = roller;
    }

    @Override
    public String toString() {
        return "BrugerDTO{" +
                "oprId=" + oprId +
                ", oprNavn='" + oprNavn + '\'' +
                ", ini='" + ini + '\'' +
                ", cpr='" + cpr + '\'' +
                ", password='" + password + '\'' +
                ", roller=" + roller +
                '}';
    }
}
