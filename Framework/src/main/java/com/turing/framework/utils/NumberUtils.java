package com.turing.framework.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import android.R.integer;
import android.text.TextUtils;
/**
 * 数字工作类
 */
public class NumberUtils {

	/**
	 * 保留2位小数
	 * @param object
	 * @return
	 */
	public static String objectSava2(Object object){
		return new DecimalFormat("#0.00").format(object);
	}


    //科学记数
	public static String format2(double value) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setGroupingSize(3);
		String valueStr = df.format(value);
		return valueStr;
	}
	
	public static String format2Older(Object value) {
		if(value == null){
			return "";
		}
		
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(Double.valueOf(String.valueOf(value)));
	}
	
	public static String format3(double value) {
		DecimalFormat df = new DecimalFormat("#0.000");
		return df.format(value);
	}
	
	public static String format3to2(double value) {
		DecimalFormat df = new DecimalFormat("#0.00");
		value = value * 1000;
		int result = (int)value;
		if((result % 10) == 0){
			value = (double)result / 1000;
			return df.format(value);
		}
		
		result = result + 10;
		result = result - result % 10;
		value = (double)result / 1000;
		return df.format(value);
	}
	
	public static String format2(Object value) {
		if(value == null){
			return "";
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		df.setGroupingSize(3);
		return df.format(Double.valueOf(String.valueOf(value)));
	}
	
	public static String unFormat2(String value) {
		if(value == null){
			return "";
		}
		if (!value.contains(",")) {
			return value;
		}
		return value.replaceAll(",", "");
	}
	
	public static String formatStrInt(Object obj)
	{
		if(obj==null)
			return 0+"";
		DecimalFormat df = new DecimalFormat("#0");
		return df.format(obj);
	}
	
	public static String format22(Object value) {
		if(value == null){
			return "";
		}
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(Double.valueOf(String.valueOf(value)));
	}
	
	public static String format4(Object value) {
		if(value == null){
			return "";
		}
		DecimalFormat df = new DecimalFormat("#0.0000");
		return df.format(Double.valueOf(String.valueOf(value)));
	}

	public static String formatUnit(Object value)
	{
		if(value == null){
			return "0.00(元)";
		}
		
		double result = Double.valueOf(value.toString());
		
		if(result > 100000000){
			return NumberUtils.format2(result / 100000000) + "(亿)";
		} else if (result > 10000){
			return NumberUtils.format2(result / 10000) + "(万)";
		}
		
		return NumberUtils.format2(value) + "(元)";
	}
	
	public static String[] formatInt(int value)
	{
		String []str = new String[2];
		if (value >= 100000000)
		{
			if (value % 100000000 == 0)
				str[0] = String.valueOf(value / 100000000);
			else
				str[0] = format2((double)value / 100000000);
			str[1] = "亿手";
		}
		else if (value >= 10000)
		{
			if (value % 10000 == 0)
				str[0] = String.valueOf(value / 10000);
			else
				str[0] = format2((double)value / 10000);
			str[1] = "万手";
		}
		else
		{
			str[0] = String.valueOf(value);
			str[1] = "手";
		}
		return str;
	}
	
	public static String formatIntUnit(int value)
	{
		if (value >= 100000000)
		{
			if (value % 100000000 == 0)
				return String.valueOf(value / 100000000) + "亿";
			else
				return format2((double)value / 100000000) + "亿";
		}
		else if (value >= 10000)
		{
			if (value % 10000 == 0)
				return String.valueOf(value / 10000) + "万";
			else
				return format2((double)value / 10000) + "万";
		}
		return String.valueOf(value);
	}
	
	public static String formatUnit(double value) 
	{
		if(value >= 100000000){
			return format2(value / 100000000) + "亿";
		} else if (value >= 10000){
			return format2(value / 10000) + "万";
		}
		return format2(value);
	}
	public static double strToDou(String value){
		if(value==null||"".equals(value)){
			return 0;
		}else{
			return Double.valueOf(value);
		}
	}
	
	public static float strToFloat(String value){
		if(value==null||"".equals(value)){
			return 0;
		}else{
			return Float.valueOf(value);
		}
	}
	
	public static int strToInt(String value){
		if(value==null||"".equals(value)){
			return 0;
		}else{
			return Integer.valueOf(value);
		}
	}
	/**
	 * 出入金的科学计数法
	 * @param value
	 * @return
	 */
	public static String format2OutInput(String value) {
		if(value == null){
			return "";
		}
		if (value.contains(".")) {
			return value;
		}
		if (value.contains(",")) {
			value = unFormat2(value);
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(0);
		df.setGroupingSize(3);
		return df.format(Double.valueOf(value));
	}
}
