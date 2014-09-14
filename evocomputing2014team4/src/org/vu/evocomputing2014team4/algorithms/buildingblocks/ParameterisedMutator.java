package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import org.vu.evocomputing2014team4.algorithms.RandomSampler;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.Mutator;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome.CrossoverType;

public class ParameterisedMutator implements Mutator {

	private boolean useCovariance = true;

	private double temp = 1; 
	private double tempRate = 0.1;

	private double maxTemp = 1;
	private double minTemp = 0.25;
	private double crossoverMutationChance= 0; 
	private CrossoverType[] crossoverTypes = {CrossoverType.LOCAL_INTERMEDIARY, CrossoverType.LOCAL_DISCRETE, CrossoverType.NONE};
	public ParameterisedMutator() {
		// TODO Auto-generated constructor stub
	}

	public ParameterisedMutator(double crossoverMutationChance) {
		this.crossoverMutationChance = crossoverMutationChance;
	}

	public void setCrossoverMutationChance(double p){
		this.crossoverMutationChance = p;

	}



	public double getTemp() {
		return temp;
	}




	public void setTemp(double temp) {
		this.temp = temp;
	}





	public double getTempRate() {
		return tempRate;
	}




	public void setTempRate(double tempRate) {
		this.tempRate = tempRate;
	}


	public void heat() {
		temp = temp * Math.exp(tempRate);
		if(temp > maxTemp) {
			temp = maxTemp;
		}
	}

	public void cool() {
		temp = temp*Math.exp(-tempRate);
		if(temp < minTemp) {
			temp = minTemp;
		}
	}




	@Override
	public Genome mutate(Genome genome) {

		//Mutate sigma's 
		double allSigmaMutation = RandomSampler.getGaussian(genome.tau);
		double newSigma[] = new double[10];
		for(int i=0; i<genome.sigma.length; i++) {
			newSigma[i] = genome.sigma[i]* Math.exp(allSigmaMutation + RandomSampler.getGaussian(genome.tauPrime)); 

			if(newSigma[i] < genome.epsilon0) {
				newSigma[i] = genome.epsilon0;
			}else if(newSigma[i] > genome.epsilonMax) {
				newSigma[i] = genome.epsilonMax;
			}
		}

		double newAlpha[][] = new double[10][10];
		for(int i=0; i<genome.sigma.length; i++) {
			for(int j=0; j<i; j++) {
				newAlpha[i][j] = genome.alpha[i][j] + genome.beta*RandomSampler.getGaussian(1);

				if(Math.abs(newAlpha[i][j]) > genome.pi) {
					newAlpha[i][j] = newAlpha[i][j]-2*genome.pi*Math.signum(newAlpha[i][j]);
				}
			}
		}

		

		double[] newValue = new double[10];
		if(useCovariance) {
			double[] randSamp = RandomSampler.getMultivariateGaussian(genome.getCovarianceMatrix());
			
			for(int i=0; i < genome.value.length; i++) {

				newValue[i] =  genome.value[i] + temp*randSamp[i];
				if(Math.abs(newValue[i]) > 5) {
					double outOfBoundary = Math.abs(newValue[i]) - 5;//'Bounce' of the walls
					newValue[i] = Math.signum(newValue[i])*(5 - outOfBoundary);
				}

			}
		}else {

			//Mutate values 
			for(int i=0; i < genome.value.length; i++) {
				double randomSamp = RandomSampler.getGaussian(newSigma[i]);
				newValue[i] =  genome.value[i] + randomSamp;
				//			while(newValue[i] < -5 || newValue[i] > 5) {//Wrap around at the edges 
				//				newValue[i] -= 10*Math.signum(newValue[i]);
				//			}
				if(Math.abs(newValue[i]) > 5) {
					double outOfBoundary = Math.abs(newValue[i]) - 5;//'Bounce' of the walls
					newValue[i] = Math.signum(newValue[i])*(5 - outOfBoundary);
				}

			}
		}

		CrossoverType crossoverType; 
		if(RandomSampler.getUniform() < crossoverMutationChance) {
			crossoverType = crossoverTypes[RandomSampler.getInt(3)];
		}else {
			crossoverType = genome.crossoverType;
		}

		Genome newGenome = new Genome.GenomeBuilder(genome).
				setValue(newValue).
				setSigma(newSigma).
				setCrossoverType(crossoverType).
				createGenome();

		return newGenome;
	}

}
