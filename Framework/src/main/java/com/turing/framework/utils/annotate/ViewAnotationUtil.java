package com.turing.framework.utils.annotate;

import android.view.View;
import android.view.View.OnClickListener;

import java.lang.reflect.Field;

/**
 * 解析注解的工具类 
 */
public class ViewAnotationUtil {
	
	/**
	 * 给activity中的控件字段进行注解解析
	 * @param currentClass
	 */
	public static void autoInjectAllField(Object currentClass, View currentView) {
		//通过反射得到activity的class
		Class<? extends Object> clazz = currentClass.getClass();
		//得到该activity的所有字段
		Field[] fields = clazz.getDeclaredFields();
		
		if(fields == null) return ;
			
		for(Field field : fields){
			//判断该字段是否标注了注解
			if(field.isAnnotationPresent(InjectView.class)){
				//如果标注了就获取该注解定义的方法中的值
				InjectView injectView = field.getAnnotation(InjectView.class);
				int id = injectView.id();
				boolean isOnClick = injectView.onClick();
				boolean visible = injectView.visible();
				if(id > 0){
					//反射出activity中的所有成员
					field.setAccessible(true);
					//反射findViewById方法
					try {
						View v = currentView.findViewById(id);
						if (v != null) {
							field.set(currentClass, v);
							if (isOnClick) {
								v.setOnClickListener((OnClickListener) currentClass);
							}

							if(!visible){
								v.setVisibility(View.GONE);
							}
						}
					} catch (IllegalAccessException | IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
