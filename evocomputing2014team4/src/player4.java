

import org.vu.contest.ContestEvaluation;
import org.vu.contest.ContestSubmission;
import org.vu.evocomputing2014team4.algorithms.AbstractEvolutionaryAlgorithm;
import org.vu.evocomputing2014team4.algorithms.CMAESEvolutionaryAlgorithm;
import org.vu.evocomputing2014team4.algorithms.FirstEvolutionaryAlgorithm;
import org.vu.evocomputing2014team4.algorithms.SecondEvolutionaryAlgorithm;

public class player4 implements ContestSubmission {
	AbstractEvolutionaryAlgorithm algo;
	ContestEvaluation evaluation_;

	public player4() {
		algo = new SecondEvolutionaryAlgorithm(1,1);
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
