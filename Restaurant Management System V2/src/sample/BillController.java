package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import javax.print.*;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.ResourceBundle;

public class BillController implements Initializable {

    private static DecimalFormat df2 = new DecimalFormat(".##");

    @FXML
    private TableView<ModelBillTable> billtable;

    @FXML
    private TableColumn<?, ?> dish_id_col;

    @FXML
    private TableColumn<?, ?> name_col;

    @FXML
    private TableColumn<?, ?> cat_col;

    @FXML
    private TableColumn<?, ?> quantcol;

    @FXML
    private TableColumn<?, ?> pricecol;

    @FXML
    private TableColumn<?, ?> totalcol;

    @FXML
    private Label subtot;

    @FXML
    private Label cgst;

    @FXML
    private Label sgst;

    @FXML
    private Label discount;

    @FXML
    private Label stax;

    @FXML
    private Label finalbill;

    @FXML
    private Label orderlbl;

    @FXML
    private JFXTextField quant;

    @FXML
    private JFXTextField discountper;

    @FXML
    private TextArea area;

    @FXML
    private JFXComboBox<String> catcombo;

    @FXML
    private JFXComboBox<String> prodcombo;

    @FXML
    private JFXTextField searchprod;

    @FXML
    private JFXTextField searchquant;

    @FXML
    private CheckBox cgstcheck;

    @FXML
    private CheckBox sgstcheck;

    @FXML
    private CheckBox servicecheck;

    @FXML
    private Button addsearch;

    @FXML
    private Button reset;

    @FXML
    private JFXButton back;

    @FXML
    private JFXButton home;

    @FXML
    private JFXButton out;

    ObservableList<String> catlist= FXCollections.observableArrayList();

    ObservableList<String> prodlist= FXCollections.observableArrayList();

    ObservableList<ModelBillTable> oblist= FXCollections.observableArrayList();

    ObservableList<String> autocomplete= FXCollections.observableArrayList();

    float subtotal=0;
    double totalbillamount,cgstax,sgstax,servicetax,discountapp,round,total;
    double cgstper=0,sgstper=0,serviceper=0;

    String order=null;

    public void addbillitem(ActionEvent actionEvent)
    {
        if(prodcombo.getSelectionModel().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Select Dish");
            errorAlert.showAndWait();
        }
        else if(quant.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Enter Quantity");
            errorAlert.showAndWait();
        }
        else {
            try {
                Connection con = DBConnection.getConnection();
                ResultSet rs = con.createStatement().executeQuery("select * from dishes where dish_name='" + prodcombo.getValue()+"'");
                ResultSet rs2 = null;

                while (rs.next()) {
                    rs2= con.createStatement().executeQuery("select * from prices where dish_id=" + rs.getString("dish_id"));
                }

                while (rs2.next())
                {
                    oblist.add(new ModelBillTable(rs.getString("dish_id"), rs.getString("dish_name"), rs.getString("cat_name"), rs2.getString("price"), quant.getText()));

                    subtotal = subtotal + (Float.valueOf(rs2.getString("price")) * Integer.parseInt(quant.getText()));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            dish_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            cat_col.setCellValueFactory(new PropertyValueFactory<>("cat"));
            pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
            quantcol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            totalcol.setCellValueFactory(new PropertyValueFactory<>("total"));

            catcombo.setValue("");
            prodcombo.setValue("");
            quant.setText("");

            billtable.setItems(oblist);

            calculatebillv2();
        }
    }

    public void generatereceipt(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        if(totalbillamount==0)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Logical Error!");
            errorAlert.setContentText("Please Calculate Bill Before Generating");
            errorAlert.showAndWait();
        }
        else {

            ModelBillTable mod = new ModelBillTable();

            Calendar now = Calendar.getInstance();

            Connection con = DBConnection.getConnection();

            String sql = "insert into orders values()";
            Statement statement = con.createStatement();
            statement.executeUpdate(sql);

            PreparedStatement ps = con.prepareStatement("select max(ord_no) from orders");
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                order=rs.getString("max(ord_no)");

            orderlbl.setText(order);

            String str=null;

            area.setText("");

            area.setText(area.getText() + "---------------------------------------------------------\n");
            area.setText(area.getText() + "                                   Restaurant Billing System\n");
            area.setText(area.getText() + "---------------------------------------------------------\n");
            area.setText(area.getText() + " Address\n");
            area.setText(area.getText() + "                            GSTIN\n");
            area.setText(area.getText() + "---------------------------------------------------------\n");
            area.setText(area.getText() + "              Date : " + now.getTime() + "\n");
            area.setText(area.getText() + "---------------------------------------------------------\n");
            area.setText(area.getText() + "                                 Order No : "+order+"\n");
            area.setText(area.getText() + "---------------------------------------------------------\n");
            //area.setText(area.getText() + " Description         Qty       Rate           Amount  \n");
            str=str = String.format("%-24s %-8s %-8s %-8s\n","Description","Qty","Rate","Amount");
            area.setText(area.getText()+str);
            area.setText(area.getText() + "---------------------------------------------------------\n");

            for (int i = 0; i < billtable.getItems().size(); i++) {
                mod = billtable.getItems().get(i);

                //area.setText(area.getText() + " " + mod.name + "      " + mod.quantity + "         " + mod.price + "            " + Float.valueOf(mod.price) * Integer.parseInt(mod.quantity) + "\n");
                str = String.format("%-24s %-8s %-8s %-8s\n", mod.name, mod.quantity, Float.valueOf(mod.price), Float.valueOf(mod.price) * Integer.parseInt(mod.quantity));
                area.setText(area.getText()+str);
            }

            area.setText(area.getText() + "---------------------------------------------------------\n");
            area.setText(area.getText() + "                                       Sub-Total :      " + subtotal + "\n");
            area.setText(area.getText() + "                                      CGST(2.5%) :     " + cgstax + "\n");
            area.setText(area.getText() + "                                      SGST(2.5%) :     " + sgstax + "\n");
            area.setText(area.getText() + "                          Service Charge(7%) :    " + servicetax + "\n");

            if(round!=0) {
                area.setText(area.getText() + "                                                Total :    " + totalbillamount + "\n");
                area.setText(area.getText() + "                                       Round Off :     -" + round + "\n");
            }

            if(Integer.parseInt(discountper.getText())>0)
                area.setText(area.getText() + "                                  Discount("+discountper.getText()+"%) :    " + discountapp + "\n");

            area.setText(area.getText() + "---------------------------------------------------------\n");
            area.setText(area.getText() + "                                        Final Total :     " + total + "\n");
            area.setText(area.getText() + "---------------------------------------------------------\n");
            area.setText(area.getText() + "                                      Thank You\n");
            area.setText(area.getText() + "---------------------------------------------------------\n");

            area.setEditable(false);

            fileWriter(order);
        }

    }

    @FXML
    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("home.fxml"));
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

    @FXML
    public void productselection() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps= con.prepareStatement("select dish_name from dishes where cat_name='" + catcombo.getValue()+"'");
        ResultSet rs = ps.executeQuery();

        while (rs.next())
            if(!prodcombo.getItems().contains(rs.getString("dish_name")))
                prodlist.add(rs.getString("dish_name"));

        prodcombo.setItems(prodlist);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        discountper.setText("0");
        discountapp=0;
        cgstcheck.setSelected(true);
        sgstcheck.setSelected(true);
        servicecheck.setSelected(true);
        cgstper=sgstper=0.025;
        serviceper=0.07;

        Connection con= null;
        try {
            con = DBConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql="select * from categories";
        PreparedStatement ps= null;
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs= null;
        try {
            rs = ps.executeQuery();

            while(rs.next()) {
                try {
                    catlist.add(rs.getString("cat_name"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        catcombo.setItems(catlist);

        catcombo.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
                    try {
                        prodcombo.getItems().clear();
                        productselection();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        );

        try {
            autocomplete();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        discountper.textProperty().addListener((observable,oldvalue,newvalue) -> {

            if (Integer.parseInt(discountper.getText())>100) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Input Error!");
                errorAlert.setContentText("Please Enter A Valid Discount Percentage");
                errorAlert.showAndWait();
            }
            else {
                discountapp = (Double.parseDouble(discountper.getText()) * subtotal) / 100;
                calculatebillv2();
            }
        });

        cgstcheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    cgstper = 0.025;
                    calculatebillv2();
                }
                else
                {
                    cgstper=0;
                    calculatebillv2();
                }
            }
        });

        sgstcheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    sgstper = 0.025;
                    calculatebillv2();
                }
                else
                {
                    sgstper=0;
                    calculatebillv2();
                }
            }
        });

        servicecheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    serviceper = 0.07;
                    calculatebillv2();
                }
                else
                {
                    serviceper=0;
                    calculatebillv2();
                }
            }
        });

        searchquant.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    searchquant.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        quant.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    quant.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void fileWriter(String file_name) {
        ObservableList<CharSequence> paragraph = area.getParagraphs();
        Iterator<CharSequence> iter = paragraph.iterator();
        try
        {
            BufferedWriter bf = new BufferedWriter(new FileWriter(new File("Orders/ord"+file_name+".txt")));
            while(iter.hasNext())
            {
                CharSequence seq = iter.next();
                bf.append(seq);
                bf.newLine();
            }
            bf.flush();
            bf.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void autocomplete() throws SQLException, ClassNotFoundException {

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from dishes");
        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {
            autocomplete.add(rs.getString("dish_name"));
        }

        TextFields.bindAutoCompletion(searchprod,autocomplete);
    }

    @FXML
    public void search(ActionEvent actionEvent)
    {
        if(searchprod.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Select Product");
            errorAlert.showAndWait();
        }
        else if(searchquant.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Enter Quantity");
            errorAlert.showAndWait();
        }
        else {
            try {
                Connection con = DBConnection.getConnection();
                ResultSet rs = con.createStatement().executeQuery("select * from dishes where dish_name='" + searchprod.getText()+"'");

                ResultSet rs2=null;

                while(rs.next()) {
                    rs2 = con.createStatement().executeQuery("select * from prices where dish_id=" + rs.getString("dish_id"));

                    if (rs.next() && rs2.next()) {

                        oblist.add(new ModelBillTable(rs.getString("dish_id"), rs.getString("dish_name"), rs.getString("cat_name"), rs2.getString("price"), searchquant.getText()));

                        subtotal = subtotal + (Float.valueOf(rs2.getString("price")) * Integer.parseInt(searchquant.getText()));
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Input Error!");
                        errorAlert.setContentText("Invalid Product");
                        errorAlert.showAndWait();
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            dish_id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
            cat_col.setCellValueFactory(new PropertyValueFactory<>("cat"));
            pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
            quantcol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            totalcol.setCellValueFactory(new PropertyValueFactory<>("total"));

            billtable.setItems(oblist);

            calculatebillv2();

            searchprod.setText("");
            searchquant.setText("");
        }
    }

    @FXML
    public void validation()
    {
        if(catcombo.getSelectionModel().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Select Category");
            errorAlert.showAndWait();
        }
        else if(prodcombo.getItems().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("No Products Available In This Category");
            errorAlert.showAndWait();
        }
    }

    @FXML
    public void removeitem(ActionEvent actionEvent)
    {
        if(billtable.getSelectionModel().getSelectedItem()==null)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Select Item To Remove");
            errorAlert.showAndWait();
        }
        else
        {
            ModelBillTable temp=billtable.getSelectionModel().getSelectedItem();
            subtotal=subtotal-Float.parseFloat(temp.getTotal());

            billtable.getItems().remove(billtable.getSelectionModel().getSelectedItem());

            calculatebillv2();
        }
    }

    private static boolean feedPrinter(byte[] b) {
        try {
            AttributeSet attrSet = new HashPrintServiceAttributeSet(new PrinterName("POS-58-Series (1)", null));

            DocPrintJob job = PrintServiceLookup.lookupPrintServices(null, attrSet)[0].createPrintJob();
            //PrintServiceLookup.lookupDefaultPrintService().createPrintJob();

            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(b, flavor, null);
            PrintJobWatcher pjDone = new PrintJobWatcher(job);

            job.print(doc, null);
            pjDone.waitForDone();
            System.out.println("Done !");
        } catch (javax.print.PrintException pex) {
            System.out.println("Printer Error " + pex.getMessage());
            return false;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void print(ActionEvent actionEvent)
    {
        if(area.getText().isEmpty())
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Error!");
            errorAlert.setContentText("Please Generate Bill Before Printing");
            errorAlert.showAndWait();
        }
        else {
            PrinterOptions p = new PrinterOptions();
            String str = null;

            p.resetAll();
            p.initialize();
            p.feedBack((byte) 2);

            p.chooseFont(4);

            p.addLineSeperator();
            p.newLine();

            p.alignCenter();
            p.setText("Restaurant Billing System");
            p.newLine();

            p.addLineSeperator();
            p.newLine();

            p.alignCenter();
            p.setText("Address");
            p.newLine();

            p.alignCenter();
            p.setText("GSTIN");
            p.newLine();

            p.addLineSeperator();
            p.newLine();

            Calendar now = Calendar.getInstance();
            p.alignCenter();
            p.setText("Date : " + now.getTime());
            p.newLine();

            p.addLineSeperator();
            p.newLine();

            p.alignCenter();
            p.setText("Order No : " + order);
            p.newLine();

            p.addLineSeperator();
            p.newLine();

            p.alignLeft();
            str = String.format("%-24s %-3s %-6s %-6s", "Description", "Qty", "Rate", "Amount");
            p.setText(str);
            p.newLine();

            p.addLineSeperator();
            p.newLine();

            p.alignLeft();
            ModelBillTable mod = new ModelBillTable();

            for (int i = 0; i < billtable.getItems().size(); i++) {
                mod = billtable.getItems().get(i);

                //p.setText(mod.name + "    " + mod.quantity + "       " + mod.price + "       " + Float.valueOf(mod.price) * Integer.parseInt(mod.quantity));
                str = String.format("%-24s %-3s %-6s %-6s", mod.name, mod.quantity, Float.valueOf(mod.price), Float.valueOf(mod.price) * Integer.parseInt(mod.quantity));
                p.setText(str);
                p.newLine();
            }

            p.addLineSeperator();
            p.newLine();

            p.alignRight();
            p.setText("Sub-Total : " + subtotal);
            p.newLine();

            p.alignRight();
            p.setText("CGST(2.5%) : " + cgstax);
            p.newLine();

            p.alignRight();
            p.setText("SGST(2.5%) : " + sgstax);
            p.newLine();

            p.alignRight();
            p.setText("Service Charge(7%) : " + servicetax);
            p.newLine();

            if (Integer.parseInt(discountper.getText()) > 0) {
                p.alignRight();
                p.setText("Discount(" + discountper.getText() + "%) : -" + discountapp);
                p.newLine();
            }

            p.alignRight();
            p.setText("Total : " + totalbillamount);
            p.newLine();

            if (round != 0) {
                p.alignRight();
                p.setText("Round-Off : -" + round);
                p.newLine();
            }

            p.addLineSeperator();
            p.newLine();

            p.alignRight();
            p.setText("Final Total : " + total);
            p.newLine();

            p.addLineSeperator();
            p.newLine();

            p.alignCenter();
            p.setText("Thank You!");
            p.newLine();

            p.addLineSeperator();
            p.newLine();

            p.feed((byte) 3);
            p.finit();

            feedPrinter(p.finalCommandSet().getBytes());
        }
    }

    public void calculatebillv2()
    {
        subtot.setText(Float.toString(subtotal));

        cgstax = subtotal * cgstper;
        cgst.setText(Double.toString(cgstax));

        sgstax=subtotal * sgstper;
        sgst.setText(Double.toString(sgstax));

        servicetax = Double.parseDouble(df2.format(subtotal * serviceper));
        stax.setText(Double.toString(servicetax));

        discount.setText(Double.toString(discountapp));

        totalbillamount = Double.parseDouble(df2.format(subtotal + cgstax + sgstax + servicetax - discountapp));
        finalbill.setText(Double.toString(totalbillamount));

        total=Math.floor(totalbillamount);

        round= Double.parseDouble(df2.format(totalbillamount-total));
    }

    public void reset(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) reset.getScene().getWindow();
        AnchorPane root;
        root = (AnchorPane) FXMLLoader.load(getClass().getResource("bill.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}
