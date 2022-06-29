package rs.ac.bg.fon.nprog.client.view.controller;

import rs.ac.bg.fon.nprog.client.constant.MyClientConstants;
import rs.ac.bg.fon.nprog.client.controller.EmployeeController;
import rs.ac.bg.fon.nprog.client.view.coordinator.Coordinator;
import rs.ac.bg.fon.nprog.client.view.form.EmployeeForm;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;
import rs.ac.bg.fon.nprog.commonlibrary.domain.util.EmployeeRole;
import rs.ac.bg.fon.nprog.commonlibrary.validation.ValidationException;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.commonlibrary.view.util.FormMode;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static rs.ac.bg.fon.nprog.commonlibrary.view.util.FormMode.EDIT;

/**
 *
 * @author Dragon
 */
public class EmployeeFormController {

    private EmployeeForm employeeForm;
    private FormMode formMode;
    private Employee selectedEmployee;
    private int selectedRow;

    public EmployeeFormController(FormMode formMode, Employee selectedEmployee, int selectedRow) {
        this.formMode = formMode;
        this.selectedEmployee = selectedEmployee;
        this.selectedRow = selectedRow;
        this.employeeForm = new EmployeeForm(Coordinator.getInstance().getMainForm(), true, this);
    }

    public void openForm() {
        prepareForm();
        employeeForm.setVisible(true);
    }

    public void closeForm() {
        employeeForm.dispose();
    }

    public void save(String firstName, String lastName, String role, String hourlyRate, String dateOfEmployment, String username, char[] password) throws Exception {
        validate(firstName, lastName, role, hourlyRate, dateOfEmployment, username, password);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(MyClientConstants.DATE_PATTERN);

        Employee employee = new Employee(firstName, lastName, EmployeeRole.valueOf(role), new BigDecimal(hourlyRate),
                LocalDate.parse(dateOfEmployment, dtf), username, String.valueOf(password));

        executeSaving(employee);
    }

    private void executeSaving(Employee employee) throws Exception {
        switch (formMode) {

            case EDIT:
                edit(employee);
                break;

            case ADD:
                add(employee);
                break;

            default:
        }
    }

    public void coordinateForms() {
        closeForm();
    }

    private void prepareForm() {
        prepareRoleComboBox();

        if (formMode == EDIT && selectedEmployee != null) {
            prepareFields();
        }
    }

    private void prepareRoleComboBox() {
        JComboBox roleCMB = employeeForm.getCmbRole();

        roleCMB.removeAllItems();

        for (EmployeeRole role : EmployeeRole.values()) {
            roleCMB.addItem(role);
        }
    }

    private void validate(String firstName, String lastName, String role, String hourlyRate, String dateOfEmployment, String username, char[] password) throws ValidationException {
        Validator.startValidation()
                .validateValueIsAllAlphabets(firstName, "First name field is required and must contain only alphabetic characters!")
                .validateValueIsAllAlphabets(lastName, "Last name field is required and must contain only alphabetic characters!")
                .validateNotNullOrEmpty(role, "Role field is required!")
                .validateValueIsNonNegativeNumber(hourlyRate, "Hourly rate is required and must be a non negative number!")
                .validateValueIsDate(dateOfEmployment, MyClientConstants.DATE_PATTERN, "Date field is required and date must be in format: " + MyClientConstants.DATE_PATTERN)
                .validateNotNullOrEmpty(username, "Username field is required!")
                .validateNotNull(password, "Password field is required!")
                .validateNotNullOrEmpty(String.valueOf(password), "Password field is required!")
                .throwIfInvalide();
    }

    private void prepareFields() {
        employeeForm.getTxtFirstName().setText(selectedEmployee.getFirstName());
        employeeForm.getTxtLastName().setText(selectedEmployee.getLastName());
        employeeForm.getCmbRole().setSelectedItem(selectedEmployee.getEmployeeRole());
        employeeForm.getTxtHourlyRate().setText(String.valueOf(selectedEmployee.getHourlyRate()));
        employeeForm.getTxtDateOfEmployment().setText(selectedEmployee.getDateOfEmployment().format(DateTimeFormatter.ofPattern(MyClientConstants.DATE_PATTERN)));
        employeeForm.getTxtUserName().setText(selectedEmployee.getUsername());
        employeeForm.getTxtPassword().setText(selectedEmployee.getPassword());
    }

    private void edit(Employee employee) throws Exception {
        if (selectedEmployee != null) {
            employee.setEmployeeID(selectedEmployee.getEmployeeID());
            EmployeeController.getInstance().editEmployee(employee);

            if (selectedRow != -1) {
                Coordinator.getInstance().refreshShowEmployeesForm(employee, selectedRow);
            }
        }
    }

    private void add(Employee employee) throws Exception {
        EmployeeController.getInstance().addEmployee(employee);
    }
}
