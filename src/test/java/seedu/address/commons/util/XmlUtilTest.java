package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.WishBook;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlAdaptedWish;
import seedu.address.storage.XmlSerializableWishBook;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.WishBookBuilder;
import seedu.address.testutil.WishBuilder;


public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validWishBook.xml");
    private static final Path MISSING_WISH_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingWishField.xml");
    private static final Path INVALID_WISH_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidWishField.xml");
    private static final Path VALID_WISH_FILE = TEST_DATA_FOLDER.resolve("validWish.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempWishBook.xml");

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_EMAIL = "hans@example";
    private static final String VALID_URL = "https://www.amazon.com/gp/product/B07D998212";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, WishBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, WishBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, WishBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        WishBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableWishBook.class).toModelType();
        assertEquals(9, dataFromFile.getWishList().size());
    }

    @Test
    public void xmlAdaptedWishFromFile_fileWithMissingWishField_validResult() throws Exception {
        XmlAdaptedWish actualWish = XmlUtil.getDataFromFile(
                MISSING_WISH_FIELD_FILE, XmlAdaptedWishWithRootElement.class);
        XmlAdaptedWish expectedWish = new XmlAdaptedWish(
                null, VALID_PHONE, VALID_EMAIL, VALID_URL, VALID_TAGS);
        assertEquals(expectedWish, actualWish);
    }

    @Test
    public void xmlAdaptedWishFromFile_fileWithInvalidWishField_validResult() throws Exception {
        XmlAdaptedWish actualWish = XmlUtil.getDataFromFile(
                INVALID_WISH_FIELD_FILE, XmlAdaptedWishWithRootElement.class);
        XmlAdaptedWish expectedWish = new XmlAdaptedWish(
                VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_URL, VALID_TAGS);
        assertEquals(expectedWish, actualWish);
    }

    @Test
    public void xmlAdaptedWishFromFile_fileWithValidWish_validResult() throws Exception {
        XmlAdaptedWish actualWish = XmlUtil.getDataFromFile(
                VALID_WISH_FILE, XmlAdaptedWishWithRootElement.class);
        XmlAdaptedWish expectedWish = new XmlAdaptedWish(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_URL, VALID_TAGS);
        assertEquals(expectedWish, actualWish);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new WishBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new WishBook());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableWishBook dataToWrite = new XmlSerializableWishBook(new WishBook());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableWishBook dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableWishBook.class);
        assertEquals(dataToWrite, dataFromFile);

        WishBookBuilder builder = new WishBookBuilder(new WishBook());
        dataToWrite = new XmlSerializableWishBook(
                builder.withWish(new WishBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableWishBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedWish}
     * objects.
     */
    @XmlRootElement(name = "wish")
    private static class XmlAdaptedWishWithRootElement extends XmlAdaptedWish {}
}
