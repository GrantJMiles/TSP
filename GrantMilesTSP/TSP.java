import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.HashMap;


/**
 * Simple GA for the Travelling Salesman Problem.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TSP
{
    /**
     * The number of cities of the TSP instance.
     */
    private int SIZE;
    
    /**
     * TSP cost matrix.
     */
    private int[][] COST;

    /**
     * The population size.
     */
    private static final int POPULATION_SIZE = 100;
    //private int POPULATION_SIZE;
    /**
     * The tournament size
     */
    private static final int TOURNAMENT_SIZE = 5;
    /**
     * The number of generations.
     */
    private static final int MAX_GENERATION = 5000;
    //private int MAX_GENERATION;
    /**
     * Probability of a mutation
     */
    private static final double MUTATION_PROBABILITY = 0.05;
    /**
     * Random number generation.
     */
    private Random random = new Random();
        
    /**
     * The current population;
     */
    private int[][] population = new int[POPULATION_SIZE][SIZE];
    
    /**
     * Fitness values of each individual of the population.
     */
    private int[] fitness = new int[POPULATION_SIZE];
    /*
     * Variables to aid my testing process. This will let me create test classes and define which implementation to use.
     */
    private String mutationType;
    private String crossoverType;
    private String selectionType;
    /**
     * Constructor that allows the choosing of which type of crossover to use.
     * @param mutation - This can be "sim" for Simple Inversion Mutation or "em" for Exchange Mutation
     * @param crossover - This can be "cx" for Cycle Crossover or "pmx" for Partially Mapped Crossover
     */
    public TSP(String mutation, String crossover, String selection) {
        if (mutation.equals("em") || mutation.equals("sim")) {
            mutationType = mutation;
        } else {
            mutationType = "sim";
        }
        if (crossover.equals("cx") || crossover.equals("pmx")) {
            crossoverType = crossover;
        } else {
            crossoverType = "cx";
        }
        if (selection.equals("r") || selection.equals("t")) {
            selectionType = selection;
        } else {
            selectionType = "t";
        }
    }
    /**
     * Constructor used for overnight testing. Set population and maximum generations
     */
    public TSP(String mutation, String crossover, String selection,int population, int maxGen) {
        if (mutation.equals("em") || mutation.equals("sim")) {
            mutationType = mutation;
        } else {
            mutationType = "sim";
        }
        if (crossover.equals("cx") || crossover.equals("pmx")) {
            crossoverType = crossover;
        } else {
            crossoverType = "cx";
        }
        if (selection.equals("r") || selection.equals("t")) {
            selectionType = selection;
        } else {
            selectionType = "t";
        }
        System.out.println();
        System.out.println("Population: " + population + " Generations: " + maxGen + " Crossover = " + 
                        crossoverType + " Mutation " + mutationType + " Selection: " + selectionType);
    }
    /**
     * Overridden construcotr to set standard values.
     */
    public TSP() {
        mutationType = "sim";
        crossoverType = "cx";
        selectionType = "r";
    }
    /**
     * Starts the execution of the GA.
     * 
     * @param filename the TSP file instance.
     */
    public void run(String filename) {
        //--------------------------------------------------------------//
        // loads the TSP problem                                        //
        //--------------------------------------------------------------//
        load(filename);
        //Create the population and assess the fitness.
        initialise();
        //Print out the header of the information that will be printed for each generation
        //System.out.println("Best,Average,Worse,Standard Deviation");
        /* ************************************************************
         * Loop until the terminiation criteria for max generations is
         * satisfied.
         * ***********************************************************/
        for (int g = 0; g < MAX_GENERATION; g++) {
            //Create a copy of the population in order to work with it.
            int[][] populationCopy = new int[POPULATION_SIZE][SIZE];
            int currentPopulationSize = 0;
            // While the new population is being created, loop through and put the offspring (next gen)
            //into the new population.
            while(currentPopulationSize < POPULATION_SIZE - 1) {
                //Get the value to use again the mutation probability
                double probability = random.nextDouble();
                if (probability <= MUTATION_PROBABILITY || (POPULATION_SIZE - currentPopulationSize) == 2) {
                    //Select the parent
                    int parent;
                    if (selectionType.equals("t")) {
                        parent = tournamentSelect();
                    } else {
                        parent = rouletteSelect();
                    }
                    int[] offspring = new int[SIZE];
                    if (mutationType.equals("sim")) {
                        //create the offspring via mutation
                        offspring = simpleInversionMutation(parent);
                    } else if (mutationType.equals("em")) {
                        //create the offspring via mutation
                        offspring = exchangeMutation(parent);
                    }
                    //Copy the offsping to the new population
                    copy(populationCopy, offspring, currentPopulationSize);
                    //increase the current population size
                    currentPopulationSize++;
                } else {
                    // Get the index's of what will be the two parents.
                    // Dependant on the selection type defined in the creation of the GA
                    int first,second;
                    if (selectionType.equals("t")) {
                        first = tournamentSelect();
                        second = tournamentSelect();
                    } else {
                        first = rouletteSelect();
                        second = rouletteSelect();
                    }
                    //create the handle to hole the two new individuals (offspring)
                    int[][] offspring = new int[2][SIZE];
                    if (crossoverType.equals("pmx")) {
                        offspring = partiallyMappedCrossover(first,second);
                    }else if(crossoverType.equals("cx")) { 
                        offspring = cycleCrossover(first,second);
                    }
                    //Copy the offsping to the new population
                    copy(populationCopy, offspring[0], currentPopulationSize);
                    //increase the current population size
                    currentPopulationSize++;
                    //Copy the offsping to the new population
                    copy(populationCopy, offspring[1], currentPopulationSize);
                    //increase the current population size
                    currentPopulationSize++;
                }
            }
            /*/ Create a variable for the fitness of an individual, a container for the best and worse individuals fitness
            //total fitness is used for mean/average
            int fitness = -1, best = -1, worst = -1, totalFitness = 0;
            double average = 0;
            for (int a = 0; a < POPULATION_SIZE; a++) {
                fitness = getFitness(a);
                totalFitness += fitness;
                // If its the first time assign the fitness to the best & worse for comparison
                if (a == 0) {
                    best = fitness;
                    worst = fitness;
                }
                // Check to see if the individuals fitness is better/worse and assign to the value accordingly
                if (fitness < best) {
                    best = fitness;
                } else if (fitness > worst) {
                    worst = fitness;
                }
            }
            // Get the average fitness for all of the population
            average = totalFitness / POPULATION_SIZE;
            //create the double handles for the mean difference for each individual and the standard deviation
            double[] meanDiff = new double[POPULATION_SIZE];
            double stdDeviation = 0;
            //Calculate the mean difference for each individual
            for (int y = 0; y < POPULATION_SIZE; y++) {
                int individualFitness = getFitness(y);
                meanDiff[y] = Math.pow(individualFitness - average,2);
            }
            // Sum all of the mean differences
            for (int d = 0; d < meanDiff.length; d++) {
                stdDeviation += meanDiff[d];
            }
            //Divide by the popultaion size
            stdDeviation = stdDeviation / POPULATION_SIZE;
            //Get the square root to reveal the standard deviation
            stdDeviation = Math.sqrt(stdDeviation);
            //Print out the best, worst, average and standard deviation for this generation of individuals
            System.out.println(best + "," + average + "," + worst + "," + String.format("%.2f", stdDeviation) + "");
            */
            
            /* ****************************************
             * THE INTRODUCTION OF ELITISM
             * ***************************************/
            int elite = getBest();
            //Copy the elite individual to the new population
            copy(populationCopy, population[elite], currentPopulationSize);
            currentPopulationSize++;
            
            //assign the new population to the main population (overwrite)
            population = populationCopy;
            
            //Evaluate the new population
            calculateFitness();
        }
        // Create a variable for the fitness of an individual, a container for the best and worse individuals fitness
            //total fitness is used for mean/average
            int fitness = -1, best = -1, worst = -1, totalFitness = 0;
            double average = 0;
            for (int a = 0; a < POPULATION_SIZE; a++) {
                fitness = getFitness(a);
                totalFitness += fitness;
                // If its the first time assign the fitness to the best & worse for comparison
                if (a == 0) {
                    best = fitness;
                    worst = fitness;
                }
                // Check to see if the individuals fitness is better/worse and assign to the value accordingly
                if (fitness < best) {
                    best = fitness;
                } else if (fitness > worst) {
                    worst = fitness;
                }
            }
            // Get the average fitness for all of the population
            average = totalFitness / POPULATION_SIZE;
            //create the double handles for the mean difference for each individual and the standard deviation
            double[] meanDiff = new double[POPULATION_SIZE];
            double stdDeviation = 0;
            //Calculate the mean difference for each individual
            for (int y = 0; y < POPULATION_SIZE; y++) {
                int individualFitness = getFitness(y);
                meanDiff[y] = Math.pow(individualFitness - average,2);
            }
            // Sum all of the mean differences
            for (int d = 0; d < meanDiff.length; d++) {
                stdDeviation += meanDiff[d];
            }
            //Divide by the popultaion size
            stdDeviation = stdDeviation / POPULATION_SIZE;
            //Get the square root to reveal the standard deviation
            stdDeviation = Math.sqrt(stdDeviation);
            //Print out the best, worst, average and standard deviation for this generation of individuals
            System.out.println(best + "," + average + "," + worst + "," + String.format("%.2f", stdDeviation) + "");

    }
    /**
     * This function returns the index of the individual with the best fitness
     */
    private int getBest() {
        int bestFitness = -1, bestIndex = -1;
        for (int x = 0; x < POPULATION_SIZE; x++) {
            int fitness = getFitness(x);
            if (x == 0 || fitness < bestFitness) {
                bestFitness = fitness;
                bestIndex = x;
            }
        }
        return bestIndex;
    }
    /**
     * This mutation method implements the Simple Inversion Mutation (SIM)
     */
    private int[] simpleInversionMutation(int index) {
        int[] parent = population[index];
        int[] child = new int[SIZE];
        int pointOne = random.nextInt(SIZE), pointTwo = random.nextInt(SIZE);
        if (pointOne == 0) {
            pointOne++;
        }
        if (pointTwo == 0) {
            pointTwo++;
        }
        int min = Math.min(pointOne, pointTwo), max = Math.max(pointOne, pointTwo);
        int range = max - min;
        int[] inversion = new int[range];
        int counter = 0;
        for (int x = max; x > min; x--) {
            inversion[counter] = parent[x];
            counter++;
        }
        counter = 0;
        for (int i = 1; i < SIZE; i++) {
            if (i > min && i <= max) {
                child[i] = inversion[counter];
                counter++;
            } else {
                child[i] = parent[i];
            }
        }
        return child;
    }
    /*
     * This mutation method implements exchange mutation
     */
    private int[] exchangeMutation(int index) { 
        // Get the parent from the population
        int[] parent = population[index];
        //create a container for the offspring
        int[] offspring = new int[SIZE];
        //Get 2 random points for the exchange to take place.
        int pointOne = random.nextInt(SIZE), pointTwo = random.nextInt(SIZE);
        if (pointOne == 0) {
            pointOne++;
        }
        if (pointTwo == 0) {
            pointTwo++;
        }
        /* *********************************************************************
         * Loop through each node in the tour sequence and populate the offspring
         * with the same value as the parent UNLESS it is at one of the random
         * points in which case, make the switch
         * *******************************************************************/
        for (int x = 1; x < SIZE; x++) {
            if (x == pointOne) {
                offspring[x] = parent[pointTwo];
            } else if (x == pointTwo) {
                offspring[x] = parent[pointOne];
            } else {
                offspring[x] = parent[x];
            }
        }
        return offspring;
    }
    /**
     * This crossover funciton implements the Cycle Crossover
     */
    private int[][] cycleCrossover(int first, int second) {
        int[][] offspring = new int[2][SIZE];
        int[] parentOne = population[first], parentTwo = population[second];
        int startingIndex = random.nextInt(SIZE);
        if (startingIndex == 0) {
            startingIndex = 1;
        }
        int workingNode, startingNode, workingIndex;
        int[][] mapOne = new int[SIZE][1];
        int[][] mapTwo = new int[SIZE][1];
        
        startingNode = parentOne[startingIndex];
        workingNode = startingNode;
        workingIndex = startingIndex;
        
        mapTwo[startingIndex][0] = startingNode;
        mapOne[startingIndex][0] = parentTwo[startingIndex];
        workingNode = mapOne[startingIndex][0];
        workingIndex = getWorkingIndex(parentOne, workingNode);
        int counter = 0;
        while (workingIndex != startingIndex) {
            mapTwo[workingIndex][0] = parentOne[workingIndex];
            mapOne[workingIndex][0] = parentTwo[workingIndex];
            workingNode = mapOne[workingIndex][0];
            workingIndex = getWorkingIndex(parentOne, workingNode);
            counter++;
        }
        
        for (int x = 0; x < SIZE; x++) {
            if (mapOne[x][0] == 0) {
                offspring[0][x] = parentOne[x];
            } else {
                offspring[0][x] = mapOne[x][0];
            }
            if (mapTwo[x][0] == 0) {
                offspring[1][x] = parentTwo[x];
            } else {
                offspring[1][x] = mapTwo[x][0];
            }
        }   
        return offspring;
    }
    private int getWorkingIndex (int[] parent, int workingNode) {
        int value = -1;
        for (int x = 0; x < parent.length; x++) {
            //get the working index;
            if (parent[x] == workingNode) {
                value = x;
            }
        }
        if (value == -1) {
            System.out.println("getWorkingIndex | value is -1");
        }
        return value;
    }
    
    /**
     * This crossover method uses the PMX (Partially Mapped Crossover)
     */
    private int[][] partiallyMappedCrossover(int first, int second) { 
        int[][] offspring = new int[2][SIZE];
        int[] parentOne = population[first], parentTwo = population[second];
        //Get 2 random points for the crossover to take place.
        int pointOne = random.nextInt(SIZE), pointTwo = random.nextInt(SIZE);
        int max = Math.max(pointOne, pointTwo), min = Math.min(pointOne, pointTwo);
        int range = max - min;
        if (min == 0) {
            min++;
        }
        
        HashMap<Integer,Integer> parentOneMapping = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> parentTwoMapping = new HashMap<Integer,Integer>();
        
        /*
         * Create the map values of each parent for use with the children
         */
        for (int x = min; x < max; x++) {
            parentOneMapping.put(parentOne[x], parentTwo[x]);
            parentTwoMapping.put(parentTwo[x], parentOne[x]);
            
            offspring[0][x] = parentTwo[x];
            offspring[1][x] = parentOne[x];
        }
        //Create th children by following the map or copying straight from the parent
        for (int i = 1; i < SIZE; i++) {
            if (i < min || i >= max) {
                int nodeNumber = parentOne[i];
                Integer node = parentTwoMapping.get(nodeNumber);
                while(parentTwoMapping.get(node) != null) {
                    node = parentTwoMapping.get(node);
                }
                if (node == null) {
                    offspring[0][i] = nodeNumber;
                } else {
                    offspring[0][i] = node;
                }
                nodeNumber = parentTwo[i];
                node = parentOneMapping.get(nodeNumber);
                while(parentOneMapping.get(node) != null) {
                    node = parentOneMapping.get(node);
                }
                if (node == null) {
                    offspring[1][i] = nodeNumber;
                } else {
                    offspring[1][i] = node;
                }
            }
        }
        
        return offspring;
    }
    /**
     * Utility method to copy offspring into the main population at a given position
     */
    private void copy(int[][] newPopulation, int[] offspring, int position) {
        for (int i = 0; i < SIZE; i++) {
            newPopulation[position][i] = offspring[i];
        }
    } 
    /**
     * Initialise the population of individuals.
     */
    private void initialise() {
        //Create a population of random candidate soloutions;
        population = new int[POPULATION_SIZE][SIZE];
        //create an array of booleans that will be false by default.
        boolean[] nodes;
        //holder for the individual that i will be working on
        int[] individual;
        //Teh random number that will represent the enxt node in the tour sequence
        int nodeNumber;
        /* **************************************************************
         * Loop through each individual in the population.
         * Set the nodes array to an array of false values that will
         * be used to tell if a node has already been selected
         * Get the working individual
         * *************************************************************/
        for (int x = 0; x < population.length; x++) {
            nodes = new boolean[SIZE];
            individual = new int[SIZE];
            /* ******************************************************
             * The introduction of a fixed starting point.
             * To do this i need to assign the first value as 0 to 
             * each individual
             * *****************************************************/
             individual[0] = 0;
             nodes[0] = true;
            /* ***********************************************************
             * Loop from 0 to number of cities and create a random number
             * check to see if it has already been using by choosing another
             * random number while the value in the nodes array is true
             * before assigning the node number to the individual tour stop
             * and moving on. (changing the flag to say that the node number
             * has been used)
             * ***********************************************************/
            for (int i = 1; i < SIZE; i++) {
                nodeNumber = random.nextInt(SIZE);
                while(nodes[nodeNumber]) {
                    nodeNumber = random.nextInt(SIZE);
                }
                individual[i] = nodeNumber;
                nodes[nodeNumber] = true;
            }
            population[x] = individual;
        }
        //testPopulation();
        calculateFitness();
    }
    /**
     * Test to make sure there are no duplications
     */
    public void testPopulation() {
        for (int x = 0; x < POPULATION_SIZE; x++) {
            int[] individual = population[x];
            boolean[] nodes = new boolean[SIZE];
            int counter = 0;
            for (int i = 0; i < individual.length; i++) {
                if (!nodes[i]) {
                    nodes[i] = true;
                } else {
                    counter++;
                }
            }
            if (counter > 0) {
                System.out.println("Population member " + x + " has " + counter + " doubles");
            }
        }
    }
    
    /**
     * Calculate the fitness by adding up the values in the cost matrix between each node in the individual sequence
     */
    private void calculateFitness() {
        /* **********************************************************************
         * Loop through each individual in the population
         * *********************************************************************/
        for (int x = 0; x < population.length; x++) {
            //Assign the individuals fitness value to the fitness array
            fitness[x] = getFitness(x);
        }
    }
    private int getFitness(int index) {
        // The placeholder for the individual that im working on
        int[] individual = population[index];
        // int values for the individual tour fitness, start city, end city and length between 2 nodes
        int fit,start,end, length;
        //reset the fitness count to 0;
        fit = 0;
        /* ***************************************************
         * Loop through each node in the individual and calculate 
         * the length betweeen them using the cost matrix. Add
         * that length to the individual's fitness.
         * **************************************************/
        for (int i = 1; i < individual.length; i++) {
            start = individual[i-1];
            end = individual[i];
            
            length = COST[start][end];
            fit += length;
        }
        start = individual[individual.length - 1];
        end = individual[0];
        length = COST[start][end];
        fit += length;
        return fit;
    }
    /**
     * Select using the Tournament method
     */
    private int tournamentSelect() {
        int[] competitors = new int[TOURNAMENT_SIZE];
        int champion = -1, bestFitness = -1;
        
        for (int x = 0; x < competitors.length; x++) {
            int fitness = 0;
            competitors[x] = random.nextInt(POPULATION_SIZE);
            /*
             * Calcualate the competitor with the best fitness
             */
            int individual = competitors[x];
            fitness = getFitness(individual);
            if (x == 0) {
                bestFitness = fitness;
                champion = individual;
            }
            if (fitness < champion) {
                bestFitness = fitness;
                champion = individual;
            }
        }
        return champion;
    }
    /**
     * Select method that implements the Roulette Method
     */
    private int rouletteSelect() {
        int index = -1;
        /*
         * Calculate the total amounf of fitness that is available by adding the fitness of all the individuals.
         * Then go through and create a new array of fitness values by doing (Total fitness - individual fitness)
         * this will leave the individuals with the smallest fitness the biggest value.
         * Create an new array of cumaliative fitness and spin the wheel!! so to speak.
         */
        double[] roulette = new double[POPULATION_SIZE];
        double[] rouletteFitness = new double[POPULATION_SIZE];
        double total = 0;
        //Create the initial total value that can be used to calcuate the reversed sizes.
        for (int i = 0; i < POPULATION_SIZE; i++) {
            total += fitness[i];
        }
        //Calculate the reversed fitness for this roulette implementation
        for (int j = 0; j < POPULATION_SIZE; j++) {
            rouletteFitness[j] = total - fitness[j];
        }
        //Calculate the new total fitness, using the newly calculated fitness'
        total = 0;
        for (int k = 0; k < POPULATION_SIZE; k++) {
            total += rouletteFitness[k];
        }
        
        double cumulative = 0.0;
        
        for (int i = 0; i < POPULATION_SIZE; i++) {
            roulette[i] = cumulative + (rouletteFitness[i] / total);
            cumulative = roulette[i];
        }
            
        roulette[POPULATION_SIZE - 1] = 1.0;
        
        double probability = random.nextDouble();
        
        //selects a parent individual
        for (int i = 0; i < POPULATION_SIZE; i++) {
            if (probability >= roulette[i]) {
                index = i;
                break;
            }
        }
        return index;
    }
    /**
     * Loads the TSP file. This method will initialise the variables
     * size and COST.
     */
    private void load(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = null;
            
            int row = 0;
            int column = 0;
            boolean read = false;
            
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("DIMENSION")) {
                    String[] tokens = line.split(":");
                    SIZE = Integer.parseInt(tokens[1].trim());
                    COST = new int[SIZE][SIZE];
                }
                else if (line.startsWith("EDGE_WEIGHT_TYPE")) {
                    String[] tokens = line.split(":");
                    if (tokens.length < 2 || !tokens[1].trim().equals("EXPLICIT"))
                    {
                        throw new RuntimeException("Invalid EDGE_WEIGHT_TYPE: " + tokens[1]);
                    }
                }
                else if (line.startsWith("EDGE_WEIGHT_FORMAT")) {
                    String[] tokens = line.split(":");
                    if (tokens.length < 2 || !tokens[1].trim().equals("LOWER_DIAG_ROW"))
                    {
                        throw new RuntimeException("Invalid EDGE_WEIGHT_FORMAT: " + tokens[1]);
                    }
                }
                else if (line.startsWith("EDGE_WEIGHT_SECTION")) {
                    read = true;
                }
                else if (line.startsWith("EOF") || line.startsWith("DISPLAY_DATA_SECTION")) {
                    break;
                }
                else if (read) {
                    String[] tokens = line.split("\\s");
                    
                    for (int i = 0; i < tokens.length; i++)
                    {
                        String v = tokens[i].trim();
                        
                        if (v.length() > 0)
                        {
                            int value = Integer.parseInt(tokens[i].trim());
                            COST[row][column] = value;
                            column++;
                            
                            if (value == 0)
                            {
                                row++;
                                column = 0;
                            }
                        }
                    }
                }
            }
            
            reader.close();
            
            // completes the cost matrix
            for (int i = 0; i < COST.length; i++) {
                for (int j = (i + 1); j < COST.length; j++) {
                    COST[i][j] = COST[j][i];
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Could not load file: " + filename, e);
        }
    }
    
    /**
     * USED ONLY DURING THE TESTING OF THIS METHOD
     */
    public int[][] crossoverTest(int[] first, int[] second) { 
        int[][] offspring = new int[2][8];
        int[] parentOne = first, parentTwo = second;
        //Get 2 random points for the crossover to take place.
        int pointOne = 3, pointTwo = 6;
        int max = Math.max(pointOne, pointTwo), min = Math.min(pointOne, pointTwo);
        int range = max - min;
        
        HashMap<Integer,Integer> parentOneMapping = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> parentTwoMapping = new HashMap<Integer,Integer>();
        
        
        for (int x = min; x < max; x++) {
            parentOneMapping.put(parentOne[x], parentTwo[x]);
            parentTwoMapping.put(parentTwo[x], parentOne[x]);
            
            offspring[0][x] = parentTwo[x];
            offspring[1][x] = parentOne[x];
        }
        
        for (int i = 0; i < 8; i++) {
            if (i < min || i >= max) {
                int nodeNumber = parentOne[i];
                Integer node = parentTwoMapping.get(nodeNumber); 
                if ( node != null) {
                    int number = -1;
                    while (node != null) {
                        number = node;
                        node = parentTwoMapping.get(number);
                    }
                    offspring[0][i] = number;
                } else {
                    offspring[0][i] = parentOne[i];
                }
                
                nodeNumber = parentTwo[i];
                node = parentOneMapping.get(nodeNumber);
                if ( node != null) {
                    int number = -1;
                    while (node != null) {
                        number = node;
                        node = parentOneMapping.get(number);
                    }
                    offspring[1][i] = number;
                } else {
                    offspring[1][i] = parentTwo[i];
                }
            }
                
        }
        
        
        return offspring;
    }
    /**
     * USED ONLY DURING THE TESTING OF THIS TYPE OF METHOD
     */
    public int[][] testCycle(int[] first, int[] second) {
        int[][] offspring = new int[2][8];
        int[] parentOne = first, parentTwo = second;
        int startingPoint = random.nextInt(4), currentNode;
        if (startingPoint == 0) {
            startingPoint++;
        }
        currentNode = startingPoint;
        
        HashMap<Integer,Integer> parentOneMapping = new HashMap<Integer,Integer>();
        HashMap<Integer,Integer> parentTwoMapping = new HashMap<Integer,Integer>();
        
        do {
            parentOneMapping.put(parentOne[currentNode],parentTwo[currentNode]);
            parentTwoMapping.put(parentTwo[currentNode],parentOne[currentNode]);
            currentNode = parentTwo[currentNode];
        } while (currentNode != startingPoint);
        
        for (int a = 0; a < 8; a++) {
            if (parentOneMapping.get(parentOne[a]) == null) {
                offspring[0][a] = parentOne[a];
            } else {
                offspring[0][a] = parentOneMapping.get(parentOne[a]);
            }
            
            if (parentTwoMapping.get(parentTwo[a]) == null) {
                offspring[1][a] = parentTwo[a];
            } else {
                offspring[1][a] = parentTwoMapping.get(parentTwo[a]);
            }
        }
        return offspring;
    }
}
