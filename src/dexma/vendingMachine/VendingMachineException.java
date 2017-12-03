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

/**
 * Custom Exception as raised by vending machine.
 * @author bhaskargautam
 *
 */
public class VendingMachineException extends Exception {
	ErrorCode errorCode;
	
	public VendingMachineException(ErrorCode e) {
		this.errorCode = e;
	}
}

