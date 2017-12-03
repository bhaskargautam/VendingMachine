package dexma.vendingMachine;

/**
 * Error Codes returned by the Vending Machine.
 * @author bhaskargautam
 *
 */
enum ErrorCode {
	PRODUCT_OUT_OF_STOCK,
	COIN_OUT_OF_STOCK,
	INSUFFICIENT_AMOUNT,
	INCORRECT_PRODUCT_ID
}

public class VendingMachineException extends Exception {
	private static final long serialVersionUID = 1L;
	ErrorCode errorCode;
	
	public VendingMachineException(ErrorCode e) {
		this.errorCode = e;
	}
}

