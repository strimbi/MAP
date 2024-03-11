package Tests;

import Domain.Appointment;
import Domain.Pacient;
import Exceptions.DuplicateElemException;
import Repository.AppointmentBinaryRepository;
import Repository.AppointmentFileRepository;
import Repository.PacientBinaryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentBinaryRepositoryTest {

    private AppointmentBinaryRepository repo;

    @BeforeEach
    void setUp() {
        repo = new AppointmentBinaryRepository("Tests/appointments_test.bin");
        try {
            repo.add(new Appointment(1, new Pacient(1, "pp", "asad", 61),
                    "12/02/2023",11,"mat"));
        }catch (DuplicateElemException ignored){}
    }

    @Test
    void add() {
        try {
            repo.add(new Appointment(3, new Pacient(3, "Dar", "Da", 21),
                    "10/12/2023", 15,"Carie"));
        }catch (DuplicateElemException e){
            assert(!e.getMessage().isEmpty());
        }
    }

    @Test
    void setup2(){
        repo = new AppointmentBinaryRepository("Tda_test.bin");
    }

    @Test
    void add_exp() {
        try {
            repo.add(new Appointment(1, new Pacient(1, "pp", "asad", 61),
                    "12/02/2023",11,"mat"));
        }catch (DuplicateElemException e){
            assert(!e.getMessage().isEmpty());
        }
    }

    @Test
    void add_exp2(){
        repo = new AppointmentBinaryRepository("fwej.txt");
        try {
            repo.add(new Appointment(1, new Pacient(1, "pp", "asad", 61),
                    "12/02/2023",11,"mat"));
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