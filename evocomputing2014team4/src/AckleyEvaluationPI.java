

import java.util.Properties;

import org.vu.contest.ContestEvaluation;

// This is an example evalation. It is based on the standard sphere function. It is a maximization problem with a maximum of 10 for 
//  	vector x=0.
// The sphere function itself is for minimization with minimum at 0, thus fitness is calculated as Fitness = 10 - 10*(f-fbest), 
//  	where f is the result of the sphere function
// Base performance is calculated as the distance of the expected fitness of a random search (with the same amount of available
//	evaluations) on the sphere function to the function minimum, thus Base = E[f_best_random] - ftarget. Fitness is scaled
//	according to this base, thus Fitness = 10 - 10*(f-fbest)/Base
public class AckleyEvaluationPI implements ContestEvaluation 
{
	// Evaluations budget
	private final static int EVALS_LIMIT_ = 10000;
	// The base performance. It is derived by doing random search on the sphere function (see function method) with the same
	//  amount of evaluations
	private final static double BASE_ = 5.765;
	// The minimum of the sphere function
	private final static double ftarget_=0;
	
	// Best fitness so far
	private double best_;
	// Evaluations used so far
	private int evaluations_;
	
	// Properties of the evaluation
	private String multimodal_ = "true";
	private String regular_ = "true";
	private String separable_ = "false";
	private String evals_ = Integer.toString(EVALS_LIMIT_);

	public boolean simulate = false;
	public AckleyEvaluationPI()
	{
		best_ = 0;
		evaluations_ = 0;		
	}

	private double function(double[] xIn)
	{	
		double[] x =new double[10];
		for(int i=0;i<10;i++) {
			x[i] = xIn[i] - Math.PI;
		}
		double norm = 0;
		for(int i=0; i<10; i++) norm += x[i]*x[i];
		
		double cosSum = 0;
		for(int i=0; i<10; i++) cosSum += Math.cos(Math.PI*2*x[i]);
		
		double out = -20 * Math.exp(-0.2*Math.sqrt(0.1*norm))
		- Math.exp(+0.1*cosSum)
		+ 20 + Math.E;	
		return out;
	}
	
	@Override
	public Object evaluate(Object result) 
	{
		// Check argument
		if(!(result instanceof double[])) throw new IllegalArgumentException();
		double ind[] = (double[]) result;
		if(ind.length!=10) throw new IllegalArgumentException();
		if(simulate) {
			return function(ind);
		}else {
		if(evaluations_>EVALS_LIMIT_) return null;
		
		// Transform function value (sphere is minimization).
		// Normalize using the base performance
		double f = 10 - 10*( (function(ind)-ftarget_) / BASE_ ) ;
		if(f>best_) best_ = f;
		evaluations_++;
		

		
		return new Double(f);
		}
	}

	@Override
	public Object getData(Object arg0) 
	{
		return null;
	}

	@Override
	public double getFinalResult() 
	{
		return best_;
	}

	@Override
	public Properties getProperties() 
	{
		Properties props = new Properties();
		props.put("Multimodal", multimodal_);
		props.put("Regular", regular_);
		props.put("Separable", separable_);
		props.put("Evaluations", evals_);
		return props;
	}
}
