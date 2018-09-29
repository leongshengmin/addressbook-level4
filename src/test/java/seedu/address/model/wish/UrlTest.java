package seedu.address.model.wish;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class UrlTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Url(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "w w";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Url(invalidAddress));
    }

    @Test
    public void isValidUrl() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Url.isValidUrl(null));

        // invalid addresses
        assertFalse(Url.isValidUrl("w w")); // empty string
        assertFalse(Url.isValidUrl(" ")); // spaces only

        // valid addresses
        assertTrue(Url.isValidUrl("https://www.amazon.com/EVGA-GeForce-Gaming-GDDR5X-Technology/dp/B0762Q49NV"));
        assertTrue(Url.isValidUrl("-")); // one character
    }
}
