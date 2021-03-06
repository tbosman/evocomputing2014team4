package org.vu.evocomputing2014team4.algorithms.buildingblocks;

import java.util.Collections;
import java.util.List;

import org.vu.evocomputing2014team4.algorithms.buildingblocks.KMeansClustering.Cluster;
import org.vu.evocomputing2014team4.algorithms.buildingblocks.interfaces.SurvivorSelector;
import org.vu.evocomputing2014team4.algorithms.datastructures.Individual;
import org.vu.evocomputing2014team4.algorithms.datastructures.Population;


/**
 * Uses k means clustering to select survivors 
 * The fittest survivor is selected from every cluster iteratively, mainly as a means of enforcing diversity  
 * @author tbosman
 *
 */
public class KMeansClusteringSelector implements SurvivorSelector {	
	int numClusters;
	KMeansClustering clusterer; 
	public KMeansClusteringSelector(int numClusters) {
		this.numClusters = numClusters;
		clusterer = new KMeansClustering(numClusters);
	}

	@Override
	public Population selectSurvivors(Population population,
			int desiredPopulationSize) {
		
		List<Cluster> clusters = clusterer.findClusters(population);
		
		Population survivors = new Population(); 
		while(survivors.size() < desiredPopulationSize) {
			for(int i=0; i<clusters.size();i++) {
				if(clusters.get(i).size() == 0) {
					clusters.remove(i);
					i--;
				}else {
					List<Individual> individuals = (List<Individual>) clusters.get(i).getMembers();
					Collections.sort(individuals);
					Individual fittestInCluster =individuals.get(individuals.size()-1); 
					survivors.add(fittestInCluster);
					clusters.get(i).remove(fittestInCluster);
				}
			}
		}
		
		return survivors;
	}

}
