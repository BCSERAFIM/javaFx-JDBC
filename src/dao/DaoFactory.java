package dao;

import dao.impl.AutorDaoJDBC;
import dao.impl.LivroAutorDaoJDBC;
import db.DB;

public class DaoFactory {

	public static AutorDao creatAutorDao() {
		return new AutorDaoJDBC(DB.getConnection());
	}

	public static LivroAutorDao creatLivroAutorDao() {
		return new LivroAutorDaoJDBC(DB.getConnection());

	}

}