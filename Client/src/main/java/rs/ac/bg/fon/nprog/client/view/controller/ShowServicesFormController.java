package rs.ac.bg.fon.nprog.client.view.controller;

import rs.ac.bg.fon.nprog.client.controller.ServiceController;
import rs.ac.bg.fon.nprog.client.view.coordinator.Coordinator;
import rs.ac.bg.fon.nprog.client.view.form.ShowServicesForm;
import rs.ac.bg.fon.nprog.client.view.form.model.TableModelServices;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Service;
import rs.ac.bg.fon.nprog.commonlibrary.validation.ValidationException;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;

import java.util.List;

/**
 *
 * @author Dragon
 */
public class ShowServicesFormController {

    private ShowServicesForm showServicesForm;

    public ShowServicesFormController() {
        showServicesForm = new ShowServicesForm(Coordinator.getInstance().getMainForm(), true, this);
    }

    public void openForm() throws Exception {
        prepareForm();
        showServicesForm.setVisible(true);
    }

    private void prepareForm() throws Exception {
        prepareTable();
    }

    private void prepareTable() throws Exception {
        List<Service> services = ServiceController.getInstance().getAllServices();
        TableModelServices tms = new TableModelServices(services);

        showServicesForm.setTableServicesModel(tms);
    }

    public void delete(int selectedRow) throws Exception {
        TableModelServices tms = (TableModelServices) showServicesForm.getTblServices().getModel();
        Service service = tms.getService(selectedRow);

        ServiceController.getInstance().deleteService(service);

        tms.removeService(selectedRow);
    }

    public void search(String name) throws Exception {
        validate(name);

        Service s = getServiceWithCondition(name);

        List<Service> services = ServiceController.getInstance().getServicesByCondition(s);

        TableModelServices tms = (TableModelServices) showServicesForm.getTblServices().getModel();
        tms.setServices(services);
    }

    private Service getServiceWithCondition(String name) {
        Service s = new Service();
        s.setName(name);
        return s;
    }

    private void validate(String name) throws ValidationException {
        Validator.startValidation()
                .validateNotNull(name, "Name must not be null!")
                .throwIfInvalide();
    }
}
