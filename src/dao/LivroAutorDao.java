package dao;

import java.util.List;

import model.entities.Autor;
import model.entities.Livro;
import model.entities.LivroAutor;


public interface LivroAutorDao {

	void insert(LivroAutor livroAutor);

	void update(LivroAutor livroAutor);

	void deleteById(Integer id);

	LivroAutor findByID(Integer id);

	List<LivroAutor> findByLivro(Livro livro);

	List<LivroAutor> findByAutor(Autor autor);
	
	List<LivroAutor> findAll();
}
