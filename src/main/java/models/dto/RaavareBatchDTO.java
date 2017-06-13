package models.dto;

/**
 * Created by Johan on 07-06-2017.
 */
public class RaavareBatchDTO
{
    /** raavare batch id i området 1-99999999. Vælges af brugerne */
    int rbId;
    /** raavare id i området 1-99999999 vælges af brugerne */
    int raavareId;
    /** mængde på lager */
    double maengde;

    public RaavareBatchDTO() {
    }

    public RaavareBatchDTO(int rbId, int raavareId, double maengde) {
        this.rbId = rbId;
        this.raavareId = raavareId;
        this.maengde = maengde;
    }

    public int getRbId() {
        return rbId;
    }

    public void setRbId(int rbId) {
        this.rbId = rbId;
    }

    public int getRaavareId() {
        return raavareId;
    }

    public void setRaavareId(int raavareId) {
        this.raavareId = raavareId;
    }

    public double getMaengde() {
        return maengde;
    }

    public void setMaengde(double maengde) {
        this.maengde = maengde;
    }

    @Override
    public String toString() {
        return "RaavareBatchDTO{" +
                "rbId=" + rbId +
                ", raavareId=" + raavareId +
                ", maengde=" + maengde +
                '}';
    }
}

