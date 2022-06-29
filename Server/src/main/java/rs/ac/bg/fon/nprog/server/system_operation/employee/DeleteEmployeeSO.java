package rs.ac.bg.fon.nprog.server.system_operation.employee;

import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.server.system_operation.AbstractSO;
import rs.ac.bg.fon.nprog.server.thread.coordinator.ThreadCoordinator;

/**
 *
 * @author Dragon
 */
public class DeleteEmployeeSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        Validator.startValidation()
                .throwIfInvalideParameterInstance(param, "Parameter must be an instance of Employee", Employee.class);

        ThreadCoordinator.getInstance().throwIfAlreadyAuthenticated((Employee) param,
                "Employee with username: " + ((Employee) param).getUsername() + " cannot be deleted because he/she is currently logged in.");
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.deleteRecord(param);
    }
}
