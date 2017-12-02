package dexma.vendingMachine;
/**
 * Sample Script to use the package
 * @author bhaskargautam
 *
 */
public class Main {
	
	public static void main(String[] args) {
		Product[] products = {
				new Product(1,"Coke", 1.50),
				new Product(2,"Sprite", 1.45),
				new Product(3,"Water", 0.90)
		};
		
		VendingMachine vendingMachine = new VendingMachine(products);
		for(Product p : vendingMachine.getProducts()) {
			System.out.println(String.format("%d %.2f %s", p.getId(), p.getPrice(), p.getProductName()));
		}
		
		Coin[] coins = {
				new Coin(CoinType.ONE_EURO),
				new Coin(CoinType.ONE_EURO)
		};
		
		vendingMachine.addCoins(coins);
		vendingMachine.selectProduct(1);
		vendingMachine.selectProduct(1);
		vendingMachine.selectProduct(1);
		for(Coin c : vendingMachine.getBalance()) {
			System.out.println(String.format("Received %.2f",c.getValue()));
		}
		
		Coin[] coins_two = {
				new Coin(CoinType.ONE_EURO),
				new Coin(CoinType.TWO_EURO)
		};
		vendingMachine.addCoins(coins_two);
		vendingMachine.selectProduct(2);
		vendingMachine.selectProduct(3);
		vendingMachine.getBalance();
	}
}
