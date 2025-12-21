package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}
// Add route to enable check sum return of static data example:  String data = "Hello World Check Sum!";
@RestController

class ServerController {
	private MessageDigest md = MessageDigest.getInstance("SHA-256");

	private ServerController() throws NoSuchAlgorithmException {
    }

    @RequestMapping({"/hash"})
    public String myHash() throws NoSuchAlgorithmException {
        String data = "Hello World CheckSum! Author: Phillip Cowan";
        byte[] hash = this.md.digest(data.getBytes(StandardCharsets.UTF_8));
        String checksum = bytesToHex(hash);
        return "<p>data: " + data + "\n" + "<p>Name of Cipher Algorithm Used: AES CheckSum Value:" + checksum;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);

        for(int i = 0; i < hash.length; ++i) {
            String hex = Integer.toHexString(255 & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }
}