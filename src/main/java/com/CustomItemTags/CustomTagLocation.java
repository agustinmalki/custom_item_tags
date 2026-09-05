package com.CustomItemTags;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum CustomTagLocation {
	BOTTOM_LEFT,
	BOTTOM_RIGHT,
	TOP_LEFT,
	TOP_RIGHT;


	public String getAbbriviated() {
		return Arrays.stream(
			name().toLowerCase().split("_")
		).filter(s -> !s.isBlank()).map(s -> s.substring(0, 1)).collect(Collectors.joining("")).trim().toLowerCase();
	}
	private static final Map<String, CustomTagLocation> lookupTable = new HashMap<String, CustomTagLocation>();
	static {
		for(CustomTagLocation v : values()) {
			lookupTable.put(v.getAbbriviated(), v);
		}
	}

	public static CustomTagLocation fromStr(String str) {
		return lookupTable.get(str.toLowerCase().trim());
	}
}
