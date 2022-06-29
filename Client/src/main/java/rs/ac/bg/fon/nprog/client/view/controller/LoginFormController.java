package rs.ac.bg.fon.nprog.client.view.controller;

import rs.ac.bg.fon.nprog.client.communication.Communication;
import rs.ac.bg.fon.nprog.client.controller.EmployeeController;
import rs.ac.bg.fon.nprog.client.view.coordinator.Coordinator;
import rs.ac.bg.fon.nprog.client.view.form.LoginForm;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;
import rs.ac.bg.fon.nprog.commonlibrary.validation.ValidationException;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;

/**
 *
 * @author Dragon
 */
public class LoginFormController {

    private LoginForm loginForm;

    public LoginFormController() {
        loginForm = new LoginForm(this);
    }

    public void openForm() {
        loginForm.setVisible(true);
    }

    public Employee logIn(String username, char[] password) throws Exception {
        validate(username, password);

        Employee requestEmployee = new Employee(username, String.valueOf(password));
        Employee employee = EmployeeController.getInstance().login(requestEmployee);
        Communication.getInstance().setAuthenticatedEmployee(employee);

        return employee;
    }

    public void coordinateForms() {
        closeForm();
        Coordinator.getInstance().openMainForm();
    }

    public void closeForm() {
        loginForm.dispose();
    }

    private void validate(String username, char[] password) throws ValidationException {
        Validator.startValidation()
                .validateNotNullOrEmpty(username, "Username field is required!")
                .validateNotNull(password, "Password field is required!")
                .validateNotNullOrEmpty(String.valueOf(password), "Password field is required!")
                .throwIfInvalide();
    }
}
