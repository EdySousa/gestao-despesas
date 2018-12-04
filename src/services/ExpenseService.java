package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import domain.Expense;

public class ExpenseService {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private static List<Expense> list = new ArrayList<>();

	public static void addAllExpense(Expense expense) {
		list.add(expense);
	}

	public static List<Expense> getListExpense() {
		return list;
	}

	public static Expense findExpense(Expense expense) {
		for (Expense obj : list) {
			if (obj.equals(expense)) {
				return obj;
			}
		}
		return null;
	}

	public static void deleteExpense(Expense expense) {
		if (findExpense(expense) != null) {
			list.remove(expense);
		}
	}

	public static void updateFileExpense(Expense expense) {
		String pathExpensive = "C:\\Users\\ASUS\\Desktop\\Curso de POO\\GestaoDespesas\\expensive\\expensive.txt";
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathExpensive, true))) {
			bw.write(expense.getExpenseId() + "," + expense.getName() + "," + sdf.format(expense.getDateExpessive())
					+ "," + String.format("%.2f", expense.getPrice()) + "," + expense.getUser().getIdUser() + ","
					+ expense.getUser().getUsername() + "," + expense.getWallet().getIdWallet());
			bw.newLine();

		} catch (IOException e) {
			System.out.println("error a escrever: " + e.getMessage());
		}

	}

}
