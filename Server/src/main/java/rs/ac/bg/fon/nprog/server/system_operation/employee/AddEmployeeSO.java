package rs.ac.bg.fon.nprog.server.system_operation.employee;

import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.server.system_operation.AbstractSO;

import java.util.List;

/**
 *
 * @author Dragon
 */
public class AddEmployeeSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        Validator.startValidation()
                .throwIfInvalideParameterInstance(param, "Parameter must be an instance of Employee", Employee.class)
                .validateValueIsAllAlphabets(((Employee) param).getFirstName(), "First name must not be null or empty and must contain only alphabetic characters")
                .validateValueIsAllAlphabets(((Employee) param).getLastName(), "Last name must not be null or empty and must contain only alphabetic characters")
                .validateNotNull(((Employee) param).getEmployeeRole(), "Employee role must not be null")
                .validateNumberIsNonNegative(((Employee) param).getHourlyRate(), "Hourly rate must be a non negative number")
                .validateNotNull(((Employee) param).getDateOfEmployment(), "Date of employment must not be null")
                .validateNotNullOrEmpty(((Employee) param).getUsername(), "Username must not be null or empty")
                .validateNotNullOrEmpty(((Employee) param).getPassword(), "Password must not be null or empty")
                .throwIfInvalide();
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        List<Employee> employees = repository.findRecords(new Employee(), null);

        for (Employee employee : employees) {
            if (employee.getUsername().equals(((Employee) param).getUsername())) {
                throw new Exception("Employee with given username already exists");
            }
        }
        repository.insertRecord(param);
    }
}
