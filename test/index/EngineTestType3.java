package index;

import clones.Clone;
import clones.CloneSet;
import java.util.List;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * JUnit tests for Type 3 clones.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class EngineTestType3 {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private int perform(String sourceDir, String distance) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("sourceDir", sourceDir);
        prop.setProperty("type", "3");
        prop.setProperty("rename", "blind");
        prop.setProperty("distance", distance);
        prop.setProperty("metric", "Levenshtein");
        return perform(prop);
    }

    private int perform2(String sourceDir, String distance) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("sourceDir", sourceDir);
        prop.setProperty("type", "3");
        prop.setProperty("rename", "blind");
        prop.setProperty("distance", distance);
        prop.setProperty("metric", "segment");
        prop.setProperty("printSimilarity", "true");
        return perform(prop);
    }

    private int perform(Properties prop) throws Exception {
        prop.setProperty("index", "full");
        prop.setProperty("compressed", "true");
        int size = testIndex(prop);
        prop.setProperty("compressed", "false");
        int size2 = testIndex(prop);
        assertEquals(size, size2);
        prop.setProperty("index", "simplified");
        prop.setProperty("compressed", "true");
        int size3 = testIndex(prop);
        assertEquals(size, size3);
        prop.setProperty("compressed", "false");
        int size4 = testIndex(prop);
        assertEquals(size, size4);
        return size;
    }

    private int testIndex(Properties prop) throws Exception {
        Engine eng = new Engine(prop);
        eng.perform();
        CloneSet set = eng.getClones();
        List<Clone> cc = set.getClones();
        return cc.size();
    }

    @Test
    public void testPerform1() throws Exception {
        int c = perform("test/type3/1", "4");
        assertEquals(1, c);
    }

    @Test
    public void testPerform2() throws Exception {
        int c = perform("test/type3/2", "4");
        assertEquals(1, c);
    }

    @Test
    public void testPerform3() throws Exception {
        int c = perform("test/type3/3", "2");
        assertEquals(1, c);
    }

    @Test
    public void testPerform4() throws Exception {
        int c = perform2("test/type3/4", "3");
        assertEquals(1, c);
    }
}
