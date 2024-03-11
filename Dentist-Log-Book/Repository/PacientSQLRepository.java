package Repository;

import Domain.Appointment;
import Domain.Pacient;
import Exceptions.DuplicateElemException;
import Exceptions.RepositoryException;
import org.sqlite.SQLiteDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PacientSQLRepository extends PacientRepository {

    private String JDBC_URL = "jdbc:sqlite:pacients_db.sqlite";

    Connection connection;

    public PacientSQLRepository()
    {
        openConnection();
        createTable();
        loadDataInMemory();
        //init_data();
    }

    private void openConnection()
    {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if (connection == null || connection.isClosed())
            {
                connection = ds.getConnection();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable()
    {
        try(final Statement st = connection.createStatement()){
            st.execute("CREATE TABLE IF NOT EXISTS patients(id int, name varchar(400), forename varhcar(400), age int);");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int random_age()
    {
        int min = 1; // Minimum value of range
        int max = 150; // Maximum value of range
        int random_int = (int)Math.floor(Math.random() * (max - min + 1) + min);
        return random_int;
    }

    private void init_data()
    {
        try {
            List<Pacient> pacientsar = new ArrayList<>();
            BufferedReader namebr = null;
            BufferedReader forenamebr = null;
            namebr = new BufferedReader(new FileReader("Repository/nume.txt"));
            forenamebr = new BufferedReader(new FileReader("Repository/forename.txt"));
            for (int i = 0; i < 50; i++)
            {
                Random rand = new Random();
                int upperbound = 50;
                int int_random = rand.nextInt(upperbound);
                pacientsar.add(new Pacient(int_random,namebr.readLine(), forenamebr.readLine(),
                        random_age()));
            }
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO patients VALUES (?,?,?,?)"))
        {
            for(Pacient p:pacientsar)
            {
                statement.setInt(1,p.getId());
                statement.setString(2,p.getName());
                statement.setString(3,p.getForename());
                statement.setInt(4,p.getAge());
                statement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
             e.printStackTrace();
        }
        }catch (IOException e){ e.printStackTrace();}
    }

    private void loadDataInMemory() {
        for (Pacient p : getAll())
        {
            try{
                super.add(p);
            }catch (DuplicateElemException ignored){}
        }
    }

    public ArrayList<Pacient> getAll()
    {
        ArrayList<Pacient> pacients = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM patients")){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                pacients.add(new Pacient(rs.getInt(1),
                        rs.getString(2), rs.getString(3),
                        rs.getInt(4)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pacients;
    }

    public void add(Pacient p) throws DuplicateElemException {
        super.add(p);
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO patients VALUES (?,?,?,?)"))
        {
            statement.setInt(1,p.getId());
            statement.setString(2,p.getName());
            statement.setString(3,p.getForename());
            statement.setInt(4,p.getAge());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void delete(int id){
        super.delete(id);
        try (final Statement stms = connection.createStatement()) {
            stms.executeUpdate("DELETE FROM patients WHERE id = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int poz, Pacient p){
        super.update(poz, p);
        try (final Statement stms = connection.createStatement()) {
            stms.executeUpdate("UPDATE patients SET name = '" + p.getName() + "', forename = '" + p.getForename() +
                    "', age = " + p.getAge() + " WHERE id = " + p.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}