package newpac;

import org.junit.BeforeClass;
import org.junit.Test;

public class Neha {

	@BeforeClass
	public void init() {
		System.out.println("one time iniz code");
	}
	@Test
	public void testMsg() {
		System.out.println("in test method");
	}

}
