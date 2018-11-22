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

public class DispDishAdminController implements Initializable {

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton home;

    @FXML
    private JFXButton out;

    @FXML
    private JFXTextField search;

    @FXML
    private TableView<ModelDishTable> table;

    @FXML
    private TableColumn<?, ?> dish_id_col;

    @FXML
    private TableColumn<?, ?> dish_name_col;

    @FXML
    private TableColumn<?, ?> cat_id_col;

    @FXML
    private TableColumn<?, ?> cat_name_col;

    @FXML
    private TableColumn<?, ?> dish_price_col;

    ObservableList<ModelDishTable> oblist= FXCollections.observableArrayList();

    Connection con=DBConnection.getConnection();
    PreparedStatement ps1=null;
    PreparedStatement ps2=null;
    ResultSet rs1=null;
    ResultSet rs2=null;

    public DispDishAdminController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            String sql1="select * from dishes";
            ps1=con.prepareStatement(sql1);
            rs1=ps1.executeQuery();

            while(rs1.next())
            {
                String sql2 = "select * from prices where dish_id=" + rs1.getString("dish_id");
                ps2 = con.prepareStatement(sql2);
                rs2 = ps2.executeQuery();

                while(rs2.next())
                    oblist.add(new ModelDishTable(rs1.getString("dish_id"),rs1.getString("dish_name"),rs1.getString("cat_id"),rs1.getString("cat_name"),rs2.getString("price")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        dish_id_col.setCellValueFactory(new PropertyValueFactory<>("dish_id"));
        dish_name_col.setCellValueFactory(new PropertyValueFactory<>("dish_name"));
        cat_id_col.setCellValueFactory(new PropertyValueFactory<>("cat_id"));
        cat_name_col.setCellValueFactory(new PropertyValueFactory<>("cat_name"));
        dish_price_col.setCellValueFactory(new PropertyValueFactory<>("dish_price"));

        table.setItems(oblist);

        FilteredList<ModelDishTable> filteredData = new FilteredList<>(oblist, p -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(temp -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (temp.getDish_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<ModelDishTable> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    @FXML
    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("admin_dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void home(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) home.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("home_admin.fxml"));
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
