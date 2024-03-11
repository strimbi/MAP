package Tests;

import Domain.Pacient;
import Exceptions.DuplicateElemException;
import Repository.PacientFileRepository;
import Repository.PacientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PacientFileRepositoryTest {

    private PacientFileRepository repo;

    @BeforeEach
    void setUp() {
        try {
            repo = new PacientFileRepository("Tests/patient_test.txt");
        }catch (IOException e){e.printStackTrace();}
        assert(repo.getNr_elems() == 5);
    }

    @AfterEach
    void remake_test_file()
    {
        ArrayList<Pacient> pacients = new ArrayList<Pacient>();
        pacients.add(new Pacient(1,"John", "Doe",10));
        pacients.add(new Pacient(2,"Alice", "Smith",20));
        pacients.add(new Pacient(3,"Bob", "Johnson",14));
        pacients.add(new Pacient(4,"Emily", "Williams",50));
        pacients.add(new Pacient(5,"Michael", "Brown",37));

        repo.writePacients(pacients);
    }

    @Test
    void createPacients() {
        assert(repo.getNr_elems() == 5);
    }

    @Test
    void setup_with_exp() {
       try{
           repo = new PacientFileRepository("bldsst.txt");
       } catch (IOException ignored){}
    }

    @Test
    void writePacients() {
    }

    @Test
    void add_file() {
        try
        {
            repo.add_file(new Pacient(6,"Carla", "Aad", 21));
        }catch (DuplicateElemException ignored){}

        assert(repo.getNr_elems() == 6);
        assert(Objects.equals(repo.getElem(5), new Pacient(6, "Carla", "Aad",
                21)));
    }

    @Test
    void add_file_with_exp(){
        try
        {
            repo.add_file(new Pacient(2,"Carla", "Aad", 21));
        }catch (DuplicateElemException e){
            assert (!e.getMessage().isEmpty());
        }
        assert(repo.getNr_elems() == 5);
    }

    @Test
    void delete_file() {
        try
        {
            repo.delete_file(0);
        }catch (IndexOutOfBoundsException e){
            assert(!e.getMessage().isEmpty());
        }
        assert(repo.size() == 4);
        assert(repo.find_pos_by_id(1) == -1);
    }

    @Test
    void update_file() {
        Pacient p = new Pacient(6,"Carl", "Aad", 21);
        try
        {
            repo.update_file(1,p);
        }catch (IndexOutOfBoundsException e){
            assert(!e.getMessage().isEmpty());
        }
        assert(repo.getElem(1) == p);
        assert(repo.find_pos_by_id(6)==1);
    }

}