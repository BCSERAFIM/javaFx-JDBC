package model.entities;

import java.io.Serializable;

public class LivroAutor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer idLivro;
	private Integer idAutor;

	private Livro livro;
	private Autor autor;

	public LivroAutor() {

	}

	public LivroAutor(Integer id, Integer idLivro, Integer idAutor, Livro livro) {

		this.id = id;
		this.idLivro = idLivro;
		this.idAutor = idAutor;
		this.livro = livro;
	}

	public LivroAutor(Integer id, Integer idLivro, Integer idAutor) {

		this.id = id;
		this.idLivro = idLivro;
		this.idAutor = idAutor;
	}

	public LivroAutor(Integer id, Integer idLivro, Integer idAutor, Livro livro, Autor autor) {

		this.id = id;
		this.idLivro = idLivro;
		this.idAutor = idAutor;
		this.livro = livro;
		this.autor = autor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(Integer idLivro) {
		this.idLivro = idLivro;
	}

	public Integer getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(Integer idAutor) {
		this.idAutor = idAutor;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	@Override
	public String toString() {
		return "LivroAutor [id=" + id + ", idLivro=" + idLivro + ", idAutor=" + idAutor + "]";
	}

}
