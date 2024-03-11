package GUI;

import Domain.Activitate;
import Exceptions.DuplicateElemException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Repository.ActivitateSQLRepository;
import Service.ActivitateService;

import java.util.Comparator;
import java.util.List;

public class JavaFXMain2 extends Application {

    @Override
    public void start(Stage primaryStage) {

        ActivitateSQLRepository activitateSQLRepository = new ActivitateSQLRepository();
        ActivitateService activitateService = new ActivitateService(activitateSQLRepository);

        HBox root = new HBox();

        VBox activitatiVBox = new VBox();
        VBox appointmentsVBox = new VBox();

        activitatiVBox.setPrefWidth(520);
        appointmentsVBox.setPrefWidth(520);
        activitatiVBox.setPadding(new javafx.geometry.Insets(10));
        appointmentsVBox.setPadding(new javafx.geometry.Insets(10));

        root.getChildren().add(activitatiVBox);
        root.getChildren().add(appointmentsVBox);

        Scene scene = new Scene(root, 1080, 720);

        ListView<Activitate> listView = new ListView<>();
        ObservableList<Activitate> items = FXCollections.observableArrayList(activitateService.getAll());
        items.sort(Comparator.comparingInt(Activitate::getNr_pasi).reversed()); // Sort in descending order based on nr_pasi
        listView.setItems(items);

        GridPane activitateGridPane = new GridPane();
        Label idLabel = new Label("ID");
        TextField activitateIDTextField = new TextField();

        Label dataLabel = new Label("Data");
        TextField activitateDataTextField = new TextField();

        Label descriereLabel = new Label("Descriere");
        TextField activitateDescriereTextField = new TextField();

        Label minuteLabel = new Label("Minute");
        TextField activitateMinuteTextField = new TextField();

        Label pasiLabel = new Label("Pasi");
        TextField activitatePasiTextField = new TextField();

        idLabel.setPadding(new javafx.geometry.Insets(10));
        dataLabel.setPadding(new javafx.geometry.Insets(10));
        descriereLabel.setPadding(new javafx.geometry.Insets(10));
        minuteLabel.setPadding(new javafx.geometry.Insets(10));
        pasiLabel.setPadding(new javafx.geometry.Insets(10));

        activitateGridPane.add(idLabel, 0, 0);
        activitateGridPane.add(activitateIDTextField, 1, 0);
        activitateGridPane.add(dataLabel, 0, 1);
        activitateGridPane.add(activitateDataTextField, 1, 1);
        activitateGridPane.add(descriereLabel, 0, 2);
        activitateGridPane.add(activitateDescriereTextField, 1, 2);
        activitateGridPane.add(minuteLabel, 0, 3);
        activitateGridPane.add(activitateMinuteTextField, 1, 3);
        activitateGridPane.add(pasiLabel, 0, 4);
        activitateGridPane.add(activitatePasiTextField, 1, 4);

        activitatiVBox.setPadding(new javafx.geometry.Insets(10));

        HBox hbox = new HBox();
        Button addButton = new Button("Add");
        Button clearButton = new Button("Clear");
        Button filterButton = new Button("Filter");
        hbox.getChildren().add(addButton);
        hbox.getChildren().add(clearButton);
        TextField filterTextField = new TextField();

        addButton.setOnMouseClicked(mouseEvent -> {
            try {
                activitateService.add(new Activitate(Integer.parseInt(activitateIDTextField.getText()),
                        activitateDataTextField.getText(),
                        activitateDescriereTextField.getText(),
                        Integer.parseInt(activitateMinuteTextField.getText()),
                        Integer.parseInt(activitatePasiTextField.getText())));
                items.setAll(activitateService.getAll());
                items.sort(Comparator.comparingInt(Activitate::getNr_pasi).reversed()); // Re-sort in descending order based on nr_pasi
                activitateIDTextField.setText("");
                activitateDataTextField.setText("");
                activitateDescriereTextField.setText("");
                activitateMinuteTextField.setText("");
                activitatePasiTextField.setText("");
            } catch (DuplicateElemException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Duplicate element");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        listView.setOnMouseClicked(mouseEvent -> {
            Activitate activitate = listView.getSelectionModel().getSelectedItem();
            activitateIDTextField.setText(String.valueOf(activitate.getId()));
            activitateDataTextField.setText(activitate.getData());
            activitateDescriereTextField.setText(activitate.getDescriere());
            activitateMinuteTextField.setText(String.valueOf(activitate.getNr_minute()));
            activitatePasiTextField.setText(String.valueOf(activitate.getNr_pasi()));
        });

        clearButton.setOnMouseClicked(mouseEvent -> {
            activitateIDTextField.setText("");
            activitateDataTextField.setText("");
            activitateDescriereTextField.setText("");
            activitateMinuteTextField.setText("");
            activitatePasiTextField.setText("");
        });

//        filterButton.setOnMouseClicked(mouseEvent -> {
//            String filterText = filterTextField.getText();
//            if (filterText.isEmpty()) {
//                // Afiseaza un mesaj de eroare daca filtrul este gol
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText("Empty filter");
//                alert.setContentText("Please enter a filter string.");
//                alert.showAndWait();
//            } else {
//                List<Activitate> activitatiFiltrate = activitateService.filtreazaDupaDescriere(filterText);
//                activitateService.setAll(activitatiFiltrate);
//                items.setAll(activitateService.getAll());
//                items.sort(Comparator.comparingInt(Activitate::getNr_pasi).reversed());
//            }
//        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}