package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.LivroAutorDao;
import db.DB;
import db.DbExceptions;
import model.entities.Autor;
import model.entities.Livro;
import model.entities.LivroAutor;

public class LivroAutorDaoJDBC implements LivroAutorDao {

	private Connection conn;

	public LivroAutorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	private Livro instantiateLivro(ResultSet rs) throws SQLException {
		Livro livro = new Livro();
		livro.setId(rs.getInt("idAutor"));
		livro.setTitulo(rs.getString("DepNome"));
		return livro;
	}

	private Autor instantiateAutor(ResultSet rs) throws SQLException {
		Autor autor = new Autor();
		autor.setId(rs.getInt("idLivro"));
		autor.setNome(rs.getString("DepNome"));
		return autor;

	}

	private LivroAutor instantiateLivroAutor(ResultSet rs) throws SQLException {
		LivroAutor livroAutor = new LivroAutor();
		livroAutor.setId(rs.getInt("id"));
		livroAutor.setIdLivro(rs.getInt("idLivro"));
		livroAutor.setIdAutor(rs.getInt("idAutor"));
		return livroAutor;
	}

	private LivroAutor instantiateLivroAutor(ResultSet rs, Livro livro) throws SQLException {
		LivroAutor livroAutor = new LivroAutor();
		livroAutor.setId(rs.getInt("id"));
		livroAutor.setLivro(livro);
		return livroAutor;

	}

	private LivroAutor instantiateLivroAutor(ResultSet rs, Autor autor) throws SQLException {
		LivroAutor livroAutor = new LivroAutor();
		livroAutor.setId(rs.getInt("id"));
		livroAutor.setAutor(autor);
		return livroAutor;

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(LivroAutor livroAutor) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO livro_autor " + "(idLivro, idAutor) " + "VALUES " + "(?,?) ",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, livroAutor.getIdLivro());
			st.setInt(2, livroAutor.getIdAutor());

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
			st = conn.prepareStatement("UPDATE livro_autor " + "SET idLivro = ?, idAutor = ? " + "where id = ? ");
			st.setInt(1, livroAutor.getIdLivro());
			st.setInt(2, livroAutor.getIdAutor());
			st.setInt(3, livroAutor.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbExceptions("Erro SQL: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public LivroAutor findByID(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT * FROM livro_autor WHERE livro_autor.id = ? ");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				LivroAutor livroAutor = instantiateLivroAutor(rs);

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
	public List<LivroAutor> findByLivro(Livro livro) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(
					"SELECT livro_autor.*, autor.nome as DepNome " + "FROM livro_autor INNER JOIN autor "
							+ "ON livro_autor.idAutor = autor.id " + "WHERE idLivro = ? ");

			st.setInt(1, livro.getId());
			rs = st.executeQuery();

			List<LivroAutor> listaLivroAutor = new ArrayList<>();
			Map<Integer, Livro> map = new HashMap<>();
			while (rs.next()) {
				Livro liv = map.get(rs.getInt("idLivro"));
				if (liv == null) {
					liv = instantiateLivro(rs);
					map.put(rs.getInt("idLivro"), liv);
				}
				LivroAutor obj = instantiateLivroAutor(rs, liv);
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

	@Override
	public List<LivroAutor> findByAutor(Autor autor) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(
					"SELECT livro_autor.*, livro.titulo as DepNome " + "FROM livro_autor INNER JOIN livro "
							+ "ON livro_autor.idLivro = livro.id " + "WHERE idAutor = ?");

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

			st = conn.prepareStatement("SELECT livro_autor.*,autor.nome as DepName "
					+ "FROM livro_autor INNER JOIN autor " + "ON livro_autor.idAutor= autor.id");

			rs = st.executeQuery();

			List<LivroAutor> listaLivroAutor = new ArrayList<>();

			while (rs.next()) {

				LivroAutor livroAutor = new LivroAutor();
				livroAutor.setId(rs.getInt("id"));
				livroAutor.setIdLivro(rs.getInt("idLivro"));
				livroAutor.setIdAutor(rs.getInt("idAutor"));
				listaLivroAutor.add(livroAutor);

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
