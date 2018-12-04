package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Wallet {

	private Integer idWallet;
	private Date date;
	private Double inValue;
	private Double outValue;

	private List<Expense> listExpense = new ArrayList<>();
	private List<Budget> listBudget = new ArrayList<>();

	private User user;

	public Wallet() {
	}

	public Wallet(Integer idWallet, Date date, Double inValue, Double outValue, User user) {
		this.idWallet= idWallet;
		this.date = date;
		this.inValue = inValue;
		this.outValue = outValue;
		this.user = user;
	}

	public Integer getIdWallet() {
		return idWallet;
	}
	public void setIdWallet(Integer idWallet) {
		this.idWallet = idWallet;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getInValue() {
		return inValue;
	}

	public void setInValue(Double inValue) {
		this.inValue = inValue;
	}

	public Double getOutValue() {
		return outValue;
	}

	public void setOutValue(Double outValue) {
		this.outValue = outValue;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double total() {
		return inValue - outValue;
	}

	public List<Expense> getListExpense() {
		return listExpense;
	}

	public void addExpense(Expense list) {
		this.listExpense.add(list);
	}

	public void removeExpense(Expense expense) {
		this.listExpense.remove(expense);
	}
	
	public List<Budget> getListBudget() {
		return listBudget;
	}

	public void addListBudget(Budget listBudget) {
		this.listBudget.add(listBudget);
	}
	
	public void removeListBudget(Budget listBudget) {
		this.listBudget.remove(listBudget);
	}





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((idWallet == null) ? 0 : idWallet.hashCode());
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
		Wallet other = (Wallet) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (idWallet == null) {
			if (other.idWallet != null)
				return false;
		} else if (!idWallet.equals(other.idWallet))
			return false;
		return true;
	}


	

}
