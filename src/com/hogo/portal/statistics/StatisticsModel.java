package com.hogo.portal.statistics;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import com.hogo.portal.db.AbstractDBModel;

public class StatisticsModel extends AbstractDBModel<StatisticsEntry> {

	private PreparedStatement statistics;

	protected StatisticsModel() throws SQLException {
		super(null, null);

		select = connection.prepareStatement("select name from skills");
		statistics = connection.prepareStatement(
				"select name, (select count(p.id) from person p where (p.einsetzbar LIKE ? OR p.gelernt LIKE ?)) as All,\r\n"
						+ "			(select count(p.id) from person p where (p.einsetzbar LIKE ? OR p.gelernt LIKE ?) and (p.status = 0)) as Free,\r\n"
						+ "			(select count(p.id) from person p where (p.einsetzbar LIKE ? OR p.gelernt LIKE ?) and (p.status = 1)) as Reserved,\r\n"
						+ "			(select count(p.id) from person p where (p.einsetzbar LIKE ? OR p.gelernt LIKE ?) and (p.status = 2)) as Busy "
						+ "from Skills s where name = ?");
	}

	public static StatisticsModel open() throws SQLException {
		return new StatisticsModel();
	}

	@Override
	public Collection<StatisticsEntry> select() throws SQLException {
		Collection<StatisticsEntry> out = new LinkedList<StatisticsEntry>();
		try (ResultSet skillsRs = select.executeQuery()) {
			while (skillsRs.next()) {
				String name = skillsRs.getString(1);

				String search = '%' + name + '%';

				for (int i = 1; i < 9; i++) {
					statistics.setString(i, search);
				}
				statistics.setString(9, name);

				try (ResultSet statRs = statistics.executeQuery()) {
					while (statRs.next()) {
						out.add(new StatisticsEntry(name, statRs.getInt("All"), statRs.getInt("Free"), statRs.getInt("Reserved"),
								statRs.getInt("Busy")));
					}
				}
			}
		}
		return out;
	}

	@Override
	public void close() throws Exception {
		super.close();
		statistics.close();
	}
}
