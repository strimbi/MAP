package Service;

import Domain.*;
import Repository.*;
import Exceptions.*;

public abstract class Service<T extends Entity>{

    protected AbstractRepository<T> repository;

    public abstract void add(T e) throws IllegalArgumentException, DuplicateElemException;

    public abstract void update(int poz, T e) throws IllegalArgumentException, DuplicateElemException;

    public abstract void delete(int poz) throws IllegalArgumentException, DuplicateElemException;

    public abstract T getElem(int poz) throws IllegalArgumentException;

    public abstract int find_pos_by_id(int id);
}