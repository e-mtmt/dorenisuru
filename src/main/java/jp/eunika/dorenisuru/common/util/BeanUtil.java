package jp.eunika.dorenisuru.common.util;

import org.springframework.beans.BeanUtils;

public interface BeanUtil {
	static <T, R> R copy(T src, Class<R> dest) throws ReflectiveOperationException {
		R instance = dest.newInstance();
		BeanUtils.copyProperties(src, instance);
		return instance;
	}
}
