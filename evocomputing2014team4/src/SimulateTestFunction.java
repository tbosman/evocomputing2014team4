import org.vu.contest.ContestEvaluation;
import org.vu.evocomputing2014team4.algorithms.RandomSampler;


public class SimulateTestFunction {

	 ContestEvaluation evaluation_;
	 int numEval = 10000; 
	 
	public SimulateTestFunction() {
		// TODO Auto-generated constructor stub
	}

	public double[] getRandomDoubleVector() {
		double[] rand = new double[10];
		for(int i=0;i<10;i++) rand[i] = RandomSampler.getUniform()*10 - 5;
		return rand;
	}
	
	public double getMinimumOver(int numSim) {
		double min = Double.MAX_VALUE; 
				
		for(int i=0; i<numSim; i++) {
			double current =  (double)evaluation_.evaluate(getRandomDoubleVector());
			if(current < min) {
				min = current;
			}
		}
		return min;
	}
	
	public void start() { 
		AckleyEvaluation evaluation = new AckleyEvaluation();
		evaluation.simulate = true; 
		this.evaluation_ = evaluation;
		
		double sum = 0;
		double numSim = 2500; 
		for(int i=0; i<numSim;i++) {
			double av = getMinimumOver(numEval);
			System.out.println(av);
			sum += av; 
			
		}
		double average = sum/numSim;
		System.out.println("Average: "+average);
		
	}
	public static void main(String[] args) {
		new SimulateTestFunction().start();

	}

}
