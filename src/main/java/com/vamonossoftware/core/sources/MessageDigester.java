package com.vamonossoftware.core.sources;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Formatter;

public class MessageDigester {

    private final MessageDigest digest;

    public MessageDigester(MessageDigest digest) {
        this.digest = digest;
    }

    public String digest(String value) {
        byte[] encodedhash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

    public String digest(InputStream inputStream) {
        try (DigestInputStream in = new DigestInputStream(inputStream, digest)) {
            while (in.read() != -1) {
                // Read the stream and do nothing with it
            }

            // Get the digest and finialise the computation
            final MessageDigest md = in.getMessageDigest();
            final byte[] digest = md.digest();
            return bytesToHex(digest);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String bytesToHex(byte[] hash) {
        // Format as HEX
        try (Formatter formatter = new Formatter()) {
            for (final byte b : hash) {
                formatter.format("%02x", b);
            }

            return formatter.toString();
        }
    }


//    private String bytesToHex(byte[] hash) {
//        StringBuffer hexString = new StringBuffer();
//        for (int i = 0; i < hash.length; i++) {
//        String hex = Integer.toHexString(0xff & hash[i]);
//        if(hex.length() == 1) hexString.append('0');
//            hexString.append(hex);
//        }
//        return hexString.toString();
//    }

}