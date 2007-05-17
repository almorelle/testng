package test.factory.magnatron;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Test {
	 private int i;
	   private int j;

	   public Test(int ii, int jj) {
	       i = ii;
	       j = jj;
	       System.out.println("Constructor-i-" + i + " j-" + j);
	   }

	   @BeforeClass(groups={"client"})
	   public void setup() {
	       System.out.println("setup-i-" + i + " j-" + j);
	   }

	   @org.testng.annotations.Test(groups = {"client"})
	   public void testMethod1() {
	       System.out.println("testMethod1-i-" + i + " j-" + j);
	   }

	   @org.testng.annotations.Test(groups = {"client"})
	   public void testMethod2() {
	       System.out.println("testMethod2-i-" + i + " j-" + j);
	   }

	   @AfterClass(groups={"client"})
	   public void cleanup() {
	       System.out.println("cleanup-i-" + i + " j-" + j);
	   }
}
