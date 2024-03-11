package Tests;

import Domain.Appointment;
import Domain.Pacient;
import Exceptions.DuplicateElemException;
import Repository.AppointmentFileRepository;
import Repository.PacientFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentFileRepositoryTest {

    private AppointmentFileRepository repo;

    @BeforeEach
    void setUp() {
        try {
            repo = new AppointmentFileRepository("Tests/appointment_test.txt");
        }catch (IOException e){e.printStackTrace();}
        assert(repo.getNr_elems() == 3);
    }

    @AfterEach
    void remake_test_file()
    {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        appointments.add(new Appointment(1, new Pacient(1,"Jhon","Doe",10),
                "10/27/2023", 9, "Braces"));
        appointments.add(new Appointment(2, new Pacient(2,"Alice","Smith",20),
                "12/28/2023", 14, "Cavities"));
        appointments.add(new Appointment(3, new Pacient(1,"Jhon","Doe",10),
                "3/13/2023", 16, "Whitening"));
        repo.writeAppointment(appointments);
    }

    @Test
    void createAppointment() {
        assert(repo.getNr_elems() == 3);
    }

    @Test
    void setup_with_exp() {
        try{
            repo = new AppointmentFileRepository("bfst.txt");
        } catch (IOException ignored){}
    }

    @Test
    void writeAppointment() {
    }

    @Test
    void add_file() {
        try
        {
            repo.add_file(new Appointment(4,new Pacient(3,"Calra","Ada",
                    32), "12/28/2023", 11, "Bild"));
        }catch (DuplicateElemException ignored){}
        assert(repo.getNr_elems() == 4);
        assert(Objects.equals(repo.getElem(3), new Appointment(4,new Pacient(3,
                "Calra","Ada", 32), "12/28/2023", 11, "Bild")));
    }

    @Test
    void add_file_with_exp(){
        try
        {
            repo.add_file(new Appointment(1, new Pacient(1,"Jhon","Doe",
                    10), "10/27/2023", 9, "Braces"));
        }catch (DuplicateElemException e){
            assert (!e.getMessage().isEmpty());
        }
    }

    @Test
    void delete_file() {
        try
        {
            repo.delete_file(0);
        }catch (IndexOutOfBoundsException e){
            assert(!e.getMessage().isEmpty());
        }
        assert(repo.size() == 2);
        assert(repo.find_pos_by_id(1) == -1);
    }

    @Test
    void update_file() {
        Appointment p = new Appointment(3,new Pacient(3,"Calra","Ada",
                32), "12/28/2023", 11, "Bild");
        try
        {
            repo.update_file(1,p);
        }catch (IndexOutOfBoundsException e){
            assert(!e.getMessage().isEmpty());
        }
        assert(repo.getElem(1) == p);
        assert(repo.find_pos_by_id(3)==1);
    }
}