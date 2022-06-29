package rs.ac.bg.fon.nprog.client.view.controller;

import rs.ac.bg.fon.nprog.client.constant.MyClientConstants;
import rs.ac.bg.fon.nprog.client.controller.RepairController;
import rs.ac.bg.fon.nprog.client.controller.RepairItemController;
import rs.ac.bg.fon.nprog.client.view.coordinator.Coordinator;
import rs.ac.bg.fon.nprog.client.view.form.RepairForm;
import rs.ac.bg.fon.nprog.client.view.form.model.TableModelRepairItems;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Repair;
import rs.ac.bg.fon.nprog.commonlibrary.domain.RepairItem;
import rs.ac.bg.fon.nprog.commonlibrary.validation.ValidationException;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.commonlibrary.view.util.FormMode;
import rs.ac.bg.fon.nprog.commonlibrary.view.util.RefreshMode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static rs.ac.bg.fon.nprog.commonlibrary.view.util.FormMode.EDIT;
import static rs.ac.bg.fon.nprog.commonlibrary.view.util.RefreshMode.REFRESH_ADD;
import static rs.ac.bg.fon.nprog.commonlibrary.view.util.RefreshMode.REFRESH_EDIT;

/**
 *
 * @author Dragon
 */
public class RepairFormController {

    private RepairForm repairForm;
    private FormMode formMode;
    private Repair currentRepair;
    private int selectedRow;

    public RepairFormController(FormMode formMode, Repair selectedRepair, int selectedRow) {
        this.formMode = formMode;
        this.currentRepair = selectedRepair;
        this.selectedRow = selectedRow;
        this.repairForm = new RepairForm(Coordinator.getInstance().getShowRepairsForm(), true, this);
    }

    public RepairForm getRepairForm() {
        return repairForm;
    }

    public void openForm() throws Exception {
        prepareForm();
        repairForm.setVisible(true);
    }

    public void coordinateForms() {
        closeForm();
    }

    private void closeForm() {
        repairForm.dispose();
    }

    private void prepareForm() throws Exception {
        prepareTable();

        if (formMode == EDIT && currentRepair != null) {
            prepareFields();
        }
        System.out.println(currentRepair.getServiceBook().toString() + " " + currentRepair.getServiceBook().getServiceBookID());
    }

    private void prepareTable() throws Exception {
        List<RepairItem> repairItems = getRepairItemList();
        TableModelRepairItems tmri = new TableModelRepairItems(repairItems);

        repairForm.setTableRepairItemsModel(tmri);
    }

    private List<RepairItem> getRepairItemList() throws Exception {
        switch (formMode) {

            case ADD:
                return new ArrayList<>();

            case EDIT:
                RepairItem repairItem = getRepairItemWithFKCondition();
                List<RepairItem> repairItems = RepairItemController.getInstance().getRepairItemsByFKCondition(repairItem);

                currentRepair.setRepairItems(repairItems);

                return repairItems;

            default:
                throw new Exception("Invalid FormMode type!");
        }
    }

    private RepairItem getRepairItemWithFKCondition() {
        RepairItem repairItem = new RepairItem();

        repairItem.setRepair(currentRepair);

        return repairItem;
    }

    private void prepareFields() {
        repairForm.getTxtName().setText(currentRepair.getName());

        updateDerivedFields();
    }

    public void openAddRepairItemForm() throws Exception {
        Coordinator.getInstance().openAddRepairItemForm(currentRepair);
    }

    public void refreshRepairForm(RepairItem repairItem, RefreshMode refreshMode) {
        updateCurrentRepair(repairItem, refreshMode);
        updateRepairFormComponents(refreshMode);
    }

    private void updateCurrentRepair(RepairItem repairItem, RefreshMode refreshMode) {
        updateRepairItems(repairItem, refreshMode);

        LocalDate startDate = getEarliestStartDate();
        BigDecimal riTotalRevenue = repairItem.getService().getPrice().add(repairItem.getAdditionalRevenue());
        BigDecimal riTotalExpense = repairItem.getEmployeeExpense().add(repairItem.getAdditionalExpense().add(repairItem.getService().getMaterialCost()));

        currentRepair.setStartDate(startDate);
        updateFinancialData(riTotalRevenue, riTotalExpense, refreshMode);
    }

    private void updateRepairItems(RepairItem repairItem, RefreshMode refreshMode) {
        switch (refreshMode) {

            case REFRESH_ADD:
                currentRepair.getRepairItems().add(repairItem);
                break;

            case REFRESH_DELETE:
                currentRepair.getRepairItems().remove(repairItem);
                break;

            default:
        }
    }

    private void updateFinancialData(BigDecimal riTotalRevenue, BigDecimal riTotalExpense, RefreshMode refreshMode) {
        switch (refreshMode) {

            case REFRESH_ADD:
                currentRepair.setTotalRevenue(currentRepair.getTotalRevenue().add(riTotalRevenue));
                currentRepair.setTotalExpense(currentRepair.getTotalExpense().add(riTotalExpense));
                break;

            case REFRESH_DELETE:
                currentRepair.setTotalRevenue(currentRepair.getTotalRevenue().subtract(riTotalRevenue));
                currentRepair.setTotalExpense(currentRepair.getTotalExpense().subtract(riTotalExpense));
                break;

            default:
        }
    }

    private LocalDate getEarliestStartDate() {
        LocalDate earliestDate = LocalDate.MAX;

        for (RepairItem repairItem : currentRepair.getRepairItems()) {
            if (repairItem.getStartDate() != null && repairItem.getStartDate().isBefore(earliestDate)) {
                earliestDate = repairItem.getStartDate();
            }
        }
//        return !earliestDate.equals(LocalDate.MAX) ? earliestDate : LocalDate.MAX;
        return earliestDate;
    }

    private void updateRepairFormComponents(RefreshMode refreshMode) {
        TableModelRepairItems tmri = (TableModelRepairItems) repairForm.getTblRepairItems().getModel();
        tmri.setRepairItems(currentRepair.getRepairItems());

        updateDerivedFields();

        if (refreshMode == RefreshMode.REFRESH_DELETE) {
            tmri.resetOrderNumbers();
        }
    }

    private void updateDerivedFields() {
        if (currentRepair.getStartDate() != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(MyClientConstants.DATE_PATTERN);
            repairForm.getTxtStartDate().setText(currentRepair.getStartDate().format(dtf));
        } else {
            repairForm.getTxtStartDate().setText("");
        }
        repairForm.getTxtTotalRevenue().setText(String.valueOf(currentRepair.getTotalRevenue()));
        repairForm.getTxtTotalExpense().setText(String.valueOf(currentRepair.getTotalExpense()));
    }

    public void save(String name) throws Exception {
        validate(name);

        currentRepair.setName(name);

        executeSaving();
    }

    private void validate(String name) throws ValidationException {
        Validator.startValidation()
                .validateNotNullOrEmpty(name, "Name field is required!")
                .validateListIsNotEmpty(currentRepair.getRepairItems(), "Repair must have at least one repair item!")
                .throwIfInvalide();
    }

    private void executeSaving() throws Exception {
        switch (formMode) {

            case EDIT:
                editRepair();
                Coordinator.getInstance().refreshShowRepairsForm(currentRepair, REFRESH_EDIT, selectedRow);
                break;

            case ADD:
                currentRepair = addRepair();
                Coordinator.getInstance().refreshShowRepairsForm(currentRepair, REFRESH_ADD, -1);
                break;

            default:
        }
    }

    private Repair addRepair() throws Exception {
        return RepairController.getInstance().addRepair(currentRepair);
    }

    public void delete(int selectedRow) {
        TableModelRepairItems tmri = (TableModelRepairItems) repairForm.getTblRepairItems().getModel();
        RepairItem repairItem = tmri.getRepairItem(selectedRow);

        refreshRepairForm(repairItem, RefreshMode.REFRESH_DELETE);
    }

    private void editRepair() throws Exception {
        RepairController.getInstance().editRepair(currentRepair);
    }

    public void showDetails(int selectedRow) {
        TableModelRepairItems tmri = (TableModelRepairItems) repairForm.getTblRepairItems().getModel();
        RepairItem repairItem = tmri.getRepairItem(selectedRow);

        Coordinator.getInstance().openEngagedEmployeesForm(repairItem.getEmployeeList());
    }
}
