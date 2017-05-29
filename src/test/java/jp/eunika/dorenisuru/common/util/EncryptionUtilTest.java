package jp.eunika.dorenisuru.common.util;

import static org.assertj.core.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.eunika.dorenisuru.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class EncryptionUtilTest {
	@Test
	public void encryptText() {
		String topicHash = "fc41b501-20ab-4390-b87b-957a389c8cc6";
		assertThat(EncryptionUtil.encryptText(topicHash))
				.isEqualTo("30BFDFD21731C1FB67D66A4EE07196AFFBAE58573FB35CDACBF1335ADD9E52EE19D41C4482205F6C");
	}
}
