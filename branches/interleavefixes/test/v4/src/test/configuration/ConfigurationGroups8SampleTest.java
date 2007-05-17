package test.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

/**
 * Run with group "A" and "B"
 * Make sure that only methods and configurations belonging to that group
 * get invoked.
 * 
 * @author cbeust
 * @date Mar 9, 2006
 */
public class ConfigurationGroups8SampleTest {
  private List<String> m_log = new ArrayList<String>();

  @Test
  public void dummy() {
    m_log.add("should not be invoked");
  }
  
  @Test(groups = { "A" })
  public void testSomething() {
    m_log.add("1");
  }

  @Test(groups = { "A" })
  public void testSomethingMore() {
    m_log.add("1");
  }

  @Configuration(afterTestMethod = true)
  private void cleanUpDummy() {
    m_log.add("should not be invoked");
  }

  @Configuration(afterTestMethod = true, groups = "A")
  private void cleanUpA() {
    m_log.add("a");
  }

  @Test(dependsOnGroups = "A", groups = "B")
  public void verify() {
    Assert.assertEquals(Arrays.asList(new String[] { "1", "a", "1", "a" }),
        m_log);
  }

}
