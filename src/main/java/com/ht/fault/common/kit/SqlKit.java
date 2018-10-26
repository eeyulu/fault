package com.ht.fault.common.kit;

import java.util.List;

public class SqlKit {
	// 将 id 列表 join 起来，用逗号分隔，并且用小括号括起来
	public static <T> void joinIds(List<T> idList, StringBuilder ret) {
		ret.append("(");
		boolean isFirst = true;
		for (T id : idList) {
			if (isFirst) {
				isFirst = false;
			} else {
				ret.append(", ");
			}
			ret.append(id.toString());
		}
		ret.append(")");
	}
}
