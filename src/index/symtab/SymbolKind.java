package index.symtab;

import javax.lang.model.element.ElementKind;

/**
 * The kind of symbol table entry.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public enum SymbolKind {

    FIELD,
    METHOD,
    LOCAL_VARIABLE,
    TYPE,
    OTHER;

    public static SymbolKind fromElementKind(ElementKind kind) {
        switch (kind) {
            case ANNOTATION_TYPE:
            case CLASS:
            case CONSTRUCTOR:
            case ENUM:
            case INTERFACE:
            case TYPE_PARAMETER:
                return TYPE;
            case ENUM_CONSTANT:
            case FIELD:
                return FIELD;
            case EXCEPTION_PARAMETER:
            case LOCAL_VARIABLE:
            case PARAMETER:
            case RESOURCE_VARIABLE:
                return LOCAL_VARIABLE;
            case METHOD:
                return METHOD;
            default:
                return OTHER;
        }
    }
}
