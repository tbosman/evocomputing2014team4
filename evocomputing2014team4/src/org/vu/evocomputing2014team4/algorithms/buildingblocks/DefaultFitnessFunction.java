package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import org.vu.contest.ContestEvaluation;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.FitnessFunction;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome;

public class DefaultFitnessFunction implements FitnessFunction {
	ContestEvaluation evaluation;
	public DefaultFitnessFunction(ContestEvaluation evaluation) {
		this.evaluation = evaluation; 
	}

	@Override
	public double fitness(Genome genome) {
		if (genome.value == null) {
			throw new Error("value = null");
		}
		if ((evaluation == null)) {
			throw new Error("evaluation = mull");
		}
				
//		for(int i=0; i<10;i++) {
//			System.out.print(", "+genome.value[i]);
//		}
//		System.out.println(" <- value of genome");
		Object fitness = evaluation.evaluate(genome.value);
		if(fitness != null) {
			return (double) fitness;
		}else {
			return 0;
		}
	}

	
}
