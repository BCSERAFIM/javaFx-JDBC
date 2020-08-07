package model.services;

import java.util.List;

import dao.LivroAutorDao;
import dao.DaoFactory;
import model.entities.LivroAutor;

public class LivroAutorService {
	
	private LivroAutorDao dao = DaoFactory.creatLivroAutorDao();
	
	public List<LivroAutor> findAll(){		
		return dao.findAll();
	}
	
	public void saveOrUpdate(LivroAutor livroAutor) {
		if(livroAutor.getId() == null) {
			dao.insert(livroAutor);
		
		}
		else {
			dao.update(livroAutor);
			
		}
	}
	
	public void remove(LivroAutor obj) {
		dao.deleteById(obj.getId());
	}

}
