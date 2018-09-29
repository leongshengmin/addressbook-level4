package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.wish.Wish;

/**
 * Provides a handle to a wish card in the wish list panel.
 */
public class WishCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String URL_FIELD_ID = "#url";
    private static final String PRICE_FIELD_ID = "#price";
    private static final String SAVED_AMOUNT_FIELD_ID = "#savedAmount";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String REMARK_FIELD_ID = "#remark";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label urlLabel;
    private final Label priceLabel;
    private final Label savedAmountLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;
    private final Label remarkLabel;

    public WishCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        urlLabel = getChildNode(URL_FIELD_ID);
        priceLabel = getChildNode(PRICE_FIELD_ID);
        savedAmountLabel = getChildNode(SAVED_AMOUNT_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        remarkLabel = getChildNode(REMARK_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return urlLabel.getText();
    }

    public String getPrice() {
        return priceLabel.getText();
    }

    public String getSavedAmount() {
        return savedAmountLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getRemark() {
        return remarkLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code wish}.
     */
    public boolean equals(Wish wish) {
        return getName().equals(wish.getName().fullName)
                && getAddress().equals(wish.getUrl().value)
                && getPrice().equals(wish.getPrice().toString())
                // TO-DO: add saved amount
                && getEmail().equals(wish.getEmail().value)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(wish.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
