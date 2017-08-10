package index.symtab;

/**
 * The kind of symbol table entry.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public enum SymbolKind {

    PARAMETER,
    FIELD,
    STATIC_FIELD,
    METHOD,
    CONSTRUCTOR,
    EXCEPTION_PARAMETER,
    LOCAL_VARIABLE,
    ENUM_CONSTANT,
    RESOURCE_VARIABLE,
    ID,
    TYPE;

    public static SymbolKind instance(String str) {
        switch (str) {
            case "PARAMETER":
                return PARAMETER;
            case "FIELD":
                return FIELD;
            case "STATIC_FIELD":
                return STATIC_FIELD;
            case "METHOD":
                return METHOD;
            case "CONSTRUCTOR":
                return CONSTRUCTOR;
            case "EXCEPTION_PARAMETER":
                return EXCEPTION_PARAMETER;
            case "LOCAL_VARIABLE":
                return LOCAL_VARIABLE;
            case "ENUM_CONSTANT":
                return ENUM_CONSTANT;
            case "RESOURCE_VARIABLE":
                return RESOURCE_VARIABLE;
            case "ID":
                return ID;
            case "TYPE":
                return TYPE;
        }
        assert false;
        return null;
    }
}
