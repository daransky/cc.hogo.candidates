package com.hogo.portal.views.log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.daro.common.ui.UIError;

import com.hogo.portal.db.AbstractDBModel;

public class LogModel extends AbstractDBModel<LogEntry> {
	
	static Function<ResultSet, LogEntry> row2e = (result) -> {
		try {
			return new LogEntry(result.getInt("id"), 
								result.getInt("logid"), 
								result.getShort("logtype"), 
								result.getTimestamp("logdate"), 
								result.getString("logmsg"));
		} catch (SQLException e1) {
			UIError.showError("DB Fehler", e1);
		}
		return null;
	};

	static BiConsumer<LogEntry, Statement> entry2stmt = (e, st) -> {
		PreparedStatement stmt = (PreparedStatement)st;
		try {
			stmt.setLong(1, e.getId());
			stmt.setInt(2, e.getMsgId());
			stmt.setShort(3, (short)e.getType().value);
			stmt.setTimestamp(4, Timestamp.valueOf(e.getTime()));
			stmt.setString(5, e.getMessage());
		} catch (Exception ee) {
			UIError.showError("DB Fehler", ee);
		}
	};
	
	protected LogModel()
			throws SQLException {
		super(row2e, entry2stmt);
		
		select = connection.prepareStatement("select id, logid, logdate, logtype, logmsg from LOG log order by logdate desc");
	}

	public static LogModel open() throws SQLException {
		return new LogModel();
	}
}
