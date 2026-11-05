package com.learn.client_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClientServiceApplicationTests {

	@Test
	void helloWorldIsPrintedWhenContextStarts(CapturedOutput output) {
		assertThat(output.getOut()).contains("hello world");
	}

}
