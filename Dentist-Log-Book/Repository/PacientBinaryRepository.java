package Repository;

import Domain.Appointment;
import Domain.Pacient;
import Exceptions.DuplicateElemException;

import java.io.*;

public class PacientBinaryRepository extends PacientRepository{
    private String fileName;

    public PacientBinaryRepository(String fileName) {
        this.fileName = fileName;
        try {
            loadFile();
        } catch (IOException | ClassNotFoundException | DuplicateElemException ee){
            return;
        }
    }

    public void add(Pacient o) throws DuplicateElemException{
        super.add(o);
        // saveFile se executa doar daca super.add() nu a aruncat exceptie
        try {
            saveFile();
        } catch (IOException e){e.printStackTrace();}
    }

    public void delete(int poz) {
        super.delete(poz);
        try {
            saveFile();
        } catch (IOException e) {e.printStackTrace();}
    }

    public void update(int poz, Pacient ap) {
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
        Pacient ent;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (fis.available() >0) {
                ent = (Pacient) ois.readObject();
                super.add(ent);
            }
            ois.close();
        }catch (EOFException ex){return;}
    }
}