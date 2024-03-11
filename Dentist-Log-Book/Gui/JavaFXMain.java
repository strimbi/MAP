package Gui;

import Domain.*;
import Exceptions.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Repository.*;
import Service.*;

public class JavaFXMain extends Application {

    @Override
    public void start(Stage primaryStage) {

        PacientSQLRepository pacientSQLRepository = new PacientSQLRepository();
        PacientService pacientService = new PacientService(pacientSQLRepository);

        AppointmentSQLRepository appointmentSQLRepository = new AppointmentSQLRepository();
        AppointmentService appointmentService = new AppointmentService(appointmentSQLRepository);

        HBox root = new HBox();

        VBox pacientsVBox = new VBox();
        VBox appointmentsVBox = new VBox();

        pacientsVBox.setPrefWidth(520);
        appointmentsVBox.setPrefWidth(520);
        pacientsVBox.setPadding(new javafx.geometry.Insets(10));
        appointmentsVBox.setPadding(new javafx.geometry.Insets(10));

        root.getChildren().add(pacientsVBox);
        root.getChildren().add(appointmentsVBox);

        Scene scene = new Scene(root, 1080, 720);

        ListView<Pacient> listView = new ListView<>();
        ObservableList<Pacient> items = FXCollections.observableArrayList(pacientService.getAll());
        listView.setItems(items);

        GridPane pacientGridPane = new GridPane();
        Label idLabel = new Label("ID");
        TextField pacientIDTextField = new TextField();

        Label nameLabel = new Label("Name");
        TextField pacientNameTextField = new TextField();

        Label surnameLabel = new Label("Forename");
        TextField pacientSurnameTextField = new TextField();

        Label varstaLabel = new Label("Age");
        TextField pacientVarstaTextField = new TextField();


        idLabel.setPadding(new javafx.geometry.Insets(10));
        nameLabel.setPadding(new javafx.geometry.Insets(10));
        surnameLabel.setPadding(new javafx.geometry.Insets(10));
        varstaLabel.setPadding(new javafx.geometry.Insets(10));
        pacientGridPane.add(idLabel, 0, 0);
        pacientGridPane.add(pacientIDTextField, 1, 0);
        pacientGridPane.add(nameLabel, 0, 1);
        pacientGridPane.add(pacientNameTextField, 1, 1);
        pacientGridPane.add(surnameLabel, 0, 2);
        pacientGridPane.add(pacientSurnameTextField, 1, 2);
        pacientGridPane.add(varstaLabel, 0, 3);
        pacientGridPane.add(pacientVarstaTextField, 1, 3);

        pacientsVBox.setPadding(new javafx.geometry.Insets(10));

        HBox hbox = new HBox();
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");
        Button findButton = new Button("Find");
        hbox.getChildren().add(addButton);
        hbox.getChildren().add(updateButton);
        hbox.getChildren().add(deleteButton);
        hbox.getChildren().add(clearButton);
        hbox.getChildren().add(findButton);

        addButton.setOnMouseClicked(mouseEvent -> {
            try {
                pacientService.add(new Pacient(Integer.parseInt(pacientIDTextField.getText()),
                        pacientNameTextField.getText(), pacientSurnameTextField.getText(),
                        Integer.parseInt(pacientVarstaTextField.getText())));
                items.setAll(pacientService.getAll());
                pacientIDTextField.setText("");
                pacientNameTextField.setText("");
                pacientSurnameTextField.setText("");
                pacientVarstaTextField.setText("");
            } catch (DuplicateElemException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Duplicate element");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        listView.setOnMouseClicked(mouseEvent -> {
            Pacient pacient = listView.getSelectionModel().getSelectedItem();
            pacientIDTextField.setText(String.valueOf(pacient.getId()));
            pacientNameTextField.setText(pacient.getName());
            pacientSurnameTextField.setText(pacient.getForename());
            pacientVarstaTextField.setText(String.valueOf(pacient.getAge()));
        });

        updateButton.setOnMouseClicked(mouseEvent -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            pacientService.update(selectedIndex, new Pacient(Integer.parseInt(pacientIDTextField.getText()),
                    pacientNameTextField.getText(),
                    pacientSurnameTextField.getText(),
                    Integer.parseInt(pacientVarstaTextField.getText())));
            items.setAll(pacientService.getAll());
            pacientIDTextField.setText("");
            pacientNameTextField.setText("");
            pacientSurnameTextField.setText("");
            pacientVarstaTextField.setText("");

        });

        deleteButton.setOnMouseClicked(mouseEvent -> {
            pacientService.delete(Integer.parseInt(pacientIDTextField.getText()));
            items.setAll(pacientService.getAll());
            pacientIDTextField.setText("");
            pacientNameTextField.setText("");
            pacientSurnameTextField.setText("");
            pacientVarstaTextField.setText("");
        });

        clearButton.setOnMouseClicked(mouseEvent -> {
            pacientIDTextField.setText("");
            pacientNameTextField.setText("");
            pacientSurnameTextField.setText("");
            pacientVarstaTextField.setText("");
        });

        findButton.setOnMouseClicked(mouseEvent -> {
            Pacient p = pacientService.find_pacient_by_id(Integer.parseInt(pacientIDTextField.getText()));
            items.setAll(p);
            pacientIDTextField.setText("");
            pacientNameTextField.setText("");
            pacientSurnameTextField.setText("");
            pacientVarstaTextField.setText("");
        });

        ListView<Appointment> listView2 = new ListView<>();
        ObservableList<Appointment> items2 = FXCollections.observableArrayList(appointmentService.getAll());
        listView2.setItems(items2);

        GridPane appointmentGridPane = new GridPane();
        Label idLabel2 = new Label("ID");
        TextField appointmentIDTextField = new TextField();
        Label pacientLabel = new Label("Pacient ID");
        TextField appointmentPacientTextField = new TextField();
        Label oraLabel = new Label("Hour");
        TextField appointmentOraTextField = new TextField();
        Label dataLabel = new Label("Date (m/d/y)");
        TextField appointmentDataTextField = new TextField();
        Label porpouseLabel = new Label("Porpouse");
        TextField appointmentScopTextField = new TextField();

        idLabel2.setPadding(new javafx.geometry.Insets(10));
        pacientLabel.setPadding(new javafx.geometry.Insets(10));
        oraLabel.setPadding(new javafx.geometry.Insets(10));
        dataLabel.setPadding(new javafx.geometry.Insets(10));
        porpouseLabel.setPadding(new javafx.geometry.Insets(10));
        appointmentGridPane.add(idLabel2, 0, 0);
        appointmentGridPane.add(appointmentIDTextField, 1, 0);
        appointmentGridPane.add(pacientLabel, 0, 1);
        appointmentGridPane.add(appointmentPacientTextField, 1, 1);
        appointmentGridPane.add(oraLabel, 0, 2);
        appointmentGridPane.add(appointmentOraTextField, 1, 2);
        appointmentGridPane.add(dataLabel, 0, 3);
        appointmentGridPane.add(appointmentDataTextField, 1, 3);
        appointmentGridPane.add(porpouseLabel, 0, 4);
        appointmentGridPane.add(appointmentScopTextField, 1, 4);


        HBox hbox2 = new HBox();
        Button addButton2 = new Button("Add");
        Button updateButton2 = new Button("Update");
        Button deleteButton2 = new Button("Delete");
        Button clearButton2 = new Button("Clear");
        Button findButton2 = new Button("Find");
        hbox2.getChildren().add(addButton2);
        hbox2.getChildren().add(updateButton2);
        hbox2.getChildren().add(deleteButton2);
        hbox2.getChildren().add(clearButton2);
        hbox2.getChildren().add(findButton2);


        listView2.setOnMouseClicked(mouseEvent -> {
            Appointment programare = listView2.getSelectionModel().getSelectedItem();
            appointmentIDTextField.setText(String.valueOf(programare.getId()));
            appointmentPacientTextField.setText(programare.getPacient().getName());
            appointmentOraTextField.setText(String.valueOf(programare.getHour()));
            appointmentDataTextField.setText(programare.getDate());
            appointmentScopTextField.setText(programare.getMotive());
        });

        addButton2.setOnMouseClicked(mouseEvent -> {
            try {
                appointmentService.add(new Appointment(Integer.parseInt(appointmentIDTextField.getText()),
                        pacientService.find_pacient_by_id(Integer.parseInt(appointmentPacientTextField.getText())),
                        appointmentDataTextField.getText(),
                        Integer.parseInt(appointmentOraTextField.getText()),
                        appointmentScopTextField.getText()));
                items2.setAll(appointmentService.getAll());
                appointmentIDTextField.setText("");
                appointmentPacientTextField.setText("");
                appointmentOraTextField.setText("");
                appointmentDataTextField.setText("");
                appointmentScopTextField.setText("");

            } catch (DuplicateElemException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Duplicate element");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        updateButton2.setOnMouseClicked(mouseEvent -> {
            int selectedIndex = listView2.getSelectionModel().getSelectedIndex();
            appointmentService.update(selectedIndex,
                    new Appointment(Integer.parseInt(appointmentIDTextField.getText()),
                            pacientService.find_pacient_by_id(Integer.parseInt(appointmentPacientTextField.getText())),
                            appointmentDataTextField.getText(),
                            Integer.parseInt(appointmentOraTextField.getText()),
                            appointmentScopTextField.getText()));
            items2.setAll(appointmentService.getAll());
            appointmentIDTextField.setText("");
            appointmentPacientTextField.setText("");
            appointmentOraTextField.setText("");
            appointmentDataTextField.setText("");
            appointmentScopTextField.setText("");
        });

        deleteButton2.setOnMouseClicked(mouseEvent -> {
            appointmentService.delete(Integer.parseInt(appointmentIDTextField.getText()));
            items2.setAll(appointmentService.getAll());
            appointmentIDTextField.setText("");
            appointmentPacientTextField.setText("");
            appointmentOraTextField.setText("");
            appointmentDataTextField.setText("");
            appointmentScopTextField.setText("");
        });

        clearButton2.setOnMouseClicked(mouseEvent -> {
            appointmentIDTextField.setText("");
            appointmentPacientTextField.setText("");
            appointmentOraTextField.setText("");
            appointmentDataTextField.setText("");
            appointmentScopTextField.setText("");        });

        pacientsVBox.getChildren().add(listView);
        pacientsVBox.getChildren().add(pacientGridPane);
        pacientsVBox.getChildren().add(hbox);
        appointmentsVBox.getChildren().add(listView2);
        appointmentsVBox.getChildren().add(appointmentGridPane);
        appointmentsVBox.getChildren().add(hbox2);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

        findButton2.setOnMouseClicked(mouseEvent -> {
            Appointment ap = appointmentService.find_appointment_by_id(Integer.parseInt(appointmentIDTextField.getText()));
            items2.setAll(ap);
            appointmentIDTextField.setText("");
            appointmentPacientTextField.setText("");
            appointmentOraTextField.setText("");
            appointmentDataTextField.setText("");
            appointmentScopTextField.setText("");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
