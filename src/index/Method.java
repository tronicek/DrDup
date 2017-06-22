package index;

/**
 * The representation of a method.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class Method {

    private final int mid;
    private final String file;
    private final int start;
    private final int end;
    private final long startLine;
    private final long endLine;
    private final MethodTokens tokens;

    public Method(int mid, String file, int start, int end, long startLine, long endLine, MethodTokens tokens) {
        this.mid = mid;
        this.file = file;
        this.start = start;
        this.end = end;
        this.startLine = startLine;
        this.endLine = endLine;
        this.tokens = tokens;
        tokens.addMid(mid);
    }

    public int getMid() {
        return mid;
    }

    public String getFile() {
        return file;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public long getStartLine() {
        return startLine;
    }

    public long getEndLine() {
        return endLine;
    }

    public MethodTokens getTokens() {
        return tokens;
    }

    public void print() {
        System.out.printf(
                "method [mid: %d, file: %s, start: %d, end: %d, startLine: %d, endLine: %d, tid: %d]%n",
                mid, file, start, end, startLine, endLine, tokens.getTid());
    }

    @Override
    public String toString() {
        return String.format(
                "[mid: %d, file: %s, start: %d, end: %d, startLine: %d, endLine: %d]",
                mid, file, start, end, startLine, endLine);
    }
}
