package fr.shoppo.ms_commerce.infrastructure.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PasswordManager {

    Random random;

    public PasswordManager() {
        /*this is for instanciation*/
    }
    public Stream<Character> getRandomSpecialChars(int count) {
        return random
                .ints(count, 33, 45)
                .mapToObj(data -> (char) data);
    }
    public Stream<Character> getRandomAlphabets(int count, boolean upperCase) {
        return (
            upperCase ?
             random.ints(count, 65, 90)
            :random.ints(count, 97, 122)
        ).mapToObj(data -> (char) data);
    }

    public Stream<Character> getRandomNumbers(int count) {
        return random
                .ints(count, 48, 57)
                .mapToObj(data -> (char) data);
    }

    public String generateSecureRandomPassword() {
        var pwdStream =
                Stream.concat(getRandomNumbers(2),
                    Stream.concat(getRandomSpecialChars(2),
                        Stream.concat(getRandomAlphabets(2, true), getRandomAlphabets(4, false))));
        var charList = pwdStream.collect(Collectors.toList());
        Collections.shuffle(charList);
        return charList.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public String hash(String pass){
        return DigestUtils.sha256Hex(pass.getBytes());
    }

    @Autowired
    public void setRandom(Random random) {
        this.random = random;
    }
}
