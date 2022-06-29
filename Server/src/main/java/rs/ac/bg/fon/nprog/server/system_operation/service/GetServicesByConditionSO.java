package rs.ac.bg.fon.nprog.server.system_operation.service;


import rs.ac.bg.fon.nprog.commonlibrary.domain.Service;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.server.system_operation.AbstractSO;

/**
 * @author Dragon
 */
public class GetServicesByConditionSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        Validator.startValidation()
                .throwIfInvalideParameterInstance(param, "Parameter must be an instance of Service", Service.class);
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        Service service = (Service) param;
        result = repository.findRecords(service, service.getAttributeValuesWhereCondition());
    }

    public Object getResult() {
        return result;
    }
}
