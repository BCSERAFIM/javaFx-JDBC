package dao;

import java.util.List;

import model.entities.Autor;
import model.entities.Livro;

public interface LivroDao {

	void insert(Livro livro);

	void update(Livro livro);

	void deleteById(Integer id);

	List<Livro> findAll();

	List<Livro> findByAutor(Autor autor);

}
