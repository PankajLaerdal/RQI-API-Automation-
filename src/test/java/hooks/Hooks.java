package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import base.BaseTest;

public class Hooks extends BaseTest {

    @Before
    public void init() {
        setup();
       // System.out.println("Test Started...");
    }
    
   
}