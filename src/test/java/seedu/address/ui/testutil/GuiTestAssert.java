package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.WishCardHandle;
import guitests.guihandles.WishListPanelHandle;
import seedu.address.model.wish.Wish;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(WishCardHandle expectedCard, WishCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPrice(), actualCard.getPrice());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysWish(Wish expectedWish, WishCardHandle actualCard) {
        assertEquals(expectedWish.getName().fullName, actualCard.getName());
        assertEquals(expectedWish.getPrice().toString(), actualCard.getPrice());
        assertEquals(expectedWish.getEmail().value, actualCard.getEmail());
        assertEquals(expectedWish.getUrl().value, actualCard.getAddress());
        assertEquals(expectedWish.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
        assertEquals(expectedWish.getRemark().value, actualCard.getRemark());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(WishListPanelHandle wishListPanelHandle, Wish... wishes) {
        for (int i = 0; i < wishes.length; i++) {
            wishListPanelHandle.navigateToCard(i);
            assertCardDisplaysWish(wishes[i], wishListPanelHandle.getWishCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(WishListPanelHandle wishListPanelHandle, List<Wish> wishes) {
        assertListMatching(wishListPanelHandle, wishes.toArray(new Wish[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(WishListPanelHandle wishListPanelHandle, int size) {
        int numberOfPeople = wishListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
