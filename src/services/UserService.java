package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import domain.User;
import domain.exceptions.DomainException;

public class UserService {

	private static List<User> list = new ArrayList<>();

	public static void addAllUser(User user) {
		list.add(user);
	}
	
	public static List<User> getListUser() {
		return list;
	}
	
	public static boolean exist(String username) {
		if (username.equals("")) {
			throw new DomainException("O username e password nao podem estar vazio, Por favor tente outra vez!\n");
			
		}
		for (User obj : list) {
			if (obj.getUsername().equals(username)) {
				throw new DomainException("Já existe user com este nome. Por favor escohe outro username!\n");
			}
		}
		return false;
	}

	public static User findUser(String username, String password) {
		if(username.equals("") || password.equals("") )
			throw new DomainException("O username e/ou password nao podem estar vazio, Por favor tente outra vez!\n");
		for (User obj : list) {
			if (obj.getUsername().equals(username) && obj.getPassword().equals(password)) {
				return obj;
			}
		}
		throw new DomainException("Os dados que inseriu não existe em nenhuma conta.Por favor tente outra vez!\n");
	}
	
	public static void deleteUser(User user) {
		if(findUser(user.getUsername(), user.getPassword())!= null) {
			list.remove(user);
		}
	}

	public static void updateFileUser(User newUser) {
		String pathUser = "C:\\Users\\ASUS\\Desktop\\Curso de POO\\GestaoDespesas\\server\\login.txt";
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathUser, true))) {	
			bw.write(newUser.getIdUser() + "," + newUser.getUsername() + "," + newUser.getPassword());
			bw.newLine();

		} catch (IOException e) {
			System.out.println("error a escrever no ficheiro User: " + e.getMessage());
		}
		
	}

}
