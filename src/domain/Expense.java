package domain;

import java.util.Date;

public class Expense {
	
	private Integer expenseId;
	private String name;
	private Date dateExpessive;
	private Double price;
	
	private Wallet wallet;
	private User user;
	
	public Expense() {
	}

	public Expense(Integer expenseId, String name, Date dateExpessive, Double price, User user, Wallet wallet) {
		this.expenseId = expenseId;
		this.name = name;
		this.dateExpessive = dateExpessive;
		this.price = price;
		this.user = user;
		this.wallet = wallet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateExpessive() {
		return dateExpessive;
	}

	public void setDateExpessive(Date date) {
		this.dateExpessive = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expenseId == null) ? 0 : expenseId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Expense other = (Expense) obj;
		if (expenseId == null) {
			if (other.expenseId != null)
				return false;
		} else if (!expenseId.equals(other.expenseId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
	

}
