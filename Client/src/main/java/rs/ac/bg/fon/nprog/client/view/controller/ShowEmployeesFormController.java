package rs.ac.bg.fon.nprog.client.view.controller;

import rs.ac.bg.fon.nprog.client.communication.Communication;
import rs.ac.bg.fon.nprog.client.controller.EmployeeController;
import rs.ac.bg.fon.nprog.client.view.coordinator.Coordinator;
import rs.ac.bg.fon.nprog.client.view.form.ShowEmployeesForm;
import rs.ac.bg.fon.nprog.client.view.form.model.TableModelEmployees;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;
import rs.ac.bg.fon.nprog.commonlibrary.validation.ValidationException;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;

import java.util.List;

/**
 *
 * @author Dragon
 */
public class ShowEmployeesFormController {

    private ShowEmployeesForm showEmployeesForm;

    public ShowEmployeesFormController() {
        showEmployeesForm = new ShowEmployeesForm(Coordinator.getInstance().getMainForm(), true, this);
    }

    public void openForm() throws Exception {
        prepareForm();
        showEmployeesForm.setVisible(true);
    }

    private void prepareForm() throws Exception {
        prepareTable();
        showEmployeesForm.getBtnEdit().setVisible(false);
    }

    private void prepareTable() throws Exception {
        List<Employee> employees = EmployeeController.getInstance().getAllEmployees();
        TableModelEmployees tme = new TableModelEmployees(employees);

        showEmployeesForm.setTableEmployeesModel(tme);
    }

    public void search(String firstName, String lastName) throws Exception {
        validate(firstName, lastName);

        Employee employee = getEmployeeWithCondition(firstName, lastName);

        List<Employee> employees = EmployeeController.getInstance().getEmployeesByConditon(employee);

        TableModelEmployees tme = (TableModelEmployees) showEmployeesForm.getTblEmployees().getModel();
        tme.setEmployees(employees);
    }

    private Employee getEmployeeWithCondition(String firstName, String lastName) {
        Employee employee = new Employee();

        employee.setFirstName(firstName);
        employee.setLastName(lastName);

        return employee;
    }

    private void validate(String firstName, String lastName) throws ValidationException {
        Validator.startValidation()
                .validateNotNull(firstName, "First name must not be null!")
                .validateNotNull(lastName, "Last name must not be null!")
                .throwIfInvalide();
    }

    public void openEditEmployeeForm(int selectedRow) {
        TableModelEmployees tme = (TableModelEmployees) showEmployeesForm.getTblEmployees().getModel();

        Coordinator.getInstance().openEditEmployeeForm(tme.getEmployee(selectedRow), selectedRow);
    }

    public void refreshShowEmployeesForm(Employee employee, int selectedRow) {
        TableModelEmployees tme = (TableModelEmployees) showEmployeesForm.getTblEmployees().getModel();

        tme.setEmployee(employee, selectedRow);
    }

    public void delete(int selectedRow) throws Exception {
        TableModelEmployees tme = (TableModelEmployees) showEmployeesForm.getTblEmployees().getModel();
        Employee employee = tme.getEmployee(selectedRow);
        
        if (employee.equals(Communication.getInstance().getAuthenticatedEmployee())) {
            throw new Exception("You cannot delete your own account!");
        }

        EmployeeController.getInstance().deleteEmployee(employee);

        tme.removeEmployee(selectedRow);      
    }
}
