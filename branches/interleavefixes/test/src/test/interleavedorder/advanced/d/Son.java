package test.interleavedorder.advanced.d;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class Son extends Parent {
  @BeforeClass
  public void beforeClassSon() {
    System.out.println(this + " beforeClassSon");
  }

  @AfterClass
  public void afterClassSon() {
    System.out.println(this + " afterClassSon");
  }

  @Test
  public void sonTest() {
    System.out.println(this + " sonTest");
  }

  @Test(dependsOnMethods="sonTest")
  public void sonTest2() {
    System.out.println(this + " sonTest2");
  }
}
