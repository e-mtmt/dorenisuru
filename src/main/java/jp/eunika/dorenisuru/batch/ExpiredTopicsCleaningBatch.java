package jp.eunika.dorenisuru.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;

import jp.eunika.dorenisuru.service.TopicService;

@EnableAutoConfiguration
public class ExpiredTopicsCleaningBatch implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(ExpiredTopicsCleaningBatch.class);

	@Autowired
	private TopicService topicService;

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ExpiredTopicsCleaningBatch.class);
		ApplicationContext context = application.run();
		SpringApplication.exit(context);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("⏰ ️有効期限切れトピックのクリーニング開始");
		topicService.cleanExpiredTopics();
		log.info("⏰ ️有効期限切れトピックのクリーニング終了");
	}
}
