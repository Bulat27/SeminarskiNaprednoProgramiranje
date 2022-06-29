package rs.ac.bg.fon.nprog.server.system_operation.employee;

import domain.Employee;
import system_operation.AbstractSO;

/**
 *
 * @author Dragon
 */
public class GetAllEmployeesSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        result = repository.findRecords(new Employee(), null);
    }

    public Object getResult() {
        return result;
    }
}
