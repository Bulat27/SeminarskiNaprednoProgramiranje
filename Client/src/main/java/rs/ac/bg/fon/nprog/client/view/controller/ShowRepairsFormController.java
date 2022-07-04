package rs.ac.bg.fon.nprog.client.view.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rs.ac.bg.fon.nprog.client.controller.RepairController;
import rs.ac.bg.fon.nprog.client.jsonreport.JsonReport;
import rs.ac.bg.fon.nprog.client.view.coordinator.Coordinator;
import rs.ac.bg.fon.nprog.client.view.form.ShowRepairsForm;
import rs.ac.bg.fon.nprog.client.view.form.model.TableModeRepairs;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Repair;
import rs.ac.bg.fon.nprog.commonlibrary.domain.ServiceBook;
import rs.ac.bg.fon.nprog.commonlibrary.view.util.RefreshMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dragon
 */
public class ShowRepairsFormController {

    private ShowRepairsForm showRepairsForm;
    private ServiceBook selectedServiceBook;
    private int selectedRow;

    public ShowRepairsFormController(ServiceBook serviceBook, int selectedRow) {
        this.selectedServiceBook = serviceBook;
        this.selectedRow = selectedRow;
        showRepairsForm = new ShowRepairsForm(Coordinator.getInstance().getShowServiceBooksForm(), true, this);
    }

    public ShowRepairsForm getShowRepairsForm() {
        return showRepairsForm;
    }

    public void openForm() throws Exception {
        prepareForm();
        showRepairsForm.setVisible(true);
    }

    private void prepareForm() throws Exception {
        prepareGeneralData();
        prepareTable();
    }

    private void prepareTable() throws Exception {
        Repair repair = getRepairWithCurrentServiceBook();

        List<Repair> repairs = RepairController.getInstance().getRepairsByFKCondition(repair);
        TableModeRepairs tmr = new TableModeRepairs(repairs);

        selectedServiceBook.setRepairs(repairs);
        showRepairsForm.setTableRepairsModel(tmr);
    }

    private void prepareGeneralData() {
        showRepairsForm.getLblServiceBookGeneralData().setText(selectedServiceBook.getGeneralData());
    }

    public void openAddRepairForm() throws Exception {
        Coordinator.getInstance().openAddRepairForm(getRepairWithCurrentServiceBook());
    }

    private Repair getRepairWithCurrentServiceBook() {
        Repair repair = new Repair();

        repair.setServiceBook(selectedServiceBook);

        return repair;
    }

    public void openEditRepairForm(int selectedRow) throws Exception {
        TableModeRepairs tmr = (TableModeRepairs) showRepairsForm.getTblRepairs().getModel();

        Coordinator.getInstance().openEditRepairForm(tmr.getRepair(selectedRow), selectedRow);
    }

    public void refreshForm(Repair repair, RefreshMode refreshMode, int selectedRow) {
        TableModeRepairs tmr = (TableModeRepairs) showRepairsForm.getTblRepairs().getModel();

        switch (refreshMode) {

            case REFRESH_ADD:
                tmr.addRepair(repair);
                break;

            case REFRESH_EDIT:
                if (selectedRow != -1) {
                    tmr.setRepair(repair, selectedRow);
                }
                break;

            default:
        }
    }

    public void delete(int selectedRow) throws Exception {
        TableModeRepairs tmr = (TableModeRepairs) showRepairsForm.getTblRepairs().getModel();
        Repair repair = tmr.getRepair(selectedRow);

        RepairController.getInstance().deleteRepair(repair);

        tmr.removeRepair(selectedRow);
    }


    public void jsonReport() {
        JsonReport.generateReport(selectedServiceBook);
    }
}
