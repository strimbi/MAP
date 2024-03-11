package Tests;

import Domain.Pacient;
import Service.PacientService;
import Exceptions.DuplicateElemException;
import Repository.PacientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PacientServiceTest {
    private PacientService service;

    @BeforeEach
    void setUp() {
        service = new PacientService(new PacientRepository());
        try {
            service.add(new Pacient(1, "John", "Doe", 10));
            service.add(new Pacient(2, "Alice", "Smith", 20));
            service.add(new Pacient(3, "Bob", "Johnson", 14));
        }catch (DuplicateElemException ignored){}
    }
    @Test
    void add() {
        Pacient p = new Pacient(5, "Michael", "Brown", 37);
        try
        {
            service.add(p);
        }catch (DuplicateElemException ignored){}
        assert(service.getSize() == 4);
        assert(service.getElem(3) == p);
    }

    @Test
    void addexp() {
        Pacient p = new Pacient(2, "Michael", "Brown", 37);
        try
        {
            service.add(p);
        }catch (DuplicateElemException exception){
            assert Objects.equals(exception.getMessage(), "A pacient with this id exists already");
        }
        assert(service.getSize() == 3);
    }

    @Test
    void update() {
        Pacient p = new Pacient(1, "Michael", "Brown", 37);
        try
        {
            service.update(0,p);
        }catch (IndexOutOfBoundsException ignored){}
        assert(service.find_pos_by_id(1) == 0);
        assert(service.getSize() == 3);
        assert(Objects.equals(service.getElem(0), p));
    }

    @Test
    void updateexp() {
        Pacient p = new Pacient(1, "Michael", "Brown", 37);
        try
        {
            service.update(7,p);
        }catch (IndexOutOfBoundsException exception){
            assert Objects.equals(exception.getMessage(), "Position is invalid");
        }
    }

    @Test
    void delete() {
        try{
            service.delete(0);
        }catch(IndexOutOfBoundsException ignored){}
        assert(service.getSize() == 2);
        assert(service.find_pos_by_id(1)==-1);
    }

    @Test
    void deleteexp() {
        try{
            service.delete(-1);
        }catch(IndexOutOfBoundsException exception){
            assert Objects.equals(exception.getMessage(), "Invalid position for deleting a pacient");
        }

    }

    @Test
    void getElem() {
        try
        {
            Pacient p = new Pacient(1, "John", "Doe", 10);
            assert(Objects.equals(service.getElem(0), p));
        }catch (IndexOutOfBoundsException ignored){}
    }

    @Test
    void getElemexp() {
        try
        {
            Pacient p = new Pacient(1, "John", "Doe", 10);
            assert(Objects.equals(service.getElem(7), p));
        }catch (IndexOutOfBoundsException exception){
            assert Objects.equals(exception.getMessage(), "Invalid position for finding a pacient");
        }
    }

    @Test
    void find_pos_by_id() {
        assert(service.find_pos_by_id(1)==0);
        assert(service.find_pos_by_id(2)==1);
        assert(service.find_pos_by_id(4)==-1);
    }

    @Test
    void getSize() {
        assert(service.getSize() == 3);
    }
}