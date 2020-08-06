package dao;

import java.util.List;

import model.entities.Autor;

public interface AutorDao {

	void insert(Autor autor);

	void update(Autor autor);

	void deleteById(Integer id);

	Autor findById(Integer id);

	List<Autor> findAll();

}
