package rs.ac.bg.fon.nprog.server.system_operation.repair;

import rs.ac.bg.fon.nprog.commonlibrary.domain.Repair;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.server.system_operation.AbstractSO;

/**
 *
 * @author Dragon
 */
public class DeleteRepairSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        Validator.startValidation()
                .throwIfInvalideParameterInstance(param, "Parameter must be an instance of Repair", Repair.class);
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.deleteRecord(param);
    }
}
