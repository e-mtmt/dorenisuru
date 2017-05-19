package jp.eunika.dorenisuru.common.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Tuple5<A, B, C, D, E> {
	private final A value1;
	private final B value2;
	private final C value3;
	private final D value4;
	private final E value5;
}
