package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import domain.Budget;
import domain.Expense;
import domain.User;
import domain.Wallet;

public class BudgetService {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static List<Budget> list = new ArrayList<>();

	public static void addAllBudget(Budget budget) {
		list.add(budget);
	}

	public static List<Budget> getListBudget() {
		return list;
	}

	public static Budget findBudget(Budget budget) {
		for (Budget obj : list) {
			if (obj.equals(budget)) {
				return obj;
			}
		}
		return null;
	}

	public static boolean isExist(String name, User user, Wallet wallet) {

		for (Budget b : list) {
			if (b.getName().equals(name) && b.getUser().getIdUser().equals(user.getIdUser())
					&& b.getWallet().getIdWallet().equals(wallet.getIdWallet())) {
				return true;
			}
			
		}

		return false;
	}

	public static void deleteBudget(Budget budget) {
		if (findBudget(budget) != null) {
			list.remove(budget);
		}
	}

	public static void updateFileBuget(Expense expense) {
		String pathBudget = "C:\\Users\\ASUS\\Desktop\\Curso de POO\\GestaoDespesas\\expensive\\budget.txt";
		for (Budget b : BudgetService.getListBudget()) {
			if ((b.getName().equals(expense.getName()))
					&& (b.getUser().getIdUser().equals(expense.getUser().getIdUser()))
					&& (b.getWallet().getIdWallet().equals(expense.getWallet().getIdWallet()))) {
				b.setDebitedAmount(b.getDebitedAmount() + expense.getPrice());
			}
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathBudget))) {
			for (Budget b : list) {
				bw.write(b.getBudgetId() + "," + b.getName() + "," + String.format("%.2f", b.getDebitedAmount()) + ","
						+ sdf.format(b.getFirstDate()) + "," + sdf.format(b.getLastDate()) + ","
						+ String.format("%.2f", b.getValueBudget()) + "," + b.getUser().getIdUser() + ","
						+ b.getUser().getUsername() + "," + b.getWallet().getIdWallet());
				bw.newLine();
			}
		} catch (IOException e) {
			System.out.println("error ao atualizar a carteira " + e.getMessage());
		}
	}

	public static void updateFileBuget(Budget budget) {
		String pathBudget = "C:\\Users\\ASUS\\Desktop\\Curso de POO\\GestaoDespesas\\expensive\\budget.txt";
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathBudget, true))) {

			bw.write(budget.getBudgetId() + "," 
					+ budget.getName() + ","
					+ String.format("%.2f", budget.getDebitedAmount()) 
					+ "," + sdf.format(budget.getFirstDate()) + ","
					+ sdf.format(budget.getLastDate()) + "," 
					+ String.format("%.2f", budget.getValueBudget()) + ","
					+ budget.getUser().getIdUser() + "," 
					+ budget.getUser().getUsername() + ","
					+ budget.getWallet().getIdWallet());
			bw.newLine();

		} catch (IOException e) {
			System.out.println("error ao atualizar a carteira " + e.getMessage());
		}
	}

}
