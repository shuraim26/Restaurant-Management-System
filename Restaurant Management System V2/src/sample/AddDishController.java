package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddDishController implements Initializable {

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton home;

    @FXML
    private JFXButton out;

    @FXML
    private JFXTextField dish_name;

    @FXML
    private JFXButton ok;

    @FXML
    private JFXComboBox<String> dish_cat;

    @FXML
    private JFXTextField dish_price;

    ObservableList<String> list= FXCollections.observableArrayList();

    Connection con=DBConnection.getConnection();
    PreparedStatement ps=null;
    ResultSet rs=null;

    public AddDishController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            String sql="select * from categories";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();

            while(rs.next())
                list.add(rs.getString("cat_name"));

            dish_cat.setItems(list);


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void add_dish(ActionEvent actionEvent) throws SQLException {

        if(dish_name.getText().isEmpty() || dish_cat.getValue().isEmpty() || dish_price.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Enter Dish Name,Category and Price");
            errorAlert.showAndWait();
        }
        else {
            String check = "select * from dishes where dish_name='" + dish_name.getText() + "'";
            ps = con.prepareStatement(check);
            rs = ps.executeQuery();

            if (rs.next()) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Data Error!");
                errorAlert.setContentText("Dish Already Exists");
                errorAlert.showAndWait();
            } else {
                String sql1="insert into dishes(dish_name,cat_id,cat_name) values('"+dish_name.getText()+"',(select cat_id from categories where cat_name='"+dish_cat.getValue()+"'),'"+dish_cat.getValue()+"')";
                String sql2="insert into prices values((select dish_id from dishes where dish_name='"+dish_name.getText()+"'),"+dish_price.getText()+")";
                ps = con.prepareStatement(sql1);
                ps.executeUpdate();
                ps = con.prepareStatement(sql2);
                ps.executeUpdate();

                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setHeaderText("Alert!");
                infoAlert.setContentText("Dish Added Successfully!");
                infoAlert.showAndWait();

                dish_name.setText("");
                dish_cat.setValue("");
                dish_price.setText("");
            }
        }
    }

    @FXML
    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void home(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) home.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void out(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) out.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
