package jp.eunika.dorenisuru.common.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class Tuple2<A, B> {
	private final A value1;
	private final B value2;
}
