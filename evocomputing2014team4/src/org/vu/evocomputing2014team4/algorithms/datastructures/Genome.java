package org.vu.evocomputing2014team4.algorithms.datastructures;

import java.util.Formatter;

public class Genome {

	/**
	 * Strategy parameters are build into genome
	 * Can add parameters at will to be used at discretion of algo  
	 * Crossover methods should have at least a maternal individual,
	 * from which to copy any chromosomes not specifically handled 
	 * @author tbosman
	 *
	 */

	/**
	 * CrossoverType signifies the crossover strategy, none designates no crossover but replication of the genotype
	 * @author tbosman
	 *
	 */
	public enum CrossoverType{
		LOCAL_INTERMEDIARY, LOCAL_DISCRETE, GLOBAL_INTERMEDIARY, GLOBAL_DISCRETE, NONE;
	}


	final public double[] value;
	final public double[] sigma; 
	final public double[][] alpha;//rotational params, make this a lower triangular matrix? 
	final public double tau; //global learning rate 
	final public double tauPrime; // coordinateWise learning rate
	final public double beta;//rotational learning rate
	final public double epsilon0; //minimum stdev 
	final public double epsilonMax; //max stdev
	final public double pi;//maximum rotational param
	final public CrossoverType crossoverType;
	/**
	 * @param value
	 * @param sigma
	 * @param alpha
	 * @param tau
	 * @param tauPrime
	 * @param beta
	 * @param epsilon0
	 * @param pi
	 */
	private Genome(double[] value, double[] sigma, double[][] alpha, double tau,
			double tauPrime, double beta, double epsilon0, double epsilonMax, double pi, CrossoverType crossoverType) {
		super();
		this.value = value;
		this.sigma = sigma;
		this.alpha = alpha;
		this.tau = tau;
		this.tauPrime = tauPrime;
		this.beta = beta;
		this.epsilon0 = epsilon0;
		this.epsilonMax = epsilonMax;
		this.pi = pi;
		this.crossoverType = crossoverType;
	}

	public String toString() {
		StringBuilder out = new StringBuilder();

		out.append("v[");
		for(int i=0; i<10; i++) {
			out.append(",");
			out.append((value[i]+"").substring(0,4));//quick and dirty formatter because security prevents String.format			
		}
		out.append("], s[");
		for(int i=0; i<10; i++) {
			out.append(",");
			out.append((sigma[i]+"").substring(0,Math.min(4, (sigma[i]+"").length())));//quick and dirty formatter because security prevents String.format			
		}
		out.append("]");
		return out.toString();
	}

	public double[][] getCovarianceMatrix(){
		//TODO implement this from sigma and alpha
		return null;
	}

	public static class GenomeBuilder{
		public double[] value;
		public double[] sigma; 
		public double[][] alpha;//rotational params, make this a lower triangular matrix? 
		public double tau; //global learning rate 
		public double tauPrime; // coordinateWise learning rate
		public double beta;//rotational learning rate
		public double epsilon0; //minimum stdev 
		public double epsilonMax;


		public double pi;//maximum rotational param
		public CrossoverType crossoverType;

		public GenomeBuilder(){
			//TODO init array fields
			//TODO maybe set some defaults here?
		}

		public GenomeBuilder(Genome fromGenome){
			this.value = fromGenome.value.clone();
			this.sigma = fromGenome.sigma.clone();
			this.alpha = fromGenome.alpha.clone();
			this.tau = fromGenome.tau;
			this.tauPrime = fromGenome.tauPrime;
			this.beta = fromGenome.beta;
			this.epsilon0 = fromGenome.epsilon0;
			this.epsilonMax = fromGenome.epsilonMax;
			this.pi = fromGenome.pi;
			this.crossoverType = fromGenome.crossoverType;
		}

		public GenomeBuilder setValue(double[] value) {
			this.value = value;
			return this;
		}

		public GenomeBuilder setSigma(double[] sigma) {
			this.sigma = sigma;
			return this;
		}

		public GenomeBuilder setAlpha(double[][] alpha) {
			this.alpha = alpha;
			return this;
		}

		public GenomeBuilder setTau(double tau) {
			this.tau = tau;
			return this;
		}

		public GenomeBuilder setTauPrime(double tauPrime) {
			this.tauPrime = tauPrime;
			return this;
		}

		public GenomeBuilder setBeta(double beta) {
			this.beta = beta;
			return this;
		}

		public GenomeBuilder setEpsilon0(double epsilon0) {
			this.epsilon0 = epsilon0;
			return this;
		}

		public GenomeBuilder setEpsilonMax(double epsilonMax) {
			this.epsilonMax = epsilonMax;
			return this;
		}

		public GenomeBuilder setPi(double pi) {
			this.pi = pi;
			return this;
		}

		public GenomeBuilder setCrossoverType(CrossoverType crossoverType) {
			this.crossoverType = crossoverType;
			return this;
		}

		public Genome createGenome() {
			return new Genome(value, sigma, alpha, tau, tauPrime, beta, epsilon0, epsilonMax, pi, crossoverType);
		}
	}

}
