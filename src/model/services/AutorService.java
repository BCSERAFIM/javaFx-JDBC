package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Autor;

public class AutorService {
	
	public List<Autor> findAll(){
		List<Autor> list = new ArrayList<>();
		list.add(new Autor(1, "Bruno Serafim"));
		list.add(new Autor(2, "Ricardo Salles"));
		list.add(new Autor(3, "Guga Chacara"));
		return list;
	}

}
