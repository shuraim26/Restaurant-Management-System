package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {

    @FXML
    private JFXTextField dish_id;

    @FXML
    private JFXButton search_dish;

    @FXML
    private JFXTextField dish_name;

    @FXML
    private JFXTextField dish_price;

    @FXML
    private JFXComboBox<String> dish_cat;

    PreparedStatement ps=null;
    ResultSet rs=null;
    Connection con=DBConnection.getConnection();

    ObservableList<String> list= FXCollections.observableArrayList();

    public UpdateController() throws SQLException {
    }

    @FXML
    public void update_dish(ActionEvent actionEvent) throws SQLException {

        if(dish_id.getText().isEmpty() || dish_name.getText().isEmpty() || dish_cat.getValue().isEmpty() || dish_price.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Enter Product ID,Name,Category and Price");
            errorAlert.showAndWait();
        }
        else {
            String sql1="update dishes set dish_name='"+dish_name.getText()+"',cat_id=(select cat_id from categories where cat_name='"+dish_cat.getValue()+"'),cat_name='"+dish_cat.getValue()+"' where dish_id="+dish_id.getText();
            ps=con.prepareStatement(sql1);
            ps.executeUpdate();

            String sql2="update prices set dish_id="+dish_id.getText()+",price="+dish_price.getText()+" where dish_id="+dish_id.getText();
            ps=con.prepareStatement(sql2);
            ps.executeUpdate();

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Alert!");
            errorAlert.setContentText("Dish Updated Successfully!");
            errorAlert.showAndWait();

            dish_name.setText("");
            dish_cat.setValue("");
            dish_price.setText("");
            dish_id.setText("");
        }
    }

    @FXML
    public void retrieve(ActionEvent actionEvent) throws SQLException {
        String sql1="select * from dishes where dish_id="+dish_id.getText();
        ps=con.prepareStatement(sql1);
        rs=ps.executeQuery();

        while(rs.next())
        {
            dish_name.setText(rs.getString("dish_name"));

            dish_cat.getSelectionModel().select(rs.getString("cat_name"));
        }

        String sql2="select * from prices where dish_id="+dish_id.getText();
        ps=con.prepareStatement(sql2);
        rs=ps.executeQuery();

        while(rs.next())
            dish_price.setText(rs.getString("price"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String sql="select * from categories";
        try {
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();

            while(rs.next())
                list.add(rs.getString("cat_name"));

            dish_cat.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
