package rs.ac.bg.fon.nprog.server.system_operation.service_book;


import rs.ac.bg.fon.nprog.commonlibrary.domain.ServiceBook;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.server.system_operation.AbstractSO;

/**
 *
 * @author Dragon
 */
public class GetServiceBooksByConditionSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        Validator.startValidation()
                .throwIfInvalideParameterInstance(param, "Parameter must be an instance of ServiceBook", ServiceBook.class);
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        ServiceBook serviceBook = (ServiceBook) param;
        result = repository.findRecords(serviceBook, serviceBook.getAttributeValuesWhereCondition());
    }

    public Object getResult() {
        return result;
    }
}
