package model.entities;

import java.io.Serializable;
import java.util.List;

public class Livro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String titulo;
	private List<Autor> autores;

	private Autor autor;

	public Livro() {

	}

	public Livro(Integer id, String titulo) {

		this.id = id;
		this.titulo = titulo;
	}

	public Livro(Integer id, String titulo, List<Autor> autores, Autor autor) {

		this.id = id;
		this.titulo = titulo;
		this.autores = autores;
		this.autor = autor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public void adicionarAutor(Autor autor) {
		this.autores.add(autor);

	}

	public void removerAutor(Autor autor) {
		this.autores.remove(autor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Livro [id = " + id + ", Nome = " + titulo + "]";
	}

}
