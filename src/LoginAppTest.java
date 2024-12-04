import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginAppTest {

    private LoginApp loginApp = new LoginApp();

    @Test
    public void testAuthenticateUser_ValidCredentials() {
        String validEmail = "alicebrown@example.com";
        String validPassword = "password101";
        String expectedUserName = "Alice Brown";

        String result = loginApp.authenticateUser(validEmail,validPassword);

        assertNotNull(result, "Incorrect Output. User should be logged in");
        assertEquals(expectedUserName, result, "Wrong Username Returned.");
        validEmail = "mikejohnson@example.com";
        validPassword = "password789";
        expectedUserName = "Mike Johnson";

        result = loginApp.authenticateUser(validEmail,validPassword);

        assertNotNull(result, "Incorrect Output. User should be logged in");
        assertEquals(expectedUserName, result, "Wrong Username Returned.");
    }

    @Test
    public void testAuthenticateUser_InvalidEmail() {
        String invalidEmail = "invalidemail@example.com";
        String validPassword = "password101";
        String result = loginApp.authenticateUser(invalidEmail,validPassword);
        assertNull(result, "Incorrect Output. User should not be logged in");
        invalidEmail = "incorrectemail@example.com";
        validPassword = "password789";
        result = loginApp.authenticateUser(invalidEmail,validPassword);
        assertNull(result, "Incorrect Output. User should not be logged in");
    }

    @Test
    public void testAuthenticateUser_InvalidPassword() {

        String validEmail = "alicebrown@example.com";
        String invalidPassword = "password103";
        String result = loginApp.authenticateUser(validEmail,invalidPassword);
        assertNull(result, "Incorrect Output. User should not be logged in because password is invalid");
    }

    @Test
    public void testAuthenticateUser_EmptyEmail() {
        String emptyEmail = "";
        String validPassword = "password101";
        String result = loginApp.authenticateUser(emptyEmail,validPassword);
        assertNull(result, "Incorrect Output. User should not be logged in because email is null");
    }

    @Test
    public void testAuthenticateUser_EmptyPassword() {

        String validEmail = "alicebrown@example.com";
        String emptyPassword = "";
        String result = loginApp.authenticateUser(validEmail,emptyPassword);
        assertNull(result, "Incorrect Output. User should not be logged in because password is null");
    }
	
	@Test
    public void testAuthenticateUser_SQLInjection() {
        String sqlInjectionEmail = "incorrectemail@example.com' OR '1'='1";
        String result = loginApp.authenticateUser(sqlInjectionEmail,"");
        assertNull(result, "SQL Injection attempt should not authenticate any user");
    }
}