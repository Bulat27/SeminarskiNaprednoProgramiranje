package rs.ac.bg.fon.nprog.client.view.controller;

import rs.ac.bg.fon.nprog.client.controller.ServiceController;
import rs.ac.bg.fon.nprog.client.view.coordinator.Coordinator;
import rs.ac.bg.fon.nprog.client.view.form.ServiceForm;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Service;
import rs.ac.bg.fon.nprog.commonlibrary.validation.ValidationException;
import rs.ac.bg.fon.nprog.commonlibrary.validation.Validator;

import java.math.BigDecimal;

/**
 *
 * @author Dragon
 */
public class ServiceFormController {

    private ServiceForm serviceForm;

    public ServiceFormController() {
        serviceForm = new ServiceForm(Coordinator.getInstance().getMainForm(), true, this);
    }

    public void openForm() {
        serviceForm.setVisible(true);
    }

    public void closeForm() {
        serviceForm.dispose();
    }

    public void save(String price, String name, String description, String materialCost) throws Exception {
        validate(price, name, materialCost);
        Service service = new Service(new BigDecimal(price), name, description, new BigDecimal(materialCost));

        ServiceController.getInstance().addService(service);
    }

    public void coordinateForms() {
        closeForm();
    }

    private void validate(String price, String name, String materialCost) throws ValidationException {
        Validator.startValidation()
                .validateNotNullOrEmpty(name, "Name field is required!")
                .validateValueIsNonNegativeNumber(price, "Price is required and must be a non negative number!")
                .validateValueIsNonNegativeNumber(materialCost, "Material cost is required and must be a non negative number!")
                .throwIfInvalide();
    }
}
