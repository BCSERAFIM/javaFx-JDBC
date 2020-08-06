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
	
	public void saveOrUpdate(Autor autor) {
		if(autor.getId() == null) {
			dao.insert(autor);
		}
		else {
			dao.update(autor);
		}
	}
	
	public void remove(Autor obj) {
		dao.deleteById(obj.getId());
	}

}
