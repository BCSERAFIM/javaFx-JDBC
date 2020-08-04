package gui;

import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.entities.Autor;

public class AutorListController implements Initializable {
	
	
	@FXML
	private TableView<Autor> tableViewAutor;
	
	@FXML
	private TableColumn<Autor, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Autor,String> tableColumnNome;
	
	@FXML
	private Button btNew;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		
	}

}
