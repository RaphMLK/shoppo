package fr.shoppo.ms_commerce.infrastructure.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PasswordManagerTest {

    PasswordManager passwordManager;

    @BeforeEach
    void setup(){
        passwordManager = new PasswordManager();
        passwordManager.setRandom(new SecureRandom());
    }

    @Test
    void should_getRandomSpecialChars(){
        var acceptable = IntStream.range(33,45).mapToObj(entier ->(char)entier).collect(Collectors.toList());
        IntStream.range(0,10).forEach(i ->{
            var specialChar = passwordManager.getRandomSpecialChars(1).findFirst();
            assertTrue(acceptable.contains(specialChar.orElse(null)));
        });
    }

    @Test
    void should_getRandomAlphabets(){
        var acceptableUppperCase = IntStream.range(65,90).mapToObj(entier ->(char)entier).collect(Collectors.toList());
        var acceptableLowerCase = IntStream.range(97,122).mapToObj(entier ->(char)entier).collect(Collectors.toList());



        IntStream.range(0,10).forEach(i ->{
            var uppercase = passwordManager.getRandomAlphabets(1,true).findFirst();
            assertTrue(acceptableUppperCase.contains(uppercase.orElse(null)));

            var lowercase = passwordManager.getRandomAlphabets(1,false).findFirst();
            assertTrue(acceptableLowerCase.contains(lowercase.orElse(null)));
        });
    }

    @Test
    void should_getRandomNumbers(){
        var acceptable = IntStream.range(48,57).mapToObj(entier ->(char)entier).collect(Collectors.toList());
        IntStream.range(0,10).forEach(i ->{
            var numbers = passwordManager.getRandomNumbers(1).findFirst();
            assertTrue(acceptable.contains(numbers.orElse(null)));
        });
    }

    @Test
    void should_generateSecureRandomPassword(){
        var listPassword = new ArrayList<String>();
        var acceptableUppperCase = IntStream.range(65,90).mapToObj(entier ->(char)entier).collect(Collectors.toList());
        var acceptableLowerCase = IntStream.range(97,122).mapToObj(entier ->(char)entier).collect(Collectors.toList());
        var acceptableNumber = IntStream.range(48,57).mapToObj(entier ->(char)entier).collect(Collectors.toList());
        var acceptableSpecials = IntStream.range(33,45).mapToObj(entier ->(char)entier).collect(Collectors.toList());

        /*On test sur un grand nombre*/
        IntStream.range(0,10_000).forEach( i -> {
            var pass = passwordManager.generateSecureRandomPassword();
            assertFalse(listPassword.contains(pass));
            assertEquals(10,pass.length());
            listPassword.add(pass);
        });
        var pass = passwordManager.generateSecureRandomPassword();

        var containLower  = pass.chars().anyMatch(i -> acceptableLowerCase.contains((char)i));
        var containUpper  = pass.chars().anyMatch(i -> acceptableUppperCase.contains((char)i));
        var containNumeric  = pass.chars().anyMatch(i -> acceptableNumber.contains((char)i));
        var containSpecials  = pass.chars().anyMatch(i -> acceptableSpecials.contains((char)i));

        assertTrue(containLower && containNumeric && containSpecials && containUpper);
    }

    @Test
    void should_hash(){
        var digest = DigestUtils.sha256Hex("abc".getBytes());
        var hash = passwordManager.hash("abc");
        assertEquals(digest,hash);
    }


}