package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateCatController {

    @FXML
    private JFXTextField cat_id;

    @FXML
    private JFXTextField cat_name;

    PreparedStatement ps=null;
    ResultSet rs=null;
    Connection con=DBConnection.getConnection();

    public UpdateCatController() throws SQLException {
    }

    @FXML
    public void update_cat(ActionEvent actionEvent) throws SQLException {
        if(cat_id.getText().isEmpty() || cat_name.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please enter Category ID and Name");
            errorAlert.showAndWait();
        }
        else
        {
            String sql="update categories set cat_name='"+cat_name.getText()+"' where cat_id="+cat_id.getText();
            ps=con.prepareStatement(sql);
            ps.executeUpdate();

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Alert!");
            errorAlert.setContentText("Successfully Updated Category!");
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void retrieve(ActionEvent actionEvent) throws SQLException {
        if(cat_id.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Enter Category ID!");
            errorAlert.showAndWait();
        }
        String sql="select cat_name from categories where cat_id="+cat_id.getText();
        ps=con.prepareStatement(sql);
        rs=ps.executeQuery();

        if(rs.next())
            cat_name.setText(rs.getString("cat_name"));
        else
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Data Error!");
            errorAlert.setContentText("Category Already Exists");
            errorAlert.showAndWait();
        }
    }
}
