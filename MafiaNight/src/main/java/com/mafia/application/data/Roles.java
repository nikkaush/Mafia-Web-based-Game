package com.mafia.application.data;

import java.util.HashMap;
import java.util.Map;

public class Roles {
	
	public static Map<Integer, String> mp = new HashMap<>();
	static {
		mp.put(1, "Mafia");
		mp.put(2, "Mafia");
		mp.put(3, "Doctor");
		mp.put(4, "Inspector");
		mp.put(5, "Citizen");
		mp.put(6, "Citizen");
		mp.put(7, "Citizen");
		mp.put(8, "Citizen");
		mp.put(9, "Citizen");
		mp.put(10, "Citizen");
	}
	
	public static String getRole(int key) {
		return Roles.mp.get(key);
	}
	

}
