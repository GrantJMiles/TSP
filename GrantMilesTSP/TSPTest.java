

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class TSPTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TSPTest
{
    private int TEST_NUMBER = 200;
    /**
     * Default constructor for test class TSPTest
     */
    public TSPTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
     @Test
    public void test() {
        TSP tSP1 = new TSP("sim","pmx","t");
            // for (int x = 0; x < TEST_NUMBER; x++) {
                // tSP1.run("dantzig.tsp");
            // }
         tSP1 = new TSP("sim","cx","t");   
            for (int y = 0; y < TEST_NUMBER; y++) {
                tSP1.run("dantzig.tsp");
            }
        
    }
    @Test
    public void groetschelTestExchangePartiallyMappedRoulette()
    {
        TSP tSP1 = new TSP("em","pmx","r");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
    }
    @Test
    public void groetschelTestExchangePartiallyMappedTournament()
    {
        TSP tSP1 = new TSP("em","pmx","t");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
    }
    @Test
    public void groetschelTestExchangeCycleRoulette()
    {
        TSP tSP1 = new TSP("em","cx","r");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
    }
    @Test
    public void groetschelTestExchangeCycleTournament()
    {
        TSP tSP1 = new TSP("em","cx","t");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
    }
    @Test
    public void groetschelTestSimpleInversionPartiallyMappedRoulette()
    {
        TSP tSP1 = new TSP("sim","pmx","r");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
    }
    @Test
    public void groetschelTestSimpleInversionPartiallyMappedTournament()
    {
        TSP tSP1 = new TSP("sim","pmx","t");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
    }
    @Test
    public void groetschelTestSimpleInversionCycleRoulette()
    {
        TSP tSP1 = new TSP("sim","cx","r");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
    }
    @Test
    public void groetschelTestSimpleInversionCycleTournament()
    {
        TSP tSP1 = new TSP("sim","cx","t");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
    }
    @Test
    public void groetschelTestSimpleInversionCycleONCERoulette()
    {
        TSP tSP1 = new TSP("sim","cx","r");
        tSP1.run("groetschel.tsp");
    }
    @Test
    public void groetschelTestSimpleInversionCycleONCETournament()
    {
        TSP tSP1 = new TSP("sim","cx","t");
        tSP1.run("groetschel.tsp");
    }
    
    @Test
    public void ftest() {
        groetschelOne();
    }
    @Test
    public void groetschelTestAllAtOnceRoulette()
    {
        TSP tSP1 = new TSP("sim","pmx","r");
        
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("em","pmx","r");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("sim","cx","r");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("em","cx","r");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
    }
    @Test
    public void groetschelTestAllAtOnceTournament()
    {
        TSP tSP1 = new TSP("sim","pmx","t");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("em","pmx","t");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("sim","cx","t");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("em","cx","t");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("groetschel.tsp");
        }
    }
    
    @Test
    public void dantzigTestAllAtOnceRoulette()
    {
        TSP tSP1 = new TSP("sim","pmx","r");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("em","pmx","r");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("sim","cx","r");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        tSP1 = new TSP("em","cx","r");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
    }
    @Test
    public void dantzigTestAllAtOnceTournament()
    {
        TSP tSP1 = new TSP("sim","pmx","t");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
        System.out.println();
        System.out.println("Next Test");
        System.out.println();
        
        // tSP1 = new TSP("em","pmx","t");
        // for (int x = 0; x < TEST_NUMBER; x++) {
            // tSP1.run("dantzig.tsp");
        // }
        // System.out.println();
        // System.out.println("Next Test");
        // System.out.println();
        
        tSP1 = new TSP("sim","cx","t");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
        // System.out.println();
        // System.out.println("Next Test");
        // System.out.println();
        
        // tSP1 = new TSP("em","cx","t");
        // for (int x = 0; x < TEST_NUMBER; x++) {
            // tSP1.run("dantzig.tsp");
        // }
    }
    
    @Test
    public void groetschelOne()
    {
        TSP tSP1 = new TSP();
            tSP1.run("groetschel.tsp");
    }

    @Test
    public void dantzigOne()
    {
        TSP tSP1 = new TSP();
            tSP1.run("dantzig.tsp");
    }
    
    @Test
    public void DANTZIGtest()
    {
        TSP tSP1 = new TSP("sim","pmx","t");
        for (int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
    }
    
    @Test
    public void dantzigOneCXEMRoulette()
    {
        TSP tSP1 = new TSP("em","cx","r");
            tSP1.run("dantzig.tsp");
    }
    @Test
    public void dantzigOneCXEMTournament()
    {
        TSP tSP1 = new TSP("em","cx","t");
            tSP1.run("dantzig.tsp");
    }
    @Test
    public void dantzigOneCXSIMRoulette()
    {
        TSP tSP1 = new TSP("sim","cx","r");
            tSP1.run("dantzig.tsp");
    }
    @Test
    public void dantzigOneCXSIMTournament()
    {
        TSP tSP1 = new TSP("sim","cx","t");
            tSP1.run("dantzig.tsp");
    }
    @Test
    public void dantzigCXSIMRoulette()
    {
        TSP tSP1 = new TSP("sim","cx","r");
        for(int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
    }
    @Test
    public void dantzigCXSIMTournament()
    {
        TSP tSP1 = new TSP("sim","cx","t");
        for(int x = 0; x < TEST_NUMBER; x++) {
            tSP1.run("dantzig.tsp");
        }
    }
    @Test
    public void dantzigOnePMXEMRoulette()
    {
        TSP tSP1 = new TSP("em","pmx","r");
            tSP1.run("dantzig.tsp");
    }
    @Test
    public void dantzigOnePMXEMTournament()
    {
        TSP tSP1 = new TSP("em","pmx","t");
            tSP1.run("dantzig.tsp");
    }
    @Test
    public void dantzigOnePMXSIMToulette()
    {
        TSP tSP1 = new TSP("sim","pmx","r");
            tSP1.run("dantzig.tsp");
    }
    @Test
    public void dantzigOnePMXSIMTournament()
    {
        TSP tSP1 = new TSP("sim","pmx","t");
            tSP1.run("dantzig.tsp");
    }
}


