package seedu.address.model.wish;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Wish's url in the wish book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrl(String)}
 */
public class Url {

    public static final String MESSAGE_URL_CONSTRAINTS =
            "URL cannot have whitespaces.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String URL_VALIDATION_REGEX = "\\S*";

    public final String value;

    /**
     * Constructs an {@code Url}.
     *
     * @param url A valid url.
     */
    public Url(String url) {
        requireNonNull(url);
        checkArgument(isValidUrl(url), MESSAGE_URL_CONSTRAINTS);
        value = url;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidUrl(String test) {
        return test.matches(URL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Url // instanceof handles nulls
                && value.equals(((Url) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
