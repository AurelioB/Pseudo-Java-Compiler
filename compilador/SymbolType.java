package compilador;

public enum SymbolType {
	STRING,
    INT,
    BOOLEAN,
    FLOAT,
    FUNCTION,
    FOR,
    WHILE,
    IF,
    BLOCK;
	
	public static SymbolType getSymbol(Lexemas lexem) {
		
		switch(lexem) {
			case TIPO_STRING:
				return SymbolType.STRING;
			case TIPO_INT:
				return SymbolType.INT;
			case TIPO_BOOLEAN:
				return SymbolType.BOOLEAN;
			case TIPO_FLOAT:
				return SymbolType.FLOAT;
			case ID:
				return SymbolType.FUNCTION;
			case FOR:
				return SymbolType.FOR;
			case WHILE:
				return SymbolType.WHILE;
			case IF:
				return SymbolType.IF;
			default:
				return null;
		}
	}
	
}
