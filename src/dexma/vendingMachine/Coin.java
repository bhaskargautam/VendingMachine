package dexma.vendingMachine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * Defines the valid Coin Types accepted by the vending machine.
 * @author bhaskargautam
 *
 */
enum CoinType {
	FIVE_CENT,
	TEN_CENT,
	TWENTY_CENT,
	FIFTY_CENT,
	ONE_EURO,
	TWO_EURO
}

/**
 * Defines a single Coin accepted by the vending machine.
 * @author bhaskargautam
 *
 */
public class Coin {
	CoinType ctype;
	Double value;
	
	/**
	 * Mapping for CoinType to its value in decimal.
	 * Using LinkedHashMap to keep coins in sorted order
	 * Note: Preserve sort order while adding new coin type.
	 * getCoinsForValue() expects this list to be sorted.
	*/
	public static final LinkedHashMap<CoinType,Double> valueMap;
	public static final double MINIMUM_VALUE = 0.05;
    static
    {
    		valueMap = new LinkedHashMap<CoinType,Double>();
    		valueMap.put(CoinType.TWO_EURO, 2.0);
    		valueMap.put(CoinType.ONE_EURO, 1.0);
    		valueMap.put(CoinType.FIFTY_CENT, 0.5);
    		valueMap.put(CoinType.TWENTY_CENT, 0.2);
    		valueMap.put(CoinType.TEN_CENT, 0.1);
    		valueMap.put(CoinType.FIVE_CENT, 0.05);
    }
	
	public Coin(CoinType ctype) {
		this.ctype = ctype;
		this.value = valueMap.get(ctype);
	}
	
	/**
	 * @return The decimal equivalent of the coin.
	 */
	public double getValue() {
		return this.value;
	}
	
	/**
	 * Converts an amount to the equivalent Coins as defined by CoinType enum.
	 * The method minimizes the number of coins used.
	 * @param value Amount in decimal which needs to be converted
	 * @return List of Coins Equivalent to Amount  
	 */
	public static ArrayList<Coin> getCoinsForValue(double value) {
		ArrayList<Coin> coins = new ArrayList<Coin>();
		while(value >= MINIMUM_VALUE) {
			for (Entry<CoinType,Double> entry : valueMap.entrySet()) {
				if(value >= entry.getValue()) {
					coins.add(new Coin(entry.getKey()));
					value = value - entry.getValue();
				}
			}
		}
		return coins;
	}
}
