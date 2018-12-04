package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import services.WalletService;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idUser;
	private String username;
	private String password;

	private List<Wallet> listWallet = new ArrayList<>();
	private List<Expense> listExpenses = new ArrayList<>();
	private List<Budget> listBudgets = new ArrayList<>();

	public User() {
	}

	public User(Integer idUser, String username, String password) {
		this.idUser = idUser;
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Wallet> getListWallet() {
		return listWallet;
	}

	public void addWallet(Wallet wallet) {
		this.listWallet.add(wallet);
	}

	public void removeWallet(Wallet wallet) {
		this.listWallet.remove(wallet);
	}

	public List<Expense> getListExpenses() {
		return listExpenses;
	}

	public void addExpenses(Expense expenses) {
		this.listExpenses.add(expenses);
	}

	public void removeExpenses(Expense expenses) {
		this.listExpenses.remove(expenses);
	}





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (idUser == null) {
			if (other.idUser != null)
				return false;
		} else if (!idUser.equals(other.idUser))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public List<Budget> getListBudgets() {
		return listBudgets;
	}

	public void addBudgets(Budget budgets) {
		this.listBudgets.add(budgets);
	}

	public void removeBudgets(Budget budgets) {
		this.listBudgets.remove(budgets);
	}

}
