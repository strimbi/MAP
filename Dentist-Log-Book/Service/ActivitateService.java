package Service;

import Domain.Activitate;
import Exceptions.DuplicateElemException;
import Repository.AbstractRepository;
import Repository.ActivitateSQLRepository;

import java.util.ArrayList;
import java.util.List;

public class ActivitateService extends Service<Activitate> {

    public ActivitateService(ActivitateSQLRepository activitateRepo) {
        this.repository = activitateRepo;
    }

    @Override
    public void add(Activitate a) throws DuplicateElemException {
        try {
            this.repository.add(a);
        } catch (DuplicateElemException ex) {
            throw new DuplicateElemException(ex.getMessage());
        }
    }

    @Override
    public void update(int poz, Activitate e) throws IllegalArgumentException, DuplicateElemException {

    }

    @Override
    public void delete(int poz) throws IllegalArgumentException {
        try {
            this.repository.delete(poz);
        } catch (IndexOutOfBoundsException ex_index) {
            throw new IndexOutOfBoundsException(ex_index.getMessage());
        }
    }

    @Override
    public Activitate getElem(int poz) throws IllegalArgumentException {
        try {
            return this.repository.getElem(poz);
        } catch (IndexOutOfBoundsException ex_index) {
            throw new IndexOutOfBoundsException(ex_index.getMessage());
        }
    }

    public Activitate find_activitate_by_id(int id) {
        return this.repository.find_elem(id);
    }

    @Override
    public int find_pos_by_id(int id) {
        return this.repository.find_pos_by_id(id);
    }

    public ArrayList<Activitate> getAll() {
        return this.repository.getAll();
    }

    public List<Activitate> filtreazaDupaDescriere(String descriereFilter) {
        List<Activitate> activitatiFiltrate = new ArrayList<>();
        for (Activitate activitate : getAll()) {
            if (activitate.getDescriere().toLowerCase().contains(descriereFilter.toLowerCase())) {
                activitatiFiltrate.add(activitate);
            }
        }
        return activitatiFiltrate;
    }

//    public void setAll(List<Activitate> activitati) {
//        repository.setAll(activitati);
//    }

}