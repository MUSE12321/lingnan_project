package com.lingnan.summer.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
 
/**
 * 系统配置信息
 * @author Administrator
 *
 */
public class SysUtil {
	
	private static Properties properties;
	 
	static {
		InputStream inputStream = SysUtil.class.getClassLoader().getResourceAsStream("sys.properties");
		 
		properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static <T> T getValue(String key) {
		return (T) properties.get(key);
	}
}
