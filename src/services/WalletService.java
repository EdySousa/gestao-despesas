package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import domain.Expense;
import domain.User;
import domain.Wallet;

public class WalletService {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static List<Wallet> list = new ArrayList<>();

	public static void addAllWallet(Wallet wallet) {
		list.add(wallet);
	}

	public static List<Wallet> getListWallet() {
		return list;
	}

	public static Wallet findWallet(Wallet wallet) {
		for (Wallet obj : list) {
			if (obj.equals(wallet)) {
				return obj;
			}
		}
		return null;
	}

	public static void deleteWallet(Wallet wallet) {
		if (findWallet(wallet) != null) {
			list.remove(wallet);
		}
	}
	public static void updateFileWallet(Expense expense) {
		String pathWallet = "C:\\Users\\ASUS\\Desktop\\Curso de POO\\GestaoDespesas\\expensive\\wallet.txt";
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(expense.getDateExpessive());
		
		//pegar data no pirmeiro dia do mes
	    cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
	    Date lastDate = cal.getTime();
	   
	    //pegar data no ultimo dia do mes
	    cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
	    Date firstDate = cal.getTime();
		
		for (Wallet w : list) {
			if (w.getIdWallet().equals(expense.getWallet().getIdWallet()) &&
				w.getUser().getIdUser().equals(expense.getUser().getIdUser()) &&
						( (double) w.getDate().compareTo(firstDate) >=0.0 )  &&
						( (double) w.getDate().compareTo(lastDate) <=0.0)
						)
				 {
				w.setOutValue(w.getOutValue() + expense.getPrice());
			}
		}
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathWallet))){
				for (Wallet w : list) {
					bw.write(w.getIdWallet() + ","
							+ sdf.format(w.getDate())+ ","
							+ String.format("%.2f", w.getInValue())+","
							+ String.format("%.2f", w.getOutValue())+","
							+ w.getUser().getIdUser()+","
							+ w.getUser().getUsername());
					bw.newLine();
				}
			} catch (IOException e) {
				System.out.println("error ao atualizar a carteira "+ e.getMessage());
			}
		
	}

	public static void updateFileWallet(Wallet wallet) {
		String pathWallet = "C:\\Users\\ASUS\\Desktop\\Curso de POO\\GestaoDespesas\\expensive\\wallet.txt";
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathWallet, true))) {
			bw.write(wallet.getIdWallet() + "," + sdf.format(wallet.getDate()) + "," 
					+ String.format("%.2f", wallet.getInValue()) + "," + wallet.getOutValue() + ","
					+ wallet.getUser().getIdUser() + "," + wallet.getUser().getUsername());
			bw.newLine();

		} catch (IOException e) {
			System.out.println("error a escrever: " + e.getMessage());
		}
	}
	
	public static Wallet currentWallet(User user) {
		Wallet wallet = firstDate(user);
		if (wallet!=null) {
			for (Wallet w : WalletService.getListWallet()) {
				if ((wallet.getDate().before(w.getDate())) && (w.getUser().getIdUser().equals(user.getIdUser()))) {
					wallet = w;
				}
			}
		}else {
			wallet = new Wallet((WalletService.getListWallet().size() + 1), new Date(), 0.0, 0.0, user);
		}
		return wallet;

	}
	
	public static Wallet firstDate(User user) {
		for (Wallet w : WalletService.getListWallet()) {
			if (w.getUser().getIdUser().equals(user.getIdUser()))
				return w;
		}
		return null;
	}

}
