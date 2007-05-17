package test.stanislav;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class S {

	@BeforeClass
	public void setup() {
		System.out.println("SBefore");
	}

	@Test()
	public void S1() {
		System.out.println("S1");
	}

	@Test(dependsOnMethods = { "S1" })
	public void S2() {
		System.out.println("S2");
	}

}