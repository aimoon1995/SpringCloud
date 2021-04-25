package com.moon.moon_commons.util;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 资源的json显示对象
 */
public class TreeItem implements Comparable<TreeItem>{
	
	public static class TreeItemState{
		public Boolean opened;
		public Boolean disabled;
		public Boolean  selected;  
	}
	
	private String kbn;
	/**
	 * 资源id
	 */
	private String id;
	/**
	 * 资源id
	 */
	private String key;
	/**
	 * 父资源id
	 */
	private String parentId;
	/**
	 * 显示文本
	 */
	private String text;
	/**
	 * 显示文本
	 */
	private String title;
	
	private String icon;
	
	/**
	 * 子资源数
	 */
	private int child;
	
	private Object data;
	
	/**
	 * 节点闭合状态
	 */
	private TreeItemState state = new TreeItemState();
	
	private int order;
	/**
	 * 子资源列表
	 */
	private List<TreeItem> children = new ArrayList<TreeItem>();

	/**
	 * 子资源列表
	 */
	private List<TreeItem> item = new ArrayList<TreeItem>();
	
	public List<TreeItem> getItem() {
		return item;
	}
	public void setItem(List<TreeItem> item) {
		this.item = item;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public TreeItemState getState() {
		return state;
	}
	public void setState(TreeItemState state) {
		this.state = state;
	}
	public List<TreeItem> getChildren() {
		return children;
	}
	public void setChildren(List<TreeItem> children) {
		this.children = children;
	}
	public int getChild() {
		return child;
	}
	public void setChild(int child) {
		this.child = child;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
		setKey(id);
	}
	public String getText() {
		return text;
	}
	public String getLabel() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
		setTitle(text);
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	@Override
	public int compareTo(TreeItem other) {
		if(other != null){
			return order - other.order;
		}
		return 1;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getKbn() {
		return kbn;
	}
	public void setKbn(String kbn) {
		this.kbn = kbn;
	}
	@JsonIgnore
	public boolean disabled(boolean all, String... ids){
		List<String> idlist = new ArrayList<String>();
		idlist.addAll(Arrays.asList(ids));
		
		boolean result = false;
		if(idlist.size() > 0){
			if(StringUtil.isNotEmpty(id) 
					&& idlist.contains(id)){
				getState().disabled = true;
				List<TreeItem> children = getChildren();
				if(all){
					for(TreeItem item:children){
						item.disabled(all, item.id);
					}
				}
				idlist.remove(id);
				result = idlist.size() == 0;
			}else{
				List<TreeItem> children = getChildren();
				for(TreeItem item:children){
					result = item.disabled(all, ids);
					if(result){
						break;
					}
				}
				
			}
		}
		return result;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}