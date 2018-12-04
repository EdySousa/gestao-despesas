package application;

import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;

public class Program {

	public static void main(String[] args) throws ParseException {
		Locale.setDefault(Locale.US);
		
		UI ui = new UI();
	
		String pathUser = "C:\\Users\\ASUS\\Desktop\\Curso de POO\\GestaoDespesas\\server\\login.txt";		
		String pathExpensive = "C:\\Users\\ASUS\\Desktop\\Curso de POO\\GestaoDespesas\\expensive\\expensive.txt";
		String pathWallet = "C:\\Users\\ASUS\\Desktop\\Curso de POO\\GestaoDespesas\\expensive\\wallet.txt";
		String pathBudget = "C:\\Users\\ASUS\\Desktop\\Curso de POO\\GestaoDespesas\\expensive\\budget.txt";			
		Scanner sc = new Scanner(System.in);
		try {
			ui.readFileUser(pathUser);
			ui.readFileExpensives(pathExpensive);
			ui.readFileWallet(pathWallet);
			ui.readFileBudget(pathBudget);
			ui.menuLogin(sc);
			
		}catch(Exception e) {
			System.out.println("error: "+e.getMessage());
		}finally {
			ui.exit(sc);
		}
	}
}
