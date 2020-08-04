package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.LivroDao;
import db.DB;
import db.DbExceptions;
import model.entities.Autor;
import model.entities.Livro;

public class LivroDaoJDBC implements LivroDao {

	private Connection conn;

	public LivroDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Livro livro) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO livro " + "(titulo) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, livro.getTitulo());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					livro.setId(id);
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
	public void update(Livro livro) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Livro> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM  livro ORDER BY titulo");
			rs = st.executeQuery();

			List<Livro> list = new ArrayList<>();

			while (rs.next()) {

				Livro livro = new Livro();
				livro.setId(rs.getInt("id"));
				livro.setTitulo(rs.getString("titulo"));
				list.add(livro);

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
	public List<Livro> findByAutor(Autor autor) {
		// TODO Auto-generated method stub
		return null;
	}

}
