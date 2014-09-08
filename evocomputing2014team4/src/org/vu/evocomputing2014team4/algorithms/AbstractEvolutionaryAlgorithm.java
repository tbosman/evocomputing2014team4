package org.vu.evocomputing2014team4.algorithms;

import java.util.Properties;
import java.util.Random;

import org.vu.contest.ContestEvaluation;
/**
 * Abstract class so that any algorithm submission can be easily done by just changing the classname to be constructed in Team4.java
 * Any algo should manage at least population/offspring size and some implementation of Mutator and Breeder
 * @author tbosman
 *
 */
public abstract class AbstractEvolutionaryAlgorithm {
	Random rnd_; 
	ContestEvaluation evaluation_;
	protected boolean multimodal;
	protected boolean regular;
	public boolean isMultimodal() {
		return multimodal;
	}

	public boolean isRegular() {
		return regular;
	}

	public boolean isSeparable() {
		return separable;
	}

	public int getEvals() {
		return evals;
	}

	protected boolean separable; 
	protected int evals;
	
	AbstractEvolutionaryAlgorithm(){
		rnd_ = new Random(); 
	}
	
	public void setSeed(long seed) {
		rnd_.setSeed(seed);
	}
	
	public void setEvaluation(ContestEvaluation evaluation) {
		evaluation_ = evaluation;
		Properties props = evaluation.getProperties();
		multimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
		regular = Boolean.parseBoolean(props.getProperty("Regular"));
		separable = Boolean.parseBoolean(props.getProperty("Separable"));
		evals = Integer.parseInt(props.getProperty("Evaluations"));
		
	}
	
	abstract public void run(); 

	
}
