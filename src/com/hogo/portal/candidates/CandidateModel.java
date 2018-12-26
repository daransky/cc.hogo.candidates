package com.hogo.portal.candidates;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.daro.common.ui.UIError;

import com.hogo.portal.db.AbstractDBModel;

public class CandidateModel extends AbstractDBModel<CandidateEntry> {
	
	static Function<ResultSet, CandidateEntry> row2entry = rs -> {
		final CandidateEntry p = new CandidateEntry();
		try {
			p.id = rs.getLong("id");
			p.created = rs.getObject("created", LocalDateTime.class);
			p.birthDay = rs.getObject("birthday", LocalDate.class); 
			p.status = Status.values()[rs.getInt("status")];
			p.name = new Name(rs.getString("vorname"), rs.getString("nachname"));
			p.tel1 = rs.getString("tel1");
			p.tel2 = rs.getString("tel2");
			p.email = rs.getString("email");
			p.verfuegbar = rs.getObject("verfuegbar", LocalDate.class);
			p.einsetzbar = rs.getString("einsetzbar");
			p.gelernt = rs.getString("gelernt");
			p.kommentar = rs.getString("kommentar");
			p.strasse =  rs.getString("strasse"); 
		    p.stadt = rs.getString("stadt");
		    p.plz = rs.getString("plz");
		    p.land = rs.getString("land");
			p.deutsch = LanguageKnowledge.valueOf(rs.getShort("deutsch"));
			Array array = rs.getArray("partner");
			if (array != null) {

				Long[] tmp = (Long[]) array.getArray();
				if (tmp != null) {
					p.partner = new long[tmp.length];
					for (int i = 0; i < tmp.length; i++)
						p.partner[i] = tmp[i];
				}
			}

			p.mobil = rs.getBoolean("istmobil");
			p.sexMale = rs.getBoolean("sex");
			p.staplerschein = rs.getBoolean("staplerschein");
			p.hochregal = rs.getBoolean("hochregal");
			p.seitenhub = rs.getBoolean("seitenhub");
			p.blacklist = rs.getBoolean("blacklist");
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
		return p;
	};

	static BiConsumer<CandidateEntry, Statement> entry2row = (p,stmt) -> {
		final PreparedStatement rs = (PreparedStatement)stmt;
		try {
		rs.setString(1, p.owner );
		rs.setInt(2, p.status.ordinal());
	    rs.setBoolean(3, p.sexMale );
	    rs.setBoolean(4, p.mobil );
	    rs.setBoolean(5, p.staplerschein );
	    rs.setBoolean(6, p.hochregal);
	    rs.setBoolean(7, p.seitenhub); 
	    rs.setBoolean(8, p.blacklist); 
	    rs.setInt(9, p.deutsch.ordinal()); 
	    if( p.partner == null || p.partner.length == 0 ) 
	    	rs.setArray(10, null );
	    else { 
	    	Long values[] = new Long[p.partner.length];
	    	for(int i =0; i< p.partner.length; i++ )
	    		values[i] = p.partner[i];
	    	Array array = rs.getConnection().createArrayOf("bigint", values);
	    	rs.setArray(10, array);
	    }
	    rs.setString(11, p.getVorname());
	    rs.setString(12, p.getNachname());
	    rs.setString(13, p.getTel1()); 
	    rs.setString(14, p.getTel2()); 
	    rs.setString(15, p.email); 
	    rs.setObject(16, p.verfuegbar);
	    rs.setString(17, p.einsetzbar); 
	    rs.setString(18, p.gelernt);
	    rs.setString(19, p.kommentar); 
	    rs.setString(20, p.strasse); 
	    rs.setString(21, p.stadt);
	    rs.setString(22, p.plz);
	    rs.setString(23, p.land);
	    rs.setObject(24, p.birthDay);
	    if( p.id != 0 )
	    	rs.setLong(25, p.id);
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
	};

	private PreparedStatement updateStatus;
	
	CandidateModel() {
		super(row2entry, entry2row);
	}

	public static CandidateModel open() throws SQLException {
		final CandidateModel model = new CandidateModel();
		final List<String> fields = Arrays.asList(
	    "id",
	    "created", 
	    "owner",
	    "status", 
	    "sex", 
	    "istmobil", 
	    "staplerschein", 
	    "hochregal", 
	    "seitenhub", 
	    "blacklist", 
	    "deutsch", 
	    "partner", 
	    "vorname", 
	    "nachname", 
	    "tel1", 
	    "tel2", 
	    "email", 
	    "verfuegbar", 
	    "einsetzbar", 
	    "gelernt", 
	    "kommentar", 
	    "strasse", 
	    "stadt",
	    "plz",
	    "land",
	    "birthday"
		);
		final String collected = fields.stream().collect(Collectors.joining(","));
		model.select = CandidateModel.connection
				.prepareStatement("Select " + collected + " from person order by created desc");
		
		final Collection<String> nonDbDft = fields.subList(2, fields.size());
		String dbFields = nonDbDft.stream().collect(Collectors.joining(", "));
		String dbValues = nonDbDft.stream().map( e-> "?").collect(Collectors.joining(","));
		model.insert = CandidateModel.connection
				.prepareStatement("insert into person ("+dbFields+") values("+dbValues+")");
		
		dbFields = nonDbDft.stream().map( e -> e + "=?").collect(Collectors.joining(", "));
		model.updateRead = CandidateModel.connection
				.prepareStatement("Select " + collected + " from person where id = ?");

		model.update = CandidateModel.connection
				.prepareStatement("update person set "+dbFields+" where id = ?");
		
		model.delete = CandidateModel.connection.prepareStatement("delete from person where id =?");
		model.updateStatus = CandidateModel.connection.prepareStatement("update person set status=? where id = ?");
		
		return model;
	}
	
	@Override
	public void add(CandidateEntry e) throws SQLException {
		e.owner = System.getProperty("user.name");
		super.add(e);
	}
	
	public void updateStatus(CandidateEntry e) throws SQLException { 
		updateStatus.setInt(1, e.getStatus().ordinal());
		updateStatus.setLong(2, e.getId());
		updateStatus.executeUpdate();
	}
}
