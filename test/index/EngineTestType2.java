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
 * JUnit tests for Type 2 clones.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class EngineTestType2 {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private int perform(String rename, String sourceDir) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("sourceDir", sourceDir);
        prop.setProperty("type", "2");
        prop.setProperty("rename", rename);
        return perform(prop);
    }

    private int performWith(String rename, String sourceDir, String propertyName, String propertyValue) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("sourceDir", sourceDir);
        prop.setProperty("type", "2");
        prop.setProperty("rename", rename);
        prop.setProperty(propertyName, propertyValue);
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
        int c = perform("blind", "test/src/1");
        assertEquals(0, c);
        c = perform("consistent", "test/src/1");
        assertEquals(0, c);
    }

    @Test
    public void testPerform2() throws Exception {
        int c = perform("blind", "test/src/2");
        assertEquals(1, c);
        c = perform("consistent", "test/src/2");
        assertEquals(1, c);
    }

    @Test
    public void testPerform3() throws Exception {
        int c = perform("blind", "test/src/3");
        assertEquals(1, c);
        c = perform("consistent", "test/src/3");
        assertEquals(1, c);
    }

    @Test
    public void testPerform4() throws Exception {
        int c = perform("blind", "test/src/4");
        assertEquals(1, c);
        c = perform("consistent", "test/src/4");
        assertEquals(1, c);
    }

    @Test
    public void testPerform5() throws Exception {
        int c = perform("blind", "test/src/5");
        assertEquals(0, c);
        c = perform("consistent", "test/src/5");
        assertEquals(0, c);
    }

    @Test
    public void testPerform6() throws Exception {
        int c = perform("blind", "test/src/6");
        assertEquals(1, c);
        c = perform("consistent", "test/src/6");
        assertEquals(1, c);
    }

    @Test
    public void testPerform7() throws Exception {
        int c = perform("blind", "test/src/7");
        assertEquals(1, c);
        c = perform("consistent", "test/src/7");
        assertEquals(0, c);
    }

    @Test
    public void testPerform8() throws Exception {
        int c = perform("blind", "test/src/8");
        assertEquals(1, c);
        c = perform("consistent", "test/src/8");
        assertEquals(1, c);
    }

    @Test
    public void testPerform9() throws Exception {
        int c = perform("blind", "test/src/9");
        assertEquals(0, c);
        c = perform("consistent", "test/src/9");
        assertEquals(0, c);
    }

    @Test
    public void testPerform10() throws Exception {
        int c = perform("blind", "test/src/10");
        assertEquals(0, c);
        c = perform("consistent", "test/src/10");
        assertEquals(0, c);
    }

    @Test
    public void testPerform11() throws Exception {
        int c = perform("blind", "test/src/11");
        assertEquals(0, c);
        c = perform("consistent", "test/src/11");
        assertEquals(0, c);
    }

    @Test
    public void testPerform12() throws Exception {
        int c = perform("blind", "test/src/12");
        assertEquals(1, c);
        c = perform("consistent", "test/src/12");
        assertEquals(1, c);
    }

    @Test
    public void testPerform13() throws Exception {
        int c = perform("blind", "test/src/13");
        assertEquals(0, c);
        c = perform("consistent", "test/src/13");
        assertEquals(0, c);
    }

    @Test
    public void testPerform14() throws Exception {
        int c = perform("blind", "test/src/14");
        assertEquals(1, c);
        c = perform("consistent", "test/src/14");
        assertEquals(0, c);
    }

    @Test
    public void testPerform15() throws Exception {
        int c = perform("blind", "test/src/15");
        assertEquals(1, c);
        c = perform("consistent", "test/src/15");
        assertEquals(0, c);
    }

    @Test
    public void testPerform16() throws Exception {
        int c = perform("blind", "test/src/16");
        assertEquals(0, c);
        c = perform("consistent", "test/src/16");
        assertEquals(0, c);
    }

    @Test
    public void testPerform17() throws Exception {
        int c = perform("blind", "test/src/17");
        assertEquals(1, c);
        c = perform("consistent", "test/src/17");
        assertEquals(0, c);
    }

    @Test
    public void testPerform18() throws Exception {
        int c = perform("blind", "test/src/18");
        assertEquals(1, c);
        c = perform("consistent", "test/src/18");
        assertEquals(0, c);
    }

    @Test
    public void testPerform19() throws Exception {
        int c = perform("blind", "test/src/19");
        assertEquals(1, c);
        c = perform("consistent", "test/src/19");
        assertEquals(1, c);
    }

    @Test
    public void testPerform20() throws Exception {
        int c = perform("blind", "test/src/20");
        assertEquals(0, c);
        c = perform("consistent", "test/src/20");
        assertEquals(0, c);
    }

    @Test
    public void testPerform21() throws Exception {
        int c = perform("blind", "test/src/21");
        assertEquals(0, c);
        c = perform("consistent", "test/src/21");
        assertEquals(0, c);
    }

    @Test
    public void testPerform22() throws Exception {
        int c = perform("blind", "test/src/22");
        assertEquals(0, c);
        c = perform("consistent", "test/src/22");
        assertEquals(0, c);
    }

    @Test
    public void testPerform23() throws Exception {
        int c = perform("blind", "test/src/23");
        assertEquals(1, c);
        c = perform("consistent", "test/src/23");
        assertEquals(1, c);
    }

    @Test
    public void testPerform24() throws Exception {
        int c = perform("blind", "test/src/24");
        assertEquals(1, c);
        c = perform("consistent", "test/src/24");
        assertEquals(0, c);
    }

    @Test
    public void testPerform25() throws Exception {
        int c = perform("blind", "test/src/25");
        assertEquals(1, c);
        c = perform("consistent", "test/src/25");
        assertEquals(1, c);
    }

    @Test
    public void testPerform26() throws Exception {
        int c = perform("blind", "test/src/26");
        assertEquals(1, c);
        c = perform("consistent", "test/src/26");
        assertEquals(1, c);
    }

    @Test
    public void testPerform27() throws Exception {
        int c = perform("blind", "test/src/27");
        assertEquals(0, c);
        c = perform("consistent", "test/src/27");
        assertEquals(0, c);
    }

    @Test
    public void testPerform28() throws Exception {
        int c = perform("blind", "test/src/28");
        assertEquals(1, c);
        c = perform("consistent", "test/src/28");
        assertEquals(0, c);
    }

    @Test
    public void testPerform29() throws Exception {
        int c = perform("blind", "test/src/29");
        assertEquals(1, c);
        c = perform("consistent", "test/src/29");
        assertEquals(1, c);
    }

    @Test
    public void testPerform30() throws Exception {
        int c = perform("blind", "test/src/30");
        assertEquals(1, c);
        c = perform("consistent", "test/src/30");
        assertEquals(0, c);
    }

    @Test
    public void testPerform31() throws Exception {
        int c = perform("blind", "test/src/31");
        assertEquals(1, c);
        c = perform("consistent", "test/src/31");
        assertEquals(0, c);
    }

    @Test
    public void testPerform32() throws Exception {
        int c = perform("blind", "test/src/32");
        assertEquals(1, c);
        c = perform("consistent", "test/src/32");
        assertEquals(0, c);
    }

    @Test
    public void testPerform33() throws Exception {
        int c = perform("blind", "test/src/33");
        assertEquals(1, c);
        c = perform("consistent", "test/src/33");
        assertEquals(0, c);
    }

    @Test
    public void testPerform34() throws Exception {
        int c = perform("blind", "test/src/34");
        assertEquals(1, c);
        c = perform("consistent", "test/src/34");
        assertEquals(0, c);
    }

    @Test
    public void testPerform35() throws Exception {
        int c = perform("blind", "test/src/35");
        assertEquals(1, c);
        c = perform("consistent", "test/src/35");
        assertEquals(0, c);
    }

    @Test
    public void testPerform36() throws Exception {
        int c = perform("blind", "test/src/36");
        assertEquals(1, c);
        c = perform("consistent", "test/src/36");
        assertEquals(1, c);
    }

    @Test
    public void testPerform37() throws Exception {
        int c = perform("blind", "test/src/37");
        assertEquals(1, c);
        c = perform("consistent", "test/src/37");
        assertEquals(1, c);
    }

    @Test
    public void testPerform38() throws Exception {
        int c = perform("blind", "test/src/38");
        assertEquals(0, c);
        c = perform("consistent", "test/src/38");
        assertEquals(0, c);
    }

    @Test
    public void testPerform39() throws Exception {
        int c = perform("blind", "test/src/39");
        assertEquals(0, c);
        c = perform("consistent", "test/src/39");
        assertEquals(0, c);
    }

    @Test
    public void testPerform40() throws Exception {
        int c = perform("blind", "test/src/40");
        assertEquals(0, c);
        c = perform("consistent", "test/src/40");
        assertEquals(0, c);
    }

    @Test
    public void testPerform41() throws Exception {
        int c = perform("blind", "test/src/41");
        assertEquals(1, c);
        c = perform("consistent", "test/src/41");
        assertEquals(1, c);
    }

    @Test
    public void testPerform42() throws Exception {
        int c = perform("blind", "test/src/42");
        assertEquals(1, c);
        c = perform("consistent", "test/src/42");
        assertEquals(1, c);
    }

    @Test
    public void testPerform43() throws Exception {
        int c = perform("blind", "test/src/43");
        assertEquals(0, c);
        c = perform("consistent", "test/src/43");
        assertEquals(0, c);
    }

    @Test
    public void testPerform44() throws Exception {
        int c = perform("blind", "test/src/44");
        assertEquals(0, c);
        c = perform("consistent", "test/src/44");
        assertEquals(0, c);
    }

    @Test
    public void testPerform45() throws Exception {
        int c = perform("blind", "test/src/45");
        assertEquals(1, c);
        c = perform("consistent", "test/src/45");
        assertEquals(0, c);
    }

    @Test
    public void testPerform46() throws Exception {
        int c = perform("blind", "test/src/46");
        assertEquals(0, c);
        c = perform("consistent", "test/src/46");
        assertEquals(0, c);
    }

    @Test
    public void testPerform47() throws Exception {
        int c = perform("blind", "test/src/47");
        assertEquals(0, c);
        c = perform("consistent", "test/src/47");
        assertEquals(0, c);
    }

    @Test
    public void testPerform48() throws Exception {
        int c = perform("blind", "test/src/48");
        assertEquals(1, c);
        c = perform("consistent", "test/src/48");
        assertEquals(1, c);
    }

    @Test
    public void testPerform49() throws Exception {
        int c = perform("blind", "test/src/49");
        assertEquals(1, c);
        c = perform("consistent", "test/src/49");
        assertEquals(1, c);
    }

    @Test
    public void testPerform50() throws Exception {
        int c = perform("blind", "test/src/50");
        assertEquals(0, c);
        c = perform("consistent", "test/src/50");
        assertEquals(0, c);
    }

    @Test
    public void testPerform51() throws Exception {
        int c = performWith("blind", "test/src/51", "ignoreModifiers", "true");
        assertEquals(1, c);
        c = performWith("consistent", "test/src/51", "ignoreModifiers", "true");
        assertEquals(1, c);
    }

    @Test
    public void testPerform52() throws Exception {
        int c = perform("blind", "test/src/52");
        assertEquals(1, c);
        c = perform("consistent", "test/src/52");
        assertEquals(1, c);
    }

    @Test
    public void testPerform53() throws Exception {
        int c = perform("blind", "test/src/53");
        assertEquals(1, c);
        c = perform("consistent", "test/src/53");
        assertEquals(1, c);
    }

    @Test
    public void testPerform54() throws Exception {
        int c = perform("blind", "test/src/54");
        assertEquals(1, c);
        c = perform("consistent", "test/src/54");
        assertEquals(1, c);
    }

    @Test
    public void testPerform55() throws Exception {
        int c = perform("blind", "test/src/55");
        assertEquals(1, c);
        c = perform("consistent", "test/src/55");
        assertEquals(0, c);
    }

    @Test
    public void testPerform56() throws Exception {
        int c = perform("blind", "test/src/56");
        assertEquals(1, c);
        c = perform("consistent", "test/src/56");
        assertEquals(0, c);
    }

    @Test
    public void testPerform57() throws Exception {
        int c = perform("blind", "test/src/57");
        assertEquals(1, c);
        c = perform("consistent", "test/src/57");
        assertEquals(0, c);
    }

    @Test
    public void testPerform58() throws Exception {
        int c = perform("blind", "test/src/58");
        assertEquals(1, c);
        c = perform("consistent", "test/src/58");
        assertEquals(0, c);
    }

    @Test
    public void testPerform59() throws Exception {
        int c = perform("blind", "test/src/59");
        assertEquals(1, c);
        c = perform("consistent", "test/src/59");
        assertEquals(1, c);
    }

    @Test
    public void testPerform60() throws Exception {
        int c = perform("blind", "test/src/60");
        assertEquals(1, c);
        c = perform("consistent", "test/src/60");
        assertEquals(1, c);
    }

    @Test
    public void testPerform61() throws Exception {
        int c = perform("blind", "test/src/61");
        assertEquals(1, c);
        c = perform("consistent", "test/src/61");
        assertEquals(1, c);
    }

    @Test
    public void testPerform62() throws Exception {
        int c = perform("blind", "test/src/62");
        assertEquals(0, c);
        c = perform("consistent", "test/src/62");
        assertEquals(0, c);
    }

    @Test
    public void testPerform63() throws Exception {
        int c = perform("blind", "test/src/63");
        assertEquals(1, c);
        c = perform("consistent", "test/src/63");
        assertEquals(1, c);
    }

    @Test
    public void testPerform64() throws Exception {
        int c = perform("blind", "test/src/64");
        assertEquals(1, c);
        c = perform("consistent", "test/src/64");
        assertEquals(0, c);
    }

    @Test
    public void testPerform65() throws Exception {
        int c = perform("blind", "test/src/65");
        assertEquals(1, c);
        c = perform("consistent", "test/src/65");
        assertEquals(0, c);
    }

    @Test
    public void testPerform66() throws Exception {
        int c = perform("blind", "test/src/66");
        assertEquals(1, c);
        c = perform("consistent", "test/src/66");
        assertEquals(0, c);
    }

    @Test
    public void testPerform67() throws Exception {
        int c = perform("blind", "test/src/67");
        assertEquals(1, c);
        c = perform("consistent", "test/src/67");
        assertEquals(0, c);
    }

    @Test
    public void testPerform68() throws Exception {
        int c = perform("blind", "test/src/68");
        assertEquals(1, c);
        c = performWith("consistent", "test/src/68", "useWrappersForPrimitiveTypes", "true");
        assertEquals(1, c);
    }

    @Test
    public void testPerform69() throws Exception {
        int c = perform("blind", "test/src/69");
        assertEquals(1, c);
        c = perform("consistent", "test/src/69");
        assertEquals(1, c);
    }

    @Test
    public void testPerform70() throws Exception {
        int c = perform("blind", "test/src/70");
        assertEquals(1, c);
        c = perform("consistent", "test/src/70");
        assertEquals(1, c);
    }

    @Test
    public void testPerform71() throws Exception {
        int c = perform("blind", "test/src/71");
        assertEquals(1, c);
        c = perform("consistent", "test/src/71");
        assertEquals(1, c);
    }

    @Test
    public void testPerform72() throws Exception {
        int c = performWith("blind", "test/src/72", "normalizeAST", "true");
        assertEquals(1, c);
        c = performWith("consistent", "test/src/72", "normalizeAST", "true");
        assertEquals(1, c);
    }

    @Test
    public void testPerform73() throws Exception {
        int c = performWith("blind", "test/src/73", "normalizeAST", "true");
        assertEquals(1, c);
        c = performWith("consistent", "test/src/73", "normalizeAST", "true");
        assertEquals(1, c);
    }

    @Test
    public void testPerform74() throws Exception {
        int c = performWith("blind", "test/src/74", "normalizeAST", "true");
        assertEquals(1, c);
        c = performWith("consistent", "test/src/74", "normalizeAST", "true");
        assertEquals(1, c);
    }

    @Test
    public void testPerform75() throws Exception {
        int c = perform("blind", "test/src/75");
        assertEquals(1, c);
        c = perform("consistent", "test/src/75");
        assertEquals(0, c);
    }

    @Test
    public void testPerform76() throws Exception {
        int c = perform("blind", "test/src/76");
        assertEquals(1, c);
        c = perform("consistent", "test/src/76");
        assertEquals(1, c);
    }
}
