package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.sql.*;

public class DeleteController {

    @FXML
    private JFXTextField dish_id;

    @FXML
    private JFXButton search_dish;

    @FXML
    private JFXButton del_btn;

    @FXML
    private Label dish_name;

    @FXML
    private Label dish_cat;

    @FXML
    private Label dish_price;

    ResultSet rs=null;
    PreparedStatement ps=null;
    Connection con=DBConnection.getConnection();

    public DeleteController() throws SQLException {
    }

    @FXML
    public void delete_dish(ActionEvent actionEvent) throws SQLException {

        if(dish_id.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Enter Dish ID");
            errorAlert.showAndWait();
        }
        else {
            String check = "select * from dishes where dish_id='" + dish_id.getText() + "'";
            ps = con.prepareStatement(check);
            rs = ps.executeQuery();

            if (rs.next()) {
                String sql = "delete from dishes where dish_id=" + dish_id.getText();
                ps = con.prepareStatement(sql);
                ps.executeUpdate();

                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setHeaderText("Alert!");
                infoAlert.setContentText("Dish Deleted Successfully!");
                infoAlert.showAndWait();

                dish_id.setText("");
                dish_name.setText("");
                dish_cat.setText("");
                dish_price.setText("");
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Data Error!");
                errorAlert.setContentText("Invalid Dish ID!");
                errorAlert.showAndWait();
            }
        }
    }

    @FXML
    public void retrieve(ActionEvent actionEvent) throws SQLException
    {
        String sql1="select * from dishes where dish_id="+dish_id.getText();
        ps=con.prepareStatement(sql1);
        rs=ps.executeQuery();

        while(rs.next()) {
            dish_name.setText(rs.getString("dish_name"));
            dish_cat.setText(rs.getString("cat_name"));
        }


        String sql2 = "select * from prices where dish_id=" + dish_id.getText();
        ps = con.prepareStatement(sql2);
        rs = ps.executeQuery();

        while (rs.next()) {
            dish_price.setText(rs.getString("price"));
        }
    }
}
