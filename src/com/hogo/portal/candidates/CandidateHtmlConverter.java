package com.hogo.portal.candidates;

import java.time.LocalDate;
import java.util.function.Function;

public class CandidateHtmlConverter implements Function<CandidateEntry, String> {

	static final String REC_FMT = "<p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt'>%s</p>";
	static final String TEMPLATE = "<html>\r\n" + 
			"\r\n" + 
			"<head>\r\n" + 
			"<meta http-equiv=Content-Type content=\"text/html; charset=windows-1252\">\r\n" + 
			"<meta name=Generator content=\"Microsoft Word 15 (filtered)\">\r\n" + 
			"<style>\r\n" + 
			"<!--\r\n" + 
			" /* Font Definitions */\r\n" + 
			" @font-face\r\n" + 
			"	{font-family:\"Cambria Math\";\r\n" + 
			"	panose-1:2 4 5 3 5 4 6 3 2 4;}\r\n" + 
			"@font-face\r\n" + 
			"	{font-family:Calibri;\r\n" + 
			"	panose-1:2 15 5 2 2 2 4 3 2 4;}\r\n" + 
			" /* Style Definitions */\r\n" + 
			" p.MsoNormal, li.MsoNormal, div.MsoNormal\r\n" + 
			"	{margin-top:0cm;\r\n" + 
			"	margin-right:0cm;\r\n" + 
			"	margin-bottom:8.0pt;\r\n" + 
			"	margin-left:0cm;\r\n" + 
			"	line-height:106%%;\r\n" + 
			"	font-size:11.0pt;\r\n" + 
			"	font-family:\"Calibri\",sans-serif;}\r\n" + 
			"a:link, span.MsoHyperlink\r\n" + 
			"	{color:#0563C1;\r\n" + 
			"	text-decoration:underline;}\r\n" + 
			"a:visited, span.MsoHyperlinkFollowed\r\n" + 
			"	{color:#954F72;\r\n" + 
			"	text-decoration:underline;}\r\n" + 
			"p.MsoNoSpacing, li.MsoNoSpacing, div.MsoNoSpacing\r\n" + 
			"	{margin:0cm;\r\n" + 
			"	margin-bottom:.0001pt;\r\n" + 
			"	font-size:11.0pt;\r\n" + 
			"	font-family:\"Calibri\",sans-serif;}\r\n" + 
			".MsoChpDefault\r\n" + 
			"	{font-family:\"Calibri\",sans-serif;}\r\n" + 
			".MsoPapDefault\r\n" + 
			"	{margin-bottom:8.0pt;\r\n" + 
			"	line-height:107%%;}\r\n" + 
			"@page WordSection1\r\n" + 
			"	{size:612.0pt 792.0pt;\r\n" + 
			"	margin:70.85pt 70.85pt 2.0cm 70.85pt;}\r\n" + 
			"div.WordSection1\r\n" + 
			"	{page:WordSection1;}\r\n" + 
			"-->\r\n" + 
			"</style>\r\n" + 
			"\r\n" + 
			"</head>\r\n" + 
			"\r\n" + 
			"<body lang=EN-US link=\"#0563C1\" vlink=\"#954F72\">\r\n" + 
			"\r\n" + 
			"<div class=WordSection1>\r\n" + 
			"\r\n" + 
			"<table class=MsoTableGrid border=0 cellspacing=0 cellpadding=0\r\n" + 
			" style='border-collapse:collapse;border:none'>\r\n" + 
			" <tr>\r\n" + 
			"  <td width=113 valign=top style='width:84.8pt;border-top:none;border-left:\r\n" + 
			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal align=right style='margin-bottom:0cm;margin-bottom:.0001pt;\r\n" + 
			"  text-align:right'>Vorname:</p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=313 valign=top style='width:234.9pt;border:none;border-bottom:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt'>%s</p>\r\n" + 
			"  </td>\r\n" + 
			" </tr>\r\n" + 
			" <tr>\r\n" + 
			"  <td width=113 valign=top style='width:84.8pt;border-top:none;border-left:\r\n" + 
			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal align=right style='margin-bottom:0cm;margin-bottom:.0001pt;\r\n" + 
			"  text-align:right'>Nachname:</p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=313 valign=top style='width:234.9pt;border:none;border-bottom:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt'>%s</p>\r\n" + 
			"  </td>\r\n" + 
			" </tr>\r\n" + 
			" <tr>\r\n" + 
			"  <td width=113 valign=top style='width:84.8pt;border-top:none;border-left:\r\n" + 
			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal align=right style='margin-bottom:0cm;margin-bottom:.0001pt;\r\n" + 
			"  text-align:right'>Tel1:</p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=313 valign=top style='width:234.9pt;border:none;border-bottom:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt'>%s</p>\r\n" + 
			"  </td>\r\n" + 
			" </tr>\r\n" + 
			" <tr>\r\n" + 
			"  <td width=113 valign=top style='width:84.8pt;border-top:none;border-left:\r\n" + 
			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal align=right style='margin-bottom:0cm;margin-bottom:.0001pt;\r\n" + 
			"  text-align:right'>Tel2:</p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=313 valign=top style='width:234.9pt;border:none;border-bottom:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt'>%s</p>\r\n" + 
			"  </td>\r\n" + 
			" </tr>\r\n" + 
			" <tr>\r\n" + 
			"  <td width=113 valign=top style='width:84.8pt;border-top:none;border-left:\r\n" + 
			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal align=right style='margin-bottom:0cm;margin-bottom:.0001pt;\r\n" + 
			"  text-align:right'>Email:</p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=313 valign=top style='width:234.9pt;border:none;border-bottom:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt'><a\r\n" + 
			"  href=\"mailto:%s\">%s</a></p>\r\n" + 
			"  </td>\r\n" + 
			" </tr>\r\n" + 
			" <tr>\r\n" + 
			"  <td width=113 valign=top style='width:84.8pt;border-top:none;border-left:\r\n" + 
			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal align=right style='margin-bottom:0cm;margin-bottom:.0001pt;\r\n" + 
			"  text-align:right'>Geschlecht:</p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=313 valign=top style='width:234.9pt;border:none;border-bottom:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt'>%s</p>\r\n" + 
			"  </td>\r\n" + 
			" </tr>\r\n" + 
			" <tr>\r\n" + 
			"  <td width=113 valign=top style='width:84.8pt;border-top:none;border-left:\r\n" + 
			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal align=right style='margin-bottom:0cm;margin-bottom:.0001pt;\r\n" + 
			"  text-align:right'>Geburtsdatum:</p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=313 valign=top style='width:234.9pt;border:none;border-bottom:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt'>%s</p>\r\n" + 
			"  </td>\r\n" + 
			" </tr>\r\n" + 
			" <tr>\r\n" + 
			"  <td width=113 valign=top style='width:84.8pt;border-top:none;border-left:\r\n" + 
			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal align=right style='margin-bottom:0cm;margin-bottom:.0001pt;\r\n" + 
			"  text-align:right'>Deutsch:</p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=313 valign=top style='width:234.9pt;border:none;border-bottom:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt'>%s</p>\r\n" + 
			"  </td>\r\n" + 
			" </tr>\r\n" + 
			" <tr>\r\n" + 
			"  <td width=113 valign=top style='width:84.8pt;border-top:none;border-left:\r\n" + 
			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal align=right style='margin-bottom:0cm;margin-bottom:.0001pt;\r\n" + 
			"  text-align:right'>Verfügbar ab:</p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=313 valign=top style='width:234.9pt;border:none;border-bottom:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal style='margin-bottom:0cm;margin-bottom:.0001pt'>%s</p>\r\n" + 
			"  </td>\r\n" + 
			" </tr>\r\n" + 
			" <tr>\r\n" + 
			"  <td width=113 valign=top style='width:84.8pt;border-top:none;border-left:\r\n" + 
			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal align=right style='margin-bottom:0cm;margin-bottom:.0001pt;\r\n" + 
			"  text-align:right'>Fähigkeiten:</p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=313 valign=top style='width:234.9pt;border:none;border-bottom:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  %s" + 
			"  </td>\r\n" + 
			" </tr>\r\n" + 
			" <tr>\r\n" + 
			"  <td width=113 valign=top style='width:84.8pt;border-top:none;border-left:\r\n" + 
			"  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal align=right style='margin-bottom:0cm;margin-bottom:.0001pt;\r\n" + 
			"  text-align:right'>Einsetzbar:</p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=313 valign=top style='width:234.9pt;border:none;border-bottom:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  %s" + 
			"  </td>\r\n" + 
			" </tr>\r\n" + 
			" <tr>\r\n" + 
			"  <td width=113 valign=top style='width:84.8pt;border:none;border-right:solid windowtext 1.0pt;\r\n" + 
			"  padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"  <p class=MsoNormal align=right style='margin-bottom:0cm;margin-bottom:.0001pt;\r\n" + 
			"  text-align:right'>Kommentar:</p>\r\n" + 
			"  </td>\r\n" + 
			"  <td width=313 valign=top style='width:234.9pt;border:none;padding:0cm 5.4pt 0cm 5.4pt'>\r\n" + 
			"%s" + 
			"  </td>\r\n" + 
			" </tr>\r\n" + 
			"</table>\r\n" + 
			"\r\n" + 
			"<p class=MsoNormal>&nbsp;</p>\r\n" + 
			"\r\n" + 
			"</div>\r\n" + 
			"\r\n" + 
			"</body>\r\n" + 
			"\r\n" + 
			"</html>\r\n";

	@Override
	public String apply(CandidateEntry arg) {
		return String.format(TEMPLATE, 
				value(arg.getVorname()), 
				value(arg.getNachname()), 
				value(arg.getTel1()), 
				value(arg.getTel2()), 
				value(arg.getEmail()),value(arg.getEmail()),
				value(arg.isSexMale() ? "Mänlich" : "Weiblich"), 
				value(arg.getBirthDay()), 
				value(arg.getDeutsch()),
				value(arg.getVerfuegbar()), 
				multiValue(arg.getGelernt()), 
				multiValue(arg.getEinsetzbar()),
				multiValue(arg.getKommentar()));
	}

	static String value(String val) {
		return val == null ? "" : val;
	}

	static String value(LanguageKnowledge val) {
		return val == null ? "" : val.toString();
	}
	
	static String value(LocalDate val) {
		return val == null ? "" : val.toString();
	}
	
	static String multiValue(String arg) {
		if (arg == null)
			return "";
		String[] values = arg.split(",");
		StringBuilder sb = new StringBuilder(1024);
		for (String v : values)
			sb.append(String.format(REC_FMT, v));

		return sb.toString();
	}

}
 