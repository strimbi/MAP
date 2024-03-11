package Tests;

import Domain.Pacient;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PacientTest {

    Pacient p = new Pacient(2, "Strimbi","Daria",21);

    @Test
    void testToString() {
        assert(Objects.equals(p.toString(), "Pacient{id_patient= 2'name= Strimbi'forename= Daria'age= 21}"));
    }

    @Test
    void getName() {
        assert(Objects.equals(p.getName(), "Strimbi"));
    }

    @Test
    void getForename() {
        assert(Objects.equals(p.getForename(), "Daria"));
    }

    @Test
    void getAge() {
        assert(p.getAge() == 21);
    }

    @Test
    void setName() {
        p.setName("Maria");
        assert(Objects.equals(p.getName(), "Maria"));
    }

    @Test
    void setForename() {
        p.setForename("Corina");
        assert(Objects.equals(p.getForename(), "Corina"));
    }

    @Test
    void setAge() {
        p.setAge(25);
        assert(p.getAge() == 25);
    }

    @Test
    void testEquals() {
        Pacient ap = new Pacient(2, "Strimbi","Daria",21);
        assert(ap.equals(p));
    }
}