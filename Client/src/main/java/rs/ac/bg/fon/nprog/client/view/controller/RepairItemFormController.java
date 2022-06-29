package rs.ac.bg.fon.nprog.client.view.controller;

import rs.ac.bg.fon.nprog.client.constant.MyClientConstants;
import rs.ac.bg.fon.nprog.client.controller.EmployeeController;
import rs.ac.bg.fon.nprog.client.controller.ServiceController;
import rs.ac.bg.fon.nprog.client.view.coordinator.Coordinator;
import rs.ac.bg.fon.nprog.client.view.form.RepairItemForm;
import rs.ac.bg.fon.nprog.commonlibrary.domain.*;
import rs.ac.bg.fon.nprog.commonlibrary.validation.ValidationException;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.commonlibrary.view.util.FormMode;
import rs.ac.bg.fon.nprog.commonlibrary.view.util.RefreshMode;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dragon
 */
public class RepairItemFormController {

    private RepairItemForm repairItemForm;
    private FormMode formMode;
    private RepairItem selectedRepairItem;
    private int selectedRow;
    private Repair currentRepair;

    public RepairItemFormController(FormMode formMode, RepairItem selectedRepairItem, int selectedRow, Repair currentRepair) {
        this.formMode = formMode;
        this.selectedRepairItem = selectedRepairItem;
        this.selectedRow = selectedRow;
        this.currentRepair = currentRepair;
        this.repairItemForm = new RepairItemForm(Coordinator.getInstance().getRepairForm(), true, this);
    }

    public void openForm() throws Exception {
        prepareForm();
        repairItemForm.setVisible(true);
    }

    private void prepareForm() throws Exception {
        prepareServiceComboBox();
        prepareEmployeeList();
    }

    public void coordinateForms() {
        closeForm();
    }

    private void closeForm() {
        repairItemForm.dispose();
    }

    private void prepareServiceComboBox() throws Exception {
        List<Service> services = ServiceController.getInstance().getAllServices();
        JComboBox<Service> cmbService = repairItemForm.getCmbService();

        cmbService.removeAllItems();

        for (Service service : services) {
            cmbService.addItem(service);
        }
    }

    private void prepareEmployeeList() throws Exception {
        List<Employee> employees = EmployeeController.getInstance().getAllEmployees();
        JList<Object> jListEmployees = repairItemForm.getjListEmployees();

        jListEmployees.removeAll();

        jListEmployees.setListData(employees.toArray());
    }

    public void add(String startDate, String endDate, String remark, String additionalExpense, String additionalRevenue, String duration, Service service, List<Employee> employees) throws ValidationException {
        validate(startDate, endDate, remark, additionalExpense, additionalRevenue, duration, service, employees);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(MyClientConstants.DATE_PATTERN);

        BigDecimal employeeExpense = getEmployeeExpense(new BigDecimal(duration), employees);
        RepairItem repairItem = new RepairItem(currentRepair, currentRepair.getRepairItems().size() + 1,
                LocalDate.parse(startDate, dtf), LocalDate.parse(endDate, dtf), remark, employeeExpense, new BigDecimal(additionalExpense),
                new BigDecimal(additionalRevenue), service);

        List<EmployeeEngagement> employeeEngagements = getEmployeeEngagementList(repairItem, duration, employees);
        repairItem.setEmployeeEngagements(employeeEngagements);

        Coordinator.getInstance().refreshRepairForm(repairItem, RefreshMode.REFRESH_ADD);
    }

    private BigDecimal getEmployeeExpense(BigDecimal duration, List<Employee> employees) {
        BigDecimal employeeExpense = BigDecimal.ZERO;

        for (Employee employee : employees) {
            employeeExpense = employeeExpense.add(duration.multiply(employee.getHourlyRate()));
        }
        return employeeExpense;
    }

    private void validate(String startDate, String endDate, String remark, String additionalExpense, String additionalRevenue, String duration, Service service, List employees) throws ValidationException {
        Validator.startValidation()
                .validateValueIsDate(startDate, MyClientConstants.DATE_PATTERN, "Start date field is required and date must be in format: " + MyClientConstants.DATE_PATTERN)
                .validateValueIsDate(endDate, MyClientConstants.DATE_PATTERN, "End date field is required and date must be in format: " + MyClientConstants.DATE_PATTERN)
                .validateIsStartDateBeforeEndDate(startDate, endDate, MyClientConstants.DATE_PATTERN, "Start date must be before end date!")
                .validateNotNull(remark, "Remark must not be null!")
                .validateValueIsNonNegativeNumber(additionalExpense, "Additional expense is required and must be a non negative number!")
                .validateValueIsNonNegativeNumber(additionalRevenue, "Additional revenue is required and must be a non negative number!")
                .validateValueIsNonNegativeInteger(duration, "Duration is required and must be a non negative integer!")
                .validateNotNull(service, "Service is required and must not be null!")
                .validateListIsNotEmpty(employees, "At least one employee must be engaged!")
                .throwIfInvalide();
    }

    private List<EmployeeEngagement> getEmployeeEngagementList(RepairItem repairItem, String duration, List<Employee> employees) {
        List<EmployeeEngagement> employeeEngagements = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeEngagement employeeEngagement = new EmployeeEngagement(employee, repairItem, Integer.parseInt(duration));
            employeeEngagements.add(employeeEngagement);
        }
        return employeeEngagements;
    }
}
