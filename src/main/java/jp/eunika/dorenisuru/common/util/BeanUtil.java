package jp.eunika.dorenisuru.common.util;

import org.springframework.beans.BeanUtils;

public interface BeanUtil {
	static <T, R> R copy(T src, Class<R> dest) {
		try {
			R instance = dest.newInstance();
			BeanUtils.copyProperties(src, instance);
			return instance;
		}
		catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	static <T, U> void copy(T src, U dest) {
		BeanUtils.copyProperties(src, dest);
	}
}
