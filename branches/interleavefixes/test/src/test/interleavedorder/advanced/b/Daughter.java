package test.interleavedorder.advanced.b;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.internal.thread.ThreadUtil;

public class Daughter extends Parent {
  @BeforeClass
  public void beforeClassDaughter() {
    System.out.println(this + " beforeClassDaughter->" + ThreadUtil.currentThreadInfo());
  }

  @AfterClass
  public void afterClassDaughter() {
    System.out.println(this + " afterClassDaughter->" + ThreadUtil.currentThreadInfo());
  }

  @Test
  public void daughterTest() {
    System.out.println(this + " daughterTest->" + ThreadUtil.currentThreadInfo());
  }

  @Test(dependsOnMethods = "daughterTest")
  public void daughterTest2() {
    System.out.println(this + " daughterTest2->" + ThreadUtil.currentThreadInfo());
  }
}
