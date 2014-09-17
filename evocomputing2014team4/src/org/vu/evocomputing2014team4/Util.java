package org.vu.evocomputing2014team4;

public class Util {

	private Util() {
	}

	public static double roundNDecimals(double a, int n) {
		int m = (int) Math.pow(10, n);
		double out = (double)Math.round(a*m)/m;
		
		
		return out; 
	}
	
	
	public static double roundNBinary(double a, int n) {
		int m = (int) Math.pow(2, n);
		double out = (double)Math.round(a*m)/m;
		
		
		return out; 
	}
}
