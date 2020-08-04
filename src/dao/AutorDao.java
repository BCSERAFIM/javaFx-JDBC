package dao;

import java.util.List;

import model.entities.Autor;
import model.entities.Livro;

public interface AutorDao {

	void insert(Autor autor);

	void update(Autor autor);

	void deleteById(Integer id);

	List<Autor> findAll();

	List<Autor> findByLivro(Livro livro);

}
