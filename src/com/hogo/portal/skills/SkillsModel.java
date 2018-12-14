package com.hogo.portal.skills;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.daro.common.ui.UIError;

import com.hogo.portal.db.AbstractDBModel;

public class SkillsModel extends AbstractDBModel<SkillsEntry> {
	static final Collection<String> skills = new LinkedList<>();
	
	static Function<ResultSet, SkillsEntry> row2entry = rs -> {
		try {
			return new SkillsEntry(rs.getString("name"));
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
			return null;
		}
	};

	static BiConsumer<SkillsEntry, Statement> entry2row = (p,stmt) -> {
		final PreparedStatement rs = (PreparedStatement)stmt;
		try {
			rs.setString(1, p.name );
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
	};
	
	SkillsModel() {
		super(row2entry, entry2row);
	}

	public static SkillsModel open() throws SQLException {
		final SkillsModel model = new SkillsModel();
		
		final String collected = "name";
		model.select = SkillsModel.connection
				.prepareStatement("Select " + collected + " from skills order by name");
		
		String dbValues = "?";
		model.insert = SkillsModel.connection
				.prepareStatement("insert into skills ("+collected+") values("+dbValues+")");
		
		model.updateRead = SkillsModel.connection
				.prepareStatement("Select " + collected + " from skills where name = ?");

		model.delete = SkillsModel.connection.prepareStatement("delete from skills where name =?");
		return model;
	}
	
	@Override
	public Collection<SkillsEntry> select() throws SQLException {
		skills.clear();

		Collection<SkillsEntry> result= super.select();
		result.forEach( e -> skills.add(e.toString()));
		
		return result;
	}
	
	public static Collection<String> getCachedSkills() { 
		if( skills.isEmpty()) { 
			try(SkillsModel m = SkillsModel.open()) {
				Collection<SkillsEntry> result = m.select();
				skills.clear();
				result.forEach( e -> skills.add(e.toString()));
			} catch (Exception e) {	}
		}
		
		return skills;
	}
	
}
