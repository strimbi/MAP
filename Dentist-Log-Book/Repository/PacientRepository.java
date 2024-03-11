package Repository;

import Domain.*;
import Exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;

public class PacientRepository extends AbstractRepository<Pacient> implements Serializable {
    public PacientRepository() {
        this.elems = new ArrayList<>();
        this.nr_elems = 0;
    }

    @Override
    public void add(Pacient e) throws DuplicateElemException {
        if (!find_by_id(e.getId())) {
            this.elems.add(e);
            this.nr_elems++;
        } else throw new DuplicateElemException("A pacient with this id exists already");
    }

    @Override
    public void update(int pos, Pacient e) throws IndexOutOfBoundsException {
        if(pos < 0 || pos >= this.nr_elems)
            throw new IndexOutOfBoundsException("Position is invalid");
        else this.elems.set(pos, e);
    }

    @Override
    public void delete(int id){

       for(Pacient p:elems)
       {
           if(p.getId() == id) elems.remove(p);
       }
        this.nr_elems--;
    }

    /* In case that the position is an invalid one, throw an exception
     * Otherwise, return the patient at the given position from the patients array
     **/
    @Override
    public Pacient getElem(int pos) {
        if(pos >= 0 && pos < this.nr_elems)
            return this.elems.get(pos);
        else throw new IndexOutOfBoundsException("Invalid position for finding a pacient");
    }

    /* True: In case there's a patient with the id given as a parameter
     *  False: Otherwise
     * */
    @Override
    public boolean find_by_id(int id_pa) {
        for (Pacient p : elems) {
            if (p.getId() == id_pa)
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
    public ArrayList<Pacient> getAll(){
        return this.elems;
    }

    @Override
    public Pacient find_elem(int id){
        for (Pacient p : elems) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }
}