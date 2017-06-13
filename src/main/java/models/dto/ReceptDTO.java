package models.dto;

/**
 * Created by emilbonnekristiansen on 06/06/2017.
 */
public class ReceptDTO {
    int receptId;
    String receptNavn;

    public ReceptDTO() {}
    public ReceptDTO(int receptId, String receptNavn) {
        this.receptId = receptId;
        this.receptNavn = receptNavn;
    }

    public int getReceptId() {
        return receptId;
    }
    public void setReceptId(int receptId) {
        this.receptId = receptId;
    }
    public String getReceptNavn() {
        return receptNavn;
    }
    public void setReceptNavn(String receptNavn) {
        this.receptNavn = receptNavn;
    }

    @Override
    public String toString() {
        return "ReceptDTO{" +
                "receptId=" + receptId +
                ", receptNavn='" + receptNavn + '\'' +
                '}';
    }
}

