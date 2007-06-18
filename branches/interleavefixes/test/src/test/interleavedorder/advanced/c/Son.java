package test.interleavedorder.advanced.c;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.internal.thread.ThreadUtil;

public class Son extends Parent {
  @BeforeClass
  public void beforeClassSon() {
    System.out.println(this + " beforeClassSon->" + ThreadUtil.currentThreadInfo());
  }

  @AfterClass
  public void afterClassSon() {
    System.out.println(this + " afterClassSon->" + ThreadUtil.currentThreadInfo());
  }

  @Test
  public void sonTest() {
    System.out.println(this + " sonTest->" + ThreadUtil.currentThreadInfo());
  }

  @Test
  public void sonTest2() {
    System.out.println(this + " sonTest2->" + ThreadUtil.currentThreadInfo());
  }
}
