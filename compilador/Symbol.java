package compilador;

public class Symbol {
	private String name;
	private Scope scope;
	private SymbolType type;
	
	public Symbol(SymbolType type, String name, Scope scope) {
		setName(name);
		setParentScope(scope);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public SymbolType getType() {
		return type;
	}
	
	public Scope getParentScope() {
		return scope;
	}

	public void setParentScope(Scope scope) {
		this.scope = scope;
	}
	
	@Override public boolean equals(Object testThat) {
		if(testThat == this)
			return true;
		if ( !(testThat instanceof Symbol) )
			return false;
		
		Symbol that = (Symbol) testThat;
		return name.equals(that.getName()) && type == that.getType();
	}
}
