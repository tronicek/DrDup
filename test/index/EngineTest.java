package index;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The test suite.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    EngineTestType2.class,
    EngineTestType3.class
})
public class EngineTest {
}
