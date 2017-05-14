package jp.eunika.dorenisuru.util;

import org.springframework.beans.BeanUtils;

public interface BeanUtil {
	static <T, R> R copy(T src, Class<R> dest) throws ReflectiveOperationException {
		R instance = dest.newInstance();
		BeanUtils.copyProperties(src, instance);
		return instance;
	}
}
