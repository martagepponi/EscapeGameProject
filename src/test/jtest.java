package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.sourceforge.jwebunit.util.TestingEngineRegistry;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
public class jtest {
	
	@Before
    public void prepare() {
		setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT); 
        setBaseUrl("http://localhost:8080/EscapeGameProject");
    }

	@Test
	public void test() {
		System.out.println("ciao");
		beginAt("Login.html");
		//assertButtonPresent("login_button");
		//assertTitleEquals("Login");
		//fail("Not yet implemented");
	}

}
