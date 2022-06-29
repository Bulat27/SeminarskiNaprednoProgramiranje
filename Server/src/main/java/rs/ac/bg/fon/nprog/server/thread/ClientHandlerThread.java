package rs.ac.bg.fon.nprog.server.thread;

import rs.ac.bg.fon.nprog.commonlibrary.communication.Receiver;
import rs.ac.bg.fon.nprog.commonlibrary.communication.Request;
import rs.ac.bg.fon.nprog.commonlibrary.communication.Response;
import rs.ac.bg.fon.nprog.commonlibrary.communication.Sender;
import rs.ac.bg.fon.nprog.commonlibrary.communication.util.Operation;
import rs.ac.bg.fon.nprog.commonlibrary.communication.util.ResponseType;
import rs.ac.bg.fon.nprog.commonlibrary.domain.*;
import rs.ac.bg.fon.nprog.commonlibrary.view.util.RefreshMode;
import rs.ac.bg.fon.nprog.server.controller.Controller;
import rs.ac.bg.fon.nprog.server.thread.coordinator.ThreadCoordinator;
import rs.ac.bg.fon.nprog.server.view.coordinator.ViewCoordinator;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Dragon
 */
public class ClientHandlerThread extends Thread {

    private Socket socket;//TODO: These can probably be final!
    private Sender sender;
    private Receiver receiver;
    private Employee authenticatedEmployee;

    public ClientHandlerThread(Socket socket) {
        this.socket = socket;
        this.sender = new Sender(socket);
        this.receiver = new Receiver(socket);
    }

    public Employee getAuthenticatedEmployee() {
        return authenticatedEmployee;
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {

                Request request = (Request) receiver.receive();
                Response response = handleRequest(request);
                sender.send(response);

            }
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);//TODO: Don't just log it, find a way to notify the
            //user of what happened.
            ThreadCoordinator.getInstance().removeClientHandlerThread(this);
            ViewCoordinator.getInstance().refreshMainForm(authenticatedEmployee, RefreshMode.REFRESH_DELETE);
        }
    }

    private Response handleRequest(Request request) {
        Operation operation = request.getOperation();

        switch (operation) {
            case LOGIN:
                return login(request);
            case ADD_SERVICE:
                return addService(request);
            case GET_ALL_SERVICES:
                return getAllServices(request);
            case DELETE_SERVICE:
                return deleteService(request);
            case GET_SERVICES_BY_CONDITION:
                return getServicesByCondition(request);
            case ADD_EMPLOYEE:
                return addEmployee(request);
            case GET_ALL_EMPLOYEES:
                return getAllEmployees(request);
            case GET_EMPLOYEES_BY_CONDITION:
                return getEmployeesByCondition(request);
            case EDIT_EMPLOYEE:
                return editEmployee(request);
            case DELETE_EMPLOYEE:
                return deleteEmployee(request);
            case ADD_SERVICE_BOOK:
                return addServiceBook(request);
            case GET_ALL_SERVICE_BOOKS:
                return getAllServiceBooks(request);
            case GET_SERVICE_BOOKS_BY_CONDITION:
                return getServiceBooksByCondition(request);
            case DELETE_SERVICE_BOOK:
                return deleteServiceBook(request);
            case EDIT_SERVICE_BOOK:
                return editServiceBook(request);
            case GET_REPAIRS_BY_FK_CONDITION:
                return getRepairsByFKCondition(request);
            case GET_REPAIR_ITEMS_BY_FK_CONDITION:
                return getRepairItemsByFKCondition(request);
            case ADD_REPAIR:
                return addRepair(request);
            case DELETE_REPAIR:
                return deleteRepair(request);
            case EDIT_REPAIR:
                return editRepair(request);
//            case LOGOUT:
//                return logout(request);    
            default:
                return null;//TODO: Maybe throw an Exception or something, not the best idea to return null
        }
    }

    private Response login(Request request) {
        Response response = new Response();

        Employee requestEmployee = (Employee) request.getArgument();

        try {
            Employee employee = Controller.getInstance().login(requestEmployee);
            System.out.println("Successful authentication!");
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(employee);

            this.authenticatedEmployee = employee;//TODO: Think about whether this is a good solution!
            ViewCoordinator.getInstance().refreshMainForm(authenticatedEmployee, RefreshMode.REFRESH_ADD);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    public void stopThread() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    //    private Response logout(Request request) {
//        Response response = new Response();
//        
////        try {       
////            Employee employee = Controller.getInstance().login(service);
////            System.out.println("Successful authentication!");
////            response.setResponseType(ResponseType.SUCCESS);
////            response.setResult(employee);
////            this.authenticatedEmployee = employee;//TODO: Think about whether this is a good solution!
////            mainForm.addEmployee(employee);
////        } catch (Exception ex) {
////            ex.printStackTrace();//TODO: Delete this!
////            response.setResponseType(ResponseType.ERROR);
////            response.setException(ex);
////        }
//        
//        return response;
//    }
    private Response addService(Request request) {
        Response response = new Response();

        Service service = (Service) request.getArgument();

        try {
            Controller.getInstance().addService(service);
            System.out.println("Successfully added Service!");
            response.setResponseType(ResponseType.SUCCESS);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getAllServices(Request request) {
        Response response = new Response();

        try {
            List<Service> services = Controller.getInstance().getAllServices();
            System.out.println("Successful retrieval of Services!");
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(services);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response deleteService(Request request) {
        Response response = new Response();

        Service service = (Service) request.getArgument();

        try {
            Controller.getInstance().deleteService(service);
            System.out.println("Successfully deleted Service!");
            response.setResponseType(ResponseType.SUCCESS);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getServicesByCondition(Request request) {
        Response response = new Response();

        Service service = (Service) request.getArgument();

        try {
            List<Service> services = Controller.getInstance().getServicesByCondition(service);
            System.out.println("Successful retrieval of Services by condition!");
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(services);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response addEmployee(Request request) {
        Response response = new Response();

        Employee employee = (Employee) request.getArgument();

        try {
            Controller.getInstance().addEmployee(employee);
            System.out.println("Successfully added Employee!");
            response.setResponseType(ResponseType.SUCCESS);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getAllEmployees(Request request) {
        Response response = new Response();

        try {
            List<Employee> employees = Controller.getInstance().getAllEmployees();
            System.out.println("Successful retrieval of Employees!");
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(employees);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getEmployeesByCondition(Request request) {
        Response response = new Response();

        Employee employee = (Employee) request.getArgument();

        try {
            List<Employee> employees = Controller.getInstance().getEmployeesByCondition(employee);
            System.out.println("Successful retrieval of Employees by condition!");
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(employees);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response editEmployee(Request request) {
        Response response = new Response();

        Employee employee = (Employee) request.getArgument();

        try {
            Controller.getInstance().editEmployee(employee);
            System.out.println("Successfully edited Employee!");
            response.setResponseType(ResponseType.SUCCESS);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response deleteEmployee(Request request) {
        Response response = new Response();

        Employee employee = (Employee) request.getArgument();

        try {
            Controller.getInstance().deleteEmployee(employee);
            System.out.println("Successfully deleted Employee!");
            response.setResponseType(ResponseType.SUCCESS);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response addServiceBook(Request request) {
        Response response = new Response();

        ServiceBook serviceBook = (ServiceBook) request.getArgument();

        try {
            Controller.getInstance().addServiceBook(serviceBook);
            System.out.println("Successfully added Service book!");
            response.setResponseType(ResponseType.SUCCESS);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getAllServiceBooks(Request request) {
        Response response = new Response();

        try {
            List<ServiceBook> serviceBooks = Controller.getInstance().getAllServiceBooks();
            System.out.println("Successful retrieval of Service books!");
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(serviceBooks);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getServiceBooksByCondition(Request request) {
        Response response = new Response();

        ServiceBook serviceBook = (ServiceBook) request.getArgument();

        try {
            List<ServiceBook> serviceBooks = Controller.getInstance().getServiceBooksByCondition(serviceBook);
            System.out.println("Successful retrieval of Service books by condition!");
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(serviceBooks);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response deleteServiceBook(Request request) {
        Response response = new Response();

        ServiceBook serviceBook = (ServiceBook) request.getArgument();

        try {
            Controller.getInstance().deleteServiceBook(serviceBook);
            System.out.println("Successfully deleted Service book!");
            response.setResponseType(ResponseType.SUCCESS);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response editServiceBook(Request request) {
        Response response = new Response();

        ServiceBook serviceBook = (ServiceBook) request.getArgument();

        try {
            Controller.getInstance().editServiceBook(serviceBook);
            System.out.println("Successfully edited Service book!");
            response.setResponseType(ResponseType.SUCCESS);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getRepairsByFKCondition(Request request) {
        Response response = new Response();

        Repair repair = (Repair) request.getArgument();

        try {
            List<Repair> repairs = Controller.getInstance().getRepairsByFKCondition(repair);
            System.out.println("Successful retrieval of Repairs by FK condition!");
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(repairs);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response getRepairItemsByFKCondition(Request request) {
        Response response = new Response();

        RepairItem repairItem = (RepairItem) request.getArgument();

        try {
            List<RepairItem> repairItems = Controller.getInstance().getRepairItemsByFKCondition(repairItem);
            System.out.println("Successful retrieval of Repair items by FK condition!");
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(repairItems);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response addRepair(Request request) {
        Response response = new Response();

        Repair requestRepair = (Repair) request.getArgument();

        try {
            Repair repair = Controller.getInstance().addRepair(requestRepair);
            System.out.println("Successfully added Repair!");
            response.setResponseType(ResponseType.SUCCESS);
            response.setResult(repair);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response deleteRepair(Request request) {
        Response response = new Response();

        Repair repair = (Repair) request.getArgument();

        try {
            Controller.getInstance().deleteRepair(repair);
            System.out.println("Successfully deleted Repair!");
            response.setResponseType(ResponseType.SUCCESS);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }

    private Response editRepair(Request request) {
        Response response = new Response();

        Repair repair = (Repair) request.getArgument();

        try {
            Controller.getInstance().editRepair(repair);
            System.out.println("Successfully edited Repair!");
            response.setResponseType(ResponseType.SUCCESS);
        } catch (Exception ex) {
            Logger.getLogger(ClientHandlerThread.class.getName()).log(Level.SEVERE, null, ex);
            response.setResponseType(ResponseType.ERROR);
            response.setException(ex);
        }
        return response;
    }
}
