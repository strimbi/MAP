package Tests;

import Domain.Pacient;
import Exceptions.DuplicateElemException;
import Repository.PacientBinaryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PacientBinaryRepositoryTest {

    private PacientBinaryRepository repo;

    @BeforeEach
    void setup(){
        repo = new PacientBinaryRepository("Tests/pacient_test.bin");
        try {
            repo.add(new Pacient(1, "Dar", "Da", 21));
        }catch (DuplicateElemException ignored){}
    }

    @Test
    void setup2(){
        repo = new PacientBinaryRepository("Tent_test.bin");
    }

    @Test
    void add() {
        try {
            repo.add(new Pacient(5, "3r", "D3", 11));
        }catch (DuplicateElemException e){
            assert(!e.getMessage().isEmpty());
        }
    }

    @Test
    void add_exp(){
        try {
            repo.add(new Pacient(1, "Dar", "Da", 21));
        }catch (DuplicateElemException e){
            assert(!e.getMessage().isEmpty());
        }
    }

    @Test
    void add_exp2(){
        repo = new PacientBinaryRepository("fewj.txt");
        try {
            repo.add(new Pacient(1, "Dar", "Da", 21));
        }catch (DuplicateElemException e){
            assert(!e.getMessage().isEmpty());
        }
    }

    @Test
    void saveFile() {
        try {
            repo.saveFile();
        }catch (IOException e){
            assert(!e.getMessage().isEmpty());
        }
    }

    @Test
    void loadFile() {
    }
}