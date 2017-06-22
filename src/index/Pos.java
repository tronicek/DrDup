package index;

import java.io.Serializable;

/**
 * The position of a code fragment.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class Pos implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int count;
    private final int mid;
    private final String file;
    private final int lines;
    private final int start;
    private final int end;
    private final int startLine;
    private final int endLine;

    public Pos(int mid, String file, int lines, int start, int end, int startLine, int endLine) {
        this.mid = mid;
        this.file = file;
        this.lines = lines;
        this.start = start;
        this.end = end;
        this.startLine = startLine;
        this.endLine = endLine;
        count++;
    }

    public static int getCount() {
        return count;
    }

    public int getMid() {
        return mid;
    }

    public String getFile() {
        return file;
    }

    public int getLines() {
        return lines;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    @Override
    public int hashCode() {
        return mid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pos) {
            Pos that = (Pos) obj;
            return mid == that.mid
                    && file.equals(that.file)
                    && start == that.start
                    && end == that.end;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format(
                "[mid: %d, file: %s, start: %d, end: %d, startline: %d, endline: %d]",
                mid, file, start, end, getStartLine(), getEndLine());
    }
}
