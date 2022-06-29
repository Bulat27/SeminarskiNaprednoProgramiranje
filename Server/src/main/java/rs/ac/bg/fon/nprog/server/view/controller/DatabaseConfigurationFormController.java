package rs.ac.bg.fon.nprog.server.view.controller;

import rs.ac.bg.fon.nprog.commonlibrary.validation.ValidationException;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.server.properties.util.UtilApplicationProperties;
import rs.ac.bg.fon.nprog.server.view.coordinator.ViewCoordinator;
import rs.ac.bg.fon.nprog.server.view.form.DatabaseConfigurationForm;

/**
 * @author Dragon
 */
public class DatabaseConfigurationFormController {

    private DatabaseConfigurationForm databaseConfigurationForm;

    public DatabaseConfigurationFormController() {
        databaseConfigurationForm = new DatabaseConfigurationForm(ViewCoordinator.getInstance().getMainForm(), true, this);
    }

    public void openForm() {
        databaseConfigurationForm.setVisible(true);
    }

    public void closeForm() {
        databaseConfigurationForm.setVisible(false);
    }

    public void save(String url, String username, String password) throws Exception {
        validate(url, username, password);

        UtilApplicationProperties.getInstance().setDatabaseURL(url);
        UtilApplicationProperties.getInstance().setDatabaseUsername(username);
        UtilApplicationProperties.getInstance().setDatabasePassword(password);
        UtilApplicationProperties.getInstance().saveChanges();
    }

    public void coordinateForms() {
        closeForm();
    }

    private void validate(String url, String username, String password) throws ValidationException {
        Validator.startValidation()
                .validateNotNullOrEmpty(url, "URL field is required")
                .validateNotNullOrEmpty(username, "Username field is required")
                .validateNotNullOrEmpty(password, "Password field is required")
                .throwIfInvalide();
    }
}
