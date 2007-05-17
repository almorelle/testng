package test.dependent;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class DepBugSampleTest {
  private static List<String> m_log = new ArrayList<String>();
  
  private static void log(String s) {
//    ppp(s);
    m_log.add(s);
  }
  
  private static void ppp(String s) {
    System.out.println("[DepBugSampleTest] " + s);
  }
  
  public static List<String> getLog() {
    System.out.println("getLog");
    return m_log;
  }

  @BeforeClass
  public void setup() throws Exception {
    log("setup");
    System.out.println("setup");
  }

  @AfterClass
  public void destroy() throws Exception {
    System.out.println("destroy");
    log("destroy");
  }

  @Test(dependsOnMethods = "send")
  public void get() throws Exception {
    System.out.println("get");
    log("get");
  }

  public void send() throws Exception {
    System.out.println("send");
    log("send");
  }

  public void list() throws Exception {
    System.out.println("list");
    log("list");
  }
}
