package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteCatController {

    @FXML
    private JFXTextField cat_id;

    @FXML
    private Label cat_name;

    PreparedStatement ps=null;
    ResultSet rs=null;
    Connection con=DBConnection.getConnection();

    public DeleteCatController() throws SQLException {
    }

    @FXML
    public void del_cat(ActionEvent actionEvent) throws SQLException {

        if(cat_id.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Enter Category ID");
            errorAlert.showAndWait();
        }
        else {
            String sql = "delete from categories where cat_id=" + cat_id.getText();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Alert!");
            errorAlert.setContentText("Category Deleted Successfully! Any Dishes belonging to this Category will automatically be deleted");
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void retrieve(ActionEvent actionEvent) throws SQLException {

        if(cat_id.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Enter Category ID");
            errorAlert.showAndWait();
        }
        else {
            String sql = "select cat_name from categories where cat_id=" + cat_id.getText();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next())
                cat_name.setText(rs.getString("cat_name"));
            else
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Data Error!");
                errorAlert.setContentText("Invalid Category ID");
                errorAlert.showAndWait();
            }
        }
    }
}
