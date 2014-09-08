package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import org.vu.evocomputing2014team4.algorithms.RandomSampler;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.Mutator;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome;

public class DefaultMutator implements Mutator {

	public DefaultMutator() {
		// TODO Auto-generated constructor stub
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
		
		
		//Mutate values 
		double[] newValue = new double[10];
		for(int i=0; i < genome.value.length; i++) {
			 double randomSamp = RandomSampler.getGaussian(newSigma[i]);
			newValue[i] =  genome.value[i] + randomSamp;
			while(newValue[i] < -5 || newValue[i] > 5) {//Wrap around at the edges 
				newValue[i] -= 10*Math.signum(newValue[i]);
			}
			
		}
		
		Genome newGenome = new Genome.GenomeBuilder(genome).
				setValue(newValue).
				setSigma(newSigma).
				createGenome();
		
		return newGenome;
	}

}
