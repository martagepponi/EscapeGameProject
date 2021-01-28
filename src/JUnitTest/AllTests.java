package JUnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({JUnitTest.TestRegistration.class,JUnitTest.TestUser.class,JUnitTest.TestRoom.class,JUnitTest.TestSubject.class})
public class AllTests {

}
