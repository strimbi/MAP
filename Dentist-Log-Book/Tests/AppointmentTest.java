package Tests;

import Domain.Appointment;
import Domain.Pacient;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    Pacient p = new Pacient(2, "Strimbi","Daria",21);
    Appointment app = new Appointment(3,p,"20/10/2023",15,"Braces");

    @Test
    void testToString() {
        assert(Objects.equals(app.toString(), "Appointment{id appointment= 3'Pacient{id_patient= 2'" +
                "name= Strimbi'" + "forename= Daria'age= 21}'date=20/10/2023'" + "hour= 15'motive=Braces }"));
    }

    @Test
    void getPacient() {
        assert(app.getPacient() == p);
    }

    @Test
    void getDate() {
        assert(Objects.equals(app.getDate(), "20/10/2023"));
    }

    @Test
    void getHour() {
        assert(app.getHour() == 15);
    }

    @Test
    void getMotive() {
        assert(Objects.equals(app.getMotive(), "Braces"));
    }

    @Test
    void testEquals() {
        Pacient p = new Pacient(2, "Strimbi","Daria",21);
        Appointment app1 = new Appointment(5,p,"12/10/2023",17,"Braces");
        assert(!app1.equals(app));
    }
}