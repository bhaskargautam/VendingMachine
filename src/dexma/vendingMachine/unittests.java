package dexma.vendingMachine;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class unittests {
	
	VendingMachine vendingMachine;
	
	@BeforeEach
	protected void setUp() { 
		System.out.println("Seting Up");
		HashMap<Product,Integer> products = new HashMap<Product,Integer>();
		products.put(new Product(1,"Coke", 1.50), 10);
		products.put(new Product(2,"Sprite", 1.45),5);
		products.put(new Product(3,"Water", 0.90),15);

		
		HashMap<CoinType,Integer> coins = new HashMap<CoinType,Integer>();
		coins.put(CoinType.FIFTY_CENT, 10);
		coins.put(CoinType.FIVE_CENT, 2);
		coins.put(CoinType.TWO_EURO, 10);
		coins.put(CoinType.TEN_CENT, 10);
		coins.put(CoinType.ONE_EURO, 10);
		
		
		vendingMachine = new VendingMachine(products,coins);
	}
	
	@Test
	void testAddCoins() {
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.ONE_EURO)),1.0);
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.ONE_EURO)),2.0);
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),4.0);
	}
	
	@Test
	void testGetProducts() {
		assertEquals(vendingMachine.getProducts().size(),3);
	}

	@Test
	void testSelectProductInsufficientFund() {
		assertThrows(VendingMachineException.class, 
				() -> vendingMachine.selectProduct(1), 
				ErrorCode.INSUFFICIENT_AMOUNT.toString());
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.ONE_EURO)),1.0);
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.ONE_EURO)),2.0);
		try {
			vendingMachine.selectProduct(1);
		} catch (VendingMachineException e) {
			fail("Should not have raised exception");
		}
		assertThrows(VendingMachineException.class, 
				() -> vendingMachine.selectProduct(1), 
				ErrorCode.INSUFFICIENT_AMOUNT.toString());
		
	}
	
	@Test
	void testSelectProductInvalidId() {
		assertThrows(VendingMachineException.class, 
				() -> vendingMachine.selectProduct(10), 
				ErrorCode.INCORRECT_PRODUCT_ID.toString());
		assertThrows(VendingMachineException.class, 
				() -> vendingMachine.selectProduct(0), 
				ErrorCode.INCORRECT_PRODUCT_ID.toString());
	}
	
	@Test
	void testSelectProductOutofStock() {
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),2.0);
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),4.0);
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),6.0);
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),8.0);
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),10.0);
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),12.0);
		try {
			vendingMachine.selectProduct(2);
			vendingMachine.selectProduct(2);
			vendingMachine.selectProduct(2);
			vendingMachine.selectProduct(2);
			vendingMachine.selectProduct(2);
		} catch (VendingMachineException e) {
			fail("Should not have raised exception");
		}
		assertThrows(VendingMachineException.class, 
				() -> vendingMachine.selectProduct(2), 
				ErrorCode.PRODUCT_OUT_OF_STOCK.toString());
	}
	
	@Test
	void testGetBalance() {
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),2.0);
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),4.0);
		try {
			vendingMachine.selectProduct(1);
			vendingMachine.selectProduct(1);
		} catch (VendingMachineException e) {
			fail("Should not have raised exception");
		}
		try {
			ArrayList<Coin> coins = vendingMachine.getBalance();
			assertEquals(coins.size(), 1);
			assertEquals(coins.get(0).ctype, CoinType.ONE_EURO);
		} catch (VendingMachineException e) {
			fail("Should not have raised exception");
			e.printStackTrace();
		}	
	}
	
	@Test
	void testGetBalance2() {
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),2.0);
		try {
			vendingMachine.selectProduct(2);
		} catch (VendingMachineException e) {
			fail("Should not have raised exception");
		}
		try {
			ArrayList<Coin> coins = vendingMachine.getBalance();
			assertEquals(coins.size(), 2);
			assertEquals(coins.get(1).ctype, CoinType.FIVE_CENT);
			assertEquals(coins.get(0).ctype, CoinType.FIFTY_CENT);
		} catch (VendingMachineException e) {
			fail("Should not have raised exception");
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetBalanceCoinOutOfStock() {
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),2.0);
		try {
			vendingMachine.selectProduct(2);
		} catch (VendingMachineException e) {
			fail("Should not have raised exception");
		}
		try {
			ArrayList<Coin> coins = vendingMachine.getBalance();
			assertEquals(coins.size(), 2);
			assertEquals(coins.get(1).ctype, CoinType.FIVE_CENT);
			assertEquals(coins.get(0).ctype, CoinType.FIFTY_CENT);
		} catch (VendingMachineException e) {
			fail("Should not have raised exception");
			e.printStackTrace();
		}
		
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),2.0);
		try {
			vendingMachine.selectProduct(2);
		} catch (VendingMachineException e) {
			fail("Should not have raised exception");
		}
		try {
			ArrayList<Coin> coins = vendingMachine.getBalance();
			assertEquals(coins.size(), 2);
			assertEquals(coins.get(1).ctype, CoinType.FIVE_CENT);
			assertEquals(coins.get(0).ctype, CoinType.FIFTY_CENT);
		} catch (VendingMachineException e) {
			fail("Should not have raised exception");
			e.printStackTrace();
		}
		
		assertEquals(vendingMachine.addCoin(new Coin(CoinType.TWO_EURO)),2.0);
		try {
			vendingMachine.selectProduct(2);
		} catch (VendingMachineException e) {
			fail("Should not have raised exception");
		}
		
		try {
			ArrayList<Coin> coins = vendingMachine.getBalance();
			assertEquals(coins.size(), 1);
			assertEquals(coins.get(0).ctype, CoinType.FIFTY_CENT);
			assertEquals(vendingMachine.activeValue, 0.05, 0.01); 
		} catch (VendingMachineException e) {
			fail("Should not have raised exception");
			e.printStackTrace();
		}
		
		assertThrows(VendingMachineException.class, 
				() -> vendingMachine.getBalance(),
				ErrorCode.COIN_OUT_OF_STOCK.toString());
	}
}
