package dao;

import java.util.List;

import model.entities.Autor;
import model.entities.LivroAutor;

public interface LivroAutorDao {

	void insert(LivroAutor obj);

	void update(LivroAutor obj);

	void deleteById(Integer id);

	LivroAutor findById(Integer id);

	List<LivroAutor> findAll();

	List<LivroAutor> findByAutor(Autor autor);
}