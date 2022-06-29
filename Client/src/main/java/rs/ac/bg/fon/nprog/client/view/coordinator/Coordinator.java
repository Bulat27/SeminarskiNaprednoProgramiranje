package rs.ac.bg.fon.nprog.client.view.coordinator;

import rs.ac.bg.fon.nprog.client.view.controller.*;
import rs.ac.bg.fon.nprog.client.view.form.MainForm;
import rs.ac.bg.fon.nprog.client.view.form.RepairForm;
import rs.ac.bg.fon.nprog.client.view.form.ShowRepairsForm;
import rs.ac.bg.fon.nprog.client.view.form.ShowServiceBooksForm;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Repair;
import rs.ac.bg.fon.nprog.commonlibrary.domain.RepairItem;
import rs.ac.bg.fon.nprog.commonlibrary.domain.ServiceBook;
import rs.ac.bg.fon.nprog.commonlibrary.view.util.RefreshMode;

import java.util.List;

import static rs.ac.bg.fon.nprog.commonlibrary.view.util.FormMode.ADD;
import static rs.ac.bg.fon.nprog.commonlibrary.view.util.FormMode.EDIT;

/**
 *
 * @author Dragon
 */
public class Coordinator {

    private static Coordinator instance;

    private LoginFormController loginController;
    private MainFormController mainFormController;
    private ServiceFormController serviceFormController;
    private ShowServicesFormController showServicesFormController;
    private EmployeeFormController employeeFormController;
    private ShowEmployeesFormController showEmployeesFormController;
    private ServiceBookFormController serviceBookFormController;
    private ShowServiceBooksFormController showServiceBooksFormController;
    private ShowRepairsFormController showRepairsFormController;
    private RepairFormController repairFormController;
    private RepairItemFormController repairItemFormController;
    private EngagedEmployeesFormController engagedEmployeesFormController;

    private Coordinator() {
    }

    public static Coordinator getInstance() {
        if (instance == null) {
            instance = new Coordinator();
        }
        return instance;
    }

    public void openLoginForm() {
        loginController = new LoginFormController();
        loginController.openForm();
    }

    public void openMainForm() {
        mainFormController = new MainFormController();
        mainFormController.openForm();
    }

    public void openServiceForm() {
        serviceFormController = new ServiceFormController();
        serviceFormController.openForm();
    }

    public MainForm getMainForm() {
        return mainFormController.getMainForm();
    }

    public ShowServiceBooksForm getShowServiceBooksForm() {
        return showServiceBooksFormController.getShowServiceBooksForm();
    }

    public RepairForm getRepairForm() {
        return repairFormController.getRepairForm();
    }

    public ShowRepairsForm getShowRepairsForm() {
        return showRepairsFormController.getShowRepairsForm();
    }

    public void openShowServicesForm() throws Exception {
        showServicesFormController = new ShowServicesFormController();
        showServicesFormController.openForm();
    }

    public void openAddEmployeeForm() {
        employeeFormController = new EmployeeFormController(ADD, null, -1);
        employeeFormController.openForm();
    }

    public void openShowEmployeesForm() throws Exception {
        showEmployeesFormController = new ShowEmployeesFormController();
        showEmployeesFormController.openForm();
    }

    public void openEditEmployeeForm(Employee employee, int selectedRow) {
        employeeFormController = new EmployeeFormController(EDIT, employee, selectedRow);
        employeeFormController.openForm();
    }

    public void refreshShowEmployeesForm(Employee employee, int selectedRow) {
        showEmployeesFormController.refreshShowEmployeesForm(employee, selectedRow);
    }

    public void openAddServiceBookForm() {
        serviceBookFormController = new ServiceBookFormController(ADD, null, -1);
        serviceBookFormController.openForm();
    }

    public void openShowServiceBooksForm() throws Exception {
        showServiceBooksFormController = new ShowServiceBooksFormController();
        showServiceBooksFormController.openForm();
    }

    public void openEditServiceBookForm(ServiceBook serviceBook, int selectedRow) {
        serviceBookFormController = new ServiceBookFormController(EDIT, serviceBook, selectedRow);
        serviceBookFormController.openForm();
    }

    public void refreshShowServiceBooksForm(ServiceBook serviceBook, int selectedRow) {
        showServiceBooksFormController.refreshShowServiceBooksForm(serviceBook, selectedRow);
    }

    public void openShowRepairsForm(ServiceBook serviceBook, int selectedRow) throws Exception {
        showRepairsFormController = new ShowRepairsFormController(serviceBook, selectedRow);
        showRepairsFormController.openForm();
    }

    public void openAddRepairForm(Repair repair) throws Exception {
        repairFormController = new RepairFormController(ADD, repair, -1);
        repairFormController.openForm();
    }

    public void openEditRepairForm(Repair repair, int selectedRow) throws Exception {
        repairFormController = new RepairFormController(EDIT, repair, selectedRow);
        repairFormController.openForm();
    }

    public void openAddRepairItemForm(Repair currentRepair) throws Exception {
        repairItemFormController = new RepairItemFormController(ADD, null, -1, currentRepair);
        repairItemFormController.openForm();
    }

    public void refreshRepairForm(RepairItem repairItem, RefreshMode refreshMode) {
        repairFormController.refreshRepairForm(repairItem, refreshMode);
    }

    public void refreshShowRepairsForm(Repair repair, RefreshMode refreshMode, int selectedRow) {
        showRepairsFormController.refreshForm(repair, refreshMode, selectedRow);
    }

    public void openEngagedEmployeesForm(List<Employee> employees) {
        engagedEmployeesFormController = new EngagedEmployeesFormController(employees);
        engagedEmployeesFormController.openForm();
    }
}
