package seedu.address.model.wish;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a saved Amount for a Wish in the wish book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSavedAmount(String)}
 */
public class SavedAmount {

    public static final String MESSAGE_PRICE_CONSTRAINTS =
            "Saved Amount numbers should only contain numbers, and at most two numbers after the decimal point.";
    public static final String SAVED_AMOUNT_VALIDATION_REGEX = "[+-]?[0-9]+([,.][0-9]{1,2})?";
    public final Double value;

    /**
     * Constructs a {@code Price}.
     *
     * @param savedAmount A valid savedAmount number.
     */
    public SavedAmount(String savedAmount) {
        requireNonNull(savedAmount);
        checkArgument(isValidSavedAmount(savedAmount), MESSAGE_PRICE_CONSTRAINTS);
        value = Double.parseDouble(savedAmount); // TO-DO: check before allowing.
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidSavedAmount(String test) {
        return test.matches(SAVED_AMOUNT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SavedAmount // instanceof handles nulls
                && value.equals(((SavedAmount) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
