package test.interleavedorder.advanced.d;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.internal.thread.ThreadUtil;

@Test(sequential=true)
abstract public class Parent {
  @BeforeClass
  public void beforeClassParent() {
    System.out.println(this + " beforeClassParent->" + ThreadUtil.currentThreadInfo());
  }

  @AfterClass
  public void afterClassParent() {
    System.out.println(this + " afterClassParent->" + ThreadUtil.currentThreadInfo());
  }

  @Test
  public void parentTest() {
    System.out.println(this + " parentTest->" + ThreadUtil.currentThreadInfo());
  }
}
