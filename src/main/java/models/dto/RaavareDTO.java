package models.dto;

/**
 * Created by emilbonnekristiansen on 06/06/2017.
 */
public class RaavareDTO {
    int raavareId;
    String raavareNavn;
    String leverandoer;


    public RaavareDTO(){}
    public RaavareDTO(int raavareId, String raavareNavn, String leverandoer) {
        this.raavareId = raavareId;
        this.raavareNavn = raavareNavn;
        this.leverandoer = leverandoer;
    }

    public int getRaavareId() {
        return raavareId;
    }
    public void setRaavareId(int raavareId) {
        this.raavareId = raavareId;
    }
    public String getRaavareNavn() {
        return raavareNavn;
    }
    public void setRaavareNavn(String raavareNavn) {
        this.raavareNavn = raavareNavn;
    }
    public String getLeverandoer() {
        return leverandoer;
    }
    public void setLeverandoer(String leverandoer) {
        this.leverandoer = leverandoer;
    }

    @Override
    public String toString() {
        return "RaavareDTO{" +
                "raavareId=" + raavareId +
                ", raavareNavn='" + raavareNavn + '\'' +
                ", leverandoer='" + leverandoer + '\'' +
                '}';
    }
}
