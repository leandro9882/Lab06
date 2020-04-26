/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.DAO.MeteoDAO;
import it.polito.tdp.meteo.model.Citta;

import it.polito.tdp.meteo.model.Model2;
import it.polito.tdp.meteo.model.Rilevamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	Model2 model;
	MeteoDAO dao;
	ObservableList<Integer> lista=FXCollections.observableArrayList(getMesi());
	
	private List<Integer> getMesi(){
		List<Integer> m=new ArrayList<>();
		for(int i=1;i<13;i++) {
			m.add(i);
		}
		return m;
	}
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaSequenza(ActionEvent event) {
    	int mese=boxMese.getValue();
    	List<Rilevamento> c=model.trovaSequenza(mese);
    	String risultato="";
    	for(Rilevamento citta:c) {
    		risultato+=citta.toString()+"\n";
    	}
    	txtResult.setText(risultato);
    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {
    	int mese=boxMese.getValue();
    	String risultato=this.model.getUmiditaMedia(mese);
    	txtResult.setText(risultato);
   
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        boxMese.setItems(lista);
    }
    public void setModel(Model2 model) {
    	this.model=model;
    }
}

