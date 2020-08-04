package model.services;

import java.util.List;

import dao.AutorDao;
import dao.DaoFactory;
import model.entities.Autor;

public class AutorService {
	
	private AutorDao dao = DaoFactory.creatAutorDao();
	
	public List<Autor> findAll(){		
		return dao.findAll();
	}

}
