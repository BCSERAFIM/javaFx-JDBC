package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbExceptions;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.LivroAutor;
import model.services.LivroAutorService;

public class LivroAutorListController implements Initializable, DataChangeListener {

	private LivroAutorService service;

	@FXML
	private TableView<LivroAutor> tableViewLivroAutor;

	@FXML
	private TableColumn<LivroAutor, Integer> tableColumnId;

	@FXML
	private TableColumn<LivroAutor, String> tableColumnNome;

	@FXML
	private TableColumn<LivroAutor, LivroAutor> tableColumnEDIT;

	@FXML
	private TableColumn<LivroAutor, LivroAutor> tableColumnREMOVE;

	@FXML
	private Button btNew;

	private ObservableList<LivroAutor> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {

		Stage parentStage = Utils.currentStage(event);
		LivroAutor obj = new LivroAutor();
		createDialogForm(obj, "/gui/LivroAutorForm.fxml", parentStage);

	}

	public void setLivroAutorService(LivroAutorService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("titulo"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewLivroAutor.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Serviço estava nulo");
		}

		List<LivroAutor> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewLivroAutor.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(LivroAutor obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			LivroAutorFormController controller = loader.getController();
			controller.setLivroAutor(obj);
			controller.setLivroAutorService(new LivroAutorService());
			controller.subscribDataChangeListener(this);
			controller.updateFormDate();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entrar com os dados do LivroAutor");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<LivroAutor, LivroAutor>() {
			private final Button button = new Button("edit");

			@Override
			protected void updateItem(LivroAutor obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/LivroAutorForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<LivroAutor, LivroAutor>() {
			private final Button button = new Button("remove");

			@Override
			protected void updateItem(LivroAutor obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(LivroAutor obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Realemnte quer Deletar? ");

		if (result.get() == ButtonType.OK) {
			if (service == null) {

				throw new IllegalStateException("Serviço está vazio");
			}
			try {
				service.remove(obj);
				updateTableView();
			} catch (DbExceptions e) {
				Alerts.showAlert("Erro ao remover o objeto", null, e.getMessage(), AlertType.ERROR);

			}

		}
	}

}
