
/**
 * Write a description of class TEST here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TEST
{
    public TEST() {
        dantzigOvernightTest();
    }
    int TEST_NUMBER = 50;
    public void dantzigOvernightTest() {
        dantzigTestAllAtOnceRoulette(200,20000);
        dantzigTestAllAtOnceTournament(200,20000);
        
        dantzigTestAllAtOnceRoulette(300,20000);
        dantzigTestAllAtOnceTournament(300,20000);
        
        dantzigTestAllAtOnceRoulette(400,20000);
        dantzigTestAllAtOnceTournament(400,20000);
    }
    
    public void dantzigTestAllAtOnceRoulette(int pop, int maxGen)
    {
        TSP tSP1 = new TSP("sim","pmx","r",pop,maxGen);
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("em","pmx","r",pop,maxGen);
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("sim","cx","r",pop,maxGen);
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("em","cx","r",pop,maxGen);
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
    }
    
    public void dantzigTestAllAtOnceTournament(int pop, int maxGen)
    {
        TSP tSP1 = new TSP("sim","pmx","t",pop,maxGen);
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("em","pmx","t",pop,maxGen);
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("sim","cx","t",pop,maxGen);
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("em","cx","t",pop,maxGen);
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
    }
}
