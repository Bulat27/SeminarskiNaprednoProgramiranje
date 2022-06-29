package rs.ac.bg.fon.nprog.client.view.controller;

import rs.ac.bg.fon.nprog.client.communication.Communication;
import rs.ac.bg.fon.nprog.client.view.coordinator.Coordinator;
import rs.ac.bg.fon.nprog.client.view.form.MainForm;

import static rs.ac.bg.fon.nprog.commonlibrary.domain.util.EmployeeRole.WORKER;

/**
 *
 * @author Dragon
 */
public class MainFormController {

    private MainForm mainForm;

    public MainForm getMainForm() {
        return mainForm;
    }

    public MainFormController() {
        mainForm = new MainForm(this);
    }

    public void openForm() {
        prepareForm();
        mainForm.setVisible(true);
    }

    public void closeForm() {
        mainForm.dispose();
    }

    public void openServiceForm() {
        Coordinator.getInstance().openServiceForm();
    }

    public void openShowServicesForm() throws Exception {
        Coordinator.getInstance().openShowServicesForm();
    }

    public void openAddEmployeeForm() {
        Coordinator.getInstance().openAddEmployeeForm();
    }

    public void openShowEmployeesForm() throws Exception {
        Coordinator.getInstance().openShowEmployeesForm();
    }

    private void prepareForm() {
        if (Communication.getInstance().getAuthenticatedEmployee().getEmployeeRole() == WORKER) {
            mainForm.getMenuEmployee().setEnabled(false);
        }

        mainForm.getLblWelcome().setText("Welcome " + Communication.getInstance().getAuthenticatedEmployee().getUsername());
    }

    public void openAddServiceBookForm() {
        Coordinator.getInstance().openAddServiceBookForm();
    }

    public void openShowServiceBooksForm() throws Exception {
        Coordinator.getInstance().openShowServiceBooksForm();
    }
}
