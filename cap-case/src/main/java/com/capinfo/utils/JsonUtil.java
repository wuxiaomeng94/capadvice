package com.capinfo.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 *json数据处理工具
 */
public class JsonUtil {
	/**
	 * 将对象转换成字符串
	 * @param o
	 * @return
	 */
	public static String objectToJsonStr(Object o){
		if(o==null){
			return "";
		}
		Gson gson = new Gson(); 
		String str = gson.toJson(o);
		return str;
	}
	
	/**
	 * json串转成指定对象(Map,List)
	 * @param <T>
	 * @param jsonStr "{key:'',value:''.....}"
	 * @return
	 */
	public  static <T> T jsonStrToObject(String jsonStr){
		if(jsonStr==null){
			return null;
		}
		T t=null;
		Gson gson = new Gson();
		t= gson.fromJson(jsonStr, new TypeToken<T>(){}.getType());
		return t;
	}
	
	
	
	
	
	/**
	 * son串转成指定对象(Po)
	 * @param str
	 * @param cls
	 * @return
	 */
	public static <T>T jsonStrToPo(String str,Class cls){
		T obj = null;
		try {
			Gson gson = new Gson();
			obj =(T)gson.fromJson(str, cls);
			return obj;
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将json字符串转换成list
	 * @param 
	 * <p>
	 * jsonStr: {password:'21',userName:'21'},{password:'2121',userName:'221'}
	 * </p>
	 * @return
	 */
	public static List jsonStrToList(String jsonStr){
		Gson gson = new Gson();
		String str = "["+jsonStr+"]";
		List list =gson.fromJson(str, List.class);
		return list;
	}
	
	
	
	
	
	
	public static <T> T  jsonStrToPo(String jsonStr,Object o){
		Gson gson = new Gson();
		T t=null;
		t=(T) gson.fromJson(jsonStr, Object.class);
		return t;
	}
	
}
