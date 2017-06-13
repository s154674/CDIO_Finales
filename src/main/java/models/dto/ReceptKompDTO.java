package models.dto;

/**
 * Created by Johan on 07-06-2017.
 */
public class ReceptKompDTO
{
    /** recept id i området 1-99999999 */
    int receptId;
    /** raavare id i området 1-99999999 vælges af brugerne */
    int raavareId;
    /** nominel nettomængde i området 0,05 - 20,0 kg */
    double nomNetto;
    /** tolerance i området 0,1 - 10,0 % */
    double tolerance;

    public ReceptKompDTO() {
    }

    public ReceptKompDTO(int receptId, int raavareId, double nomNetto, double tolerance) {
        this.receptId = receptId;
        this.raavareId = raavareId;
        this.nomNetto = nomNetto;
        this.tolerance = tolerance;
    }

    public int getReceptId() {
        return receptId;
    }

    public void setReceptId(int receptId) {
        this.receptId = receptId;
    }

    public int getRaavareId() {
        return raavareId;
    }

    public void setRaavareId(int raavareId) {
        this.raavareId = raavareId;
    }

    public double getNomNetto() {
        return nomNetto;
    }

    public void setNomNetto(double nomNetto) {
        this.nomNetto = nomNetto;
    }

    public double getTolerance() {
        return tolerance;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    @Override
    public String toString() {
        return "ReceptKompDTO{" +
                "receptId=" + receptId +
                ", raavareId=" + raavareId +
                ", nomNetto=" + nomNetto +
                ", tolerance=" + tolerance +
                '}';
    }
}
