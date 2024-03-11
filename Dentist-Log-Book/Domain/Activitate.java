package Domain;

import java.io.Serializable;
import java.util.Objects;

public class Activitate extends Entity implements Serializable {
    private String data;
    private int nr_pasi;
    private String descriere;

    private int nr_minute;

    // Constructor class with given values
    public Activitate(int id_activ, String data, String descrire, int nr_minute, int nr_pasi)
    {
        super(id_activ);
        this.data = data;
        this.descriere = descrire;
        this.nr_minute = nr_minute;
        this.nr_pasi = nr_pasi;
    }

    public String toString()
    {
        return "Activitate=(" + "id_activitate=" + getId() + " || " + "data=" + this.data + " || "
                + "pasi total=" + this.nr_pasi  + " || " + "descriere=" + this.descriere + " || " +
                "minute=" + this.nr_minute +')';
    }

    public String getDescriere(){
        return this.descriere;
    }

    public String getData(){
        return this.data;
    }

    public int getNr_pasi(){
        return this.nr_pasi;
    }

    public int getNr_minute(){
        return this.nr_minute;
    }

    public void setData(String name) {
        this.data = name;
    }

    public void setDescriere(String descriere) {
        this.descriere =descriere;
    }

    public void setNr_pasi(int pasi) {
        this.nr_pasi =  pasi;
    }
    public void setNr_minute(int pasi) {
        this.nr_minute=  pasi;
    }


    public boolean equals(Object O)
    {
        if (this == O) return true;
        if (O == null || getClass() != O.getClass()) return false;
        Activitate ac = (Activitate) O;
        return this.getId() == ac.getId() &&
                this.nr_minute == ac.nr_minute &&
                this.nr_pasi == ac.nr_pasi &&
                Objects.equals(this.descriere, ac.descriere) &&
                Objects.equals(this.data, ac.data);
    }

}