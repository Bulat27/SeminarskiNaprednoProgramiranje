package rs.ac.bg.fon.nprog.client.view.controller;

import rs.ac.bg.fon.nprog.client.controller.ServiceBookController;
import rs.ac.bg.fon.nprog.client.view.coordinator.Coordinator;
import rs.ac.bg.fon.nprog.client.view.form.ServiceBookForm;
import rs.ac.bg.fon.nprog.commonlibrary.domain.ServiceBook;
import rs.ac.bg.fon.nprog.commonlibrary.validation.ValidationException;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;
import rs.ac.bg.fon.nprog.commonlibrary.view.util.FormMode;

import java.time.LocalDate;

import static rs.ac.bg.fon.nprog.commonlibrary.view.util.FormMode.EDIT;

/**
 *
 * @author Dragon
 */
public class ServiceBookFormController {

    private ServiceBookForm serviceBookForm;
    private FormMode formMode;
    private ServiceBook selectedServiceBook;
    private int selectedRow;

    public ServiceBookFormController(FormMode formMode, ServiceBook serviceBook, int selectedRow) {
        this.formMode = formMode;
        this.selectedServiceBook = serviceBook;
        this.selectedRow = selectedRow;
        serviceBookForm = new ServiceBookForm(Coordinator.getInstance().getMainForm(), true, this);
    }

    public void openForm() {
        prepareForm();
        serviceBookForm.setVisible(true);
    }

    public void closeForm() {
        serviceBookForm.dispose();
    }

    private void prepareForm() {
        if (formMode == EDIT && selectedServiceBook != null) {
            prepareFields();
        }
    }

    public void save(String clientFirstName, String clientLastName, String vehicleDescription, boolean active) throws Exception {
        validate(clientFirstName, clientLastName, vehicleDescription);

        ServiceBook serviceBook = new ServiceBook(clientFirstName, clientLastName, vehicleDescription, LocalDate.now(), active);

        executeSaving(serviceBook);
    }

    public void coordinateForms() {
        closeForm();
    }

    private void validate(String clientFirstName, String clientLastName, String vehicleDescription) throws ValidationException {
        Validator.startValidation()
                .validateValueIsAllAlphabets(clientFirstName, "Client first name field is required and must contain only alphabetic characters!")
                .validateValueIsAllAlphabets(clientLastName, "Client last name field is required and must contain only alphabetic characters!")
                .validateNotNullOrEmpty(vehicleDescription, "Vehicle description field is required!")
                .throwIfInvalide();
    }

    private void executeSaving(ServiceBook serviceBook) throws Exception {
        switch (formMode) {

            case ADD:
                add(serviceBook);
                break;

            case EDIT:
                edit(serviceBook);
                break;

            default:
        }
    }

    private void add(ServiceBook serviceBook) throws Exception {
        ServiceBookController.getInstance().addServiceBook(serviceBook);
    }

    private void prepareFields() {
        serviceBookForm.getTxtClientFirstName().setText(selectedServiceBook.getClientFirstName());
        serviceBookForm.getTxtClientLastName().setText(selectedServiceBook.getClientLastName());
        serviceBookForm.getTxtVehicleDescription().setText(selectedServiceBook.getVehicleDescription());
        serviceBookForm.getCheckBoxActive().setSelected(selectedServiceBook.isActive());
    }

    private void edit(ServiceBook serviceBook) throws Exception {
        if (selectedServiceBook != null) {
            serviceBook.setServiceBookID(selectedServiceBook.getServiceBookID());
            serviceBook.setInitialDate(selectedServiceBook.getInitialDate());

            ServiceBookController.getInstance().editServiceBook(serviceBook);

            if (selectedRow != -1) {
                Coordinator.getInstance().refreshShowServiceBooksForm(serviceBook, selectedRow);
            }
        }
    }
}
