package hu.bla;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
//@Ignore
@Suite.SuiteClasses({
		TestJpa.class,
		TestQuery.class
})
public class TestSuite {
}