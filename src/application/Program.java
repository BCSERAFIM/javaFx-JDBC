package application;

import java.util.Scanner;

import dao.AutorDao;
import dao.DaoFactory;
import model.entities.Autor;

public class Program {

	public static void main(String[] args) {
		
		AutorDao autorDao = DaoFactory.creatAutorDao();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digitar Nome do Autor");
		System.out.print("  Nome: ");
		String nome = sc.nextLine();
		Autor newAutor = new Autor(null, nome);
		autorDao.insert(newAutor);
		System.out.println("Inserted! New id = " + newAutor.getId());

		
		sc.close();
	}

}
