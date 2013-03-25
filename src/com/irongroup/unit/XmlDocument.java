package com.irongroup.unit;

import java.io.InputStream;
import java.util.Map;


public interface XmlDocument {
	

	/**
	 * 
	 * @param fileName：文件路径及名称
	 */
	public void createXml(String fileName);

	/**
	 * 
	 * @param fileName:文件路径及名称
	 */
	public void parserXml(String fileName);
	/**
	 * 
	 * @param InputStream:
	 */
	public Map<String, String> parserXml(InputStream is);
}

