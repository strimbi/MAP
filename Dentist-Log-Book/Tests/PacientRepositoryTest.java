package Tests;

import Domain.Pacient;
import Exceptions.DuplicateElemException;
import Repository.PacientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

class PacientRepositoryTest {

    private PacientRepository repo;

    @BeforeEach
    void setUp() {
        repo = new PacientRepository();
        try {
            repo.add(new Pacient(1, "John", "Doe", 10));
            repo.add(new Pacient(2, "Alice", "Smith", 20));
            repo.add(new Pacient(3, "Bob", "Johnson", 14));
        }catch (DuplicateElemException ignored){}
    }

    @Test
    void add() {
        try{
            repo.add(new Pacient(4, "Emily", "Williams", 50));
        }catch (DuplicateElemException ignored){}
        assert(repo.find_pos_by_id(4) == 3);
        assert(repo.getNr_elems() == 4);
    }

    @Test
    void addexp(){
        try{
            repo.add(new Pacient(1, "Emily", "Williams", 50));
        }catch (DuplicateElemException exception){
            assert Objects.equals(exception.getMessage(), "A pacient with this id exists already");
        }
    }

    @Test
    void update() {
        Pacient a = new Pacient(5, "Michael", "Brown", 37);
        try{
            repo.update(0, a);
        }catch (IndexOutOfBoundsException ignored){}
        assert(repo.getElem(0) == a);
    }

    @Test
    void updateexp() {
        Pacient a = new Pacient(5, "Michael", "Brown", 37);
        try{
            repo.update(6, a);
        }catch (IndexOutOfBoundsException exception){
            assert Objects.equals(exception.getMessage(), "Position is invalid");
        }
        assert(repo.getElem(0) != a);
    }

    @Test
    void delete() {
        try{
            repo.delete(1);
        }catch (IndexOutOfBoundsException ignored){}
        assert(repo.getNr_elems() == 2);
    }

    @Test
    void deleteexp() {
        try{
            repo.delete(-1);
        }catch (IndexOutOfBoundsException exception){
            assert Objects.equals(exception.getMessage(), "Invalid position for deleting a pacient");
        }
        assert(repo.getNr_elems() == 3);
    }

    @Test
    void getElem() {
        Pacient p =new Pacient(1, "John", "Doe", 10);
        try{
            assert(Objects.equals(repo.getElem(0), p));
        }catch (IndexOutOfBoundsException ignored){}
    }

    @Test
    void getElemexp() {
        Pacient p =new Pacient(1, "John", "Doe", 10);
        try{
            assert(Objects.equals(repo.getElem(7), p));
        }catch (IndexOutOfBoundsException exception){
            assert Objects.equals(exception.getMessage(), "Invalid position for finding a pacient");
        }
    }

    @Test
    void find_by_id() {
        assert(repo.find_by_id(1));
        assert (!repo.find_by_id(5));
    }

    @Test
    void find_pos_by_id() {
        assert(repo.find_pos_by_id(1) == 0);
        assert(repo.find_pos_by_id(6) == -1);
    }
}