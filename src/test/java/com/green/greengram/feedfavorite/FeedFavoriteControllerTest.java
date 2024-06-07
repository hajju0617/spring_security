package com.green.greengram.feedfavorite;

import com.green.greengram.CharEncodingConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import(CharEncodingConfiguration.class)
@WebMvcTest(FeedFavoriteServiceImpl.class)
class FeedFavoriteControllerTest {

    @Test
    void toggleFavorite() {
    }
}