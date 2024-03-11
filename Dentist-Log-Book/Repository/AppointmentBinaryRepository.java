package Repository;

import Domain.Appointment;
import Domain.Pacient;
import Exceptions.DuplicateElemException;

import java.io.*;

public class AppointmentBinaryRepository extends AppointmentRepository{
    private String fileName;

    public AppointmentBinaryRepository(String fileName) {
        this.fileName = fileName;
        try {
            loadFile();
        } catch (IOException | DuplicateElemException | ClassNotFoundException e){
            return;
        }
    }

    public void add(Appointment o) throws DuplicateElemException {
        super.add(o);
        // saveFile se executa doar daca super.add() nu a aruncat exceptie
        try {
            saveFile();
        } catch (IOException e) {throw new DuplicateElemException("An appointment with this id exists already");}
    }

    public void delete(int poz) {
        super.delete(poz);
        try {
            saveFile();
        } catch (IOException e) {e.printStackTrace();}
    }

    public void update(int poz,Appointment ap) {
        super.update(poz,ap);
        try {
            saveFile();
        } catch (IOException e) {e.printStackTrace();}
    }

    public void saveFile() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        for(int i=0;i<this.nr_elems;i++) {
            oos.writeObject(this.elems.get(i));
        }
    }

    public void loadFile() throws IOException, ClassNotFoundException, DuplicateElemException {
        Appointment ent;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (fis.available() >0) {
                ent = (Appointment) ois.readObject();
                super.add(ent);
            }
            ois.close();
        }catch (EOFException ex){return;}
    }
}