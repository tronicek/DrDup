package index;

import clones.CloneSet;
import java.io.IOException;

public interface Index {

    public void print();

    public void writeMethods(String filename) throws IOException;

    public CloneSet detectClonesType2(int minSize);

    public CloneSet detectClonesType3(int minSize, int distance);
}
