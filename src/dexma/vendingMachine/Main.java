package dexma.vendingMachine;

import java.util.HashMap;

/**
 * Sample Script to use the package
 * @author bhaskargautam
 *
 */
public class Main {
	
	public static void main(String[] args) {
		HashMap<Product,Integer> products = new HashMap<Product,Integer>();
		products.put(new Product(1,"Coke", 1.50), 10);
		products.put(new Product(2,"Sprite", 1.45),5);
		products.put(new Product(3,"Water", 0.90),15);

		
		HashMap<CoinType,Integer> coins = new HashMap<CoinType,Integer>();
		coins.put(CoinType.FIFTY_CENT, 10);
		coins.put(CoinType.FIVE_CENT, 10);
		coins.put(CoinType.TWO_EURO, 10);
		coins.put(CoinType.TEN_CENT, 10);
		coins.put(CoinType.ONE_EURO, 10);
		
		
		VendingMachine vendingMachine = new VendingMachine(products,coins);
		for(Product p : vendingMachine.getProducts()) {
			System.out.println(String.format("%d %.2f %s", p.getId(), p.getPrice(), p.getProductName()));
		}
		
		Coin[] coins_one = {
				new Coin(CoinType.ONE_EURO),
				new Coin(CoinType.ONE_EURO)
		};
		
		vendingMachine.addCoins(coins_one);
		try {
			vendingMachine.selectProduct(1);
		} catch (VendingMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			vendingMachine.selectProduct(1);
		} catch (VendingMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			vendingMachine.selectProduct(1);
		} catch (VendingMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			for(Coin c : vendingMachine.getBalance()) {
				System.out.println(String.format("Received %.2f",c.getValue()));
			}
		} catch (VendingMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Coin[] coins_two = {
				new Coin(CoinType.ONE_EURO),
				new Coin(CoinType.TWO_EURO)
		};
		vendingMachine.addCoins(coins_two);
		try {
			vendingMachine.selectProduct(2);
		} catch (VendingMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			vendingMachine.selectProduct(3);
		} catch (VendingMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			vendingMachine.getBalance();
		} catch (VendingMachineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
