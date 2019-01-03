package net.is_bg.ltf.grao.ltf;

import java.util.List;
import grao.integration.ltf.address.structure.INameKindId;
import grao.integration.structure.impl.GraoUtils;

class Utils {

	
	
	
	/***
	 * Replaces a qualifier for city or street & returns the new String !!!
	 * @param s
	 * @param quantifiers
	 * @return
	 */
	static INameKindId removeQualifier(String s, List<String> quantifiers, String defaultKind){
		NameIdKind newVal = new NameIdKind();
		if(s == null) return null;
		newVal.kind = defaultKind;
		newVal.name = s;
		
		for(String q: quantifiers){
			if(s.startsWith(q)){
				String replaced = s.replaceFirst(q, "");
				System.out.println(s + " converted to   " + replaced);
				newVal.name = replaced;
				return newVal;
			}
		}
		System.out.println("No conversion for " + s);
		return newVal;
	}

	/***
	 * Extracts the admin region name from String!
	 * @param s
	 * @return
	 */
	static String extractAdmRegionName(String s,  List<String> admregionQulifiers){
		String region = null;
		if(s == null) return s;
		s = GraoUtils.toUpperCaseSafe(s);
		for(String q:admregionQulifiers){
			int admregIndeg = s.indexOf(q);
			if(admregIndeg <=-1) continue;
			region = s.substring(admregIndeg + q.length());
		}
		return region;
	}
	
	/***
	 * Extract admin region code from String!
	 * @param s
	 * @return
	 */
	static String extractAdmRegionCode(String s){
		String code = null;
		if(s == null) return s;
		s = GraoUtils.toUpperCaseSafe(s);
		for(int i =0; i < s.length(); i++){
			if(Character.isDigit(s.charAt(i))){
				code = "" + s.charAt(i);
				int j = i+1;
				while(j < s.length() &&  Character.isDigit(s.charAt(j))){ code+=s.charAt(j++); };
				break;
			}
		}
		return code;
	}
	
	/***
	 * Removes the white spaces between the qaulifier & name
	 * @return
	 */
	static String trimWhiteSpacesBetweenNameQuailifier(String s, List<String> qualifiers){
		if(s ==null) return s;
		s = GraoUtils.toUpperCaseSafe(s);

		for(String q: qualifiers){
			if(s.startsWith(q)){
				String replaced = s.replaceFirst(q, "");
				GraoUtils.safeTrim(replaced);
				return q.trim() + replaced;
			}
		}
		return s;
	}
	
	
}
