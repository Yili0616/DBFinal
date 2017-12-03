import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;



import java.sql.ResultSet;
import java.sql.SQLException;


public class breezeCardManagement {

    @FXML
    public TableView<Card> tableView;

    @FXML
    public TableColumn<Card, String> columnCardNumber;

    @FXML
    public TableColumn<Card, String> columnValue;
    @FXML
    public TableColumn<Card, String> columnOwner;


    public ObservableList<Card> data;

    @FXML
    public CheckBox showsus;

    @FXML
    public TextField newVal;

    @FXML
    public TextField owner;
    private static Stage window;
    @FXML
    private TextField cardnumber;
    @FXML
    private TextField low;
    @FXML
    private TextField high;

    @FXML
    private TextField newUser;


    public static void display() throws Exception {
        Parent root = FXMLLoader.load(breezeCardManagement.class.getResource("breezeCardManagement.fxml"));
//        System.out.println("owner");
//        System.out.println(owner == null);
//        System.out.println("columnOwner");
//        System.out.println( columnOwner == null);
//        System.out.println("tableView");
//        System.out.println(tableView == null);
        try {
//            data = initialize();
//            System.out.println(data == null);
//            System.out.println(columnCardNumber == null);
//            columnCardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
//
//            columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
//            System.out.println(columnValue == null);
//            System.out.println("check");
//            System.out.println( columnOwner== null);
//            columnOwner.setCellValueFactory(new PropertyValueFactory<>("owner"));
//
//            tableView.setItems(null);
//            tableView.setItems(data);

            window = new Stage();

            Scene scene = new Scene(root, 600, 500);

            window.setTitle("Breeze Card Management");
            window.setScene(scene);
            window.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("here is the exception");
        }

    }

    public void update(ActionEvent actionEvent) {
        ConnectionConfig con = new ConnectionConfig();
        String sql = getSql();
        ResultSet rs = con.getResult(sql);
        ResultSet rs2 = con.getResult("select BreezecardNum from Conflict");
        String breezenum;
        data = FXCollections.observableArrayList();
        try {
            while(rs.next()){
                rs2.beforeFirst();
                boolean temp = false;
                //Iterate Row
//                ObservableList<String> row = FXCollections.observableArrayList();
//                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
//                    //Iterate Column
//                    row.add(rs.getString(i));
//                }
//                System.out.println("Row [1] added "+row );
//                data.add(row);
                breezenum= rs.getString("BreezecardNum");
                while (rs2.next()) {
                    if (breezenum.equals(rs2.getString("BreezecardNum"))){
                        temp = true;
                    }
                }

                if (temp) {
                    data.add(new Card(rs.getString("BreezeCardNum"), rs.getString("Value"),"Suspended"));
                } else {

                    data.add(new Card(rs.getString("BreezeCardNum"), rs.getString("Value"), rs.getString("BelongsTo")));
                }


            }

            System.out.println(columnCardNumber==null);
            columnCardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
            columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
            columnOwner.setCellValueFactory(new PropertyValueFactory<>("owner"));

            tableView.setItems(null);

            tableView.setItems(data);
        } catch (SQLException e) {
            System.out.println("sql exception");
            e.printStackTrace();
        }
        con.close();


    }

    private String getSql() {
        boolean ownerb = owner.getText().length() != 0;
        boolean cardnumberb = cardnumber.getText().length() != 0;
        boolean lowb = low.getText().length()!=0;
        boolean highb = high.getText().length() !=0;
        boolean showb = showsus.isSelected();


        String sql = "Select * from Breezecard";

        if ((ownerb || cardnumberb) ||(lowb || (highb|| showb) )) {
            sql += " where ";


            if (owner.getText().length() != 0) {
                sql += " BelongsTo = '" + owner.getText() + "' and ";
            }
            if (cardnumber.getText().length() != 0) {

                sql += " BreezecardNum = '" + cardnumber.getText() + "' and ";
            }
            if (low.getText().length() != 0) {
                sql += "value >='" + low.getText() + "' and ";

            }
            if (high.getText().length()!=0) {
                sql += "value <='" + high.getText() + "' and ";
            }


            if (showsus.isSelected()) {

                sql += " NOT \n" +
                        "EXISTS (\n" +
                        "SELECT 1 \n" +
                        "FROM Conflict\n" +
                        "WHERE Conflict.BreezecardNum = Breezecard.BreezecardNum\n" +
                        ") and ";
            }
            sql = sql.substring(0, sql.length()-4);
        }


        System.out.println(sql);
        return sql;
    }

    public void reset(ActionEvent actionEvent) {
        owner.clear();
        cardnumber.clear();
        low.clear();
        high.clear();
        showsus.setSelected(false);
    }

    public void setValue(ActionEvent actionEvent) {
        int val = Integer.parseInt(newVal.getText().trim());

        if (val > 1000) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Value could not exceed 1000!");
            alert.showAndWait();
        } else if (val < 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Value could not be less than 0!");
            alert.showAndWait();

        } else {
            System.out.println("herer");
            Card card = tableView.getSelectionModel().getSelectedItem();
            System.out.println(card.getCardNumber());
            String bnum = card.getCardNumber();
        // Update Breezecard SET Value = 'val' where BreezecardNum = ''
            String sql = "Update Breezecard SET Value = " + Integer.toString(val) + " where BreezecardNum = " + bnum;
            ConnectionConfig con = new ConnectionConfig();
            try {
                con.update(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            update(new ActionEvent());
            con.close();


        }



    }

    public void transfer(ActionEvent actionEvent) {
        String user = newUser.getText();
        // check admin or not

        
    }
//    public static ObservableList<Card> initialize() {
//        ConnectionConfig con = new ConnectionConfig();
//        ResultSet rs = con.getResult("select * from Breezecard");
//        data = FXCollections.observableArrayList();
//        try {
//            while(rs.next()){
//                //Iterate Row
////                ObservableList<String> row = FXCollections.observableArrayList();
////                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
////                    //Iterate Column
////                    row.add(rs.getString(i));
////                }
////                System.out.println("Row [1] added "+row );
////                data.add(row);
//                data.add(new Card(rs.getString("BreezeCardNum"), rs.getString("Value"), rs.getString("BelongsTo")));
//
//
//            }
//
////            System.out.println(columnCardNumber==null);
////            columnCardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
////            columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
////            columnOwner.setCellValueFactory(new PropertyValueFactory<>("owner"));
////
////            tableView.setItems(null);
////            tableView.setItems(data);
//        } catch (SQLException e) {
//            System.out.println("sql exception");
//            e.printStackTrace();
//        }
//        con.close();
//        return data;
////
////        //FINALLY ADDED TO TableView
////        System.out.println(tableView == null);
////        tableView.setItems(data);

//    }


}
