package application;

import java.util.Scanner;

import dao.AutorDao;
import dao.DaoFactory;
import dao.LivroAutorDao;
import model.entities.Autor;
import model.entities.LivroAutor;

public class Program {

	public static void main(String[] args) {
		
		LivroAutorDao livroAutorDao = DaoFactory.creatLivroAutorDao();
		AutorDao autorDao = DaoFactory.creatAutorDao();
		Scanner sc = new Scanner(System.in);
		
//		System.out.println("Digitar Nome do Autor");
//		System.out.print("  Nome: ");
//		String nome = sc.nextLine();
//		Autor newAutor = new Autor(null, nome);
//		autorDao.insert(newAutor);
//		System.out.println("Inserted! New id = " + newAutor.getId());

//		System.out.println("Digitar Nome do Livro");
//		System.out.print("  Nome: ");
//		String titulo = sc.nextLine();
//		System.out.println("Vincular ao Autor");
//		System.out.print("ID Autor: ");
//		int autorId = sc.nextInt();
//		sc.nextLine();
//		Autor autor = new Autor(autorId, null);
//		LivroAutor newLivroAutor = new LivroAutor(null, titulo, autor);
//		livroAutorDao.insert(newLivroAutor);
//		System.out.println("Inserted! New id = " + newLivroAutor.getId());
		
		
		sc.close();
	}

}
