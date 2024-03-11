package Tests;

import Domain.Appointment;
import Domain.Pacient;
import Exceptions.DuplicateElemException;
import Repository.AppointmentRepository;
import Repository.PacientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentRepositoryTest {

    private AppointmentRepository repo;

    @BeforeEach
    void setUp() {
        repo = new AppointmentRepository();
        try {
            repo.add(new Appointment(1, new Pacient(1, "John", "Doe", 10),
                    "10/29/2023", 13,"Cavities") );
            repo.add(new Appointment(2, new Pacient(2, "Alice", "Smith", 20),
                    "10/30/2023", 12, "Floss"));
            repo.add(new Appointment(3, new Pacient(3, "Bob", "Johnson", 14),
                    "10/27/2023", 18, "Whitening"));
        }catch (DuplicateElemException ignored){}
    }

    @Test
    void add() {
        assert(repo.size() == 3);
        Appointment app = new Appointment(4, new Pacient(5, "Michael", "Brown", 37),
                "9/12/2023",10,"Scaling");
        try{
            repo.add(app);
        }catch (DuplicateElemException ignored){}
        assert(repo.size() == 4);
        assert(repo.getElem(3) == app);
    }

    @Test
    void add_exp()
    {
        try {
            Appointment app =new Appointment(1, new Pacient(1, "John", "Doe",
                    10), "10/29/2023", 13,"Cavities");
            repo.add(app);
        }catch (DuplicateElemException exception)
        {
            assert(!exception.getMessage().isEmpty());}
        assert(repo.size() == 3);
    }

    @Test
    void update() {
        Appointment app = new Appointment(4, new Pacient(5, "Michael", "Brown", 37),
                "9/12/2023",10,"Scaling");
        try
        {
            repo.update(0,app);
        }catch (IndexOutOfBoundsException ignored){}
        assert(repo.size() == 3);
        assert(repo.find_pos_by_id(4) == 0);
        assert(repo.getElem(0) == app);
    }

    @Test
    void update_exp(){
        Appointment app = new Appointment(4, new Pacient(5, "Michael", "Brown",
                37), "9/12/2023",10,"Scaling");
        try
        {
            repo.update(6,app);
        }catch (IndexOutOfBoundsException exception){
            assert(!exception.getMessage().isEmpty());
        }
    }

    @Test
    void delete() {
        try{
            repo.delete(0);
        }catch (IndexOutOfBoundsException ignored){}
        assert(repo.getNr_elems() == 2);
        assert(repo.find_pos_by_id(1) == -1);
    }

    @Test
    void delete_exp(){
        try{
            repo.delete(5);
        }catch (IndexOutOfBoundsException exception){
            assert(!exception.getMessage().isEmpty());
        }
    }

    @Test
    void getElem() {
        Appointment app = new Appointment(1, new Pacient(1, "John", "Doe", 10),
                "10/29/2023", 13,"Cavities");
        try{
            assert(Objects.equals(repo.getElem(0), app));
        }catch (IndexOutOfBoundsException ignored){}
    }

    @Test
    void geElem_exp(){
        Appointment app = new Appointment(1, new Pacient(1, "John", "Doe", 10),
                "10/29/2023", 13,"Cavities");
        try{
            assert(Objects.equals(repo.getElem(5), app));
        }catch (IndexOutOfBoundsException exception){
            assert(!exception.getMessage().isEmpty());
        }
    }

    @Test
    void find_by_id() {
        assert(repo.find_by_id(1));
        assert(!repo.find_by_id(9));
    }

    @Test
    void find_pos_by_id() {
        assert(repo.find_pos_by_id(2) == 1);
        assert(repo.find_pos_by_id(5) == -1);
    }
}