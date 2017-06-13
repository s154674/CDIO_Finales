package models.dto;

/**
 * Created by Johan on 07-06-2017.
 */
public class ProduktBatchKompDTO
{
    /** produkt batch id i området 1-99999999. Vælges af brugerne */
    int pbId;
    /** raavare batch id i området 1-99999999. Vælges af brugerne */
    int rbId;
    /** tara i kg */
    double tara;
    /** afvejet nettomængde i kg */
    double netto;
    /** Laborant-identifikationsnummer */
    int oprId;

    public ProduktBatchKompDTO() {
    }

    public ProduktBatchKompDTO(int pbId, int rbId, double tara, double netto, int oprId) {
        this.pbId = pbId;
        this.rbId = rbId;
        this.tara = tara;
        this.netto = netto;
        this.oprId = oprId;
    }

    public int getPbId() {
        return pbId;
    }

    public void setPbId(int pbId) {
        this.pbId = pbId;
    }

    public int getRbId() {
        return rbId;
    }

    public void setRbId(int rbId) {
        this.rbId = rbId;
    }

    public double getTara() {
        return tara;
    }

    public void setTara(double tara) {
        this.tara = tara;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public int getOprId() {
        return oprId;
    }

    public void setOprId(int oprId) {
        this.oprId = oprId;
    }

    @Override
    public String toString() {
        return "ProduktBatchKompDTO{" +
                "pbId=" + pbId +
                ", rbId=" + rbId +
                ", tara=" + tara +
                ", netto=" + netto +
                ", bruger_id=" + oprId +
                '}';
    }
}

