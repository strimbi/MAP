package Repository;

import Domain.Activitate;
import Exceptions.DuplicateElemException;
import org.sqlite.SQLiteDataSource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class ActivitateSQLRepository extends ActivitateRepo {

    private String JDBC_URL = "jdbc:sqlite:activities_db.sqlite";

    Connection connection;

    public ActivitateSQLRepository() {
        openConnection();
        createTable();
        loadDataInMemory();
        init_data();
    }

    private void openConnection() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if (connection == null || connection.isClosed()) {
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try (final Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS activities(id int, data varchar(400), descriere varchar(400), nr_minute int, nr_pasi int);");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void init_data() {
        Properties properties = new Properties();
        List<String> descriereList = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream("settings.properties")) {
            properties.load(fileInputStream);
            String descriereText = properties.getProperty("descriere_text");
            String[] descriereArray = descriereText.split(",");
            for (String descriere : descriereArray) {
                descriereList.add(descriere.trim());
            }

            try {
                super.add(new Activitate(1, "2024-01-09", descriereList.get(0), 30, 5000));
                super.add(new Activitate(2, "2024-01-10", descriereList.get(1), 45, 8000));
                super.add(new Activitate(3, "2024-01-11", descriereList.get(2), 60, 1000));
                super.add(new Activitate(4, "2024-01-12", descriereList.get(3), 20, 2000));
                super.add(new Activitate(5, "2024-01-13", descriereList.get(4), 40, 6000));

                try (PreparedStatement statement = connection.prepareStatement("INSERT INTO activities VALUES (?,?,?,?,?)")) {
                    for (Activitate a : super.getAll()) {
                        statement.setInt(1, a.getId());
                        statement.setString(2, a.getData());
                        statement.setString(3, a.getDescriere());
                        statement.setInt(4, a.getNr_minute());
                        statement.setInt(5, a.getNr_pasi());
                        statement.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (DuplicateElemException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int random_minute() {
        return new Random().nextInt(60);
    }

    private int random_pasi() {
        return new Random().nextInt(10000);
    }

    private void loadDataInMemory() {
        for (Activitate a : getAll()) {
            try {
                super.add(a);
            } catch (DuplicateElemException ignored) {
            }
        }
    }

    public ArrayList<Activitate> getAll() {
        ArrayList<Activitate> activities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM activities")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                activities.add(new Activitate(rs.getInt(1),
                        rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getInt(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    public void add(Activitate a) throws DuplicateElemException {
        super.add(a);
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO activities VALUES (?,?,?,?,?)")) {
            statement.setInt(1, a.getId());
            statement.setString(2, a.getData());
            statement.setString(3, a.getDescriere());
            statement.setInt(4, a.getNr_minute());
            statement.setInt(5, a.getNr_pasi());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        super.delete(id);
        try (final Statement stms = connection.createStatement()) {
            stms.executeUpdate("DELETE FROM activities WHERE id = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}