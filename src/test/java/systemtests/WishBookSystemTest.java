package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import guitests.guihandles.WishListPanelHandle;
import seedu.address.TestApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.Model;
import seedu.address.model.WishBook;
import seedu.address.testutil.TypicalWishes;
import seedu.address.ui.BrowserPanel;
import seedu.address.ui.CommandBox;

/**
 * A system test class for WishBook, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class WishBookSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();

        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        //waitUntilBrowserLoaded(getBrowserPanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
        EventsCenter.clearSubscribers();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected WishBook getInitialData() {
        return TypicalWishes.getTypicalWishBook();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public WishListPanelHandle getWishListPanel() {
        return mainWindowHandle.getWishListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);

        //waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Displays all wishes in the address book.
     */
    protected void showAllWishes() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getWishBook().getWishList().size(), getModel().getFilteredSortedWishList().size());
    }

    /**
     * Displays all wishes with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showWishesWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + PREFIX_NAME + keyword);
        assertTrue(getModel().getFilteredSortedWishList().size() < getModel().getWishBook().getWishList().size());
    }

    /**
     * Selects the wish at {@code index} of the displayed list.
     */
    protected void selectWish(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getWishListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all wishes in the address book.
     */
    protected void deleteAllWishes() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getWishBook().getWishList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same wish objects as {@code expectedModel}
     * and the wish list panel displays the wishes in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new WishBook(expectedModel.getWishBook()),
                testApp.readStorageWishBook());
        assertListMatching(getWishListPanel(), expectedModel.getFilteredSortedWishList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code WishListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        //getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getWishListPanel().rememberSelectedWishCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url remains displaying the details
     * of the previously selected wish.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        //assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getWishListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the wish in the wish list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see WishListPanelHandle#isSelectedWishCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getWishListPanel().navigateToCard(getWishListPanel().getSelectedCardIndex());
        String selectedCardName = getWishListPanel().getHandleToSelectedCard().getName();
        URL expectedUrl;
        try {
            expectedUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + selectedCardName.replaceAll(" ", "%20"));
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }

        //assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getWishListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the wish list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see WishListPanelHandle#isSelectedWishCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        //assertFalse(getBrowserPanel().isUrlChanged());
        //assertFalse(getWishListPanel().isSelectedWishCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getWishListPanel(), getModel().getFilteredSortedWishList());
        //assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE), getBrowserPanel().getLoadedUrl());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
