package gui;


import java.net.URL;
import java.util.ResourceBundle;

import db.DbExceptions;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Autor;
import model.services.AutorService;

public class AutorFormController implements Initializable{
	
	private Autor entity;
	
	private AutorService service;
	
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtNome;
	
	@FXML
	private Label labelErrorNome;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	public void setAutor(Autor entity) {
		this.entity = entity;		
	}
	
	public void setAutorService(AutorService service) {
		this.service = service;
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entidade esá vazia");
		}
		if(service == null) {
			throw new IllegalStateException("Seriço está vazia");
		}
		try {
			
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
			
		}
		catch (DbExceptions e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private Autor getFormData() {
		
		Autor autor = new Autor();
				
		autor.setNome(txtNome.getText());
		
		return autor;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {	
		
		initializeNodes();
		
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
	}
	
	public void updateFormDate() {
		if(entity == null) {
			throw new IllegalStateException("Entidade está vazia");
		}
		
		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getNome());
	}

}
