package jp.eunika.dorenisuru.common.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Tuple3<A, B, C> {
	private final A value1;
	private final B value2;
	private final C value3;
}
