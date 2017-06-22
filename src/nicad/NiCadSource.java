package nicad;

import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * Implementation of the NiCad output format.
 * 
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
@XmlType(name = "source", propOrder = {"file", "startline", "endline"})
@XmlAccessorType(XmlAccessType.PROPERTY)
public class NiCadSource {

    private String file;
    private Integer startline;
    private Integer endline;
    private String sourceCode;

    public NiCadSource() {
    }

    public NiCadSource(String file, Integer startline, Integer endline) {
        this.file = file;
        this.startline = startline;
        this.endline = endline;
    }

    @XmlAttribute
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @XmlAttribute
    public Integer getStartline() {
        return startline;
    }

    public void setStartline(Integer startline) {
        this.startline = startline;
    }

    @XmlAttribute
    public Integer getEndline() {
        return endline;
    }

    public void setEndline(Integer endline) {
        this.endline = endline;
    }

    @XmlValue
    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NiCadSource) {
            NiCadSource that = (NiCadSource) obj;
            return file.equals(that.file)
                    && startline.equals(that.startline)
                    && endline.equals(that.endline);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(file, startline, endline);
    }
}
