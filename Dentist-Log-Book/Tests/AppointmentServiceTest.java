package Tests;

import Domain.Appointment;
import Domain.Pacient;
import Repository.AppointmentRepository;
import Service.AppointmentService;
import Exceptions.DuplicateElemException;
import Service.PacientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    private AppointmentService service;

    @BeforeEach
    void setUp() {
        service = new AppointmentService(new AppointmentRepository());
        try {
            service.add(new Appointment(1, new Pacient(1, "John", "Doe", 10),
                    "10/29/2023", 13,"Cavities") );
            service.add(new Appointment(2, new Pacient(2, "Alice", "Smith", 20),
                    "10/30/2023", 12, "Floss"));
           service.add(new Appointment(3, new Pacient(3, "Bob", "Johnson", 14),
                    "10/27/2023", 18, "Whitening"));
        }catch (DuplicateElemException ignored){}
    }

    @Test
    void add() {
        assert(service.getSize() == 3);
        Appointment app = new Appointment(4, new Pacient(5, "Michael", "Brown",
                37), "9/12/2023",10,"Scaling");
        try {
            service.add(app);
        }catch (DuplicateElemException ignored){}
        assert(service.getSize() == 4);
        assert(Objects.equals(service.getElem(3), app));
    }

    @Test
    void add_exp() {
        assert(service.getSize() == 3);
        Appointment app = new Appointment(2, new Pacient(5, "Michael", "Brown",
                37), "9/12/2023",10,"Scaling");
        try {
            service.add(app);
        }catch (DuplicateElemException exception){
            assert(Objects.equals(exception.getMessage(), "An appointment with this id exists already"));
        }
    }

    @Test
    void update() {
        Appointment app = new Appointment(4, new Pacient(5, "Michael", "Brown",
                37), "9/12/2023",10,"Scaling");
        try{
            service.update(0,app);
        }catch(IndexOutOfBoundsException ignored){}
        assert(service.getSize() == 3);
        assert(service.find_pos_by_id(1)==-1);
        assert(Objects.equals(service.getElem(0), app));
    }

    @Test
    void update_exp() {
        Appointment app = new Appointment(4, new Pacient(5, "Michael", "Brown",
                37), "9/12/2023",10,"Scaling");
        try{
            service.update(7,app);
        }catch(IndexOutOfBoundsException exception){
            assert(Objects.equals(exception.getMessage(), "Position is invalid"));
        }
    }

    @Test
    void delete() {
        try
        {
            service.delete(0);
        }catch(IndexOutOfBoundsException ignored){}
        assert(service.getSize() == 2);
        assert(service.find_pos_by_id(1) == -1);
    }

    @Test
    void delete_exp() {
        try
        {
            service.delete(5);
        }catch(IndexOutOfBoundsException exception){
            assert(Objects.equals(exception.getMessage(), "Invalid position for deleting an appointment"));
        }
    }

    @Test
    void getElem() {
        Appointment app = new Appointment(1, new Pacient(1, "John", "Doe",
                10), "10/29/2023", 13,"Cavities");
        try
        {
            assert(Objects.equals(service.getElem(0), app));
        }catch (IndexOutOfBoundsException ignored){}
    }

    @Test
    void getElem_exp() {
        Appointment app = new Appointment(1, new Pacient(1, "John", "Doe",
                10), "10/29/2023", 13,"Cavities");
        try
        {
            assert(Objects.equals(service.getElem(7), app));
        }catch (IndexOutOfBoundsException exception){
            assert(Objects.equals(exception.getMessage(), "Invalid position for finding an appointment"));
        }
    }

    @Test
    void find_pos_by_id() {
        assert(service.find_pos_by_id(1)==0);
        assert(service.find_pos_by_id(5)==-1);
    }

    @Test
    void deletePacient() {
       service.deletePacient(1);
       assert(service.getSize()==2);
       assert(service.find_pos_by_id(1)==-1);
    }

    @Test
    void updatePacient() {
        Pacient p = new Pacient(1, "Michael", "Brown", 37);
        service.updatePacient(p);
        assert(Objects.equals(service.getElem(0).getPacient(), p));
    }

    @Test
    void getSize() {
        assert(service.getSize() == 3);
    }
}