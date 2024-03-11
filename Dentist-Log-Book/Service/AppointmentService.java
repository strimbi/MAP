package Service;

import Domain.Appointment;
import Domain.Pacient;
import Exceptions.DuplicateElemException;
import Repository.AbstractRepository;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class AppointmentService extends Service<Appointment>{

    public AppointmentService(AbstractRepository<Appointment> pacientrepo)
    {
        this.repository = pacientrepo;
    }

    @Override
    public void add(Appointment ap) throws DuplicateElemException{
        try {
            this.repository.add(ap);
        }  catch (DuplicateElemException ex)
        {
            throw new DuplicateElemException(ex.getMessage());
        }
    }

    @Override
    public void update(int poz, Appointment ap) throws IllegalArgumentException{
        try {
            this.repository.update(poz, ap);
        } catch (IndexOutOfBoundsException ex_index){
            throw new IndexOutOfBoundsException(ex_index.getMessage());
        }
    }

    @Override
    public void delete(int poz) throws IllegalArgumentException {
        try {
            this.repository.delete(poz);
        } catch (IndexOutOfBoundsException ex_index){
            throw new IndexOutOfBoundsException(ex_index.getMessage());
        }
    }

    @Override
    public Appointment getElem(int poz) throws IllegalArgumentException{
        try {
            return this.repository.getElem(poz);
        }  catch (IndexOutOfBoundsException ex_index){
            throw new IndexOutOfBoundsException(ex_index.getMessage());
        }
    }

    @Override
    public int find_pos_by_id(int id){
        return this.repository.find_pos_by_id(id);
    }

    public void deletePacient(int id){
        for(int i = 0; i < this.repository.size(); i++)
            if(this.repository.getElem(i).getPacient().getId() == id)
                this.repository.delete(i);
    }


    public void updatePacient(Pacient p) {
        for(int i = 0; i < this.repository.size(); i++)
            if (this.repository.getElem(i).getPacient().getId() == p.getId())
            {
                this.repository.getElem(i).getPacient().setName(p.getName());
                this.repository.getElem(i).getPacient().setForename(p.getForename());
                this.repository.getElem(i).getPacient().setAge(p.getAge());
            }
    }

    public int getSize()
    {
        return this.repository.size();
    }

    public Appointment find_appointment_by_id(int id)
    {
        return this.repository.find_elem(id);
    }

    // 1. Numărul de programări pentru fiecare pacient în parte
    public void nr_app_pacient() {
        Map<Pacient, Long> appperPacient = this.repository.getAll().stream()
                .collect(Collectors.groupingBy(Appointment::getPacient, Collectors.counting()));

        appperPacient.entrySet().stream()
                .sorted(Map.Entry.<Pacient, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    Pacient pacient = entry.getKey();
                    Long nrAppointments = entry.getValue();
                    System.out.println("Pacient: " + pacient.getName() + " " + pacient.getForename() +
                            ", Number appointments: " + nrAppointments);
                });
    }

    // 2. Numărul total de programări pentru fiecare lună a anului
    public void appointments_per_month() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y");

        Map<Month, Long> appPerMonth = this.repository.getAll().stream()
                .collect(Collectors.groupingBy(appointment ->
                                LocalDate.parse(appointment.getDate(), formatter).getMonth(),
                        Collectors.counting()));

        appPerMonth.entrySet().stream()
                .sorted(Map.Entry.<Month, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    Month luna = entry.getKey();
                    Long nrAppointments = entry.getValue();
                    System.out.println("Month: " + luna + ", Number appointments: " + nrAppointments);
                });
    }

    //3.Numărul de zile trecute de la ultima programare a fiecărui pacient
    public void days_passed_appointment() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y");

        this.repository.getAll().stream()
                .collect(Collectors.groupingBy(Appointment::getPacient,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(appointment ->
                                        LocalDate.parse(appointment.getDate(), formatter))),
                                maxAppointment -> {
                                    if (maxAppointment.isPresent()) {
                                        Pacient pacient = maxAppointment.get().getPacient();
                                        LocalDate date_lastapp = LocalDate.parse(maxAppointment.get().getDate(), formatter);
                                        long daysPassed = LocalDate.now().toEpochDay() - date_lastapp.toEpochDay();
                                        System.out.println("Pacient: " + pacient.getName() + " " + pacient.getForename() +
                                                ", Date of last appointment: " + date_lastapp +
                                                ", Days passed: " + daysPassed);
                                    }
                                    return null;
                                })));
    }


    // 4. Cele mai aglomerate luni ale anului
    public void busiest_months() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/y");

        Map<Month, Long> appPerMonth = this.repository.getAll().stream()
                .collect(Collectors.groupingBy(appointment ->
                                LocalDate.parse(appointment.getDate(), formatter).getMonth(),
                        Collectors.counting()));

        appPerMonth.entrySet().stream()
                .sorted(Map.Entry.<Month, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    Month luna = entry.getKey();
                    Long nrApp = entry.getValue();
                    System.out.println("Month: " + luna + ", Number appointments " + nrApp);
                });
    }

    public ArrayList<Appointment> getAll()
    {
        return this.repository.getAll();
    }
}