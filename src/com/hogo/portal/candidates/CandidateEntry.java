package com.hogo.portal.candidates;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CandidateEntry {
	
	class	Address { 
		String 			strasse;
		String			stadt;
		String			plz;
		String			land;
		
		public Address() {
		}

		public Address(String strasse, String stadt, String plz, String land) {
			super();
			this.strasse = strasse;
			this.stadt = stadt;
			this.plz = plz;
			this.land = land;
		}
		public String getStrasse() {
			return strasse;
		}
		public void setStrasse(String strasse) {
			this.strasse = strasse;
		}
		public String getStadt() {
			return stadt;
		}
		public void setStadt(String stadt) {
			this.stadt = stadt;
		}
		public String getPlz() {
			return plz;
		}
		public void setPlz(String plz) {
			this.plz = plz;
		}
		public String getLand() {
			return land;
		}
		public void setLand(String land) {
			this.land = land;
		}
		
		
	}
	
	long			id;
	boolean			mobil;
	boolean			staplerschein;
	boolean			hochregal;
	boolean			seitenhub;
	boolean			sexMale;
	boolean			blacklist;
	long[] 			partner;
	Name			name = new Name();
	LocalDateTime 	created;
	LocalDate		birthDay;
	LanguageKnowledge deutsch = LanguageKnowledge.None;
    Status 			status = Status.Free;
    String 			owner;
    String			tel1, tel2;
	String			email; 
	LocalDate		verfuegbar;
	String 			einsetzbar;
	String 			gelernt;
	String 			kommentar;
	String 			strasse;
	String			stadt;
	String			plz;
	String			land;
	
	public CandidateEntry() {
		status = Status.Free;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	
	public String getVorname() { 
		return name.firstname;
	}
	public void setVorname(String arg) { 
		name.firstname = arg;
	}

	public String getNachname() { 
		return name.surname;
	}
	
	public void setNachname(String arg) { 
		name.surname = arg;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	public boolean isMobil() {
		return mobil;
	}
	public void setMobil(boolean mobil) {
		this.mobil = mobil;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getVerfuegbar() {
		return verfuegbar;
	}
	public void setVerfuegbar(LocalDate verfuegbar) {
		this.verfuegbar = verfuegbar;
	}
	public String getEinsetzbar() {
		return einsetzbar;
	}
	public void setEinsetzbar(String einsetzbar) {
		this.einsetzbar = einsetzbar;
	}
	public String getGelernt() {
		return gelernt;
	}
	public void setGelernt(String gelernt) {
		this.gelernt = gelernt;
	}
	public String getKommentar() {
		return kommentar;
	}
	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}
	
	public long[] getPartner() {
		return partner;
	}

	public void setPartner(long[] partner) {
		this.partner = partner;
	}

	public LanguageKnowledge getDeutsch() {
		return deutsch;
	}

	public void setDeutsch(LanguageKnowledge deutsch) {
		this.deutsch = deutsch;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getStadt() {
		return stadt;
	}

	public void setStadt(String stadt) {
		this.stadt = stadt;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public Object[] toArray() {
		return new Object[] { 
				id,
				//booolean
				mobil,
				staplerschein,
				hochregal,
				seitenhub,
				sexMale,
				blacklist,
				//long[]
				partner,
				//String
				name,
				created,
				deutsch,
			    status,
			    owner,
			    tel1, 
			    tel2,
				email, 
				verfuegbar,
				einsetzbar,
				gelernt,
				kommentar
		};
	}


	@Override
	public String toString() {
		return name != null ? name.toString() : super.toString();
	}

	public boolean isStaplerschein() {
		return staplerschein;
	}

	public void setStaplerschein(boolean staplerschein) {
		this.staplerschein = staplerschein;
	}

	public boolean isHochregal() {
		return hochregal;
	}

	public void setHochregal(boolean hochregal) {
		this.hochregal = hochregal;
	}

	public boolean isSeitenhub() {
		return seitenhub;
	}

	public void setSeitenhub(boolean seitenhub) {
		this.seitenhub = seitenhub;
	}

	public boolean isSexMale() {
		return sexMale;
	}

	public void setSexMale(boolean sexMale) {
		this.sexMale = sexMale;
	}

	public boolean isBlacklist() {
		return blacklist;
	}

	public void setBlacklist(boolean blacklist) {
		this.blacklist = blacklist;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}
	
	
}
