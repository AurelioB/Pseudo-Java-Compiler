package compilador;

import java.util.*;

public class Scope extends Symbol {
	
	private Map<String, Symbol> table;
	private ArrayList<Symbol> arguments;
	
	public Scope(SymbolType type, String name, Scope parent) {
		super(type, name, parent);
		table = new HashMap<String, Symbol>();
		arguments = new ArrayList<Symbol>();
		 
	}
	/*
	public Scope(SymbolType type, String name, Scope parent, Lexemas... args) {
		super(type, name, parent);
		table = new HashMap<String, Symbol>();
		arguments = new ArrayList<Symbol>(args.length);
		
		addArguments(args);
		
	}*/
	
	public void addArgument(Lexemas type, String name) {
		
		Variable variable = new Variable(SymbolType.getSymbol(type), name, this);
		arguments.add(variable);
		put(variable);
	}
	
	public boolean checkArgumentType(int n, Lexemas type) {
		return arguments.get(n).getType() == SymbolType.getSymbol(type);
	}
	
	public Symbol getArgumentType(int n) {
		return arguments.get(n);
	}
	
	public Boolean has(String name) {
		return table.containsKey(name);
	}
	
	public void put(Symbol symbol) {
		table.put(symbol.getName(), symbol);
	}
	
	public void put(String name, Symbol symbol) {
		table.put(name, symbol);
	}
	
	public Symbol get(String name) {
		return table.get(name);
	}

}
