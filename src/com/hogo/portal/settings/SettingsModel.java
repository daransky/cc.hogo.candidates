package com.hogo.portal.settings;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.daro.common.ui.UIError;

import com.hogo.portal.db.AbstractDBModel;

public class SettingsModel extends AbstractDBModel<SettingsEntry> {

	static Function<ResultSet, SettingsEntry> row2entry = rs -> {
		try {
			return new SettingsEntry(rs.getString("key"), rs.getString("value"));
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
			return null;
		}
	};

	static BiConsumer<SettingsEntry, Statement> entry2row = (p, stmt) -> {
		final PreparedStatement rs = (PreparedStatement) stmt;
		try {
			rs.setString(1, p.getKey());
			rs.setString(2, p.getValue());
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
	};

	SettingsModel() {
		super(row2entry, entry2row);
	}

	public static SettingsModel open() throws SQLException {
		final SettingsModel model = new SettingsModel();
		model.select = SettingsModel.connection.prepareStatement("Select key,value from settings order by key");

		model.insert = SettingsModel.connection
				.prepareStatement("insert into settings (key, value ) values(?,?)");

		model.updateRead = SettingsModel.connection
				.prepareStatement("Select key,value from settings where key = ?");
		model.update = SettingsModel.connection.prepareStatement("update settings set value=? where key = ?");
		model.delete = SettingsModel.connection.prepareStatement("delete from settings where key =?");
		return model;
	}
	
	@Override
	public void update(SettingsEntry e) throws SQLException {
		update.setString(1, e.getValue());
		update.setString(2, e.getKey());
		update.executeUpdate();
	}
}
