package com.jock.fex.util;

public class BooleanTools {

	/**
	 * 2018年11月23日<br>
	 * 两个布尔是否相等
	 * 
	 * @param first
	 * @param second
	 * @return true-相等，false-不相等
	 */
	public final static boolean equals(Boolean first, Boolean second) {
		return (first == null && second == null) || (first != null && second != null && first.equals(second));
	}
}
