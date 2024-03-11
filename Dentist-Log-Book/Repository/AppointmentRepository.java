package Repository;

import Domain.*;

import java.io.Serializable;
import java.util.ArrayList;
import Exceptions.*;

public class AppointmentRepository extends AbstractRepository<Appointment> implements Serializable {

    public AppointmentRepository()
    {
        this.elems = new ArrayList<>();
        this.nr_elems = 0;
    }

    @Override
    public void add(Appointment ap) throws DuplicateElemException {
        if(!find_by_id(ap.getId()))
        {
            this.elems.add(ap);
            this.nr_elems++;
        } else throw new DuplicateElemException("An appointment with this id exists already");
    }

    @Override
    public void update(int poz, Appointment ap) throws IndexOutOfBoundsException{
        if(poz < 0 || poz >= this.nr_elems)
            throw new IndexOutOfBoundsException("Position is invalid");
        else this.elems.set(poz, ap);
    }

    @Override
    public void delete(int poz){
        if (poz >= 0 && poz < this.nr_elems){
            this.elems.remove(poz);
            this.nr_elems--;
        }
        else throw new IndexOutOfBoundsException("Invalid position for deleting an appointment");
    }

    @Override
    public Appointment getElem(int poz) {
        if(poz >= 0 && poz < this.nr_elems)
            return this.elems.get(poz);
        else throw new IndexOutOfBoundsException("Invalid position for finding an appointment");
    }

    @Override
    public boolean find_by_id(int id_app) {

        for (Appointment ap : elems) {
            if (ap.getId() == id_app)
                return true;
        }
        return false;
    }

    @Override
    public int find_pos_by_id(int id){
        for(int i=0;i < elems.size(); i++){
            if(elems.get(i).getId()==id)
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getNr_elems(){
        return this.nr_elems;
    }

    @Override
    public ArrayList<Appointment> getAll(){
        return this.elems;
    }

    @Override
    public Appointment find_elem(int id){
        for (Appointment ap : elems) {
            if (ap.getId() == id)
                return ap;
        }
        return null;
    }
}