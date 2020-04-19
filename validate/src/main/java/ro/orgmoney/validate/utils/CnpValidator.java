package ro.orgmoney.validate.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Copied from
 * 
 * https://github.com/beradrian/jcommon/blob/master/src/main/java/net/sf/jcommon/geo/CnpValidator.java
 *
 *
 */

public class CnpValidator {

	/** The standard length of a CNP. */
	public static final int LENGTH = 13;

	private static int[] CONTROL_VALUES = new int[] { 2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9 };
	
	private static int[] YEAR_OFFSET = new int[] {0, 1900, 1900, 1800, 1800, 2000, 2000};

	private static int[] getDigits(String cnp) {
		int[] digits = new int[LENGTH];
		for (int i = 0; i < LENGTH; i++) {
			char c = cnp.charAt(i);
			if (!Character.isDigit(c)) {
				return null;
			}
			digits[i] = (byte) Character.digit(c, 10);
		}
		return digits;
	}

	private static int getControlSum(int[] twelveDigits) {
		int k = 0;
		for (int i = 0; i < 12; i++) {
			k += CONTROL_VALUES[i] * twelveDigits[i];
		}
		k %= 11;
		if (k == 10) {
			k = 1;
		}
		return k;
	}

	public static Boolean isCnpValid(String cnp) {
		if (cnp == null) {
			return false;
		}
		
		if (cnp.length() != LENGTH) {
			return false;
		}
		
		int[] cnpDigits = getDigits(cnp);
		if (cnpDigits == null) {
			return false;			
		}
		
		if (cnpDigits[LENGTH - 1] != getControlSum(cnpDigits)) {
			return false;						
		}
		
		int month = cnpDigits[3] * 10 + cnpDigits[4];
        if (month < 1 && month > 12) {
        	return false;
        }
        
        int dayOfMonth = cnpDigits[5] * 10 + cnpDigits[6];
        if (dayOfMonth < 1) {
        	return false;
        }
        
        int year = YEAR_OFFSET[cnpDigits[0]] + cnpDigits[1] * 10 + cnpDigits[2];
        int maxDayOfMonth = new GregorianCalendar(year, month, dayOfMonth).getActualMaximum(Calendar.DAY_OF_MONTH);
        if (dayOfMonth > maxDayOfMonth) {
        	return false;
        }
        
		return true;
	}
}
