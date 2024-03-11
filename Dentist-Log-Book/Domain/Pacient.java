package Domain;

import java.io.Serializable;
import java.util.Objects;

public class Pacient extends Entity implements Serializable {
    private String name;
    private String forename;
    private int age;

    // Constructor class with given values
    public Pacient(int id_patient, String name, String forename, int age)
    {
        super(id_patient);
        this.name = name;
        this.forename = forename;
        this.age = age;
    }

    //Over writing toString function to return a string about our 'pacient'
    @Override
    public String toString()
    {
        return "Pacient=(" + "id_patient=" + getId() + " || " + "name=" + this.name +
                " || " + "forename=" + this.forename
                + " || " + "age=" + this.age + ')';
    }

    public String getName(){
        return this.name;
    }

    public String getForename(){
        return this.forename;
    }

    public int getAge(){
        return this.age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setForename(String forename) {
        this.forename =forename;
    }

    public void setAge(int age) {
        this.age =  age;
    }

    @Override
    public boolean equals(Object O)
    {
        if (this == O) return true;
        if (O == null || getClass() != O.getClass()) return false;
        Pacient pacient = (Pacient) O;
        return this.getId() == pacient.getId() &&
                this.age == pacient.age &&
                Objects.equals(this.name, pacient.name) &&
                Objects.equals(this.forename, pacient.forename);
    }

}