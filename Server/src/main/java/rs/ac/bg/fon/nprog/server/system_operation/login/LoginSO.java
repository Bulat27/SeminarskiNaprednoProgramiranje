package rs.ac.bg.fon.nprog.server.system_operation.login;

import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;
import rs.ac.bg.fon.nprog.commonlibrary.domain.GeneralDObject;
import rs.ac.bg.fon.nprog.commonlibrary.validation.ValidationException;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.server.system_operation.AbstractSO;

import java.util.List;

/**
 *
 * @author Dragon
 */
public class LoginSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws ValidationException {
        Validator.startValidation()
                .throwIfInvalideParameterInstance(param, "Parameter must be an instance of Employee", Employee.class);
    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        List<GeneralDObject> employees = repository.findRecords(new Employee(), null);//TODO: Check out the thing about generics here!

        Employee employeeParam = (Employee) param;

        for (GeneralDObject e : employees) {
            if (((Employee) e).getUsername().equals(employeeParam.getUsername())
                    && ((Employee) e).getPassword().equals(employeeParam.getPassword())) {
                result = e;
            }
        }
        if (result == null) {
            throw new Exception("Unknown employee!");
        }

        ThreadCoordinator.getInstance().throwIfAlreadyAuthenticated((Employee) result,
                "Employee with username: " + ((Employee) result).getUsername() + " is already logged in.");
    }

    public Object getResult() {
        return result;
    }
}
