package com.dossier.checker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RomanianCitizenshipCheckerTests {

	static {
		ApiContextInitializer.init();
	}

	@Test
	public void contextLoads() throws TelegramApiRequestException {
	}

}
