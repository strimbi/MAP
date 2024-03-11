package Repository;

import Domain.*;
import java.util.ArrayList;
import Exceptions.*;


public abstract class AbstractRepository<T extends Entity>{
    protected ArrayList<T> elems;

    protected int nr_elems;

    public int size(){
        return nr_elems;
    }

    public abstract void add(T e) throws IllegalArgumentException, DuplicateElemException;

    public abstract void update(int pos, T e) throws IllegalArgumentException;

    public abstract void delete(int pos) throws IllegalArgumentException;

    public abstract T getElem(int pos) throws IndexOutOfBoundsException;

    public abstract boolean find_by_id(int id);

    public abstract int find_pos_by_id(int id);

    public abstract int getNr_elems();

    public abstract ArrayList<T> getAll();

    public abstract T find_elem(int id);
}