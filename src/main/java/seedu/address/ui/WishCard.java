package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.wish.Wish;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class WishCard extends UiPart<Region> {

    private static final String FXML = "WishListCard.fxml";
    private static final String[] TAG_COLORS = { "red", "yellow", "blue", "orange", "brown", "green", "pink", "black" };

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on WishBook level 4</a>
     */

    public final Wish wish;

    @FXML
    private HBox cardPane;

    @FXML
    private Label name;

    @FXML
    private Label id;

    @FXML
    private Label price;

    @FXML
    private Label savedAmount;

    @FXML
    private Label url;

    @FXML
    private Label email;

    @FXML
    private Label remark;

    @FXML
    private FlowPane tags;

    public WishCard(Wish wish, int displayedIndex) {
        super(FXML);
        this.wish = wish;
        id.setText(displayedIndex + ". ");
        name.setText(wish.getName().fullName);
        price.setText(wish.getPrice().toString());
        savedAmount.setText(wish.getSavedAmount().toString());
        url.setText(wish.getUrl().value);
        email.setText(wish.getEmail().value);
        remark.setText(wish.getRemark().value);
        initTags(wish);
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        return TAG_COLORS[Math.abs(tagName.hashCode()) % TAG_COLORS.length];
    }

    /**
     * Creates the tag labels for {@code wish}.
     */
    private void initTags(Wish wish) {
        wish.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WishCard)) {
            return false;
        }

        // state check
        WishCard card = (WishCard) other;
        return id.getText().equals(card.id.getText())
                && wish.equals(card.wish);
    }
}
