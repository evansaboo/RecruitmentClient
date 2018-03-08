
import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;
import org.testng.annotations.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *Used to run test suite number amount of times
 * @author Evan
 */
public class LoopTestSuite {
    
    /**
     * Method used to run test suite number amount of times
     */
    @Test
    public void test(){
        for(int i=0;i<200;i++)
        {
            List<String> suites = new ArrayList<>();
            suites.add("src/test/java/suites.xml"); //path of .xml file to be run-provide complete path

            TestNG tng = new TestNG();
            tng.setTestSuites(suites);

            tng.run(); //run test suite
        }
        
    }
}
