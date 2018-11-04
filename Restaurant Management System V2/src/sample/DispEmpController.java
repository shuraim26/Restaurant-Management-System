package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DispEmpController implements Initializable {

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton home;

    @FXML
    private JFXButton out;

    @FXML
    private JFXTextField search;

    @FXML
    private TableView<ModelEmpTable> table;

    @FXML
    private TableColumn<?, ?> uname_col;

    ObservableList<ModelEmpTable> oblist= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Connection con= DBConnection.getConnection();

            String sql="select * from users";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();

            while(rs.next())
            {
                oblist.add(new ModelEmpTable(rs.getString("username")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        uname_col.setCellValueFactory(new PropertyValueFactory<>("uname"));

        table.setItems(oblist);

        FilteredList<ModelEmpTable> filteredData = new FilteredList<>(oblist, p -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(temp -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (temp.getUname().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<ModelEmpTable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
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
