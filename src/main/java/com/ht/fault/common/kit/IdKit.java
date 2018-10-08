package com.ht.fault.common.kit;

import java.util.UUID;

public class IdKit {
	
	public static String id() {
		UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        String uuidStr=str.replace("-", "");
        return uuidStr;
		
	}
	
}
