package systemtests;

import guitests.GuiRobot;
import guitests.guihandles.AlertDialogHandle;
import org.junit.Test;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postLater;
import static seedu.address.ui.UiManager.*;

public class ErrorDialogGuiTest extends AddressBookSystemTest {

    private static final IOException IO_EXCEPTION_STUB = new IOException("Stub");
    private final GuiRobot guiRobot = new GuiRobot();

    @Test
    public void showErrorDialogs() {
        postLater(new DataSavingExceptionEvent(IO_EXCEPTION_STUB));

        guiRobot.waitForEvent(() -> guiRobot.isWindowShown(FILE_OPS_ERROR_DIALOG_STAGE_TITLE));

        AlertDialogHandle alertDialog = new AlertDialogHandle(guiRobot.getStage(FILE_OPS_ERROR_DIALOG_STAGE_TITLE));
        assertEquals(FILE_OPS_ERROR_DIALOG_HEADER_MESSAGE, alertDialog.getHeaderText());
        assertEquals(FILE_OPS_ERROR_DIALOG_CONTENT_MESSAGE + ":\n" + IO_EXCEPTION_STUB.toString(),
                alertDialog.getContentText());
    }

}
