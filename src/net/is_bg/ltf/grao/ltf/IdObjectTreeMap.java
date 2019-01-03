package net.is_bg.ltf.grao.ltf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import grao.integration.ltf.address.structure.INameIdParent;


/**
 * A tree - like structure based on Map where the node children are elements of map!
 * The keys in map are the ids of map elements!
 * @author lubo
 * @param <T>
 */
class IdObjectTreeMap<T extends INameIdParent> {
	private IdObjectTreeMap<? extends INameIdParent> parent;
	private T data;
	private Map<Long, IdObjectTreeMap<? extends INameIdParent>> map = new ConcurrentHashMap<Long, IdObjectTreeMap<? extends INameIdParent>>();
	
	/**a hashMap that is used as index to search by object name! The key of hashMap is the object name hashCode & the value is the object id*/
	private Map<Integer, Long> nameIndex = new HashMap<Integer, Long>();   
	
	/**A map that contains a list with object ids that have duplicate names */
	private Map<Integer, List<Long>> duplicateNamesObjectIds =  new HashMap<Integer, List<Long>>();  
	
	/***
	 * Wraps data into tree node!!!
	 * @param data
	 */
	public IdObjectTreeMap(T data){
		this.data = data;
	}
	
	/***Returns the values in current node map*/
	/*public Collection<IdObjectTreeMap<?extends INameIdParent>> getChildren(){
		return map.values();
	}*/
	
	/**Returns the data in current node*/
	public T getData() {
		return data;
	}
	
	/***Returns the parent node of this node!*/
	/*public IdObjectTreeMap<? extends INameIdParent> getParent(){
		return parent;
	}*/
	
	/**
	 * Add element in current node map!
	 * @param key
	 * @param child
	 */
	private void addChild(IdObjectTreeMap<? extends INameIdParent> child){
		child.parent = this;
		int hash = child.data.getName()==null  ? 0 : child.data.getName().hashCode();
		
		if(!nameIndex.containsKey(hash)){ 
			nameIndex.put(hash, child.data.getId());
		}else{
			List<Long> idList = duplicateNamesObjectIds.get(hash);
			if(idList == null){ idList = new ArrayList<Long>();  duplicateNamesObjectIds.put(hash, idList);  idList.add(nameIndex.get(hash)); }
			idList.add(child.data.getId());
		}
		map.put(child.data.getId(), child);
	}
	
	
	/***
	 * Converts a list of objects to tree map node where elements are children nodes!!
	 */
	public static <T extends INameIdParent>  IdObjectTreeMap<T> listToTreeMap(List<T> l){
		IdObjectTreeMap<T> m = new IdObjectTreeMap<T>(null);
		for(T p: l){
			m.addChild(new IdObjectTreeMap<T>(p));
		}
		return m;
	}
	
	/***
	 * Returns element from map based on element key!
	 * @param key
	 * @return
	 */
	public IdObjectTreeMap<?extends INameIdParent> getElementdByKey(Long key){
		return map.get(key);
	}
	
	/**
	 * Try to find element by searching the element based on name hashCode!!!
	 * For example if we pass city name, the  city element from name index Map is returned by the city name hashCode!!
	 * If null is passed as name the map is checked for zero hashcode!!!
	 * @param name
	 * @return
	 */
	public IdObjectTreeMap<?extends INameIdParent> getElementByName(String name){
		Long elementId = nameIndex.get(name == null ? 0: name.hashCode());
		return elementId == null ? null : map.get(elementId);
	}
	
	/**Return a list with the ids of duplicate object names or null if name  is not duplicated!
	 * For example if we have 2 cities with the same name the List will contain the ids of both cities!*/
	public List<Long> getDuplicateNameIds(String name){
		return duplicateNamesObjectIds.get(name == null ? 0 : name.hashCode());
	}
	
	/**Returns the names of the duplicate objects*/
	public List<String> getDuplicateNames(){
		List<String> names = new ArrayList<String>();
		for(Integer hash : duplicateNamesObjectIds.keySet()){
			names.add(map.get(duplicateNamesObjectIds.get(hash).get(0)).data.getName());
		}
		return names;
	}
	
	/**Returns the names & count of duplicate objects */
	public Map<String, Integer> getDuplicateNamesCount(){
		Map<String, Integer> m = new HashMap<String, Integer>();
		for(Integer hash : duplicateNamesObjectIds.keySet()){
			m.put(map.get(duplicateNamesObjectIds.get(hash).get(0)).data.getName(),  duplicateNamesObjectIds.get(hash).size());
		}
		return m;
	}
}

