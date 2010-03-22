package test.mannotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;
import org.testng.internal.annotations.DefaultAnnotationTransformer;
import org.testng.internal.annotations.IAfterSuite;
import org.testng.internal.annotations.IBeforeSuite;
import org.testng.internal.annotations.IConfiguration;
import org.testng.internal.annotations.IDataProvider;
import org.testng.internal.annotations.IExpectedExceptions;
import org.testng.internal.annotations.IFactory;
import org.testng.internal.annotations.IParameters;
import org.testng.internal.annotations.ITest;
import org.testng.internal.annotations.JDK15AnnotationFinder;

@Test(enabled = true)
public class MAnnotationSampleTest {
  private JDK15AnnotationFinder m_finder;

  @Configuration(beforeTestClass = true, enabled = true)
  public void init() {
    m_finder = new JDK15AnnotationFinder(new DefaultAnnotationTransformer());
  }

  public void verifyTestClassLevel() {
    //
    // Tests on MTest1SampleTest
    //
    ITest test1 = (ITest) m_finder.findAnnotation(MTest1.class, ITest.class);
    Assert.assertTrue(test1.getEnabled());
    Assert.assertEquals(test1.getGroups(), new String[] { "group1", "group2" });
    Assert.assertTrue(test1.getAlwaysRun());
    Assert.assertEquals(test1.getParameters(), new String[] { "param1", "param2" });
    Assert.assertEqualsNoOrder(test1.getDependsOnGroups(), new String[] { "dg1", "dg2" },  "depends on groups");
    Assert.assertEqualsNoOrder( test1.getDependsOnMethods(), new String[] { "dm1", "dm2" });
    Assert.assertEquals(test1.getTimeOut(), 42);
    Assert.assertEquals(test1.getInvocationCount(), 43);
    Assert.assertEquals(test1.getSuccessPercentage(), 44);
    Assert.assertEquals(test1.getThreadPoolSize(), 3);
    Assert.assertEquals(test1.getDataProvider(), "dp");
    Assert.assertEquals(test1.getDescription(), "Class level description");
    
    //
    // Tests on MTest1SampleTest (test defaults)
    //
    ITest test2 = (ITest) m_finder.findAnnotation(MTest2.class, ITest.class);
    // test default for enabled
    Assert.assertTrue(test2.getEnabled());
    Assert.assertFalse(test2.getAlwaysRun());
    Assert.assertEquals(test2.getTimeOut(), 0);
    Assert.assertEquals(test2.getInvocationCount(), 1);
    Assert.assertEquals(test2.getSuccessPercentage(), 100);
    Assert.assertEquals(test2.getDataProvider(), "");
  }
  
  public void verifyTestMethodLevel() throws SecurityException, NoSuchMethodException 
  {
    //
    // Tests on MTest1SampleTest
    //
    Method method = MTest1.class.getMethod("f", new Class[0]);
    ITest test1 = (ITest) m_finder.findAnnotation(method, ITest.class);
    Assert.assertTrue(test1.getEnabled());
    Assert.assertEqualsNoOrder(test1.getGroups(), new String[] { "group1", "group3", "group4", "group2" });
    Assert.assertTrue(test1.getAlwaysRun());
    Assert.assertEquals(test1.getParameters(), new String[] { "param3", "param4" });
    Assert.assertEqualsNoOrder(test1.getDependsOnGroups(), new String[] { "dg1", "dg2", "dg3", "dg4" });
    Assert.assertEqualsNoOrder(test1.getDependsOnMethods(), new String[] { "dm1", "dm2", "dm3", "dm4" });
    Assert.assertEquals(test1.getTimeOut(), 142);
    Assert.assertEquals(test1.getInvocationCount(), 143);
    Assert.assertEquals(test1.getSuccessPercentage(), 61);
    Assert.assertEquals(test1.getDataProvider(), "dp2");
    Assert.assertEquals(test1.getDescription(), "Method description");
    Class[] exceptions = test1.getExpectedExceptions();
    Assert.assertEquals(exceptions.length, 1);
    Assert.assertEquals(exceptions[0], NullPointerException.class);
  }  
  
  public void verifyTestConstructorLevel() throws SecurityException, NoSuchMethodException 
  {
    //
    // Tests on MTest1SampleTest
    //
    Constructor constructor = MTest1.class.getConstructor(new Class[0]);
    ITest test1 = (ITest) m_finder.findAnnotation(constructor, ITest.class);
    Assert.assertNotNull(test1);
    Assert.assertTrue(test1.getEnabled());
    Assert.assertEqualsNoOrder(test1.getGroups(), new String[] { "group5", "group1", "group6", "group2" });
    Assert.assertTrue(test1.getAlwaysRun());
    Assert.assertEquals(test1.getParameters(), new String[] { "param5", "param6" });
    Assert.assertEqualsNoOrder(test1.getDependsOnGroups(), new String[] { "dg1", "dg2", "dg5", "dg6" });
    Assert.assertEqualsNoOrder(test1.getDependsOnMethods(), new String[] { "dm1", "dm2", "dm5", "dm6" });
    Assert.assertEquals(test1.getTimeOut(), 242);
    Assert.assertEquals(test1.getInvocationCount(), 243);
    Assert.assertEquals(test1.getSuccessPercentage(), 62);
    Assert.assertEquals(test1.getDataProvider(), "dp3");
    Assert.assertEquals(test1.getDescription(), "Constructor description");
    Class[] exceptions = test1.getExpectedExceptions();
    Assert.assertEquals(exceptions.length, 1);
    Assert.assertEquals(exceptions[0], NumberFormatException.class);
  }  
  
  public void verifyConfigurationBefore() throws SecurityException, NoSuchMethodException 
  {
    Method method = MTest1.class.getMethod("before", new Class[0]);
    IConfiguration configuration = 
      (IConfiguration) m_finder.findAnnotation(method, IConfiguration.class);
    Assert.assertNotNull(configuration);
    Assert.assertTrue(configuration.getBeforeSuite());
    Assert.assertTrue(configuration.getBeforeTestMethod());
    Assert.assertTrue(configuration.getBeforeTest());
    Assert.assertTrue(configuration.getBeforeTestClass());
    Assert.assertFalse(configuration.getAfterSuite());
    Assert.assertFalse(configuration.getAfterTestMethod());
    Assert.assertFalse(configuration.getAfterTest());
    Assert.assertFalse(configuration.getAfterTestClass());
    Assert.assertEquals(0, configuration.getAfterGroups().length);
    String[] bg = configuration.getBeforeGroups();
    Assert.assertEquals(bg.length, 2);
    Assert.assertEqualsNoOrder(bg, new String[] {"b1", "b2"});
    
    // Default values
    Assert.assertTrue(configuration.getEnabled());
    Assert.assertTrue(configuration.getInheritGroups());
    Assert.assertFalse(configuration.getAlwaysRun());
  }
  
  public void verifyConfigurationAfter() throws SecurityException, NoSuchMethodException
  {
    Method method = MTest1.class.getMethod("after", new Class[0]);
    IConfiguration configuration = 
      (IConfiguration) m_finder.findAnnotation(method, IConfiguration.class);
    Assert.assertNotNull(configuration);
    Assert.assertFalse(configuration.getBeforeSuite());
    Assert.assertFalse(configuration.getBeforeTestMethod());
    Assert.assertFalse(configuration.getBeforeTest());
    Assert.assertFalse(configuration.getBeforeTestClass());
    Assert.assertTrue(configuration.getAfterSuite());
    Assert.assertTrue(configuration.getAfterTestMethod());
    Assert.assertTrue(configuration.getAfterTest());
    Assert.assertTrue(configuration.getAfterTestClass());
    Assert.assertEquals(0, configuration.getBeforeGroups().length);
    String[] ag = configuration.getAfterGroups();
    Assert.assertEquals(ag.length, 2);
    Assert.assertEqualsNoOrder(ag, new String[] {"a1", "a2"});
    
    // Default values
    Assert.assertTrue(configuration.getEnabled());
    Assert.assertTrue(configuration.getInheritGroups());
    Assert.assertFalse(configuration.getAlwaysRun());
  }  
  
  public void verifyConfigurationOthers() throws SecurityException, NoSuchMethodException
  {
    Method method = MTest1.class.getMethod("otherConfigurations", new Class[0]);
    IConfiguration configuration = 
      (IConfiguration) m_finder.findAnnotation(method, IConfiguration.class);
    Assert.assertNotNull(configuration);
    Assert.assertFalse(configuration.getBeforeSuite());
    Assert.assertFalse(configuration.getBeforeTestMethod());
    Assert.assertFalse(configuration.getBeforeTest());
    Assert.assertFalse(configuration.getBeforeTestClass());
    Assert.assertFalse(configuration.getAfterSuite());
    Assert.assertFalse(configuration.getAfterTestMethod());
    Assert.assertFalse(configuration.getAfterTest());
    Assert.assertFalse(configuration.getAfterTestClass());
    
    Assert.assertFalse(configuration.getEnabled());
    Assert.assertEquals(configuration.getParameters(), new String[] { "oparam1", "oparam2" });
    Assert.assertEqualsNoOrder(configuration.getGroups(), new String[] { "group1", "ogroup1", "ogroup2", "group2" }, "groups");
    Assert.assertEqualsNoOrder(configuration.getDependsOnGroups(), new String[] { "odg1", "odg2" }, "depends on groups");
    Assert.assertEqualsNoOrder(configuration.getDependsOnMethods(), new String[] { "odm1", "odm2" }, "depends on methods");
    Assert.assertFalse(configuration.getInheritGroups());
    Assert.assertTrue(configuration.getAlwaysRun());
    Assert.assertEquals(configuration.getDescription(), "beforeSuite description");
  }  
  
  public void verifyDataProvider() throws SecurityException, NoSuchMethodException 
  {
    Method method = MTest1.class.getMethod("otherConfigurations", new Class[0]);
    IDataProvider dataProvider = 
      (IDataProvider) m_finder.findAnnotation(method, IDataProvider.class);
    Assert.assertNotNull(dataProvider);
    Assert.assertEquals(dataProvider.getName(), "dp4");
  }  
  
  public void verifyExpectedExceptions() throws SecurityException, NoSuchMethodException 
  {
    Method method = MTest1.class.getMethod("otherConfigurations", new Class[0]);
    IExpectedExceptions exceptions= 
      (IExpectedExceptions) m_finder.findAnnotation(method, IExpectedExceptions.class);
    
    Assert.assertNotNull(exceptions);
    Assert.assertEquals(exceptions.getValue(), new Class[] { MTest1.class, MTest2.class });
  }

  public void verifyFactory() throws SecurityException, NoSuchMethodException 
  {
    Method method = MTest1.class.getMethod("factory", new Class[0]);
    IFactory factory= 
      (IFactory) m_finder.findAnnotation(method, IFactory.class);
    
    Assert.assertNotNull(factory);
    Assert.assertEquals(factory.getParameters(), new String[] { "pf1", "pf2" });
  }

  public void verifyParameters() throws SecurityException, NoSuchMethodException 
  {
    m_finder = new JDK15AnnotationFinder(new DefaultAnnotationTransformer());
    Method method = MTest1.class.getMethod("parameters", new Class[0]);
    IParameters parameters = 
      (IParameters) m_finder.findAnnotation(method, IParameters.class);
    
    Assert.assertNotNull(parameters);
    Assert.assertEquals(parameters.getValue(), new String[] { "pp1", "pp2", "pp3" });
  }

  public void verifyNewConfigurationBefore() throws SecurityException, NoSuchMethodException 
  {
    Method method = MTest1.class.getMethod("newBefore", new Class[0]);
    IConfiguration configuration = 
      (IConfiguration) m_finder.findAnnotation(method, IBeforeSuite.class);
    Assert.assertNotNull(configuration);
    Assert.assertTrue(configuration.getBeforeSuite());
    
    // Default values
    Assert.assertTrue(configuration.getEnabled());
    Assert.assertTrue(configuration.getInheritGroups());
    Assert.assertFalse(configuration.getAlwaysRun());
  }

  public void verifyNewConfigurationAfter() throws SecurityException, NoSuchMethodException 
  {
    Method method = MTest1.class.getMethod("newAfter", new Class[0]);
    IConfiguration configuration = 
      (IConfiguration) m_finder.findAnnotation(method, IAfterSuite.class);
    Assert.assertNotNull(configuration);
    Assert.assertTrue(configuration.getAfterSuite());
    
    // Default values
    Assert.assertTrue(configuration.getEnabled());
    Assert.assertTrue(configuration.getInheritGroups());
    Assert.assertFalse(configuration.getAlwaysRun());
  }

  private static void ppp(String s) {
    System.out.println("[MAnnotationSampleTest] " + s);
  }
}