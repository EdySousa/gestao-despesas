package reposity;

import java.text.ParseException;
import java.util.Scanner;

import domain.User;

public interface UIReposity {

	User login(String name, String password);
	
	User createUser(String name, String password);

	void overview(User user);

	void monthlySummary(User user);

	void showExpenses(User user);

	void showBudget(User user);
	
	void defineBudget(User user, Scanner sc)throws ParseException;

	void addExpense(User user, Scanner sc)throws ParseException;

	void addWallet(User user, Scanner sc)throws ParseException;

	void exit(Scanner sc);

}
