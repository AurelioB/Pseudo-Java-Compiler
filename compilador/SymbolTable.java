package compilador;

public class SymbolTable {
	private Scope global;
	private Scope currentScope;
	
	public SymbolTable() {
		global = new Scope(null, "global", null);
		setCurrentScope(global);
	}
	
	public Symbol tryInsert(Lexemas lex, String name) {

		if(tryMatch(name) == null) {
			
			SymbolType type = SymbolType.getSymbol(lex);
			if(type == SymbolType.FUNCTION) {
				Scope method = new Scope(type, name, getCurrentScope());
				getCurrentScope().put(method);
				enterScope(method);
			} else {
				Symbol variable = new Variable(type, name, getCurrentScope());
				getCurrentScope().put(variable);
			}
			return null;
		}
		return tryMatch(name);
	}
	
	// Checks if a symbol exists, just by name
	
	public Symbol tryMatch(String name) {
		Scope tmpScope = getCurrentScope();
		while(tmpScope != null) {
			if(tmpScope.has(name))
				return tmpScope.get(name);
			tmpScope = tmpScope.getParentScope();
		}
		return null;
	}
	
	// checks if a symbol with a specified name and type exists
	
	public Symbol tryMatch(Lexemas type, String name) {
		Symbol variable = tryMatch(name);
		if(variable != null)
			if(SymbolType.getSymbol(type) != variable.getType())
				return variable;
		return null;
	}
	
	// Checks if the specified function exists with all the specified argument types
	
	public Symbol tryMatch(String name, Lexemas... args) {
		Symbol method = tryMatch(name);
		if(method != null) {
			int index = 0;
			for(Lexemas arg: args) {
				if(!getCurrentScope().checkArgumentType(index, arg)) {
					return getCurrentScope().getArgumentType(index);
				}
				index++;
			}
		}
		return null;
	}
	
	public void enterScope(Scope scope) {
		setCurrentScope( scope );
	}
	
	public void exitScope() {
		if( getCurrentScope().getParentScope() != null)
			setCurrentScope( getCurrentScope().getParentScope() );
	}

	public Scope getCurrentScope() {
		return currentScope;
	}

	public void setCurrentScope(Scope currentScope) {
		this.currentScope = currentScope;
	}
	
}
