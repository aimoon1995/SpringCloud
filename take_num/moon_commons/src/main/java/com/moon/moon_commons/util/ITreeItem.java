package com.moon.moon_commons.util;

public interface ITreeItem {

	public String getTreeId();
	public String getTreeParentId();
	public String getTreeName();
	public int getTreeOrder();
	public boolean disabled();
}
