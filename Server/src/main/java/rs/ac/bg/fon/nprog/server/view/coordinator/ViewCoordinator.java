package rs.ac.bg.fon.nprog.server.view.coordinator;

import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;
import rs.ac.bg.fon.nprog.commonlibrary.view.util.RefreshMode;
import rs.ac.bg.fon.nprog.server.view.controller.DatabaseConfigurationFormController;
import rs.ac.bg.fon.nprog.server.view.controller.MainFormController;
import rs.ac.bg.fon.nprog.server.view.controller.PortConfigurationFormController;
import rs.ac.bg.fon.nprog.server.view.form.MainForm;

/**
 *
 * @author Dragon
 */
public class ViewCoordinator {

    private static ViewCoordinator instance;

    private MainFormController mainFormController;
    private PortConfigurationFormController portConfigurationFormController;
    private DatabaseConfigurationFormController databaseConfigurationFormController;

    private ViewCoordinator() {
    }

    public static ViewCoordinator getInstance() {
        if (instance == null) {
            instance = new ViewCoordinator();
        }
        return instance;
    }

    public MainForm getMainForm() {
        return mainFormController.getMainForm();
    }

    public void openMainForm() {
        mainFormController = new MainFormController();
        mainFormController.openForm();
    }

    public void refreshMainForm(Employee employee, RefreshMode refreshMode) {
        mainFormController.refreshForm(employee, refreshMode);
    }

    public void openPortConfigurationForm() {
        portConfigurationFormController = new PortConfigurationFormController();
        portConfigurationFormController.openForm();
    }

    public void openDatabaseConfigurationForm() {
        databaseConfigurationFormController = new DatabaseConfigurationFormController();
        databaseConfigurationFormController.openForm();
    }
}
