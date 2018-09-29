package seedu.address.model.wish;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.address.testutil.TypicalWishes.ALICE;
import static seedu.address.testutil.TypicalWishes.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.WishBuilder;

public class WishTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Wish wish = new WishBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        wish.getTags().remove(0);
    }

    @Test
    public void isSameWish() {
        // same object -> returns true
        assertTrue(ALICE.isSameWish(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameWish(null));

        // different phone and email -> returns false
        Wish editedAlice = new WishBuilder(ALICE).withPrice(VALID_PRICE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameWish(editedAlice));

        // different name -> returns false
        editedAlice = new WishBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameWish(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new WishBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withUrl(VALID_URL_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameWish(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new WishBuilder(ALICE).withPrice(VALID_PRICE_BOB).withUrl(VALID_URL_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameWish(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new WishBuilder(ALICE).withUrl(VALID_URL_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameWish(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Wish aliceCopy = new WishBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different wish -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Wish editedAlice = new WishBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new WishBuilder(ALICE).withPrice(VALID_PRICE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new WishBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new WishBuilder(ALICE).withUrl(VALID_URL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new WishBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
