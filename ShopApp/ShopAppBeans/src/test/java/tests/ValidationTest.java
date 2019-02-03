package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.shopapp.beans.Validation;
import pl.shopapp.entites.SettingsApp;

class ValidationTest {
	
	SettingsApp setting;
	Validation valid;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		setting = new SettingsApp();
		setting.setMaxCharInLogin(12);
		setting.setMaxCharInPass(20);
		setting.setMinCharInLogin(5);
		setting.setMinCharInPass(6);
		setting.setNumbersInPass(2);
		setting.setUpperCaseInPass(1);
		valid = new Validation(setting);
	}

	@AfterEach
	void tearDown() throws Exception {
		setting = null;
	}

	@Test
	void testPasswordToCode() {
		String expected = "dcca2ed163582435afa9d42ce361eb4";
		String actual = valid.passwordToCode("Admin11");
		assertEquals(expected, actual);
		assertNotEquals("Admin1", actual);
	}

	@Test
	void testLoginValidation() {
		assertTrue(valid.loginValidation("login1"));
		assertTrue(valid.loginValidation("login"));
		assertFalse(valid.loginValidation("login-"));
		assertFalse(valid.loginValidation("log"));
		assertFalse(valid.loginValidation("login;"));
		assertFalse(valid.loginValidation(""));
		assertFalse(valid.loginValidation("1"));
		assertFalse(valid.loginValidation("."));
	}

	@Test
	void testPasswordValidation() {
		assertTrue(valid.passwordValidation("Password11"));
		assertTrue(valid.passwordValidation("Pass11"));
		assertTrue(valid.passwordValidation("Pass11;-%"));
		assertFalse(valid.passwordValidation("password11"));
		assertFalse(valid.passwordValidation("password1111111111111111"));
		assertFalse(valid.passwordValidation("Password"));
		assertFalse(valid.passwordValidation(""));
		assertFalse(valid.passwordValidation("."));
		assertFalse(valid.passwordValidation("1"));
	}

	@Test
	void testTelephoneNoValidation() {
		assertTrue(valid.telephoneNoValidation("123456789"));
		assertTrue(valid.telephoneNoValidation("12 345 67 89"));
		assertTrue(valid.telephoneNoValidation("+012 345 67 89"));
		assertFalse(valid.telephoneNoValidation("-012 345 67 89"));
		assertFalse(valid.telephoneNoValidation(" 12 345"));
		assertFalse(valid.telephoneNoValidation(""));
		assertFalse(valid.telephoneNoValidation("1 2 3 4 5 6 7 8 9 0"));
	}

	@Test
	void testNameValidation() {
		assertTrue(valid.nameValidation("nazwa"));
		assertTrue(valid.nameValidation("Nazwa-*&()@$.?"));
		assertFalse(valid.nameValidation(""));
		assertFalse(valid.nameValidation("a1"));
		assertFalse(valid.nameValidation("a123%"));
		assertFalse(valid.nameValidation("a123^"));
		assertFalse(valid.nameValidation("a123|"));
		assertFalse(valid.nameValidation("a123:"));
		assertFalse(valid.nameValidation("a123;"));
		assertFalse(valid.nameValidation("a111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"));
	}

	@Test
	void testEmailValidation() {
		assertTrue(valid.emailValidation("abC.def@Abc.com"));
		assertTrue(valid.emailValidation("abC@Abc.com"));
		assertFalse(valid.emailValidation("abcdef@abccom"));
		assertFalse(valid.emailValidation(" @abc456.com"));
		assertFalse(valid.emailValidation("abc1234@abc456.com"));
		assertFalse(valid.emailValidation("ab@abc.com"));
		assertFalse(valid.emailValidation("ab@abc,com"));
		assertFalse(valid.emailValidation("a-b@abc.com"));
		assertFalse(valid.emailValidation("ab@abcaAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA.com"));
		assertFalse(valid.emailValidation("abcccccccccccccccccccccccccccccccccccccccccccccccccc@abc.com"));
	}

	@Test
	void testNumberValidation() {
		assertTrue(valid.numberValidation("1234567.89"));
		assertTrue(valid.numberValidation("1"));	
		assertFalse(valid.numberValidation(""));
		assertFalse(valid.numberValidation("."));
		assertFalse(valid.numberValidation("-"));
		assertFalse(valid.numberValidation(","));
		assertFalse(valid.numberValidation("01234567890"));
	}

	@Test
	void testNipValidation() {
		assertTrue(valid.nipValidation("1234567890"));
		assertFalse(valid.nipValidation("123456789"));
		assertFalse(valid.nipValidation("01234567890"));
		assertFalse(valid.nipValidation("123456789-"));
		assertFalse(valid.nipValidation("1.23456789"));
	}

	@Test
	void testPeselValidation() {
		assertTrue(valid.peselValidation("01234567890"));
		assertFalse(valid.peselValidation("0123456789"));
		assertFalse(valid.peselValidation("012345678901"));
		assertFalse(valid.peselValidation("0123456789-"));
		assertFalse(valid.peselValidation("01.23456789"));
	}

	@Test
	void testZipCodeValidation() {
		assertTrue(valid.zipCodeValidation("11-222"));
		assertTrue(valid.zipCodeValidation("00-000"));
		assertFalse(valid.zipCodeValidation("1-222"));
		assertFalse(valid.zipCodeValidation("11-22"));
		assertFalse(valid.zipCodeValidation("11 222"));
		assertFalse(valid.zipCodeValidation("11.222"));
		assertFalse(valid.zipCodeValidation("11_222"));
	}

	@Test
	void testLocationValidation() {
		assertTrue(valid.locationValidation("lokalizacja"));
		assertTrue(valid.locationValidation("Lokalizacja-*&()@$.?"));
		assertFalse(valid.locationValidation(""));
		assertFalse(valid.locationValidation("a123%"));
		assertFalse(valid.locationValidation("a123^"));
		assertFalse(valid.locationValidation("a123|"));
		assertFalse(valid.locationValidation("a123:"));
		assertFalse(valid.locationValidation("a123;"));
		assertFalse(valid.locationValidation("a11111111111111111111111111111111111111111111111111111111111"));	
	}

	@Test
	void testRegonValidation() {
		assertTrue(valid.regonValidation("123456789"));
		assertFalse(valid.regonValidation("12345678"));
		assertFalse(valid.regonValidation("0123456789"));
		assertFalse(valid.regonValidation("12345678-"));
		assertFalse(valid.regonValidation("1.2345678"));
	}

}
