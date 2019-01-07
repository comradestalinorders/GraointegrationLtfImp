

package net.is_bg.ltf.grao.ltf;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import grao.integration.ltf.address.structure.INameIdParent;
import net.is_bg.ltf.db.common.SelectSqlStatement;

abstract class IdNameSelect<T extends INameIdParent> extends SelectSqlStatement{
	
	protected static String countrySelect = " select  country_id id, upper(name) n from country order by n";
	protected static String provinceSelect = " select province_id id , upper(name) n from province ";
	protected static String municipalitySelect = " select municipality_id id, upper(name) n, province_id from municipality  ";
	protected static String citySelect = " select city_id id, upper(name) n, municipality_id  from city  ";
	protected static String admRegionSelect = " select admregion_id id, upper(name) n, city_id from admregion ";
	protected static String cityUnknowSelect = " select city_id id, upper(name) n, municipality_id  from city where city_id =  ? ";
	protected static String countryBGSelect = " select country_id id, \"name\" n from country where upper(code) = 'BG' or upper(shortname) = 'BG' ";
	protected static String streetSelectById = " select street_id id, \"name\"  n, city_id p from street where street_id = ?  ";
/*	//try to find city by grao name 
	protected static String selectCityByGraoName = 
				    " select c.city_id  cityid, \n"+
					" upper(c.name) name, c.kind_city, \n"+
					" gc.city_name, gc.city_code, gc.province_code, gc.empty, c.ekatte \n"+
					" from  " + GRAOCITY_TABLE +" gc\n"+
					" inner join city c on c.ekatte = gc.city_code\n"+
					" where 1=1\n"+
					" and upper(gc.city_name) = ? ";
	*/
	
	/*protected static String selectCityByCityGraoNameAndMunicipalityName = " select c.city_id cityid from " + GRAOCITY_TABLE +" gc\n"+
			" join municipality m on gc.municipality_ekatte = m.ekatte\n"+
			" join city c on gc.ekatte = c.ekatte\n"+
			" where name = ?  \n"+
			" and ( upper(m.name) = ? \n"+
			" or upper(m.fullname) = ? );\n";
	
	protected static String allGraoCitiesSelect = " select  province_code, municipality_code,   "
			+ " ekatte, region_code,  upper(\"name\") city_name, kind, post_code, state_code, name_no_qualifier, municipality_ekatte  "+
		 " from " + GRAOCITY_TABLE ;
	
	private static final String selectGraocitiesNotInDatabse = " select \"name\" n from " + GRAOCITY_TABLE +" gc \n " +
                                                               " left join city c on gc.ekatte = c.ekatte where c.city_id is null \n";
	
	//select unique grao city names from database 
	private static final String uniqueGraoCityName = " select distinct name n from " +  GRAOCITY_TABLE;

	//select duplicate grao city names
	private static final String duplicateGraoCityNames = " select name n from " +  GRAOCITY_TABLE  +" group by name having count(*) > 1 ";*/
	
	
	abstract List<T> getReuslt();
	
	
	static IdNameSelect<INameIdParent> getProvinceSelect(){
		return new IdNameSelect<INameIdParent>() {
			List<INameIdParent> result = new ArrayList<INameIdParent>();
			
			@Override
			List<INameIdParent> getReuslt() {
				return result;
			}

			@Override
			protected String getSqlString() {
				return provinceSelect;
			}
			
			@Override
			protected void retrieveResult(ResultSet rs) throws SQLException {
				while (rs.next()) {
					NameIdParent p = new NameIdParent();
					p.id = rs.getLong("id");
					p.name = rs.getString("n");
					result.add(p);
				}
			}
		};
		
	}
	
	
	static IdNameSelect<INameIdParent> getBulgarianSelect(){
		return new IdNameSelect<INameIdParent>() {
			List<INameIdParent> result = new ArrayList<INameIdParent>();
			
			@Override
			List<INameIdParent> getReuslt() {
				return result;
			}

			@Override
			protected String getSqlString() {
				return countryBGSelect;
			}
			
			@Override
			protected void retrieveResult(ResultSet rs) throws SQLException {
				while (rs.next()) {
					NameIdParent p = new NameIdParent();
					p.id = rs.getLong("id");
					p.name = rs.getString("n");
					result.add(p);
				}
			}
		};
		
	}

	static IdNameSelect<INameIdParent> getMunicipalitySelect(){
		return new IdNameSelect<INameIdParent>() {
			List<INameIdParent> result = new ArrayList<INameIdParent>();
			@Override
			List<INameIdParent> getReuslt() {
				return result;
			}

			@Override
			protected String getSqlString() {
				return municipalitySelect;
			}
			
			@Override
			protected void retrieveResult(ResultSet rs) throws SQLException {
				while (rs.next()) {
					NameIdParent p = new NameIdParent();
					p.id = rs.getLong("id");
					p.name = rs.getString("n");
					p.parentId = rs.getLong("province_id");
					result.add(p);
				}
			}
		};
	}
	
	
	
	static IdNameSelect<INameIdParent> getCityStreetSelectByCityStreetCode(final String cityCode, final String streetCode){
		return new IdNameSelect<INameIdParent>() {
			List<INameIdParent> result = new ArrayList<INameIdParent>();
			@Override
			List<INameIdParent> getReuslt() {
				return result;
			}

			@Override
			protected String getSqlString() {
				return " select street_id id, city_id city_id, street.name n from street \r\n" + 
						" where city_id = ? \r\n" + 
						" and ekkpa  = ? " ;
			}
			
			@Override
			protected void setParameters(PreparedStatement prStmt) throws SQLException {
				bindVarData.setLong(Long.valueOf(cityCode));
				bindVarData.setString(streetCode);
				bindVarData.setParameters(prStmt);
			}
			
			@Override
			protected void retrieveResult(ResultSet rs) throws SQLException {
				while (rs.next()) {
					NameIdParent p = new NameIdParent();
					p.id = rs.getLong("id");
					p.name = rs.getString("n");
					p.parentId = rs.getLong("city_id");
					result.add(p);
				}
			}
		};
	}
	
	
	
	
	static IdNameSelect<AdminRegion> getAdmregionSelect(){
		return new IdNameSelect<AdminRegion>() {
			List<AdminRegion> result = new ArrayList<AdminRegion>();
			
			@Override
			protected String getSqlString() {
				return admRegionSelect;
			}
			
			@Override
			List<AdminRegion> getReuslt() {
				return result;
			}
			
			@Override
			protected void retrieveResult(ResultSet rs) throws SQLException {
				while (rs.next()) {
					AdminRegion p = new AdminRegion();
					p.id = rs.getLong("id");
					p.name = rs.getString("n");
					p.parentId = rs.getLong("city_id");
					result.add(p);
				}
			}
		};
	}
	
	
	/**Selects unknown city*/
	static IdNameSelect<INameIdParent>  getCityUnknown(){
		return new IdNameSelect<INameIdParent>() {
			List<INameIdParent> result = new ArrayList<INameIdParent>();
			@Override
			List<INameIdParent> getReuslt() {
				return result;
			}

			@Override
			protected String getSqlString() {
				return cityUnknowSelect;
			}
			@Override
			protected void setParameters(PreparedStatement prStmt) throws SQLException {
				bindVarData.setLong(Long.valueOf(Bundles.cityUnknownId.getString("cityunknownId")));
				bindVarData.setParameters(prStmt);
			}
			
			@Override
			protected void retrieveResult(ResultSet rs) throws SQLException {
				while (rs.next()) {
					NameIdParent p = new NameIdParent();
					p.id = rs.getLong("id");
					p.name = rs.getString("n");
					p.parentId = rs.getLong("municipality_id");
					result.add(p);
				}
			}
		};
	}
	
	
	/***
	 * Select cities in current database.
	 * @return
	 */
	static IdNameSelect<INameIdParent> getCitySelect(){
		return new IdNameSelect<INameIdParent>() {
			
			List<INameIdParent> result = new ArrayList<INameIdParent>();
			
			@Override
			protected String getSqlString() {
				return citySelect;
			}
			
			@Override
			List<INameIdParent> getReuslt() {
				return result;
			}
			
			@Override
			protected void retrieveResult(ResultSet rs) throws SQLException {
				while (rs.next()) {
					NameIdParent p = new NameIdParent();
					p.id = rs.getLong("id");
					p.name = rs.getString("n");
					p.parentId = rs.getLong("municipality_id");
					result.add(p);
				}
			}
		};
	}
	
	
	static IdNameSelect<INameIdParent> getStreetSelect(final long streetId){
		return new IdNameSelect<INameIdParent>() {
			
			List<INameIdParent> result = new ArrayList<INameIdParent>();
			
			@Override
			protected String getSqlString() {
				return streetSelectById;
			}
			
			@Override
			List<INameIdParent> getReuslt() {
				return result;
			}
			
			@Override
			protected void setParameters(PreparedStatement prStmt) throws SQLException {
				bindVarData.setLong(streetId);
				bindVarData.setParameters(prStmt);
			}
			
			@Override
			protected void retrieveResult(ResultSet rs) throws SQLException {
				while (rs.next()) {
					NameIdParent p = new NameIdParent();
					p.id = rs.getLong("id");
					p.name = rs.getString("n");
					p.parentId = rs.getLong("p");
					result.add(p);
				}
			}
		};
	}

	/***
	 * Return select statement for street by street name & cityId
	 * @param cityId
	 * @param streetName
	 * @return
	 */
	static IdNameSelect<INameIdParent> getStreetSelect(final long cityId, final String streetExpression){
		return new IdNameSelect<INameIdParent>() {
			List<INameIdParent> result = new ArrayList<INameIdParent>();
			@Override
			List<INameIdParent> getReuslt() {
				return result;
			}

			@Override
			protected String getSqlString() {
				return "  select street_id id, upper(name) n  from street where city_id = ? and upp er(name) like ?  order by name";
			}
			
			@Override
			protected void setParameters(PreparedStatement prStmt) throws SQLException {
				bindVarData.setLong(cityId);
				bindVarData.setString(streetExpression);
			}
			
			protected void retrieveResult(ResultSet rs) throws SQLException {
				while (rs.next()) {
					NameIdParent p = new NameIdParent();
					p.id = rs.getLong("id");
					p.name = rs.getString("n");
					p.parentId = cityId;
					result.add(p);
				}
			}
		};
	}
	
	
	/**Selects all countries in current database */
	static IdNameSelect<INameIdParent> getCountriesSelect() {
		return new IdNameSelect<INameIdParent>() {
			
			List<INameIdParent> result = new ArrayList<INameIdParent>();
			
			@Override
			protected String getSqlString() {
				return countrySelect;
			}
			
			@Override
			List<INameIdParent> getReuslt() {
				return result;
			}
			
			@Override
			protected void retrieveResult(ResultSet rs) throws SQLException {
				while (rs.next()) {
					NameIdParent p = new NameIdParent();
					p.id = rs.getLong("id");
					p.name = rs.getString("n");
					result.add(p);
				}
			}
		};
	}
	
/*	*//***
	 * Returns a list with  founded city ids by grao cityname!!!
	 * @param graoName
	 * @return
	 *//*
	static SelectSqlStatementGeneric<Long> getCityIdByCityGraoNameSelect(final String graoName){
			return new SelectSqlStatementGeneric<Long>() {
			
			List<Long> result = new ArrayList<Long>();
			
			@Override
			protected String getSqlString() {
				return selectCityByGraoName;
			}
			
			
			@Override
			protected void setParameters(PreparedStatement prStmt) throws SQLException {
				bindVarData.setString(graoName);
				bindVarData.setParameters(prStmt);
			}
			
			@Override
			protected void retrieveResult(ResultSet rs) throws SQLException {
				while (rs.next()) {
					result.add(rs.getLong("cityid"));
				}
			}
		};	
	}
	*/
	
	
}
