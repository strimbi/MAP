package Repository;

import Domain.*;
import Exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;


public class ActivitateRepo extends AbstractRepository<Activitate> implements Serializable {
    public ActivitateRepo() {
        this.elems = new ArrayList<>();
        this.nr_elems = 0;
    }

    @Override
    public void add(Activitate e) throws DuplicateElemException {
        if (!find_by_id(e.getId())) {
            this.elems.add(e);
            this.nr_elems++;
        } else throw new DuplicateElemException("An activity with this id exists already");
    }

    @Override
    public void update(int pos, Activitate e) throws IllegalArgumentException {

    }


    @Override
    public void delete(int id){
        int pos = find_pos_by_id(id);
        if (pos != -1) {
            this.elems.remove(pos);
            this.nr_elems--;
        }
    }

    @Override
    public Activitate getElem(int pos) {
        if(pos >= 0 && pos < this.nr_elems)
            return this.elems.get(pos);
        else throw new IndexOutOfBoundsException("Invalid position for finding an activity");
    }

    public boolean find_by_id(int id_pa) {
        for (Activitate a : elems) {
            if (a.getId() == id_pa)
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
    public int getNr_elems() {
        return 0;
    }

    @Override
    public ArrayList<Activitate> getAll(){
        return this.elems;
    }

    @Override
    public Activitate find_elem(int id){
        for (Activitate a : elems) {
            if (a.getId() == id)
                return a;
        }
        return null;
    }
}