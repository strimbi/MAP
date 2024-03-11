package Interface;

import Domain.*;
import Exceptions.*;
import Repository.*;
import Service.*;

import java.util.Scanner;
import java.util.Objects;

public class UI{
    private PacientService pacientService;
    private AppointmentService appointmentService;

    public UI(PacientService pacientService, AppointmentService appointmentService){
        this.appointmentService = appointmentService;
        this.pacientService = pacientService;
    }

    public void add_appointment(int id_app, int id_pacient, String data, int hour, String motive){
        try {
            Pacient pacient = this.pacientService.getElem(id_pacient-1);
            if (pacient.getId() != -1) {
                //LocalDate date = LocalDate.of(0,0,0);
                Appointment p = new Appointment(id_app, pacient, data, hour, motive);
                this.appointmentService.add(p);
            }
        } catch (DuplicateElemException exp){
            System.out.println(exp.getMessage());
        }
    }

    public void add_pacient(int id, String name, String forename, int age){
        try {
            Pacient p = new Pacient(id, name, forename, age);
            this.pacientService.add(p);
        } catch (DuplicateElemException | IndexOutOfBoundsException exp){
            System.out.println(exp.getMessage());
        }
    }

    public Pacient find_pacient_by_id(int id){
        try {
            return this.pacientService.find_pacient_by_id(id);
        } catch (IndexOutOfBoundsException exp){
            System.out.println(exp.getMessage());
        }
        return null;
    }

    public Appointment find_app_by_id(int id){
        try {
            return this.appointmentService.find_appointment_by_id(id);
        } catch (IndexOutOfBoundsException | NullPointerException exp){
            System.out.println(exp.getMessage());
        }
        return null;
    }

    public void update_patient(int id, String name, String forename, int age){
        try {
            int poz = this.pacientService.find_pos_by_id(id);
            Pacient p = new Pacient(id, name, forename, age);
            this.pacientService.update(poz, p);
            this.appointmentService.updatePacient(p);
        } catch (IndexOutOfBoundsException exp){
            System.out.println(exp.getMessage());
        }
    }

    public void update_appointment(int id, int pacient_id, String date, int hour, String motive){
        try {
            int poz = this.pacientService.find_pos_by_id(pacient_id);
            Pacient pacient = this.pacientService.getElem(poz);
            if (pacient.getId() != -1)
            {
                int poz_app = this.appointmentService.find_pos_by_id(id);
                Appointment p = new Appointment(id, pacient, date, hour, motive);
                this.appointmentService.update(poz_app, p);
            }
        } catch (IndexOutOfBoundsException exp){
            System.out.println(exp.getMessage());
        }
    }

    public void delete_patient(int id){
        try {
            int poz = this.pacientService.find_pos_by_id(id);
            this.pacientService.delete(poz);
            this.appointmentService.deletePacient(id);
        } catch (IndexOutOfBoundsException exp){
            System.out.println(exp.getMessage());
        }
    }

    public void delete_appointment(int id){
        try {
            int poz = this.appointmentService.find_pos_by_id(id);
            this.appointmentService.delete(poz);
        } catch (IndexOutOfBoundsException exp){
            System.out.println(exp.getMessage());
        }
    }

    public void show_pacients()
    {
        for(int i=0;i<this.pacientService.getSize();i++)
        {
            System.out.println(this.pacientService.getElem(i).toString());
        }
    }

    public void show_appointments()
    {
        for(int i=0;i<this.appointmentService.getSize();i++)
        {
            System.out.println(this.appointmentService.getElem(i).toString());
        }
    }

    public void run_menu()
    {
        Scanner input = new Scanner(System.in);
        print_menu();
        String stringinput = input.nextLine();
        while(!Objects.equals(stringinput, "x"))
        {
            switch(stringinput)
            {
                case"1":
                    show_pacients();
                    break;
                case "2":
                    show_appointments();
                    break;
                case"3":
                    System.out.println("Input id:");
                    int id = Integer.parseInt(String.valueOf(input.nextLine()));
                    System.out.println("Input name:");
                    String name = String.valueOf(input.nextLine());
                    System.out.println("Input forename:");
                    String forename = String.valueOf(input.nextLine());
                    System.out.println("Input age:");
                    int age = Integer.parseInt(String.valueOf(input.nextLine()));
                    add_pacient(id,name,forename,age);
                    break;
                case"4":
                    System.out.println("Input id:");
                    int id_app = Integer.parseInt(String.valueOf(input.nextLine()));
                    System.out.println("Input pacient id:");
                    int id_pacient = Integer.parseInt(String.valueOf(input.nextLine()));
                    System.out.println("Input date:");
                    String date = String.valueOf(input.nextLine());
                    System.out.println("Input hour:");
                    int hour = Integer.parseInt(String.valueOf(input.nextLine()));
                    System.out.println("Input motive:");
                    String motive = String.valueOf(input.nextLine());
                    add_appointment(id_app,id_pacient,date,hour,motive);
                    break;
                case"5":
                    System.out.println("Input pacient id:");
                    int id_p = Integer.parseInt(String.valueOf(input.nextLine()));
                    Pacient p = find_pacient_by_id(id_p);
                    System.out.println(p.toString());
                    break;
                case "6":
                    System.out.println("Input appointment id:");
                    int poz = Integer.parseInt(String.valueOf(input.nextLine()));
                    Appointment appointment = find_app_by_id(poz);
                    try {
                        System.out.println(appointment.toString());
                        break;
                    } catch (NullPointerException ex){
                        System.out.println(ex.getMessage());
                        break;
                    }
                case "7":
                    System.out.println("Input the id of the patient you want to update:");
                    id = Integer.parseInt(String.valueOf(input.nextLine()));
                    System.out.println("Update the pacient's info:");
                    System.out.println("Input name:");
                    name = input.nextLine();
                    System.out.println("Input forename:");
                    forename =input.nextLine();
                    System.out.println("Input age:");
                    age = Integer.parseInt(String.valueOf(input.nextLine()));
                    update_patient(id, name, forename, age);
                    break;

                case "8":
                    System.out.println("Input the id of the appointment you want to update:");
                    id = Integer.parseInt(String.valueOf(input.nextLine()));
                    System.out.println("Update the appointment's info");
                    System.out.println("Input pacient id:");
                    id_pacient = Integer.parseInt(String.valueOf(input.nextLine()));
                    System.out.println("Input date:");
                    date = input.nextLine();
                    System.out.println("Input hour:");
                    hour = Integer.parseInt(String.valueOf(input.nextLine()));
                    System.out.println("Input motive:");
                    motive = input.nextLine();
                    update_appointment(id, id_pacient, date, hour, motive);
                    break;

                case "9":
                    System.out.println("Input the patient's id which you want to delete");
                    id = Integer.parseInt(String.valueOf(input.nextLine()));
                    delete_patient(id);
                    break;
                case "10":
                    System.out.println("Input the appointment's id which you want to delete");
                    id = Integer.parseInt(String.valueOf(input.nextLine()));
                    delete_appointment(id);
                    break;
                case "11":
                    this.appointmentService.nr_app_pacient();
                    break;
                case "12":
                    this.appointmentService.appointments_per_month();
                    break;
                case "13":
                    this.appointmentService.days_passed_appointment();
                    break;
                case "14":
                   this.appointmentService.busiest_months();
                    break;
                case "x":
                    break;
            }
            print_menu();
            stringinput = input.nextLine();
        }
    }

    public void print_menu()
    {
        String message = "";
        message += "1. Show all pacients\n";
        message += "2. Show all appointments\n";
        message += "3. Add pacient\n";
        message += "4. Add appointment\n";
        message += "5. Show pacient\n";
        message += "6. Show appointment\n";
        message += "7. Update pacient\n";
        message += "8. Update appointment\n";
        message += "9. Delete pacient\n";
        message += "10. Delete appointment\n";
        message += "11. Display pacients and their nr of appointments\n";
        message += "12. Display number of appointments per month\n";
        message += "13. Display numbers of day passed from last app for each pacient\n";
        message += "14. Display busiest months of the year\n";
        message += "x. Exit\n";
        message += "Input option:";
        System.out.println(message);
    }
}
