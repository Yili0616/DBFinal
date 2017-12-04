/*
Create a Passenger Flow Report page
*/

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;


import java.io.IOException;
import java.security.*;
import javax.swing.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.sql.*;

@SuppressWarnings("unused")
public class FlowReport {

    @FXML
    public TextField StartTime;
    @FXML
    public TextField EndTime;
    @FXML
    public Button update;
    @FXML
    public Button reset;
    @FXML
    public TableView<ReportInfo> report;
    @FXML
    public TableColumn<ReportInfo, String> columnStationName;
    @FXML
    public TableColumn<ReportInfo, String> columnPassengerIn;
    @FXML
    public TableColumn<ReportInfo, String> columnPassengerOut;
    @FXML
    public TableColumn<ReportInfo, String> columnFlow;
    @FXML
    public TableColumn<ReportInfo, String> columnRevenue;

    public ObservableList<ReportInfo> data;

    private static Stage Window;

    public static void display() {
        Parent root = null;
        try {
            root = FXMLLoader.load(FlowReport.class.getResource("FlowReport.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage window = new Stage();

        Scene scene = new Scene(root, 800,600);

        window.setTitle("Passenger Flow Report");
        window.setScene(scene);
        window.show();

    }

    // If click on "Update" button, show the passenger flow report
    public void update(ActionEvent actionEvent) {

        String startTime = StartTime.getText();
        String endTime = EndTime.getText();

//        // Check if the input format is correct
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY HH");
//        dateFormat.setLenient(false);
//        try {
//            dateFormat.parse(startTime);
//        } catch (ParseException pe) {
//            return false;
//        }
//        return true;
        ConnectionConfig con = new ConnectionConfig();
        String sql1 = "(SELECT Name, PassengerIn, PassengerOut, Revenue "
        + "FROM (SELECT StopID,Name, COUNT(*) AS PassengerIn,Sum(TripFare) AS Revenue "
        + "FROM Trip JOIN Station ON Trip.StartsAt=Station.StopID "
        + "WHERE StartTime>='"+startTime+ "' AND StartTime<='" +endTime +"' "
        +" GROUP BY StartsAt) AS S "
        + "NATURAL LEFT OUTER JOIN "
        + "(SELECT StopID,Name, COUNT(*) AS PassengerOut "
        + "FROM Trip JOIN Station ON Trip.EndsAt=Station.StopID "
        +" WHERE StartTime>=' "+startTime+ "' AND StartTime<='" + endTime + "' "
        + "GROUP BY EndsAt) AS F "
        + "WHERE Name IS NOT NULL) "
+ ")UNION(" +
          "SELECT Name, PassengerIn, PassengerOut, Revenue "
                        + "FROM (SELECT StopID,Name, COUNT(*) AS PassengerIn,Sum(TripFare) AS Revenue "
                        + "FROM Trip JOIN Station ON Trip.StartsAt=Station.StopID "
                        + "WHERE StartTime>='"+ startTime+ "' AND StartTime<='"+ endTime+ "'"
                       + " GROUP BY StartsAt) AS S "
        + "NATURAL RIGHT OUTER JOIN "
        + "(SELECT StopID,Name, COUNT(*) AS PassengerOut "
        + "FROM Trip JOIN Station ON Trip.EndsAt=Station.StopID "
        + "WHERE StartTime>= '"+startTime + "'AND StartTime<='"+ endTime+ "' "
         + "GROUP BY EndsAt) AS F "
        + "WHERE Name IS NOT NULL)";

        ResultSet rs1 = con.getResult(sql1);



        //        System.out.println("SELECT Station.StopID, Station.Name, COUNT( Station.StopID ) As CountIn, SUM(Trip.Tripfare) As Rev\n" +
//                "FROM Trip\n" +
//                "INNER JOIN Station ON Trip.StartsAt = Station.StopID\n" +
//                "WHERE StartTime >= '" + startTime + "'\n" +
//                "AND StartTime <= '" + endTime + "'\n" +
//                "GROUP BY StartsAt");
//        // Get all the "start" station that sends out passengers in the time interval
//        ResultSet start = con.getResult("SELECT Station.StopID, Station.Name, COUNT( Station.StopID ) As CountIn, SUM(Trip.Tripfare) As Rev\n" +
//                "FROM Trip\n" +
//                "INNER JOIN Station ON Trip.StartsAt = Station.StopID\n" +
//                "WHERE StartTime >= '" + startTime + "'\n" +
//                "AND StartTime <= '" + endTime + "'\n" +
//                "GROUP BY StartsAt");
////        System.out.println("SELECT Station.StopID, Station.Name, COUNT( Station.StopID ) As CountIn, SUM(Station.EnterFare) As Rev\n" +
////                "FROM Trip\n" +
////                "INNER JOIN Station ON Trip.StartsAt = Station.StopID\n" +
////                "WHERE StartTime >= '" + startTime + "'\n" +
////                "AND StartTime <= '" + endTime + "'\n" +
////                "GROUP BY StartsAt");
//
//        // Get all the corresponding "end" station in the time interval
////        ResultSet end = con1.getResult("SELECT Station.StopID, Station.Name, COUNT(Station.StopID) As CountOut" +
////                "FROM Trip INNER JOIN Station ON Trip.EndsAt = Station.StopID " +
////                "WHERE StartTime >= '"+ startTime + "'AND StartTime <= '"
////                + endTime + "'GROUP BY StartsAt");
//
//        ResultSet end = con.getResult("SELECT Station.StopID, Station.Name, COUNT(Station.StopID) As CountOut FROM Trip INNER JOIN Station ON Trip.EndsAt = Station.StopID WHERE StartTime >= '"+ startTime + "'AND StartTime <= '" + endTime + "'GROUP BY StartsAt");


//        System.out.println("SELECT Station.StopID, Station.Name, COUNT(Station.StopID) As CountOut FROM Trip INNER JOIN Station ON Trip.EndsAt = Station.StopID WHERE StartTime >= '"+ startTime + "'AND StartTime <= '" + endTime + "'GROUP BY StartsAt");
        ResultSet rs = con.getResult(sql1);
        data = FXCollections.observableArrayList();
        try {

//            System.out.println("try!");
//            if (!start.next()) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText("Error Operation!");
//                alert.setContentText("There is no trip");
//                alert.showAndWait();
//            }
//            System.out.println(start.next());
//            System.out.println(end.next());
//            else {
//                start.beforeFirst();
//                while (start.next()) {
//                    end.next();
//                    // Calculate passenger Flow
//                    System.out.println("create table!");
//                    int a = start.getInt("CountIn");
//                    System.out.println(a);
//                    int b = end.getInt("CountOut");
//                    System.out.println(b);
//                    int CalcFlow = a - b;
////                int CalcFlow = start.getInt("countIn") - end.getInt("countOut");
//                    System.out.println("flow:" + CalcFlow);
//
//                    data.add(new ReportInfo(start.getString("Name"), start.getInt("CountIn"),  end.getInt("CountOut"), CalcFlow, start.getDouble("Rev")));
//
//                    columnStationName.setCellValueFactory(new PropertyValueFactory<>("StationName"));
//                    columnPassengerIn.setCellValueFactory(new PropertyValueFactory<>("PassengerIn"));
//                    columnPassengerOut.setCellValueFactory(new PropertyValueFactory<>("PassengerOut"));
//                    columnFlow.setCellValueFactory(new PropertyValueFactory<>("Flow"));
//                    columnRevenue.setCellValueFactory(new PropertyValueFactory<>("Revenue"));
//
//                    report.setItems(null);
//                    report.setItems(data);
//                }
//            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
//            System.out.println("wrong!!!!!!!!!!!!!!!!!!!");
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText("Error Operation!");
//            alert.setContentText("You input time is in the wrong format!");
//            alert.showAndWait();
        }

//            // TEST!!----------------------
//            ResultSetMetaData startmd = start.getMetaData();
//            int columnNum = startmd.getColumnCount();
//            while (start.next()) {
//                for (int i = 1; i <= columnNum; i++) {
//                    if (i > 1) System.out.println(", ");
//                    String attribute = start.getString(i);
//                    System.out.println(attribute + "" + startmd.getColumnName(i));
//                }
//                System.out.println("");
//            }

        con.close();


    }

    // If click on "Reset" button, clear all the contents in this page
    public void reset(ActionEvent actionEvent) {
        StartTime.clear();
        report.setItems(null);
        EndTime.clear();
//        for (int i = 0; i < report.getItems().size(); i++) {
//            report.getItems().clear();
//        }
    }

}
