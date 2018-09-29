package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wish.Email;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Price;
import seedu.address.model.wish.Remark;
import seedu.address.model.wish.SavedAmount;
import seedu.address.model.wish.Url;
import seedu.address.model.wish.Wish;

/**
 * A utility class to help with building Wish objects.
 */
public class WishBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PRICE = "85.53";
    public static final String DEFAULT_SAVED_AMOUNT = "0.00";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_URL = "https://www.lazada.sg/products/"
            + "ps4-092-hori-real-arcade-pron-hayabusaps4ps3pc-i223784444-s340908955.html";
    public static final String DEFAULT_REMARK = "";

    private Name name;
    private Price price;
    private SavedAmount savedAmount;
    private Email email;
    private Url url;
    private Remark remark;
    private Set<Tag> tags;

    public WishBuilder() {
        name = new Name(DEFAULT_NAME);
        price = new Price(DEFAULT_PRICE);
        savedAmount = new SavedAmount(DEFAULT_SAVED_AMOUNT);
        email = new Email(DEFAULT_EMAIL);
        url = new Url(DEFAULT_URL);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
    }

    /**
     * Initializes the WishBuilder with the data of {@code wishToCopy}.
     */
    public WishBuilder(Wish wishToCopy) {
        name = wishToCopy.getName();
        price = wishToCopy.getPrice();
        savedAmount = wishToCopy.getSavedAmount();
        email = wishToCopy.getEmail();
        url = wishToCopy.getUrl();
        remark = wishToCopy.getRemark();
        tags = new HashSet<>(wishToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Wish} that we are building.
     */
    public WishBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Wish} that we are building.
     */
    public WishBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Url} of the {@code Wish} that we are building.
     */
    public WishBuilder withUrl(String url) {
        this.url = new Url(url);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Wish} that we are building.
     */
    public WishBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code SavedAmount} of the {@code Wish} that we are building.
     */
    public WishBuilder withSavedAmount(String savedAmount) {
        this.savedAmount = new SavedAmount(savedAmount);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Wish} that we are building.
     */
    public WishBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Wish} that we are building.
     */
    public WishBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Wish build() {
        return new Wish(name, price, email, url, savedAmount, remark, tags);
    }

}
