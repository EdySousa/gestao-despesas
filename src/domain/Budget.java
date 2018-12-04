package domain;

import java.util.Date;

import services.ExpenseService;

public class Budget {
	
	private Integer budgetId;
	private String name;
	private Double debitedAmount;
	private Date firstDate;
	private Date lastDate;
	private Double valueBudget;
	
	private User user;
	private Wallet wallet;
	
	
	public Budget() {
	}

	public Budget(Integer budgetId, String name, Double debitedAmount, Date firstDate, Date lastDate,
			Double valueBudget, User user, Wallet wallet) {
		this.budgetId = budgetId;
		this.name = name;
		this.debitedAmount = debitedAmount;
		this.firstDate = firstDate;
		this.lastDate = lastDate;
		this.valueBudget = valueBudget;
		this.user = user;
		this.wallet = wallet;

	}


	public Integer getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(Integer budgetId) {
		this.budgetId = budgetId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Double getDebitedAmount() {
		return debitedAmount;
	}

	public void setDebitedAmount(Double debitedAmount) {
		this.debitedAmount = debitedAmount;
	}
	
	public Date getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public Double getValueBudget() {
		return valueBudget;
	}
	public void setValueBudget(Double valueBudget) {
		this.valueBudget = valueBudget;
	}


	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
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
	
	public double totalBudget(User user, Budget budget) {
			
		double total = ExpenseService.getListExpense().stream()
				.filter(
						e -> e.getName().equals(budget.getName()) && 
						user.getIdUser().equals(e.getUser().getIdUser()) &&
						((double) e.getDateExpessive().compareTo(budget.getFirstDate())) >=0.0  &&
						((double) e.getDateExpessive().compareTo(budget.getLastDate())) <=0.0
						)
				.map(e -> e.getPrice())
				.reduce(0.0, (x, y) -> x + y);
	
			
			
		
		return total;
	}
}
