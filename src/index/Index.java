package index;

import clones.CloneSet;
import java.io.IOException;

public interface Index {

    public void print();

    public void writeMethods(String filename) throws IOException;

    public CloneSet detectClonesType2(int minSize, int maxSize);

    public CloneSet detectClonesType3(int minSize, int maxSize, int distance);
    
    public CloneSet detectClonesType23(int minSize, int maxSize, int distance);
}
