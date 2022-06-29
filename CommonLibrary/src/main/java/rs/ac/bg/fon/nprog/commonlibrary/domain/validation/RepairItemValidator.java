package rs.ac.bg.fon.nprog.commonlibrary.domain.validation;

import rs.ac.bg.fon.nprog.commonlibrary.domain.EmployeeEngagement;
import rs.ac.bg.fon.nprog.commonlibrary.domain.RepairItem;
import rs.ac.bg.fon.nprog.commonlibrary.validation.ValidationException;

import java.math.BigDecimal;

/**
 *
 * @author Dragon
 */
public class RepairItemValidator {

    public void validateEmployeeExpense(RepairItem repairItem) throws ValidationException {
        if (repairItem == null || repairItem.getEmployeeEngagements() == null) {
            throw new ValidationException();
        } else {
            BigDecimal employeeExpense = BigDecimal.ZERO;

            for (EmployeeEngagement employeeEngagement : repairItem.getEmployeeEngagements()) {
                employeeExpense = employeeExpense.add(employeeEngagement.getEmployee().getHourlyRate()
                        .multiply(BigDecimal.valueOf(employeeEngagement.getDuration())));
            }

            if (repairItem.getEmployeeExpense().compareTo(employeeExpense) != 0) {
                throw new ValidationException();
            }
        }
    }
}
