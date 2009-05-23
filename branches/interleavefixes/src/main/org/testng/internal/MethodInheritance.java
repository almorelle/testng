package org.testng.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestNGMethod;

public class MethodInheritance {
  /**
   * Look in map for a class that is a superclass of methodClass
   * @param map
   * @param methodClass
   * @return
   */
  private static List<ITestNGMethod> findMethodListSuperClass(Map<Class<?>, List<ITestNGMethod>> map, 
      Class< ? extends ITestNGMethod> methodClass)
  {
    for (Class<?> cls : map.keySet()) {
      if (cls.isAssignableFrom(methodClass)) {
        return map.get(cls);
      }
    }
    
    return null;
  }
  
  /**
   * Look in map for a class that is a subclass of methodClass
   * @param map
   * @param methodClass
   * @return
   */
  private static Class<?> findSubClass(Map<Class<?>, List<ITestNGMethod>> map, 
      Class< ? extends ITestNGMethod> methodClass)
  {
    for (Class<?> cls : map.keySet()) {
      if (methodClass.isAssignableFrom(cls)) {
        return cls; 
      }
    }
    
    return null;
  }

  /**
   * Fix the methodsDependedUpon to make sure that @Configuration methods
   * respect inheritance (before methods are invoked in the order Base first
   * and after methods are invoked in the order Child first)
   * 
   * @param methods
   * @param baseClassToChild
   */
  public static void fixMethodInheritance(ITestNGMethod[] methods, boolean baseClassToChild) {
    //
    // First, make sure that none of these methods define a dependency of its own
    //
    for (ITestNGMethod m : methods) {
      String[] mdu = m.getMethodsDependedUpon();
      String[] groups = m.getGroupsDependedUpon();
      if ((mdu != null && mdu.length > 0) || (groups != null && groups.length > 0)) {
        return;
      }
    }

    // Map of classes -> List of methods that belong to this class or same hierarchy
    Map<Class<?>, List<ITestNGMethod>> map = new HashMap<Class<?>, List<ITestNGMethod>>();
    
    //
    // Put the list of methods in their hierarchy buckets
    //
    for (ITestNGMethod method : methods) {
      @SuppressWarnings("all")
      Class< ? extends ITestNGMethod> methodClass = method.getRealClass();
      List<ITestNGMethod> l = findMethodListSuperClass(map, methodClass);
      if (null != l) {
        l.add(method);
      }
      else {
        Class<?> subClass = findSubClass(map, methodClass);
        if (null != subClass) {
          l = map.get(subClass);
          l.add(method);
          map.remove(subClass);
          map.put(methodClass, l);
        }
        else {
          l = new ArrayList<ITestNGMethod>();
          l.add(method);
          map.put(methodClass, l);
        }
      }
    }
    
    //
    // Each bucket that has a list bigger than one element gets sorted
    //
    for (List<ITestNGMethod> l : map.values()) {
      if (l.size() > 1) {
        // Sort them
        sortMethodsByInheritance(l, baseClassToChild);

        for (int i = 0; i < l.size(); i++) {
          ITestNGMethod m1 = l.get(i);
          // Look for any method further down that is a subclass of this one.
          // This handles the case when there are multiple BeforeClass/AfterClass
          // methods in the same class.
          for (int j = i + 1; j < l.size(); j++) {
            ITestNGMethod m2 = l.get(j);
            if (! equalsEffectiveClass(m1, m2)) {
              Utils.log ("MethodInheritance", 4, m2 + " DEPENDS ON " + m1);
              m2.addMethodDependedUpon(MethodHelper.calculateMethodCanonicalName(m1));
            }
          }
        }
      }
    }
  }
  
  private static boolean equalsEffectiveClass(ITestNGMethod m1, ITestNGMethod m2) {
    try {
      Class<?> c1 = m1.getRealClass();
      Class<?> c2 = m2.getRealClass();
      
      boolean isEqual = c1 == null ? c2 == null : c1.equals(c2);
      
      return isEqual; // && m1.getMethod().equals(m2.getMethod());
    }
    catch(Exception ex) {
      return false;
    }
  }

  
  /**
   * Given a list of methods belonging to the same class hierarchy, orders them
   * from the base class to the child (if true) or from child to base class (if false)
   * @param methods
   */
  private static void sortMethodsByInheritance(List<ITestNGMethod> methods, 
      boolean baseClassToChild)
  {
    Collections.sort(methods);
    if (! baseClassToChild) {
      Collections.reverse(methods);
    }
  }
  
  private static void ppp(String s) {
    System.out.println("[MethodInheritance] " + s);
  }
}