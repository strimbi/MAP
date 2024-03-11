package Domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    //Entity- genenric entity for objects
    private final int id;

    //constructor with parameter
    public Entity(int id) {
        this.id = id;
    }

    //getter for id
    public int getId() {
        return id;
    }
}