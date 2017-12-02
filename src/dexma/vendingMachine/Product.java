package dexma.vendingMachine;
/**
 * Defines the Products sold by the vending machine.
 * @author bhaskargautam
 *
 */
public class Product {
	/**
	 * Identifier of the Product. User needs to provide this to purchase.
	 */
	private int id;
	/**
	 * Name of the Product
	 */
	private String productName;
	/**
	 * Price of the product in euros.
	 */
	private double price;
	
	public Product(int id, String prodcutName, double price) {
		this.id = id;
		this.price = price;
		this.productName = prodcutName;
	}
	
	public int getId() {
		return this.id;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public String getProductName() {
		return this.productName;
	}
}
