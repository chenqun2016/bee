package com.bee.user.utils;

import android.util.Log;

import com.bee.user.BuildConfig;

/**
 * 创建时间：2020/8/19
 * 编写人： 进京赶考
 * 功能描述：打印log工具类
 */
public class LogUtil {
	
	static String className;
	static String methodName;
	static int lineNumber;
	static boolean DEBUGGABLE = BuildConfig.LOG_DEBUG; //false/true/BuildConfig.DEBUG
	
    private LogUtil(){
        /* Protect from instantiations */
    }

	public static boolean isDebuggable() {
		return DEBUGGABLE;
	}

	private static String createLog(String log ) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append(methodName);
		buffer.append(":");
		buffer.append(lineNumber);
		buffer.append("]");
		buffer.append(log);

		return buffer.toString();
	}
	
	private static void getMethodNames(StackTraceElement[] sElements){
		className = sElements[1].getFileName();
		methodName = sElements[1].getMethodName();
		lineNumber = sElements[1].getLineNumber();
	}

	public static void e(String message){
		if (!isDebuggable())
			return;

		// Throwable instance must be created before any methods  
		getMethodNames(new Throwable().getStackTrace());
		Log.e(className, createLog(message));
	}

	public static void e(String tag, String message){
		if (!isDebuggable())
			return;

		// Throwable instance must be created before any methods
		getMethodNames(new Throwable().getStackTrace());
		Log.e(tag, createLog(message));
	}

	public static void i(String message){
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.i(className, createLog(message));
	}
	
	public static void d(String message){
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.d(className, createLog(message));
	}
	public static void d(String tag, String message){
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.d(tag, createLog(message));
	}
	
	public static void v(String message){
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.v(className, createLog(message));
	}

	public static void w(String message){
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.w(className, createLog(message));
	}
	
	public static void wtf(String message){
		if (!isDebuggable())
			return;

		getMethodNames(new Throwable().getStackTrace());
		Log.wtf(className, createLog(message));
	}

}
