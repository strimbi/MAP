package Service;

import Domain.Pacient;
import Exceptions.DuplicateElemException;
import Repository.AbstractRepository;

import java.util.ArrayList;

public class PacientService extends Service<Pacient>{

    public PacientService(AbstractRepository<Pacient> pacientrepo)
    {
        this.repository = pacientrepo;
    }

    @Override
    public void add(Pacient p) throws DuplicateElemException{
        try {
            this.repository.add(p);
        }  catch (DuplicateElemException ex)
        {
            throw new DuplicateElemException(ex.getMessage());
        }
    }

    @Override
    public void update(int poz, Pacient p) throws IllegalArgumentException{
        try {
            this.repository.update(poz, p);
        } catch (IndexOutOfBoundsException ex_index){
            throw new IndexOutOfBoundsException(ex_index.getMessage());
        }
    }

    @Override
    public void delete(int poz) throws IllegalArgumentException {
        try {
            this.repository.delete(poz);
        } catch (IndexOutOfBoundsException ex_index){
            throw new IndexOutOfBoundsException(ex_index.getMessage());
        }
    }

    @Override
    public Pacient getElem(int poz) throws IllegalArgumentException{
        try {
            return this.repository.getElem(poz);
        }  catch (IndexOutOfBoundsException ex_index){
            throw new IndexOutOfBoundsException(ex_index.getMessage());
        }
    }

    public Pacient find_pacient_by_id(int id)
    {
        return this.repository.find_elem(id);
    }

    @Override
    public int find_pos_by_id(int id){
        return this.repository.find_pos_by_id(id);
    }

    public int getSize()
    {
        return this.repository.size();
    }

    public ArrayList<Pacient> getAll()
    {
        return this.repository.getAll();
    }
}