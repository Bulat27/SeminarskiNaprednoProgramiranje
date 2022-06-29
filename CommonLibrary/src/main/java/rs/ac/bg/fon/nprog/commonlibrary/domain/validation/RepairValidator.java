package rs.ac.bg.fon.nprog.commonlibrary.domain.validation;

import domain.Repair;
import domain.RepairItem;
import validation.ValidationException;

import java.math.BigDecimal;

/**
 *
 * @author Dragon
 */
public class RepairValidator {

    public void validateTotalRevenue(Repair repair) throws ValidationException {
        if (repair == null || repair.getRepairItems() == null) {
            throw new ValidationException();
        } else {
            BigDecimal totalRevenue = BigDecimal.ZERO;

            for (RepairItem repairItem : repair.getRepairItems()) {
                totalRevenue = totalRevenue.add(repairItem.getService().getPrice().add(repairItem.getAdditionalRevenue()));
            }

            if (repair.getTotalRevenue().compareTo(totalRevenue) != 0) {
                throw new ValidationException();
            }
        }
    }

    public void validateTotalExpense(Repair repair) throws ValidationException {
        if (repair == null || repair.getRepairItems() == null) {
            throw new ValidationException();
        } else {
            BigDecimal totalExpense = BigDecimal.ZERO;

            for (RepairItem repairItem : repair.getRepairItems()) {
                totalExpense = totalExpense.add(repairItem.getService().getMaterialCost().add(repairItem.getEmployeeExpense().add(repairItem.getAdditionalExpense())));
            }

            if (repair.getTotalExpense().compareTo(totalExpense) != 0) {
                throw new ValidationException();
            }
        }
    }
}
