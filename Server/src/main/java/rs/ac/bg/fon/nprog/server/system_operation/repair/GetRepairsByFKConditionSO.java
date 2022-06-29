package rs.ac.bg.fon.nprog.server.system_operation.repair;


import rs.ac.bg.fon.nprog.commonlibrary.domain.Repair;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.server.system_operation.AbstractSO;

/**
 *
 * @author Dragon
 */
public class GetRepairsByFKConditionSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        Validator.startValidation()
                .throwIfInvalideParameterInstance(param, "Parameter must be an instance of Repair", Repair.class);
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Repair repair = (Repair) param;
        result = repository.findRecords(repair, repair.getFKWhereCondition());
    }

    public Object getResult() {
        return result;
    }
}
