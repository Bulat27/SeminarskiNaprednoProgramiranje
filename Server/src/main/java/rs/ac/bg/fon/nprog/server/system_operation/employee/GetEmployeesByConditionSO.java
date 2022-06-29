package rs.ac.bg.fon.nprog.server.system_operation.employee;

import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.server.system_operation.AbstractSO;

/**
 *
 * @author Dragon
 */
public class GetEmployeesByConditionSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        Validator.startValidation()
                .throwIfInvalideParameterInstance(param, "Parameter must be an instance of Employee", Employee.class);
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Employee employee = (Employee) param;
        result = repository.findRecords(employee, employee.getAttributeValuesWhereCondition());
    }

    public Object getResult() {
        return result;
    }
}
