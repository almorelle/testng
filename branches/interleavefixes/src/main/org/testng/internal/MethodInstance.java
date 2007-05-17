package org.testng.internal;

import java.util.Comparator;

import org.testng.ITestNGMethod;

public class MethodInstance {
  private ITestNGMethod m_method;
  private Object[] m_instances;

  public MethodInstance(ITestNGMethod method, Object[] instances) {
    m_method = method;
    m_instances = instances;
  }

  public ITestNGMethod getMethod() {
    return m_method;
  }

  public Object[] getInstances() {
    return m_instances;
  }
  
  public String toString() {
    return "[MethodInstance m:" + m_method + " i:" + m_instances[0];
  }

  public static final Comparator<MethodInstance> SORT_BY_CLASS = new Comparator<MethodInstance>() {
    @Override
    @SuppressWarnings("all")
    public int compare(MethodInstance o1, MethodInstance o2) {
      return o1.getMethod().getTestClass().getName().compareTo(o2.getMethod().getTestClass().getName());
    }
  };

}
