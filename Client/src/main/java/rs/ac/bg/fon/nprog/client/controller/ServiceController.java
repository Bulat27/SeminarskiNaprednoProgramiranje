
package rs.ac.bg.fon.nprog.client.controller;

import rs.ac.bg.fon.nprog.client.communication.Communication;
import rs.ac.bg.fon.nprog.commonlibrary.communication.Request;
import rs.ac.bg.fon.nprog.commonlibrary.communication.Response;
import rs.ac.bg.fon.nprog.commonlibrary.communication.util.Operation;
import rs.ac.bg.fon.nprog.commonlibrary.communication.util.ResponseType;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Service;

import java.util.List;

/**
 *
 * @author Dragon
 */
public class ServiceController {

    private static ServiceController instance;

    private ServiceController() {
    }

    public static ServiceController getInstance() {
        if (instance == null) {
            instance = new ServiceController();
        }
        return instance;
    }

    public void addService(Service service) throws Exception {
        Request request = new Request(Operation.ADD_SERVICE, service);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.ERROR)) {
            throw response.getException();
        }
    }

    public List<Service> getAllServices() throws Exception {
        Request request = new Request(Operation.GET_ALL_SERVICES, null);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Service>) response.getResult();
        }
        throw response.getException();
    }

    public void deleteService(Service service) throws Exception {
        Request request = new Request(Operation.DELETE_SERVICE, service);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.ERROR)) {
            throw response.getException();
        }
    }

    public List<Service> getServicesByCondition(Service s) throws Exception {
        Request request = new Request(Operation.GET_SERVICES_BY_CONDITION, s);
        Response response = Communication.getInstance().sendRequest(request);

        if (response.getResponseType().equals(ResponseType.SUCCESS)) {
            return (List<Service>) response.getResult();
        }
        throw response.getException();
    }
}
