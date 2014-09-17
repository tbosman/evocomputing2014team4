package org.vu.evocomputing2014team4.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.vu.evocomputing2014team4.Util;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.*;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.KMeansClustering.Cluster;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.*;
import org.vu.evocomputing2014team4.algorithms.datastructures.Embryo;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome;
import org.vu.evocomputing2014team4.algorithms.datastructures.GenomeCarrier;
import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;
import org.vu.evocomputing2014team4.algorithms.datastructures.Genome.CrossoverType;

public class SecondEvolutionaryAlgorithm extends AbstractEvolutionaryAlgorithm {

	private int populationSize;
	private int offspringSize; 
	private Initialiser initialiser; 
	private FitnessFunction fitnessFunction;
	private Mutator mutator; 
	private int evalsLeft;

	boolean muPlusLambda = false; 

	private int TOURNAMENT_SIZE = 10;


	ArrayList<Double> bestList = new ArrayList<Double>();
	private boolean instantReturn = false;
	private int startSize;
	private boolean addZeroVector = false;


	public boolean verbose = false;

	private double crossoverTypeMutationChance = 0.1;
	
	private Individual globalBest;

	public SecondEvolutionaryAlgorithm(int populationSize, int offspringSize) {
		super();
		this.populationSize = populationSize;
		this.offspringSize = offspringSize;
		


	}

	@Override
	public void run() {
		if(instantReturn) {
			return;
		}

		Population currentPopulation;
		currentPopulation = initialiser.initialisePopulation(startSize);
		this.evalsLeft -= startSize;
		bestList.add(currentPopulation.getMaximumFitness());
		globalBest = (currentPopulation.get(0));

		//			if(addZeroVector ) {
		//				Genome zeroGenome = new Genome.GenomeBuilder(currentPopulation.get(0).genome).
		//						setValue(new double[10]).createGenome();
		//				currentPopulation.add(new Individual(zeroGenome, fitnessFunction.fitness(zeroGenome)));
		//			}

		Breeder breeder = new DefaultBreeder();
		//		SurvivorSelector survivorSelector = new TournamentSurvivorSelector(offspringSize/TOURNAMENT_SIZE);
		SurvivorSelector survivorSelector = new KMeansClusteringSelector(populationSize/4);
		
		currentPopulation = survivorSelector.selectSurvivors(currentPopulation, populationSize);
		int iteration = 1;
		while(fitnessFunction.evalsLeft() > 0) {


			Iterable<Embryo> embryos = breeder.breed(currentPopulation, offspringSize);

			embryos = mutateAll(embryos, false);
			Population newPopulation = raiseAll(embryos);

//			int numClusters = 10; 
//			boolean useHeuristicClusteringNumber = true; 
//			if(useHeuristicClusteringNumber) {
//				numClusters = 1+(evalsLeft/evals)*populationSize/2;
//			}
//
//			if(isMultimodal() || !isRegular() || true) {
//				survivorSelector = new KMeansClusteringSelector(numClusters);
//			}


			bestList.add(newPopulation.getMaximumFitness());


			newPopulation = survivorSelector.selectSurvivors(newPopulation, populationSize);



			currentPopulation = newPopulation;

			evalsLeft -= offspringSize;

			adjustTemperature();
			Collections.sort(currentPopulation);
			Individual best = currentPopulation.get(currentPopulation.size()-1);
			if(best.fitness >globalBest.fitness) {
				globalBest = best;
			}
			if(verbose) {
				
				
				System.out.println("#DBG: Precision of best: "+best.genome.precision);
				System.out.print("#DBG: Sigm of best: ");
				for(double d : best.genome.sigma) {
					System.out.print(Util.roundNDecimals(d,3) + ",");
				}
				System.out.print("#DBG: Value of best: ");
				for(double d : best.genome.value) {
					System.out.print(Util.roundNDecimals(d,best.genome.precision) + ",");
				}
				System.out.println();
			}


			//			System.out.println("Iteration: "+iteration++ +" - best fitness: "+currentPopulation.getMaximumFitness());
		}
		if(verbose) {
			System.out.println("Temp: "+mutator.getTemp());
			System.out.println("#DBG: Precision of Globalbest: "+globalBest.genome.precision);
			System.out.print("#DBG: Sigm of Gbest: ");
			for(double d : globalBest.genome.sigma) {
				System.out.print(Util.roundNDecimals(d,3) + ",");
			}
			System.out.print("#DBG: Value of Gbest: ");
			for(double d : globalBest.genome.value) {
				System.out.print(Util.roundNDecimals(d,globalBest.genome.precision) + ",");
			}
			System.out.println(" #DBG: fitness of Gbest: "+globalBest.fitness);
		
			
		}
		//		System.out.println("#DBG Best/Temp"+ bestList.get(bestList.size()-1) + " - "+((DefaultMutator)mutator).getTemp());
	}

	private void adjustTemperature() {


		//TODO heat/cool mutator
		//		int lookback = 5;
		int lookback = bestList.size()/5;
		double tol = 0.1;
		if(bestList.size() >= 5 && false) {
			if(bestList.get(bestList.size()-1)>bestList.get(bestList.size()-2)+tol) {
				mutator.setTemp(0.1);
			}	
			else {
				boolean worst = true;
				for(int i=2 ; i<=lookback+1;i++) {
					if(bestList.get(bestList.size()-1) > bestList.get(bestList.size()-i)) {
						worst = false;
					}
				}

				if(worst) {
					mutator.heat();
				}else {
					mutator.cool();
				}
			}
		}

		List<Double> sortList = new ArrayList<Double>();
		sortList.addAll(bestList);
		Collections.sort(bestList);
		double last = sortList.get(sortList.size()-1);
		double best = bestList.get(bestList.size()-1);


		//System.out.println(((DefaultMutator)mutator).getTemp());
		//		System.out.println("#DBG best"+bestList.get(bestList.size()-1));
		if(verbose) {
			System.out.println("#DBG Best/Last/Temp "+best+ " - " + last + " - "+mutator.getTemp());
		}
	}
	private Population raiseAll(Iterable<Embryo> embryos) {
		Population  newPopulation = new Population();
		for(Embryo current : embryos) {
			//			System.out.println("In raiseall: "+current.getGenome());
			newPopulation.add(current.birth(fitnessFunction));
		}

		return newPopulation;
	}

	private Iterable<Embryo> mutateAll(Iterable<Embryo> embryos, boolean skipCrossover) {
		ArrayList<Embryo> outEmbryos = new ArrayList<Embryo>();
		for(Embryo current : embryos) {
			Embryo newEmbryo;
			if(!skipCrossover || current.getGenome().crossoverType == CrossoverType.NONE) {
				newEmbryo = new Embryo(mutator.mutate(current.getGenome()));
			}else {
				newEmbryo = current;
				System.err.println("#DBG: no x/o "+current.getGenome().crossoverType);
			}
			outEmbryos.add(newEmbryo);

		}
		return outEmbryos;
	}
	private void init() {
		this.fitnessFunction = new HashedFitnessFunction(evaluation_);
		ParameterisedRandomInitialiser initialiser = new ParameterisedRandomInitialiser(fitnessFunction);
		ParameterisedMutator mutator = new ParameterisedMutator(crossoverTypeMutationChance);
		
		mutator.setMaxPrecision(8);
		
		
		
		this.evalsLeft = this.evals;

		//		if(!isRegular() && !isSeparable()) {
		//			initialiser.defaultCrossover = CrossoverType.LOCAL_DISCRETE;
		//			initialiser.defaultCrossoverSet = true;
		//			muPlusLambda = true;
		//		}

		if(isMultimodal()) {
			initialiser.defaultEpsilon0 = 0.001;
		}









		int offspringMulti = 4; 



		//		if(muPlusLambda) {
		//			if(getEvals() <= 1000000) {
		//				this.populationSize = 5;
		//			}else {
		//				this.populationSize = 20;
		//			}
		//			initialiser.minValue = -5;
		//		}else {
		////			this.addZeroVector  = true;
		//		}

		//		this.addZeroVector  = true;

		this.populationSize = 50;

		//		initialiser.maxValue = 0;
		if(isRegular()) {
			initialiser.minValue = 0;
			mutator.setModuloPrecision(false);
			mutator.setPrecisionMutationChance(0.5);//Regularity allows for precision to converge more quickly
			this.populationSize = 100;
			
		}

		this.startSize = this.populationSize*4; 
		this.offspringSize = 4*this.populationSize;

		this.initialiser = initialiser;
		this.mutator = mutator;
	}

	@Override
	public void onSetEvaluation() {
		init();		
	}

}
