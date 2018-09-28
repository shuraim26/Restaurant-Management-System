package sample;

import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.protocol.Resultset;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddCatController {

    @FXML
    private JFXTextField cat_name;

    PreparedStatement ps=null;
    ResultSet rs=null;
    Connection con=DBConnection.getConnection();

    public AddCatController() throws SQLException {
    }

    @FXML
    public void add_cat(ActionEvent actionEvent) throws SQLException {
        if(cat_name.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Enter Category Name");
            errorAlert.showAndWait();
        }
        else {
            String check = "select * from categories where cat_name='" + cat_name.getText() + "'";
            ps = con.prepareStatement(check);
            rs=ps.executeQuery();

            if (rs.next()) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Data Error!");
                errorAlert.setContentText("Dish Already Exists");
                errorAlert.showAndWait();
            } else {
                String sql="insert into categories(cat_name) values('"+cat_name.getText()+"')";
                ps = con.prepareStatement(sql);
                ps.executeUpdate();

                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setHeaderText("Alert!");
                infoAlert.setContentText("Category Added Successfully!");
                infoAlert.showAndWait();

                cat_name.setText("");
            }
        }
    }
}
