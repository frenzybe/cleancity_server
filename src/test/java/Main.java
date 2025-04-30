import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.frenzybe.server.services.JwtService;

import java.security.Key;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;
    private String secretKey = "v7HAYPB0972wIc9XSjY7qplXPFYGdRsSum7FwrKKX7k=";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        jwtService.secretKey = secretKey;
        userDetails = new User("testuser", "password", Collections.emptyList());
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void testExtractUserName() {
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUserName(token);
        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void testIsTokenValid() {
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testIsTokenExpired() {
        String token = jwtService.generateToken(userDetails);
        assertFalse(jwtService.isTokenExpired(token));
    }

    @Test
    void testGetSigningKey() {
        Key key = jwtService.getSigningKey();
        assertNotNull(key);
        assertEquals(Keys.hmacShaKeyFor(secretKey.getBytes()).getAlgorithm(), key.getAlgorithm());
    }
}