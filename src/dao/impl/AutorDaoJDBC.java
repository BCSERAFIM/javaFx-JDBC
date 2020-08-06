package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.AutorDao;
import db.DB;
import db.DbExceptions;
import model.entities.Autor;

public class AutorDaoJDBC implements AutorDao {

	private Connection conn;

	public AutorDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	private Autor instantiateAutor(ResultSet rs) throws SQLException {
		Autor aut = new Autor();
		aut.setId(rs.getInt("id"));
		aut.setNome(rs.getString("nome"));
		return aut;
	}

	@Override
	public void insert(Autor autor) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO autor " + "(nome) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, autor.getNome());

			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					autor.setId(id);
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
	public void update(Autor autor) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE autor " + "SET Nome = ? " + "WHERE id = ? ");

			st.setString(1, autor.getNome());
			st.setInt(2, autor.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbExceptions(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM autor WHERE id = ?");

			st.setInt(1, id);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbExceptions(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public List<Autor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM  autor ORDER BY Nome");
			rs = st.executeQuery();

			List<Autor> list = new ArrayList<>();

			while (rs.next()) {

				Autor autor = new Autor();
				autor.setId(rs.getInt("id"));
				autor.setNome(rs.getString("Nome"));
				list.add(autor);

			}
			return list;

		} catch (SQLException e) {
			throw new DbExceptions("Erro SQL: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public Autor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT * FROM autor WHERE autor.id = ? ");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Autor aut = instantiateAutor(rs);

				return aut;
			}
			return null;
		} catch (SQLException e) {
			throw new DbExceptions(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}