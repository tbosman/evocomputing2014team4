# Project setup 
## Datastructures 
The representation is split up in a few different classes: 
### Genome
This holds the solution as well as any other strategy parameters like stdev and crossover strategy etc. 
Genome is immutable and should be constructed using the nested GenomeBuilder class. A copy constructor is included for use in mutation etc. 
### Individual 
Represents an individual with a definitive genotype and fitness, both of which are immutable. 
### Embryo 
Represents a genotype that is still mutable and has no fitness defined yet. Any actions on the offspring pool can be done to these objects before they are finalized into the next generation, which can be done with the birth() method. At that point fitness is evaluated and an Individual object produced. 
### Population 
Represents a set of individuals. Any useful statistics about the current population should be implemented as methods in this class. 
## Building blocks
Any implementation of AbstractEvolutionaryAlgorithm can use the following  building blocks to define the settings of an EA. At this point only interfaces are defined but implementing classes should specify different strategies/methods. 

(-Question?-: Maybe AbstractEvolutionaryAlgorithm should be split up in another two classes with one controller for mu and lambda, and the algorithm taken those two as input.) 
### Initialiser 
Initialises a population, for example randomly or as a grid. Strategy params in the genotype need to be initialised here too. -Question?- How paramaterised should this class be? 

### FitnessFunction 
To evaluate fitness of a genotype. Abstraction of the direct function to be optimised so that for example penalties for clustering can be added. 

### Breeder  
Defines how individuals are selected for crossover and handles the crossover process. 

### MonogamousCrossover
Crossover between two parents (local). Takes two individuals as argument. Trivial implementations are discrete and intermediary. 

### PolygamousCrossover
Crossover between entire population. Takes population and an individual as an argument, with the latter one providing the genes for any relevant strategy parameters (like crossover type). Trivial implementations are discrete and intermediary. 

### Mutator 
Defines mutation strategy. The solution value mutation should probably be covariance adaptive gaussian for all implementations, but other strategy parameters might need mutation too. 

### SurvivorSelector
Defines survivor selection strategy and returns the trimmed population. 


# TODOLIST 
- Pick library for random sampling
