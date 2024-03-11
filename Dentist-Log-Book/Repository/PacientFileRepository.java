package Repository;

import Domain.Pacient;
import Exceptions.DuplicateElemException;

import java.io.*;
import java.util.ArrayList;

public class PacientFileRepository extends PacientRepository {

    private String filename;
    public PacientFileRepository(String fileName) throws IOException{
        this.filename = fileName;
        try {
            createPacients();
        }catch (IOException e){e.printStackTrace();}
    }

    public void createPacients() throws IOException{
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(this.filename));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split("[|]");
                if (elements.length < 4) continue;
                Pacient p = new Pacient(Integer.parseInt(elements[0].strip()),
                        elements[1].strip(), elements[2].strip(),
                        Integer.parseInt(elements[3].strip()));
                try{
                    this.add(p);
                }catch (DuplicateElemException e){
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {throw e;} finally {
            if (br != null) try {
                br.close();
            } catch (IOException e) {
                System.out.println("Error while closing the file " + e);
            }
        }
    }

    public void writePacients(ArrayList<Pacient> pacients) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(this.filename));
            for (Pacient b : pacients) {
                bw.write(b.getId() + " | " + b.getName() + " | " + b.getForename() + " | " + b.getAge());
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

    public void add_file(Pacient e) throws DuplicateElemException {
        this.add(e);
        this.writePacients(this.getAll());
        try {
            this.createPacients();
        }catch (IOException exp){exp.printStackTrace();}
    }

    public void delete_file(int pos) throws IllegalArgumentException{
        this.delete(pos);
        this.writePacients(this.getAll());
        try {
            this.createPacients();
        }catch (IOException e){e.printStackTrace();}
    }

    public void update_file(int pos, Pacient p) throws IllegalArgumentException{
        this.update(pos,p);
        this.writePacients(this.getAll());
        try {
            this.createPacients();
        }catch (IOException e){e.printStackTrace();}
    }

}