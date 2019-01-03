package net.is_bg.ltf.grao.ltf;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.is_bg.ltf.db.common.SelectSqlStatement;

abstract  class  SelectSqlStatementGeneric<T> extends SelectSqlStatement {
	protected List<T> result = new  ArrayList<T>();
	protected Set<T> resultSet = new  TreeSet<T>();
	List<T> getResult() {
		return result;
	}
	public Set<T> getResultSet() {
		return resultSet;
	}
}
