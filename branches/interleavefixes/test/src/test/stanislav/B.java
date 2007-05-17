package test.stanislav;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class B {

	@BeforeClass
	public void setup() {
		System.out.println("BBefore");
	}
	
	@Test()
	public void B1() {
		System.out.println("B1");
	}
	
	@Test(dependsOnMethods = { "B1" })
	public void B2() {
		System.out.println("B2");
	}

}