package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbExceptions;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Autor;
import model.entities.LivroAutor;
import model.exceptions.ValidationException;
import model.services.AutorService;
import model.services.LivroAutorService;

public class LivroAutorFormController implements Initializable {

	private LivroAutor entity;

	private LivroAutorService service;

	private AutorService autorService;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	public void subscribDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private ComboBox<Autor> comboBoxAutor;

	@FXML
	private Label labelErrorNome;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	private ObservableList<Autor> obsList;

	public void setLivroAutor(LivroAutor entity) {
		this.entity = entity;
	}

	public void setServices(LivroAutorService service, AutorService autorService) {
		this.service = service;
		this.autorService = autorService;
	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entidade esá vazia");
		}
		if (service == null) {
			throw new IllegalStateException("Seriço está vazia");
		}
		try {

			entity = getFormData();
			service.saveOrUpdate(entity);
			notfyDataChangeListeners();
			Utils.currentStage(event).close();

		} catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		} catch (DbExceptions e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notfyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}

	}

	private LivroAutor getFormData() {

		LivroAutor livroAutor = new LivroAutor();

		ValidationException exception = new ValidationException("Erro de Validação");

		livroAutor.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("titulo", "  O campo nao pode ser vazio");
		}
		livroAutor.setTitulo(txtNome.getText());

		if (exception.getErrors().size() > 0) {
			throw exception;

		}

		return livroAutor;
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
		Constraints.setTextFieldMaxLength(txtNome, 100);
		
		initializeComboBoxAutor();
	}

	public void updateFormDate() {
		if (entity == null) {
			throw new IllegalStateException("Entidade está vazia");
		}

		txtId.setText(String.valueOf(entity.getId()));
		txtNome.setText(entity.getTitulo());
		
		if(entity.getAutor() == null) {
			comboBoxAutor.getSelectionModel().selectFirst();
		}
		else {
			comboBoxAutor.setValue(entity.getAutor());
		}
		
	}

	public void loadAssociatedObjects() {
		if (autorService == null) {
			throw new IllegalStateException("AutorService estava vazio");
		}
		List<Autor> list = autorService.findAll();
		obsList = FXCollections.observableArrayList(list);
		comboBoxAutor.setItems(obsList);
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("titulo")) {
			labelErrorNome.setText(errors.get("titulo"));
		}
	}

	private void initializeComboBoxAutor() {
		Callback<ListView<Autor>, ListCell<Autor>> factory = lv -> new ListCell<Autor>() {
			@Override
			protected void updateItem(Autor item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getNome());
			}
		};
		comboBoxAutor.setCellFactory(factory);
		comboBoxAutor.setButtonCell(factory.call(null));
	}

}
