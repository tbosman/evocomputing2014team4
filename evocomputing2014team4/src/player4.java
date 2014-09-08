

import org.vu.contest.ContestEvaluation;
import org.vu.contest.ContestSubmission;
import org.vu.evocomputing2014team4.algorithms.AbstractEvolutionaryAlgorithm;
import org.vu.evocomputing2014team4.algorithms.FirstEvolutionaryAlgorithm;

public class player4 implements ContestSubmission {
	AbstractEvolutionaryAlgorithm algo;
	ContestEvaluation evaluation_;

	public player4() {
		algo = new FirstEvolutionaryAlgorithm(1024, 3072);
	}


	@Override
	public void setEvaluation(ContestEvaluation evaluation) {
		algo.setEvaluation(evaluation);
		
	}

	@Override
	public void setSeed(long seed) {
		algo.setSeed(seed); 
	}

	@Override
	public void run() {
		algo.run();
		
	}
	
	public static void main(String... args) {
		
	}
}
