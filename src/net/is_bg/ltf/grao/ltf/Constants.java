package net.is_bg.ltf.grao.ltf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class Constants {

	/***
	 * City, street admregion qualifier mappings!!!
	 * @author lubo
	 *
	 */
	public static enum QUALIFIER_MAPPINGS{
		CITY_MAPPINGS {
			@Override
			protected void initMappings() {
				this.mappings.add(new QulifierNameKind("гр.", ""));
				this.mappings.add(new QulifierNameKind("с. ", ""));
				this.mappings.add(new QulifierNameKind("град", ""));
				this.mappings.add(new QulifierNameKind("село ", ""));
			    this.mappings.add(new QulifierNameKind("гр ", ""));
			    this.mappings.add(new QulifierNameKind("сел.", ""));
			    this.mappings.add(new QulifierNameKind("сел ", ""));
			    this.mappings.add(new QulifierNameKind("г.", ""));
			    
			    this.mappings.add(new QulifierNameKind("жилищен квартал", ""));
			    this.mappings.add(new QulifierNameKind("квартал ", ""));
			    this.mappings.add(new QulifierNameKind("ж-к.", ""));
			    this.mappings.add(new QulifierNameKind("ж.к.", ""));
			    this.mappings.add(new QulifierNameKind("жк.", ""));
			    this.mappings.add(new QulifierNameKind("ж-к ", ""));
			    this.mappings.add(new QulifierNameKind("ж.к ", ""));
			    this.mappings.add(new QulifierNameKind("кв.", ""));
			    this.mappings.add(new QulifierNameKind("жк ", ""));
			    this.mappings.add(new QulifierNameKind("к.", ""));
			    
			    this.mappings.add(new QulifierNameKind("жилищен квартал", ""));
			    this.mappings.add(new QulifierNameKind("квартал ", ""));
			    this.mappings.add(new QulifierNameKind("ж-к.", ""));
			    this.mappings.add(new QulifierNameKind("ж.к.", ""));
			    this.mappings.add(new QulifierNameKind("жк.", ""));
			    this.mappings.add(new QulifierNameKind("ж-к ", ""));
			    this.mappings.add(new QulifierNameKind("ж.к ", ""));
			    this.mappings.add(new QulifierNameKind("кв.", ""));
			    this.mappings.add(new QulifierNameKind("жк ", ""));
			    this.mappings.add(new QulifierNameKind("к.", ""));
			    
			    this.mappings.add(new QulifierNameKind("ж. квартал", ""));
			    this.mappings.add(new QulifierNameKind("жил. кв.", ""));
			    this.mappings.add(new QulifierNameKind("жил.кв.", ""));
			    this.mappings.add(new QulifierNameKind("ж. кв.", ""));
			    this.mappings.add(new QulifierNameKind("ж.кв.", ""));
			    
			    this.mappings.add(new QulifierNameKind("местност ", ""));
			    this.mappings.add(new QulifierNameKind("мест.", ""));
			    this.mappings.add(new QulifierNameKind("мест ", ""));
			    this.mappings.add(new QulifierNameKind("м. ", ""));
			    this.mappings.add(new QulifierNameKind("м.", ""));
			    
			    for(QulifierNameKind e: mappings){
			    	qualifierKindMap.put(e.name, e);
			    }
			}
		},
		ADMREGION_MAPPINGS {
			@Override
			protected void initMappings() {
			    this.mappings.add(new QulifierNameKind("район ", ""));
			    this.mappings.add(new QulifierNameKind("р-н ", ""));
			    this.mappings.add(new QulifierNameKind("рн. ", ""));
			    this.mappings.add(new QulifierNameKind("рн ", ""));
			    
			    for(QulifierNameKind e: mappings){
			    	qualifierKindMap.put(e.name, e);
			    }
			}
		},
		STREET_MAPPINGS {
			@Override
			protected void initMappings() {
				this.mappings.add(new QulifierNameKind("улица", "1"));
			    this.mappings.add(new QulifierNameKind("ул.", "1"));
			    this.mappings.add(new QulifierNameKind("ул ", "1"));
			    this.mappings.add(new QulifierNameKind("у.л.", "1"));
			    this.mappings.add(new QulifierNameKind("у-л.", "1"));
			    this.mappings.add(new QulifierNameKind("у-л", "1"));
			    this.mappings.add(new QulifierNameKind("ули.", "1"));
			    this.mappings.add(new QulifierNameKind("ули ", "1"));
			    
			    this.mappings.add(new QulifierNameKind("булевард ", "2"));
			    this.mappings.add(new QulifierNameKind("бул.", "2"));
				this.mappings.add(new QulifierNameKind("бул ", "2"));
			    this.mappings.add(new QulifierNameKind("б-л.", "2"));
			    this.mappings.add(new QulifierNameKind("б-л ", "2"));
			    this.mappings.add(new QulifierNameKind("бл.", "2"));
			    this.mappings.add(new QulifierNameKind("бл ", "2"));
			    this.mappings.add(new QulifierNameKind("б.", "2"));
			    this.mappings.add(new QulifierNameKind("б ", "2"));
			    
			    this.mappings.add(new QulifierNameKind("площад", "3"));
			    this.mappings.add(new QulifierNameKind("п.л.", "3"));
			    this.mappings.add(new QulifierNameKind("п-л,", "3"));
			    this.mappings.add(new QulifierNameKind("п-л", "3"));
			    this.mappings.add(new QulifierNameKind("пл.", "3"));
			    this.mappings.add(new QulifierNameKind("пл ", "3"));
			    
			    this.mappings.add(new QulifierNameKind("жилищен квартал", ""));
			    this.mappings.add(new QulifierNameKind("квартал ", ""));
			    this.mappings.add(new QulifierNameKind("ж-к.", ""));
			    this.mappings.add(new QulifierNameKind("ж.к.", ""));
			    this.mappings.add(new QulifierNameKind("жк.", ""));
			    this.mappings.add(new QulifierNameKind("ж-к ", ""));
			    this.mappings.add(new QulifierNameKind("ж.к ", ""));
			    this.mappings.add(new QulifierNameKind("кв.", ""));
			    this.mappings.add(new QulifierNameKind("жк ", ""));
			    this.mappings.add(new QulifierNameKind("к.", ""));
			    
			    this.mappings.add(new QulifierNameKind("ж. квартал", ""));
			    this.mappings.add(new QulifierNameKind("жил. кв.", ""));
			    this.mappings.add(new QulifierNameKind("жил.кв.", ""));
			    this.mappings.add(new QulifierNameKind("ж. кв.", ""));
			    this.mappings.add(new QulifierNameKind("ж.кв.", ""));
			    
			    this.mappings.add(new QulifierNameKind("местност ", ""));
			    this.mappings.add(new QulifierNameKind("мест.", ""));
			    this.mappings.add(new QulifierNameKind("мест ", ""));
			    this.mappings.add(new QulifierNameKind("м. ", ""));
			    this.mappings.add(new QulifierNameKind("м.", ""));
			    
			    for(QulifierNameKind e: mappings){
			    	qualifierKindMap.put(e.name, e);
			    }
			}
		};
		
		protected List<QulifierNameKind> mappings = new ArrayList<QulifierNameKind>();
		protected Map<String, QulifierNameKind> qualifierKindMap = new HashMap<String, QulifierNameKind>();
		
		QUALIFIER_MAPPINGS(){
			initMappings();
		}
		
		protected abstract void initMappings();
		
		/**
		 * Returns a copy list of the qualifiers as lower case!!!
		 * @return
		 */
		public List<String> getMappingsListLowerCase(){
			List<String>  copyList = new ArrayList<String>();
			for(QulifierNameKind s: mappings){
				copyList.add(s.name.toLowerCase());
			}
			return copyList;
		}
		
		
		public String getQualifierKind(String qualifier){
			if(qualifier == null) return null;
			QulifierNameKind k =  qualifierKindMap.get(qualifier);
			if(k == null) return null;
			return k.kind;
		}
		
		/**
		 * Returns a copy list of the qualifiers as upper case!!!
		 * @return
		 */
		public List<String> getMappingsListUpperCase(){
			List<String>  copyList = new ArrayList<String>();
			for(QulifierNameKind s: mappings){
				copyList.add(s.name.toUpperCase());
			}
			return copyList;
		}
		
		/**
		 * Returns a copy set of the qualifiers as lower case!!!
		 * @return
		 */
		public Set<String> getMappingsSetLowerCase(){
			Set<String>  copyList = new TreeSet<String>();
			for(QulifierNameKind s: mappings){
				copyList.add(s.name.toLowerCase());
			}
			return copyList;
		}
		
		/**
		 * Returns a copy set of the qualifiers as upper case!!!
		 * @return
		 */
		public Set<String> getMappingsSetUpperCase(){
			Set<String>  copyList = new TreeSet<String>();
			for(QulifierNameKind s: mappings){
				copyList.add(s.name.toUpperCase());
			}
			return copyList;
		}
	}
	
	
	public enum MATCHING_LEVEL {
		EQUALS{
			@Override
			String addPercentSign(String s) {
				if(s==null) return s;
				return s;
			}
		},
		STARTS{
			@Override
			String addPercentSign(String s) {
				if(s==null) return s;
				return s + "%";
			}
		},
		ENDS{
			@Override
			String addPercentSign(String s) {
				if(s==null) return s;
				return  "%" + s; 
			}
		},
		CONTAINS{
			@Override
			String addPercentSign(String s) {
				if(s==null) return s;
				return "%" + s + "%";
			}
		},
		NOT_FOUND{
			@Override
			String addPercentSign(String s) {
				return null;
			}
		};
		
		 abstract String addPercentSign(String s);
	}
	
	
	
}
