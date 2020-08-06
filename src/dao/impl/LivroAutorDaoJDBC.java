package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.LivroAutorDao;
import db.DB;
import db.DbExceptions;
import model.entities.Autor;
import model.entities.LivroAutor;

public class LivroAutorDaoJDBC implements LivroAutorDao {

	private Connection conn;

	public LivroAutorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	private Autor instantiateAutor(ResultSet rs) throws SQLException {
		Autor autor = new Autor();
		autor.setId(rs.getInt("AutorId"));
		autor.setNome(rs.getString("DepNome"));
		return autor;

	}

	private LivroAutor instantiateLivroAutor(ResultSet rs, Autor autor) throws SQLException {
		LivroAutor livroAutor = new LivroAutor();
		livroAutor.setId(rs.getInt("id"));
		livroAutor.setTitulo(rs.getString("titulo"));
		livroAutor.setAutor(autor);
		return livroAutor;

	}

	@Override
	public void insert(LivroAutor livroAutor) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO autor_livro " + "(titulo, AutorId) " + "VALUES " + "(?,?) ",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, livroAutor.getTitulo());
			st.setInt(2, livroAutor.getAutor().getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					livroAutor.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbExceptions("Erro Inesperado! Linha não inclusa!");
			}
		} catch (SQLException e) {
			throw new DbExceptions("Erro SQL: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(LivroAutor livroAutor) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE autor_livro " + "SET titulo = ?, AutorId = ? " + "where id = ? ");
			st.setString(1, livroAutor.getTitulo());
			st.setInt(2, livroAutor.getAutor().getId());
			st.setInt(3, livroAutor.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbExceptions("Erro SQL: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM autor_livro WHERE Id = ? ");
			st.setInt(1, id);

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbExceptions(e.getMessage());
		}

		finally {

			DB.closeStatement(st);

		}

	}

	@Override
	public LivroAutor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(
					"SELECT autor_livro.*,autor.nome as DepNome " + "FROM autor_livro INNER JOIN autor "
							+ "ON autor_livro.AutorId = autor.id " + "WHERE autor_livro.id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Autor autor = instantiateAutor(rs);
				LivroAutor livroAutor = instantiateLivroAutor(rs, autor);
				return livroAutor;
			}
			return null;
		} catch (SQLException e) {
			throw new DbExceptions("Erro SQL: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<LivroAutor> findByAutor(Autor autor) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(
					"SELECT autor_livro.*, autor.nome as DepNome " + "FROM autor_livro INNER JOIN autor "
							+ "ON autor_livro.AutorId = autor.id " + "WHERE AutorId = ? ");

			st.setInt(1, autor.getId());
			rs = st.executeQuery();

			List<LivroAutor> listaAutorLivro = new ArrayList<>();

			while (rs.next()) {
				Autor aut = instantiateAutor(rs);
				LivroAutor obj = instantiateLivroAutor(rs, aut);
				listaAutorLivro.add(obj);

			}
			return listaAutorLivro;

		} catch (SQLException e) {
			throw new DbExceptions("Erro SQL: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<LivroAutor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT autor_livro.*,autor.nome as DepNome "
					+ "FROM autor_livro INNER JOIN autor " + "ON autor_livro.AutorId= autor.id ");

			rs = st.executeQuery();

			List<LivroAutor> listaLivroAutor = new ArrayList<>();

			while (rs.next()) {

				Autor aut = instantiateAutor(rs);
				LivroAutor obj = instantiateLivroAutor(rs, aut);
				listaLivroAutor.add(obj);

			}

			return listaLivroAutor;

		} catch (SQLException e) {
			throw new DbExceptions("Erro SQL: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

}