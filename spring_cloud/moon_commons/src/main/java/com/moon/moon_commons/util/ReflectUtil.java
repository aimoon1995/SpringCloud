package com.moon.moon_commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 
 * @author 浩
 *
 */
public class ReflectUtil {
	private static Logger log = LoggerFactory.getLogger(ReflectUtil.class);

    public static Object getPropertyValue(Object object, String propertyName) {

        try {
            String getMethod = StringUtil.makeGetMethod(propertyName);
            try {
                if ("boolean".equals(object.getClass().getDeclaredField(propertyName).getType().getName())) {
                    getMethod = "is" + StringUtil.toUpperInitial(propertyName);
                }
            } catch (Exception ex) {
            	log.debug("Maybe java.lang.NoSuchFieldException", ex);
            }
            return object.getClass().getMethod(getMethod, new Class[0]).invoke(object, new Object[0]);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getProperty(Object object, String propertyName) {
        String getMethod = StringUtil.makeGetMethod(propertyName);
        try {
        	Object data = object.getClass().getMethod(getMethod, new Class[0]).invoke(object, new Object[0]);
            if (data == null)
            {
            	return "";
            }
            if (data instanceof Date)
            {
            	return DateUtil.format((Date)data, DateUtil.DATE_FORMAT_DATETIME_S_HMS);
            }
        	return String.valueOf(data);
        } catch (Exception ex) {
        	log.error("getProperty error", ex);
            return "";
        }
    }

    public static Object getObjectByListMemberClassName(String className, Object[] objects) {
        if (objects == null || objects.length == 0) {
            return null;
        }
        for (Object object : objects) {
            if (object instanceof List) {
                List list = (List) object;
                if (list.size() > 0) {
                    if (list.get(0).getClass().getName().endsWith("." + className)) {
                        return object;
                    }
                }
            }
        }
        return null;
    }

    public static Object getObjectByClassName(String className, Object[] objects) {
        if (objects == null || objects.length == 0) {
            return null;
        }
        for (Object object : objects) {
            if (object.getClass().getName().endsWith("." + className)) {
                return object;
            }
        }
        return null;
    }

    /**
     *
     * @param target Object
     * @param fname String
     * @param ftype Class
     * @param fvalue Object
     */
    public static void setFieldValue(Object target, String fname, Class<?> ftype,
            Object fvalue) {
        if (target == null
                || fname == null
                || "".equals(fname)
                || (fvalue != null && !ftype
                        .isAssignableFrom(fvalue.getClass()))) {
            return;
        }
        Class<?> clazz = target.getClass();
        try {
            Method method = clazz.getDeclaredMethod("set"
                    + Character.toUpperCase(fname.charAt(0))
                    + fname.substring(1), ftype);
            if (!Modifier.isPublic(method.getModifiers())) {
                method.setAccessible(true);
            }
            method.invoke(target, fvalue);

        } catch (Throwable me) {

            try {
                Field field = clazz.getDeclaredField(fname);
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                }
                field.set(target, fvalue);
            } catch (Throwable fe) {
            	log.error("setFieldValue error", fe);
            }
        }
    }

    /**
	 * getPropertyByMethod
	 * @param object Object
	 * @param propertyName String
	 * @return String
	 */
    public static String getPropertyByMethod(Object object, String propertyName) {
        String getMethod = StringUtil.makeGetMethod(propertyName);
        Object obj = null;
		String strRet = "";
		try {
			obj = object.getClass().getMethod(getMethod, new Class[0]).invoke(object, new Object[0]);
			if(obj == null){
				return strRet;
			} else if (obj.getClass().equals(String.class)) {
				strRet = StringUtil.getOrElse((String) obj);
	        } else if (obj.getClass().equals(Date.class)) {
	        	strRet = DateUtil.format((Date) obj, DateUtil.DATE_FORMAT_DATETIME_S_HMS);
	        } else {
	        	strRet = String.valueOf(obj);
	        }
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException", e);
		} catch (InvocationTargetException e) {
			log.error("InvocationTargetException", e);
		} catch (NoSuchMethodException e) {
			log.error("NoSuchMethodException", e);
		} catch (IllegalArgumentException e) {
			log.error("IllegalArgumentException", e);
		} catch (SecurityException e) {
			log.error("SecurityException", e);
		}
		return strRet;
    }
    
    public static Field getDeclaredField(Class<?> clazz, String fieldName){  
    	 Field field = null ;  
         for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
             try {  
                 field = clazz.getDeclaredField(fieldName) ;  
                 return field ;  
             } catch (Exception e) {  
            	 log.debug("getDeclaredField Exception", e);
             }   
         }  
         return null;  
    }
    
    public static List<Field> getDeclaredFields(Class clazz) {
        List<Field> fields = new ArrayList<Field>();

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field[] fs = clazz.getDeclaredFields();
                for (Field f : fs) {
                    fields.add(f);
                }
            } catch (Exception e) {
           	 	log.debug("getDeclaredFields Exception", e);
            }
        }

        return fields;
    }
    
    /**
     * @param clazz
     * @return
     */
    public static Map<String,Field> getDeclaredFieldMap(Class clazz) {
        Map<String,Field> map = new HashMap<String,Field> ();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field[] fs = clazz.getDeclaredFields();
                for (Field f : fs) {
                    // 父类的属性和子类的属性重名的，忽略掉父类的属性
                    if(!map.containsKey(f.getName())){
                        map.put(f.getName(), f);
                    }
                }
            } catch (Exception e) {
            	log.debug("getDeclaredFieldMap Exception", e);
            }
        }
        return map;
    }
}
