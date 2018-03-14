package sample;

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class Controller {

//    public Controller() {
//
//    }

//    @FXML
//    private void przycisk(ActionEvent event){
//        System.out.println("Coś tam");
//        String fullname = pole.getText()+ " "+combo.getValue();
//        area.setText(fullname);
//    }
    @FXML
    TableView<sample.Kosztorysy> tabela;
    @FXML
    TableColumn<sample.Kosztorysy, Integer> lp;
    @FXML
    TableColumn<sample.Kosztorysy, Double> ilosc, cena, wartosc;
    @FXML
    TableColumn<sample.Kosztorysy, String> norma, opis, jm;



    @FXML
    public void initialize(){
//        combo.getItems().addAll("jeden", "dwa", "trzy");
        tabela.getItems().add(new sample.Kosztorysy(1,"KNR", "Malowanie", "szt", 10,15,0));
        tabela.getItems().add(new sample.Kosztorysy(2,"KNR", "Malowanie", "m3", 100.13,13,15));
        tabela.getItems().add(new sample.Kosztorysy(3,"KNR", "Malowanie", "m2", 13.13,66.66,0));
        norma.setVisible(true);
        lp.setCellValueFactory(new PropertyValueFactory<>("lp"));
        norma.setCellValueFactory(new PropertyValueFactory<>("norma"));
        opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        jm.setCellValueFactory(new PropertyValueFactory<>("jm"));
        ilosc.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
        cena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        wartosc.setCellValueFactory(new PropertyValueFactory<>("wartosc"));
//        lp.setCellFactory(TextFieldTableCell.forTableColumn());
        norma.setCellFactory(TextFieldTableCell.forTableColumn());
        opis.setCellFactory(TextFieldTableCell.forTableColumn());
        jm.setCellFactory(TextFieldTableCell.forTableColumn());
//        ilosc.setCellFactory(TextFieldTableCell.forTableColumn());

    }
    public void onEditCommitLp(TableColumn.CellEditEvent<sample.Kosztorysy, Integer> kosztorysyIntegerCellEditEvent){
        kosztorysyIntegerCellEditEvent.getNewValue();
    }

    public void onEditCommitNorma(TableColumn.CellEditEvent<sample.Kosztorysy,String> kosztorysyStringCellEditEvent) {
        kosztorysyStringCellEditEvent.getNewValue();
    }

    public void onEditCommitOpis(TableColumn.CellEditEvent<sample.Kosztorysy,String> kosztorysyStringCellEditEvent) {
        kosztorysyStringCellEditEvent.getNewValue();
    }

    public void onEditCommitJm(TableColumn.CellEditEvent<sample.Kosztorysy,String> kosztorysyStringCellEditEvent) {
        kosztorysyStringCellEditEvent.getNewValue();
    }

    public void onEditCommitIlosc(TableColumn.CellEditEvent<sample.Kosztorysy,Double> kosztorysyDoubleCellEditEvent) {
        kosztorysyDoubleCellEditEvent.getNewValue();
    }

    public void onEditCommitCena(TableColumn.CellEditEvent<sample.Kosztorysy,Double> kosztorysyDoubleCellEditEvent) {
        kosztorysyDoubleCellEditEvent.getNewValue();
    }
}
