package dexma.vendingMachine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Defines and provides interface to operate vending machine. 
 * @author bhaskargautam
 *
 */
public class VendingMachine {
	/**
	 *  Basic Logger for vending machine
	 */
	private final static Logger LOGGER = Logger.getLogger(VendingMachine.class.getName());
	
	/**
	 * Map of products sold by the vending machine with counts.
	 */
	HashMap<Product,Integer> products;
	
	/**
	 * Map of coins and their counts
	 */
	HashMap<CoinType,Integer> coins;
	
	/**
	 * Current amount paid by user.
	 * This amount is refunded on cancel.
	 */
	double activeValue;
	
	public VendingMachine(HashMap<Product,Integer> products, HashMap<CoinType,Integer> coins) {
		this.products = products;
		this.coins = coins;
	}
	
	/**
	 * Adds single Coin to the vending machine
	 * @param coin Coin Object
	 * @return the active value in vending machine.
	 */
	public double addCoin(Coin coin) {
		this.activeValue = this.activeValue + coin.getValue();
		if(this.coins.containsKey(coin.ctype)) {
			this.coins.put(coin.ctype, this.coins.get(coin.ctype) + 1);
		} else {
			this.coins.put(coin.ctype, 1);
		}
		LOGGER.log(Level.INFO, String.format(
				"%.2f Euro added. Balance %.2f Euro", 
				coin.getValue(),
				this.activeValue));
		return this.activeValue;
	}
	
	/**
	 * Adds multiple coins to the vending machine.
	 * @param coins List of Coins inserted in the vending machine.
	 * @return the active value in vending machine.
	 * */
	public double addCoins(Coin[] coins) {
		for (Coin coin : coins) {
			addCoin(coin);
		}
		return this.activeValue;
	}
	
	/**
	 * Lists the products currently available with vending machine
	 * @return List of Products available with the vending machine
	 */
	public ArrayList<Product> getProducts() {
		ArrayList<Product> available_products = new ArrayList<Product>(); 
		for(Entry<Product,Integer> entry : this.products.entrySet()) {
			if(entry.getValue() > 0) {
				available_products.add(entry.getKey());
			}
		}
		return available_products;
	}
	
	/**
	 * Allows to purchase a product. 
	 * Verifies that Product is in stock and User has balance.
	 * @param product_id ID of the product selected
	 * @return Whether the purchase can proceed or not.
	 * @throws VendingMachineException INCORRECT_PRODUCT_ID INSUFFICIENT_AMOUNT PRODUCT_OUT_OF_STOCK
	 * */
	public Boolean selectProduct(int product_id) throws VendingMachineException {
		for(Entry<Product,Integer> entry : this.products.entrySet()) {
			Product product = entry.getKey();
			if(product.getId() == product_id) {
				
				//Verify that the product is in stock
				if(entry.getValue() == 0) {
					LOGGER.log(Level.SEVERE, String.format(
							"Product id %d is out of stock",
							product_id));
					throw new VendingMachineException(ErrorCode.PRODUCT_OUT_OF_STOCK);
				}
				
				//Verify User has sufficient funds
				if(this.activeValue >= product.getPrice()) {
					//User can purchase the product 
					this.activeValue = this.activeValue - product.getPrice();
					this.products.put(product, this.products.get(product) - 1);
					LOGGER.log(Level.INFO, String.format(
							"Thanks for puchasing %s for %.2f Euro",
							product.getProductName(),
							product.getPrice()));
					return true;
				}
				else {
					double remainingAmount = product.getPrice() - this.activeValue;
					LOGGER.log(Level.INFO, String.format("Please add %.2f Euro more",remainingAmount));
					throw new VendingMachineException(ErrorCode.INSUFFICIENT_AMOUNT);	
				}
			}
		}
		throw new VendingMachineException(ErrorCode.INCORRECT_PRODUCT_ID);
	}
	
	/**
	 * Refunds the Balance Amount remaining in vending machine.
	 * @return List of Coins to be dispatched from the vending machine.
	 * @throws VendingMachineException COIN_OUT_OF_STOCK
	 */
	public ArrayList<Coin> getBalance() throws VendingMachineException {
		ArrayList<Coin> coins = new ArrayList<Coin>();
		for(Entry<CoinType,Double> entry : Coin.valueMap.entrySet()) {
			if(this.coins.containsKey(entry.getKey())) {
				while(this.coins.get(entry.getKey()) > 0 && this.activeValue >= entry.getValue()) {
					LOGGER.log(Level.INFO, String.format("Returning Coin %.2f Euro", entry.getValue()));
					coins.add(new Coin(entry.getKey()));
					this.coins.put(entry.getKey(), this.coins.get(entry.getKey()) - 1);
					this.activeValue = this.activeValue - entry.getValue();
				}
			}
		}
		
		//If No coins can be returned, throw error.
		if(coins.isEmpty() && this.activeValue >= Coin.MINIMUM_VALUE) {
			LOGGER.log(Level.SEVERE, "Coins are Out of Stock. Cannot Dispense remaining amount");
			throw new VendingMachineException(ErrorCode.COIN_OUT_OF_STOCK);
		}
		
		return coins;
	}
}
