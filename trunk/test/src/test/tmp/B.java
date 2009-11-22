package test.tmp;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

public class B {
  @BeforeClass
  public void bc() {
    log("************** beforeClass");
  }

  @AfterClass
  public void ac() {
    log("afterClass");
  }

  @BeforeMethod
  public void bm() {
    log("beforeMethod");
  }

  @AfterMethod
  public void am() {
    log("afterMethod");
  }

  @Test(groups = "1")
  public void a1() {
    log("a1");
  }

  @Test(groups = "1")
  public void a2() {
    log("a2");
  }

  private void log(String string) {
    System.out.println(string + "() thread:" + Thread.currentThread().getId());
    int sleep = Math.abs(new Random().nextInt() % 4000);
//    System.out.println("   sleeping " + sleep);
//    try {
//        Thread.sleep(4000);
//    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
//        e.printStackTrace();
//    }
  }

  @Test(groups = "2", dependsOnGroups = "1")
  public void b1() {
    log("b1");
  }

  @Test(groups = "2", dependsOnGroups = "1")
  public void b2() {
    log("b2");
  }

  @Test(dependsOnGroups = "2")
  public void c1() {
    log("c1");
  }

  @Test(dependsOnGroups = { "1" })
  public void d() {
    
  }

  @Test
  public void x() {
    log("x");
  }

  @Test
  public void y() {
    log("y");
  }
  //  @Test(groups = "mytest", dependsOnMethods = "g")
//  public void f() {
//  }
//
//
//  @AfterMethod
//  public void after() {
//  }

}
