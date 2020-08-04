package model.entities;

import java.io.Serializable;
import java.util.List;

public class Autor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private List<Livro> livros;
	private Livro livro;

	public Autor() {

	}

	public Autor(Integer id, String nome) {

		this.id = id;
		this.nome = nome;
	}

	public Autor(Integer id, String nome, List<Livro> livros, Livro livro) {

		this.id = id;
		this.nome = nome;
		this.livros = livros;
		this.livro = livro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void adicionarLivro(Livro livro) {
		this.livros.add(livro);

	}

	public void removerLivro(Livro livro) {
		this.livros.remove(livro);
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
		Autor other = (Autor) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Autor [id=" + id + ", nome = " + nome + "]";
	}

}
