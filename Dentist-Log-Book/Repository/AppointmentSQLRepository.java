package Repository;

import Domain.*;
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

public class AppointmentSQLRepository extends AppointmentRepository {

    private String JDBC_URL = "jdbc:sqlite:pacients_db.sqlite";

    Connection connection;

    public AppointmentSQLRepository()
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
            st.execute("CREATE TABLE IF NOT EXISTS appointments (id int, pacient_id int," +
                    "date VARCHAR(255), hour int, motive VARCHAR(400), FOREIGN KEY(pacient_id) REFERENCES" +
                    " patients(id));");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Pacient getPacientsql(int id_pacient)
    {
        ArrayList<Pacient> pacients = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM patients")){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                pacients.add(new Pacient(rs.getInt(1),
                        rs.getString(2), rs.getString(3),
                        rs.getInt(4)));
            }
            for(Pacient ap:pacients)
            {
                if(ap.getId() == id_pacient) return ap;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return pacients.get(0);
    }

    private int random_hour()
    {
        int min = 8; // Minimum value of range
        int max = 21; // Maximum value of range
        int random_int = (int)Math.floor(Math.random() * (max - min + 1) + min);
        return random_int;
    }

    private void init_data()
    {
        try {
            List<Appointment> appointments = new ArrayList<>();
            BufferedReader datebr = null;
            BufferedReader motivebr = null;
            datebr = new BufferedReader(new FileReader("Repository/date.txt"));
            motivebr = new BufferedReader(new FileReader("Repository/motive.txt"));
            for (int i = 0; i < 50; i++) {
                Random rand = new Random();
                int upperbound = 50;
                int int_random = rand.nextInt(upperbound);
                Pacient pacient = getPacientsql(int_random);
                appointments.add(new Appointment(i + 1, pacient, datebr.readLine(), random_hour(),
                        motivebr.readLine()));

            }
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO appointments VALUES (?,?,?,?,?)"))
        {
            for(Appointment ap:appointments){
                statement.setInt(1,ap.getId());
                statement.setInt(2,ap.getPacient().getId());
                statement.setString(3,ap.getDate());
                statement.setInt(4,ap.getHour());
                statement.setString(5,ap.getMotive());
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
        for (Appointment ap : getAll())
        {
            try{
                super.add(ap);
            }catch (DuplicateElemException ignored){}
        }
    }

    public ArrayList<Appointment> getAll()
    {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM appointments")){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                appointments.add(new Appointment(rs.getInt(1),
                        getPacientsql(rs.getInt(2)),   //?
                        rs.getString(3), rs.getInt(4),
                        rs.getString(5)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    public void add(Appointment ap) throws DuplicateElemException {
        super.add(ap);
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO appointments VALUES (?,?,?,?,?)"))
        {
            statement.setInt(1,ap.getId());
            statement.setInt(2,ap.getPacient().getId()); // ?
            statement.setString(3,ap.getDate());
            statement.setInt(4,ap.getHour());
            statement.setString(5,ap.getMotive());
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void delete(int id){
        super.delete(id);
        try (final Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM appointments WHERE id = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int poz, Appointment ap){
        super.update(poz, ap);
        try (final Statement stms = connection.createStatement()) {
            stms.executeUpdate("UPDATE appointments SET pacient_id = '" + ap.getPacient().getId() + "'," +
                    " date = '" + ap.getDate() + "', hour = '" + ap.getHour() + "', motive = '" +
                    ap.getMotive() + "' WHERE id = " + ap.getId());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}