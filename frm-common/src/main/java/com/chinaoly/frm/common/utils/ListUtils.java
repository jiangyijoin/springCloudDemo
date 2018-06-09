package com.chinaoly.frm.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装list
 * @author jiangyi
 *
 */
public class ListUtils {

	public static <T> List<List<T>> fixedSize(List<T> list, int size) {
		List<List<T>> result = new ArrayList<List<T>>();
		int index = 0;
		while (index + size <= list.size()) {
			result.add(list.subList(index, index + size));
			index += size;
		}
		if (index < list.size()) {
			result.add(list.subList(index, list.size()));
		}
		return result;
	}

	public static <T> T first(List<T> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public static <T> T last(List<T> list) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(list.size() - 1);
	}

}
