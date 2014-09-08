package org.vu.evocomputing2014team4.algorithms;

import java.util.Random;

/**
 * class should implement static methods for sampling rv's
 * so the seed can be set once, and methods called from anywhere.
 * @author tbosman
 *
 */
public  class RandomSampler {
	
	private static int seed;
	private static Random random = new Random(); 
	

	//private constructor to prevent instantiation
	private RandomSampler() {
	}
	
	/**
	 * set the random seed
	 * @param seed
	 */
	public static void setSeed(int seed) {
		//set random seed.
	}
	
	/**
	 * 
	 * @param sigma stdev of gaussian 
	 * @return random sample from gaussian (0, sigma) distribution
	 */
	public static double getGaussian(double sigma) {
		//TODO implement
		return 0; 
	}
	/**
	 * 
	 * @param covarianceMatrix symmetric nxn covariance matrix 
	 * @return n-tuple of sample from gaussian (0, covmatrix) distribution
	 */
	public static double getMultivariateGaussian(double[][] covarianceMatrix) {
		return 0;
	}
	
}
