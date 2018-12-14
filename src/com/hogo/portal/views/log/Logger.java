package com.hogo.portal.views.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.daro.common.ui.UIError;

import com.hogo.portal.views.log.LogEntry.Type;

public class Logger {

	private static Logger instance;

	private final PreparedStatement insert;

	public static final int GENERAL_ERROR = 10;
	public static final int IMPORT_COMPLETED = 100;
	public static final int RECORD_UPDATED = 110;

	Logger(PreparedStatement stmt) {
		insert = stmt;
	}

	
	public void write(LogEntry e) {
		try {
			insert.setInt(1, e.getMsgId());
			insert.setShort(2, (short) e.getType().value);
			insert.setString(3, e.getMessage());
			insert.executeUpdate();
			insert.getConnection().commit();
		} catch (SQLException e1) {
			UIError.showError("DB Fehler", e1);
		}
	}

	public static Logger instance() {
		if (instance == null) {
			try {
				Connection c = LogModel.getConnection();
				instance = new Logger(c.prepareStatement("INSERT INTO LOG (logid, logtype, logmsg) values(?,?,?)") );
			} catch (SQLException e) {
				UIError.showError("DB Fehler", e);
			}
		}
		
		return instance;
	}
	
	public static void logInfo(String msg) {
		instance().write(new LogEntry(Type.INFO, msg));
	}
	
	public static void logWarning(String msg) {
		instance().write(new LogEntry(Type.WARNING, msg));
	}

	public static void logError(String msg) {
		instance().write(new LogEntry(Type.ERROR, msg));
	}
}
