package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import domain.Budget;
import domain.Expense;
import domain.User;
import domain.Wallet;
import domain.exceptions.DomainException;
import enums.Mes;
import reposity.UIReposity;
import services.BudgetService;
import services.ExpenseService;
import services.UserService;
import services.WalletService;

public class UI implements UIReposity {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public User login(String name, String password) {
		return UserService.findUser(name, password);
	}

	@Override
	public User createUser(String name, String password) {
		User newUser = new User((UserService.getListUser().size() + 1), name, password);
		UserService.addAllUser(newUser);
		UserService.updateFileUser(newUser);
		return newUser;

	}

	@Override
	public void overview(User user) {

		Wallet wallet = WalletService.currentWallet(user);

		Date d = wallet.getDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		int month = 1 + cal.get(Calendar.MONTH);
		String montStr = mesAtual(month);
		int year = cal.get(Calendar.YEAR);

		StringBuffer sb = new StringBuffer();

		sb.append("\t\n***Resumo***" + "\n");
		sb.append("Mês de " + montStr + " de " + year + "\n");
		sb.append("Entrada: " + String.format("%.2f", wallet.getInValue()) + "€" + "\n");

		char sinal = (wallet.getOutValue() > 0) ? '-' : ' ';
		sb.append("Saída: " + sinal + String.format("%.2f", wallet.getOutValue()) + "€" + "\n");

		sb.append("Total: " + String.format("%.2f", wallet.total()) + "€" + "\n");

		System.out.println(sb.toString());

	}

	@Override
	public void monthlySummary(User user) {

		Comparator<Wallet> comp = (s1, s2) -> s1.getDate().compareTo(s2.getDate());

		List<Wallet> names = WalletService.getListWallet().stream()
				.filter(p -> p.getUser().getIdUser().equals(user.getIdUser())).map(p -> p).sorted(comp.reversed())
				.collect(Collectors.toList());

		StringBuffer sb = new StringBuffer();
		sb.append("\n\tResumos Mensais\n\n");

		for (Wallet w : names) {

			Date d = w.getDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);

			String monthStr = mesAtual((1 + cal.get(Calendar.MONTH)));
			int year = cal.get(Calendar.YEAR);

			sb.append("Mês de " + monthStr + " de " + year + "\n");
			sb.append("___________________________________________\n");
			sb.append("Entrada: " + String.format("%.2f", w.getInValue()) + "€" + "\n");

			String sinal = (w.getOutValue() > 0) ? " -" : " ";
			sb.append("Saída: " + sinal + String.format("%.2f", w.getOutValue()) + "€" + "\n");

			sb.append("Total: " + String.format("%.2f", w.total()) + "€" + "\n\n\n");

		}
		System.out.println(sb.toString());

	}

	@Override
	public void showExpenses(User user) {

		List<Expense> list = ExpenseService.getListExpense().stream()
				.filter(p -> p.getUser().getIdUser().equals(user.getIdUser())).map(p -> p).collect(Collectors.toList());

		StringBuffer sb = new StringBuffer();
		sb.append("\t\nDespesas\n\n");
		for (Expense e : list) {
			sb.append("Data " + sdf.format(e.getDateExpessive()) + "\n");
			sb.append(e.getName() + ": -" + String.format("%.2f", e.getPrice()) + "€\n\n");
		}
		System.out.println(sb.toString());

	}

	@Override
	public void showBudget(User user) {

		StringBuffer sb = new StringBuffer();
		for (Budget b : BudgetService.getListBudget()) {

			if (user.getIdUser().equals(b.getUser().getIdUser())) {

				Double total = b.totalBudget(user, b);

				b.setDebitedAmount(total);
				sb.append("\n" + b.getName() + "\n");
				sb.append("Data de inicio: (" + sdf.format(b.getFirstDate()) + ")___________________" + "Data de Fim ("
						+ sdf.format(b.getLastDate()) + ")\n");
				sb.append("Valor incial: 0.00€____________________________Orçamento("
						+ String.format("%.2f", b.getValueBudget()) + "€)\n");
				sb.append("Total gasto: " + String.format("%.2f", b.getDebitedAmount()) + "€\n");

				double res = (b.getDebitedAmount() >= b.getValueBudget()) ? 0.0
						: b.getValueBudget() - b.getDebitedAmount();

				sb.append("Resta: " + String.format("%.2f", res) + "€\n");
			}

		}

		System.out.println(sb.toString());

	}

	@Override
	public void defineBudget(User user, Scanner sc) {

		try {
			System.out.println("\t\tDefinir Orçamento");

			// pegar a carteira atual
			Wallet wallet = WalletService.currentWallet(user);
			Calendar cal = Calendar.getInstance();
			cal.setTime(wallet.getDate());

			// pegar data no pirmeiro dia do mes
			cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
			Date firstDate = cal.getTime();

			// pegar data no ultimo dia do mes
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
			Date lastDate = cal.getTime();

			System.out.print("Nome da despesa: ");
			sc.nextLine();
			String name = sc.nextLine();
			System.out.print("Valor de Orçamento: ");
			Double valueBudget = sc.nextDouble();

			if (BudgetService.isExist(name, user, wallet)) {
				System.out.println("OPs! Já existe orçamento para esta depesas.");
			} else {
				// incrementar o numero de budget
				Integer budgetId = BudgetService.getListBudget().size() + 1;
				Double debitedAmount = 0.0;

				Budget budget = new Budget(budgetId, name, debitedAmount, firstDate, lastDate, valueBudget, user,
						wallet);
				BudgetService.addAllBudget(budget);

				BudgetService.updateFileBuget(budget);

				System.out.println("Orçamento definido com sucesso.");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void addExpense(User user, Scanner sc) throws ParseException {

		try {
			System.out.println("\t\tNova Despesa");
			System.out.print("Nome: ");
			sc.nextLine();
			String name = sc.nextLine();
			System.out.print("Data (dd/mm/yyyy): ");
			Date dateExpessive = sdf.parse(sc.nextLine());
			System.out.print("Preço: ");
			Double price = sc.nextDouble();

			boolean exist = false;
			Calendar cal = Calendar.getInstance();
			for (Wallet w : WalletService.getListWallet()) {

				if (w.getUser().getIdUser().equals(user.getIdUser())) {

					cal.setTime(w.getDate());

					// pegar data no pirmeiro dia do mes
					cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
					Date firstDateW = cal.getTime();

					// pegar data no ultimo dia do mes
					cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
					Date lastDateW = cal.getTime();

					Wallet wallet = WalletService.currentWallet(user);
					Integer expenseId = (ExpenseService.getListExpense().size() + 1);

					if (dateExpessive.compareTo(firstDateW) >= 0 && dateExpessive.compareTo(lastDateW) <= 0) {
						exist = true;
						Expense expense = new Expense(expenseId, name, dateExpessive, price, user, w);

						ExpenseService.addAllExpense(expense);
						ExpenseService.updateFileExpense(expense);
						BudgetService.updateFileBuget(expense);
						WalletService.updateFileWallet(expense);

						if (!(w.getDate().equals(wallet.getDate()))) {
							System.out.println(
									"Depesa foi adicionada com sucesso na outra carteira já existente.");
						} else {
							System.out.println("A despesa foi adicionada com sucesso na carteira atual");
						}

					}
				}

			}
			if (!exist) {
				System.out.println("OPs! Não existe carteira para a data indicada na despesa.");
			}

		} catch (Exception e) {
			System.out.println("error a escrever: " + e.getMessage());
		}

	}

	@Override
	public void addWallet(User user, Scanner sc) throws ParseException {

		try {
			System.out.println("\t\tNova Carteira");

			Integer idWallet = WalletService.getListWallet().size() + 1;
			System.out.print("Data (dd/mm/yyyy): ");
			sc.nextLine();
			Date date = sdf.parse(sc.nextLine());
			System.out.print("Valor: ");
			Double inValue = sc.nextDouble();

			Wallet wallet = new Wallet(idWallet, date, inValue, 0.0, user);

			WalletService.addAllWallet(wallet);
			WalletService.updateFileWallet(wallet);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void exit(Scanner sc) {
		System.out.println("Obrigado!");
		if (sc != null) {
			sc.close();
		}

	}

	public void readFileUser(String pathUser) {

		try (BufferedReader br = new BufferedReader(new FileReader(pathUser))) {

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");

				int userID = Integer.parseInt(fields[0]);
				String username = fields[1];
				String password = fields[2];

				UserService.addAllUser(new User(userID, username, password));

				line = br.readLine();
			}

		} catch (IOException e) {
			System.out.println("error em ler ficheiros de Users " + e.getMessage());
		}

	}

	public void readFileExpensives(String pathExpensive) throws ParseException {

		try (BufferedReader br = new BufferedReader(new FileReader(pathExpensive))) {

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");

				int expenseId = Integer.parseInt(fields[0]);
				String name = fields[1];
				Date date = sdf.parse(fields[2]);
				double price = Double.parseDouble(fields[3]);

				int userId = Integer.parseInt(fields[4]);
				String nameuser = fields[5];
				String password = null;
				User user = new User(userId, nameuser, password);

				int WalletId = Integer.parseInt(fields[6]);
				Date dateWallet = null;
				Double inValue = null;
				Double outValue = null;
				Wallet wallet = new Wallet(WalletId, dateWallet, inValue, outValue, user);

				Expense expense = new Expense(expenseId, name, date, price, user, wallet);
				ExpenseService.addAllExpense(expense);

				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("error " + e.getMessage());
		}

	}

	public void readFileWallet(String pathWallet) throws ParseException {

		try (BufferedReader br = new BufferedReader(new FileReader(pathWallet))) {

			Wallet wallet = null;
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");

				int WalletId = Integer.parseInt(fields[0]);
				Date date = sdf.parse(fields[1]);
				double inValue = Double.parseDouble(fields[2]);
				double outValue = Double.parseDouble(fields[3]);

				int userId = Integer.parseInt(fields[4]);
				String name = fields[5];
				String password = null;

				User user = new User(userId, name, password);

				wallet = new Wallet(WalletId, date, inValue, outValue, user);
				WalletService.addAllWallet(wallet);

				line = br.readLine();
			}

		} catch (IOException e) {
			System.out.println("error " + e.getMessage());
		}

	}

	public void readFileBudget(String pathBudget) throws ParseException {

		try (BufferedReader br = new BufferedReader(new FileReader(pathBudget))) {

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");

				int budgetId = Integer.parseInt(fields[0]);
				String name = fields[1];
				double debitedAmount = Double.parseDouble(fields[2]);
				Date firstDate = sdf.parse(fields[3]);
				Date lastDate = sdf.parse(fields[4]);
				Double valueBudget = Double.parseDouble(fields[5]);

				int userId = Integer.parseInt(fields[6]);
				String nameuser = fields[7];
				User user = new User(userId, nameuser, null);

				int WalletId = Integer.parseInt(fields[8]);
				Wallet wallet = new Wallet(WalletId, null, null, null, user);

				Budget budget = new Budget(budgetId, name, debitedAmount, firstDate, lastDate, valueBudget, user,
						wallet);

				BudgetService.addAllBudget(budget);

				line = br.readLine();
			}
		} catch (IOException e) {
			System.out.println("error " + e.getMessage());
		}

	}

	public void menuLogin(Scanner sc) throws ParseException {
		System.out.println("\t\tSeja Bem Vindo App de Gestão de Despesas\n");
		System.out.println("\tESCOLHE A OPÇÃO");
		System.out.println("1. INICIAR SESSÃO");
		System.out.println("2. REGISTA-TE");
		int op = sc.nextInt();

		boolean exist = true;
		do {
			try {
				sc.nextLine();
				switch (op) {
				case 1:
					System.out.print("Username: ");
					String username = sc.nextLine();
					System.out.print("Password: ");
					String password = sc.nextLine();

					User user = login(username, password);
					menu(user, sc);
					exist = false;
					break;
				case 2:
					System.out.println("\t\tNovo Registo:");

					System.out.print("Username: ");
					username = sc.nextLine();
					UserService.exist(username);

					System.out.print("Password: ");
					password = sc.nextLine();
					UserService.exist(password);

					user = createUser(username, password);
					menu(user, sc);
					exist = false;
					break;

				default:
					System.out.println("Opção errada!");
					exist = false;
					break;

				}
			} catch (DomainException e) {
				System.out.println("erro: " + e.getMessage());
				System.out.println("\tESCOLHE A OPÇÃO");
				System.out.println("1. INICIAR SESSÃO");
				System.out.println("2. REGISTA-TE");
				op = sc.nextInt();
			}
		} while (exist);

	}

	public void menu(User user, Scanner sc) throws ParseException {
		int op = 8;
		do {
			System.out.println();
			System.out.println("1. Visão Geral");
			System.out.println("2. Resumo Mensais");
			System.out.println("3. Despesas");
			System.out.println("4. Orçamento Mensal");
			System.out.println("5. Adicionar Despesas");
			System.out.println("6. Adicionar Carteira");
			System.out.println("7. Definir Orçamento");
			System.out.println("8. Sair");

			System.out.print("\tESCOLHE A OPÇÃO: ");
			op = sc.nextInt();

			switch (op) {
			case 1:
				overview(user);
				break;
			case 2:
				monthlySummary(user);
				break;
			case 3:
				showExpenses(user);
				break;
			case 4:
				showBudget(user);
				break;
			case 5:
				addExpense(user, sc);
				break;
			case 6:
				addWallet(user, sc);
				break;
			case 7:
				defineBudget(user, sc);
				break;
			default:
				break;
			}

		} while (op != 8);

	}

	private String mesAtual(int mesNumerico) {
		String mes = null;
		switch (mesNumerico) {
		case 1:
			mes = String.valueOf(Mes.JANEIRO);
			break;
		case 2:
			mes = String.valueOf(Mes.FEVEREIRO);
			break;
		case 3:
			mes = String.valueOf(Mes.MARÇO);
			break;
		case 5:
			mes = String.valueOf(Mes.MAIO);
			break;
		case 6:
			mes = String.valueOf(Mes.JUNHO);
			break;
		case 7:
			mes = String.valueOf(Mes.JULHO);
			break;
		case 8:
			mes = String.valueOf(Mes.AGOSTO);
			break;
		case 9:
			mes = String.valueOf(Mes.SETEMBRO);
			break;
		case 10:
			mes = String.valueOf(Mes.OUTUBRO);
			break;
		case 11:
			mes = String.valueOf(Mes.NOVEMBRO);
			break;
		case 12:
			mes = String.valueOf(Mes.DEZEMBRO);
			break;
		default:
			mes = "invalid";
			break;
		}
		return mes;
	}

}
