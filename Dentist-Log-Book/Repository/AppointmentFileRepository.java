package Repository;

import Domain.*;
import Exceptions.DuplicateElemException;

import java.io.*;
import java.util.ArrayList;

public class AppointmentFileRepository extends AppointmentRepository {

    private String filename;
    public AppointmentFileRepository(String fileName) throws IOException{
        this.filename = fileName;
        try {
            createAppointment();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void createAppointment() throws IOException{
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(this.filename));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split("[|]");
                if (elements.length < 8) continue;
                Appointment app = new Appointment(Integer.parseInt(elements[0].strip()),
                        new Pacient(Integer.parseInt(elements[1].strip()), elements[2].strip(),
                        elements[3].strip(), Integer.parseInt(elements[4].strip())),
                        elements[5].strip(), Integer.parseInt(elements[6].strip()),
                        elements[7].strip());
                try{
                    this.add(app);
                }catch (DuplicateElemException e){
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            if (br != null) try {
                br.close();
            } catch (IOException e) {
                System.out.println("Error while closing the file " + e);
            }
        }
    }

    public void writeAppointment(ArrayList<Appointment> appointments) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(this.filename));
            for (Appointment b : appointments) {
                bw.write(b.getId() + " | " + b.getPacient().getId() + " | " + b.getPacient().getName() +
                        " | " + b.getPacient().getForename() + " | " + b.getPacient().getAge() + " | " +
                        b.getDate() + " | " + b.getHour() + " | " + b.getMotive());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert bw != null;
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void add_file(Appointment app)throws DuplicateElemException {
        this.add(app);
        this.writeAppointment(this.getAll());;
        try {
            this.createAppointment();;
        }catch (IOException exp){exp.printStackTrace();}
    }

    public void delete_file(int pos) throws IndexOutOfBoundsException{
        this.delete(pos);
        this.writeAppointment(this.getAll());;
        try {
            this.createAppointment();;
        }catch (IOException exp){exp.printStackTrace();}
    }

    public void update_file(int pos, Appointment app) throws IndexOutOfBoundsException{
        this.update(pos,app);
        this.writeAppointment(this.getAll());;
        try {
            this.createAppointment();;
        }catch (IOException exp){exp.printStackTrace();}
    }

}