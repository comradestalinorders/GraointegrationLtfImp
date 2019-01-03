package net.is_bg.ltf.grao.ltf.massive.client.sqlstatements;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.is_bg.ltf.db.common.DBExecutor;
import net.is_bg.ltf.db.common.SelectSqlStatement;

/***/
class SelSeq extends SelectSqlStatement {

	protected long nextVal;
	private String sequenceName;

	
	public SelSeq (String sequenceName){
		this.sequenceName = sequenceName;
	}
	
	@Override
	protected String getSqlString() {
		return "select nextval('" + sequenceName + "') ";
    }

    @Override
	protected void retrieveResult(ResultSet rs) throws SQLException {
		while (rs.next()) nextVal = rs.getLong("nextVal");
	}
	
	public long getNextVal() {
		return this.nextVal;
	}
	
	public static long getNextVal(DBExecutor ex, String sequenceName) {
		SelSeq sel = new SelSeq(sequenceName);
		ex.execute(sel);
		return sel.getNextVal();
	}
}
