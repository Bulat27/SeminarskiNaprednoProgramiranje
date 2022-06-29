package rs.ac.bg.fon.nprog.server.system_operation.repair_item;


import rs.ac.bg.fon.nprog.commonlibrary.domain.EmployeeEngagement;
import rs.ac.bg.fon.nprog.commonlibrary.domain.RepairItem;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.server.system_operation.AbstractSO;
import rs.ac.bg.fon.nprog.server.system_operation.employee_engagement.GetEmployeeEngagementsByFKConditionSO;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Dragon
 */
public class GetRepairItemsByFKConditionSO extends AbstractSO {

    @Override
    protected void precondition(Object param) throws Exception {
        Validator.startValidation()
                .throwIfInvalideParameterInstance(param, "Parameter must be an instance of RepairItem", RepairItem.class);
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        RepairItem repairItem = (RepairItem) param;
//        result = repository.findRecords(repairItem, repairItem.getFKWhereCondition());
        List<RepairItem> repairItems = repository.findRecords(repairItem, repairItem.getFKWhereCondition());

        for (RepairItem ri : repairItems) {
            EmployeeEngagement employeeEngagementWithFKCondition = getEmployeeEngagementWithFKCondition(ri);

//            List<EmployeeEngagement> employeeEngagements
//                    = repository.findRecords(employeeEngagementWithFKCondition, employeeEngagementWithFKCondition.getFKWhereCondition());
            GetEmployeeEngagementsByFKConditionSO getEmployeeEngagementsByFKConditionSO = new GetEmployeeEngagementsByFKConditionSO(repository);
            getEmployeeEngagementsByFKConditionSO.executeAsSuboperation(employeeEngagementWithFKCondition);

            ri.setEmployeeEngagements((List<EmployeeEngagement>) getEmployeeEngagementsByFKConditionSO.getResult());
        }
        sortByOrderNumber(repairItems);

        result = repairItems;
    }

    public Object getResult() {
        return result;
    }

    private EmployeeEngagement getEmployeeEngagementWithFKCondition(RepairItem repairItem) {
        EmployeeEngagement employeeEngagement = new EmployeeEngagement();

        employeeEngagement.setRepairItem(repairItem);

        return employeeEngagement;
    }

    private void sortByOrderNumber(List<RepairItem> repairItems) {
        Collections.sort(repairItems, new Comparator<RepairItem>() {
            @Override
            public int compare(RepairItem ri1, RepairItem ri2) {
                return ri1.getOrderNumber() - ri2.getOrderNumber();
            }
        });
    }
}
