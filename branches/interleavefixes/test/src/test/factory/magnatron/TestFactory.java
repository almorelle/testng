package test.factory.magnatron;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Factory;

public class TestFactory {
	   @Factory
	   public Test[] testLogin() {
	          List<Test> result = new ArrayList<Test>();
	           for (int i=0;i<3;i++) {
	               for (int j=0;j<2;j++) {
	                   result.add(new Test(i, j));
	               }
	           }
	       return result.toArray(new Test[result.size()]);
	   }
}
