package com.itcast.utils;

import java.util.UUID;

public class CommonsUtils {
	
	//生成UUID的方法
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

}
