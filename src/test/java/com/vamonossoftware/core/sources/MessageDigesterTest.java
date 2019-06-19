package com.vamonossoftware.core.sources;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageDigesterTest {

    @Test
    public void should_digest_string() throws Exception {
        MessageDigester digester = new MessageDigester(MessageDigest.getInstance("SHA-1"));
        String digest = digester.digest("hello world");
        assertThat(digest).isEqualTo("2aae6c35c94fcfb415dbe95f408b9ce91ee846ed");
    }

    @Test
    public void should_digest_input_stream() throws Exception {
        MessageDigester digester = new MessageDigester(MessageDigest.getInstance("SHA-1"));
        String digest = digester.digest(new ByteArrayInputStream("hello world".getBytes()));
        assertThat(digest).isEqualTo("2aae6c35c94fcfb415dbe95f408b9ce91ee846ed");
    }

}