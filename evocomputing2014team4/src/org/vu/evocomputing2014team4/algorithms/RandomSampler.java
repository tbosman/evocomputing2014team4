package org.vu.evocomputing2014team4.algorithms;

import java.util.ArrayList;
import java.util.Random;


/**
 * class should implement static methods for sampling rv's
 * so the seed can be set once, and methods called from anywhere.
 * @author tbosman
 *
 */
public  class RandomSampler {
	
	private static int seed;
	public static Random random = new Random(); 
//	private static RandomDataGenerator rdg = new RandomDataGenerator();
	

	//private constructor to prevent instantiation
	private RandomSampler() {
	}
	
	/**
	 * set the random seed
	 * @param seed
	 */
	public static void setSeed(long seed) {
		random.setSeed(seed);
//		rdg.reSeed(seed);
	}
	
	/**
	 * 
	 * @param sigma stdev of gaussian 
	 * @return random sample from gaussian (0, sigma) distribution
	 */
	public static double getGaussian(double sigma) {
		double u1 = getUniform();
		double u2 = getUniform();
		double z0 = Math.sqrt(-2*Math.log(u1))*Math.cos(Math.PI*2*u2);
		return z0;
//		return rdg.nextGaussian(0, sigma); 
	}
	/**
	 * 
	 * @param covarianceMatrix symmetric nxn covariance matrix 
	 * @return n-tuple of sample from gaussian (0, covmatrix) distribution
	 */
	public static double getMultivariateGaussian(double[][] covarianceMatrix) {
		return 0;
	}
	
	
	public static double getUniform() {
		return random.nextDouble();
	
	}
	/**
	 * 
	 * @param numSamples 
	 * @return numSamples dimensional array of uniform samples
	 */
	public static double[] getNUniform(int numSamples) {
		double[] samples = new double[numSamples];
		for(int i=0; i<numSamples ; i++) {
			samples[i] = getUniform();
			
		}
		return samples;
	}
	
//	/**
//	 * generate random int between lower(inc) and upper(excl)
//	 * @param lower
//	 * @param upper
//	 * @return random int 
//	 */
//	public static int getInt(int lower, int upper) {
//		return rdg.nextInt(lower, upper-1);
//	}
	
	public static int getInt(int n) {
		return random.nextInt(n);
	}
	
	/**
	 * Generates an integer array of length size whose entries are selected randomly, 
	 * without repetition, from the integers 0, ..., n - 1 (inclusive).
	 * @param n
	 * @param size
	 * @return
	 */
	public static int[] getPermutation(int n, int size) {
		ArrayList<Integer> perm = new ArrayList<Integer>();	
		while(perm.size()<size) {
			int addInt = getInt(n);
			if(!perm.contains(addInt)) {
				perm.add(addInt);
			}
		}
		
		int[] out = new int[size];
		for(int i=0; i<size;i++) {
			out[i] = perm.get(i);
		}
		return out;
	}
	
}
