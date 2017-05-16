package jp.eunika.dorenisuru.common.util;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface CollectionsUtil {
	@SafeVarargs
	static <T> List<T> $list(T... values) {
		return Stream.of(values).collect(Collectors.toList());
	}

	@SafeVarargs
	static <T> Set<T> $set(T... values) {
		return Stream.of(values).collect(Collectors.toSet());
	}

	@SafeVarargs
	static <K, V> Map<K, V> $map(Map.Entry<? extends K, ? extends V>... entries) {
		return Stream.of(entries).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	static <K, V> Map.Entry<K, V> $(K key, V value) {
		return new AbstractMap.SimpleEntry<K, V>(key, value);
	}
}
