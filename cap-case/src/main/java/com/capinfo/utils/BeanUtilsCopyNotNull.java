package com.capinfo.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
/**
 * Bean的工具类
 * @author 
 *
 */
public class BeanUtilsCopyNotNull extends org.apache.commons.beanutils.BeanUtils{
	/**
	 *  对象转化  将非空字段赋值，如果不为空则不对其进行赋值。
	 * @param dest 目标
	 * @param orig 源
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyNotNullProperties(Object dest, Object orig)
			throws IllegalAccessException, InvocationTargetException {
		BeanUtilsBean beanUtils = BeanUtilsBean.getInstance();
		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		// Copy the properties, converting as necessary
		if (orig instanceof DynaBean) {
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass()
					.getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (beanUtils.getPropertyUtils().isReadable(orig, name)
						&& beanUtils.getPropertyUtils().isWriteable(dest, name)) {
					Object value = ((DynaBean) orig).get(name);
					beanUtils.copyProperty(dest, name, value);
				}
			}
		} else if (orig instanceof Map) {
			Iterator entries = ((Map) orig).entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				if (beanUtils.getPropertyUtils().isWriteable(dest, name)) {
					beanUtils.copyProperty(dest, name, entry.getValue());
				}
			}
		} else /* if (orig is a standard JavaBean) */{
			PropertyDescriptor[] origDescriptors = beanUtils.getPropertyUtils()
					.getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if ("class".equals(name)) {
					continue; // No point in trying to set an object's class
				}
				if (beanUtils.getPropertyUtils().isReadable(orig, name)
						&& beanUtils.getPropertyUtils().isWriteable(dest, name)) {
					try {
						Object value = beanUtils.getPropertyUtils().getSimpleProperty(orig, name);
						if(value!=null){
							beanUtils.copyProperty(dest, name, value);
						}
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	
	/**
	 * 动态封装JavaBean 用于前台显示不定值
	 * @param newList
	 * @return
	 */
	public static  Map<String, Object> assignDynValue(List<String> newList) {
		
//		LazyDynaMap propertyMap = new LazyDynaMap();  
//		//前台页面有几个就写几个
//		for (int i = 1; i <= 5; i++) {
//			try {
//				propertyMap.set("level"+i, Class.forName("java.lang.String"));
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  
//		}
		LazyDynaBean bean = new LazyDynaBean();
		for (int i = 1; i <= newList.size(); i++) {
				bean.set("level"+i, newList.get(i-1));
		}
	    return bean.getMap();
	}
	/**
	 * 用于拆分数据 得到数值
	 * @param temp
	 * @return
	 */
	public static List<String> splitData(String temp) {
		String[] ts= temp.split("-");
		List<String> tsList = new ArrayList<String>(Arrays.asList(ts));
		List<String> newList = new ArrayList<String>();
		//必须要大于3才行
		if(tsList.size()>=3){
			String power = tsList.get(tsList.size()-2)+"-"+tsList.get(tsList.size()-1);
			tsList.remove(tsList.size()-1);
			tsList.remove(tsList.size()-1);
			//剩下的开始for循环
			for (String string : tsList) {
				newList.add(string);
			}
			newList.add(power);
		}
		return newList;
	}

	/**
	 * 转换对象中String类型属性特殊字符
	 * @param  ，如：bcode.capinfo.com.modules.order.entity.CapBusiOrder
	 * @param ob 对象实例，如：capBusiOrder
	 * @author hanwangdong
	 * @throws Exception 
	 * @date 2017-3-10
	 */
	public static void unescapeHtml(String objectName,Object ob) throws Exception{
		//使用反射技术完成对象属性的输出  
        Class<?> c = null;  
        c = Class.forName(objectName);  
        if (c != null) {
			
        	java.lang.reflect.Field[] fields = c.getDeclaredFields();
        	for (int i = 0; i < fields.length; i++) {
        		String name = fields[i].getName();
        		if(PropertyUtils.isReadable(ob, name) && PropertyUtils.isWriteable(ob, name)) {
        			if("java.lang.String".equals(PropertyUtils.getPropertyType(ob, name).getName())){
        				BeanUtils.setProperty(ob, name, StringEscapeUtils.unescapeHtml4(BeanUtils.getProperty(ob, name)));
        			}
        		}
        	}
		}
	}
	
	
}
