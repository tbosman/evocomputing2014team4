package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import java.util.*;

import org.vu.contest.ContestEvaluation;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.FitnessFunction;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome;

public class HashedFitnessFunction implements FitnessFunction {
	ContestEvaluation evaluation;
	int evalsLeft;
	HashMap<List<Double>, Double> allEvaluated = new HashMap<List<Double>, Double>();
	
	
	public HashedFitnessFunction(ContestEvaluation evaluation) {
		this.evaluation = evaluation;
		evalsLeft = Integer.parseInt(evaluation.getProperties().getProperty("Evaluations"));
		
		
		
	}

	private List<Double> arrayToList(double[] values){
		ArrayList<Double> valueList = new ArrayList<Double>();
		for(double d : values) {
			valueList.add(d);
		}
		return valueList;
	}
	@Override
	public double fitness(Genome genome) {
		if (genome.value == null) {
			throw new Error("value = null");
		}
		if ((evaluation == null)) {
			throw new Error("evaluation = mull");
		}
		
		List<Double> valueList = arrayToList(genome.value);
		if(allEvaluated.containsKey(valueList)) {
//			System.out.println("#DBG: Cached!");
			return allEvaluated.get(valueList);
		}
		
		Object fitness = evaluation.evaluate(genome.value);
		evalsLeft--;
		if(fitness != null) {
			
			allEvaluated.put(valueList, (Double) fitness);
			return (double) fitness;
		}else {
			return 0;
		}
	}


	@Override
	public int evalsLeft() {
		return evalsLeft;
	}

	
}
