package dexma.vendingMachine;
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
	 * List of products sold by the vending machine.
	 * Assumption: Not considering product counts. 
	 * Considering All products are always available
	 */
	Product[] products;
	
	/**
	 * Current amount paid by user.
	 * This amount is refunded on cancel.
	 */
	double activeValue;
	
	public VendingMachine(Product[] products) {
		this.products = products;
	}
	
	/**
	 * Adds coins to the vending machine.
	 * @param coins List of Coins inserted in the vending machine.
	 * @return the active value in vending machine.
	 * */
	public double addCoins(Coin[] coins) {
		for (Coin coin : coins) {
			this.activeValue = this.activeValue + coin.getValue();
			LOGGER.log(Level.INFO, String.format(
					"%.2f Euro added. Balance %.2f Euro", 
					coin.getValue(),
					this.activeValue));
		}
		return this.activeValue;
	}
	/**
	 * 
	 * @return List of Products available with the vending machine
	 */
	public Product[] getProducts() {
		return this.products;
	}
	
	/**
	 * Allows to purchase a product. 
	 * @param product_id ID of the product selected
	 * @return Whether the purchase can proceed or not.
	 * */
	public Boolean selectProduct(int product_id) {
		for(Product product : this.products) {
			if(product.getId() == product_id) {
				if(this.activeValue >= product.getPrice()) {
					//User can purchase the product 
					this.activeValue = this.activeValue - product.getPrice();
					LOGGER.log(Level.INFO, String.format(
							"Thanks for puchasing %s for %.2f Euro",
							product.getProductName(),
							product.getPrice()));
					return true;
				}
				else {
					double remainingAmount = product.getPrice() - this.activeValue;
					LOGGER.log(Level.INFO, String.format("Please add %.2f Euro more",remainingAmount));
					return false;
				}
			}
		}
		LOGGER.log(Level.SEVERE, String.format("No product Exist with id %d", product_id));
		return false;
	}
	
	/**
	 * Refunds the Balance Amount remaining in vending machine.
	 * @return List of Coins to be dispatched from the vending machine.
	 */
	public Coin[] getBalance() {
		Coin[] coins = Coin.getCoinsForValue(this.activeValue).toArray(new Coin[0]); 
		for(Coin c : coins) {
			LOGGER.log(Level.INFO, String.format("Returning Coin %.2f Euro", c.getValue()));
			this.activeValue = this.activeValue - c.getValue();
		}
		return coins;
	}
}
