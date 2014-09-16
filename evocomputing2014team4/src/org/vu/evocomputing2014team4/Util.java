package org.vu.evocomputing2014team4;

public class Util {

	private Util() {
	}

	public static double roundNDecimals(double a, int n) {
		int m = (int) Math.pow(10, n);
		return (double)Math.round(a*m)/m;
	}
}
