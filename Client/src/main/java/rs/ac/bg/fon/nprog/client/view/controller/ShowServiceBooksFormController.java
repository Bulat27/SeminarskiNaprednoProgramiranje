package rs.ac.bg.fon.nprog.client.view.controller;

import rs.ac.bg.fon.nprog.client.controller.ServiceBookController;
import rs.ac.bg.fon.nprog.client.view.coordinator.Coordinator;
import rs.ac.bg.fon.nprog.client.view.form.ShowServiceBooksForm;
import rs.ac.bg.fon.nprog.client.view.form.model.TableModelServiceBooks;
import rs.ac.bg.fon.nprog.commonlibrary.domain.ServiceBook;
import rs.ac.bg.fon.nprog.commonlibrary.validation.ValidationException;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;

import java.util.List;

/**
 *
 * @author Dragon
 */
public class ShowServiceBooksFormController {

    private ShowServiceBooksForm showServiceBooksForm;

    public ShowServiceBooksFormController() {
        showServiceBooksForm = new ShowServiceBooksForm(Coordinator.getInstance().getMainForm(), true, this);
    }

    public ShowServiceBooksForm getShowServiceBooksForm() {
        return showServiceBooksForm;
    }

    public void openForm() throws Exception {
        prepareForm();
        showServiceBooksForm.setVisible(true);
    }

    private void prepareForm() throws Exception {
        prepareTable();
    }

    private void prepareTable() throws Exception {
        List<ServiceBook> serviceBooks = ServiceBookController.getInstance().getAllServiceBooks();
        TableModelServiceBooks tmsb = new TableModelServiceBooks(serviceBooks);

        showServiceBooksForm.setTableServiceBooksModel(tmsb);
    }

    public void search(String clientFirstName, String clientLastName) throws Exception {
        validate(clientFirstName, clientLastName);

        ServiceBook serviceBook = getServiceBookWithCondition(clientFirstName, clientLastName);

        List<ServiceBook> serviceBooks = ServiceBookController.getInstance().getServiceBooksByCondition(serviceBook);

        TableModelServiceBooks tmsb = (TableModelServiceBooks) showServiceBooksForm.getTblServiceBooks().getModel();
        tmsb.setServiceBooks(serviceBooks);
    }

    private void validate(String clientFirstName, String clientLastName) throws ValidationException {
        Validator.startValidation()
                .validateNotNull(clientFirstName, "Client first name must not be null!")
                .validateNotNull(clientLastName, "Client last name must not be null!")
                .throwIfInvalide();
    }

    private ServiceBook getServiceBookWithCondition(String clientFirstName, String clientLastName) {
        ServiceBook serviceBook = new ServiceBook();

        serviceBook.setClientFirstName(clientFirstName);
        serviceBook.setClientLastName(clientLastName);

        return serviceBook;
    }

    public void delete(int selectedRow) throws Exception {
        TableModelServiceBooks tmsb = (TableModelServiceBooks) showServiceBooksForm.getTblServiceBooks().getModel();
        ServiceBook serviceBook = tmsb.getServiceBook(selectedRow);

        ServiceBookController.getInstance().deleteServiceBook(serviceBook);

        tmsb.removeServiceBook(selectedRow);
    }

    public void openEditServiceBookForm(int selectedRow) {
        TableModelServiceBooks tmsb = (TableModelServiceBooks) showServiceBooksForm.getTblServiceBooks().getModel();

        Coordinator.getInstance().openEditServiceBookForm(tmsb.getServiceBook(selectedRow), selectedRow);
    }

    public void refreshShowServiceBooksForm(ServiceBook serviceBook, int selectedRow) {
        TableModelServiceBooks tmsb = (TableModelServiceBooks) showServiceBooksForm.getTblServiceBooks().getModel();

        tmsb.setServiceBook(serviceBook, selectedRow);
    }

    public void openShowRepairsForm(int selectedRow) throws Exception {
        TableModelServiceBooks tmsb = (TableModelServiceBooks) showServiceBooksForm.getTblServiceBooks().getModel();

        Coordinator.getInstance().openShowRepairsForm(tmsb.getServiceBook(selectedRow), selectedRow);
    }
}
