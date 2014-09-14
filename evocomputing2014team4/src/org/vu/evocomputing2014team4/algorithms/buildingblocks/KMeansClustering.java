package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import java.util.ArrayList;
import java.util.List;

import org.vu.evocomputing2014team4.algorithms.RandomSampler;
import org.vu.evocomputing2014team4.algorithms.datastructures.Embryo;
import org.vu.evocomputing2014team4.algorithms.datastructures.GenomeCarrier;

public class KMeansClustering {
	int numClusters; 
	ArrayList<Cluster> clusters;
	int MAX_IT = 1000000;
	
	public KMeansClustering(int numClusters) {
		this.numClusters = numClusters;
		
	}
	
	
	public void initClusters() {
		clusters = new ArrayList<Cluster>(numClusters);
		for(int i=0; i<numClusters; i++) clusters.add(new Cluster(randVector()));
	}
	
	public double[] randVector() {
		double[] value = new double[10];
		for(int i=0;i<10;i++) {
			value[i] = RandomSampler.getUniform()*10-5;
		}
		return value;
	}

	
	public class Cluster{
		double[] mean;
		ArrayList<GenomeCarrier> members; 
		
		Cluster(double[] mean){
			this.mean = mean;
			this.members = new ArrayList<GenomeCarrier>(); 
		}
		
		double getDistanceTo(GenomeCarrier gc) {
			double dist = 0; 
			for(int i=0;i<10;i++) {
				dist += (gc.getGenome().value[i] - mean[i])*(gc.getGenome().value[i] - mean[i]);
			}
			return Math.sqrt(dist);
		}
		
		public List<? extends GenomeCarrier> getMembers(){
			return members;
		}
		
		public void remove(GenomeCarrier gc) {
			members.remove(gc);
		}
		
		public void addMember(GenomeCarrier gc) {
			members.add(gc);
		}
		/**
		 * 
		 * @return true if center was changed
		 */
		public boolean relocate() {
			
			if(size() == 0) {//No members do nothing
				return false; 
			}
			
			double[] newMean = new double[10];
			for(GenomeCarrier gc : members) {
				for(int i=0; i<10;i++) {
					newMean[i] += gc.getGenome().value[i];
				}
			}
			
			for(int i=0;i<10;i++) {
				newMean[i] = newMean[i] / members.size();
			}
			
			boolean changed = false;
			for(int i=0; i<10;i++) {
				if(mean[i] != newMean[i]) {
					mean[i] = newMean[i];
					changed = true;
				}
			}
			
			return changed;
			
		}
		
		public void clear() {
			members.clear();
		}
		
		public int size() {
			return members.size();
		}
		
		public String toString() {
			String out = "Sz: "+ members.size() + "[";
			for(double d : mean) {
				out = out+ ", "+d;
			}
			return out + "]";					
		}
		
	}

	public List<Cluster> findClusters(List<? extends GenomeCarrier> points) {
		initClusters();
		boolean clustersNotConverged = true;
		
		int it = 0;
		while(clustersNotConverged && it < MAX_IT) {
			
			
			it++;
			clustersNotConverged = false; 
			
			//reset cluster members
			for(Cluster cluster : clusters) cluster.clear();
			
			for(GenomeCarrier point : points) {//add point to closest cluster
				double minDistance = Double.MAX_VALUE; 
				Cluster minCluster = null; 
				for(Cluster cluster : clusters) {
					double dist = cluster.getDistanceTo(point);
					if(dist<minDistance) {
						minDistance = dist; 
						minCluster = cluster; 
					}
				}
				minCluster.addMember(point);
			}
			
			//recalculate centers and update converging tracking variable
			for(Cluster cluster : clusters) {
				clustersNotConverged =  cluster.relocate() || clustersNotConverged;
			}		
		}
		
		//remove empty clusters (if any) 
		for(int i=0; i<clusters.size();i++) {
			if(clusters.get(i).size() == 0) {
				clusters.remove(i);
				i--;
			}
		}
		
		//init new clusters (if any)
		if(clusters.size() < numClusters) {
			int make = numClusters - clusters.size(); 
			for(int i=0; i<make; i++) {
				clusters.add(new Cluster(randVector()));
			}
		}
		
		
		return clusters;
	}
	
	
}
