package rs.ac.bg.fon.nprog.server.controller;

import rs.ac.bg.fon.nprog.commonlibrary.domain.*;
import rs.ac.bg.fon.nprog.server.system_operation.employee.*;
import rs.ac.bg.fon.nprog.server.system_operation.login.LoginSO;
import rs.ac.bg.fon.nprog.server.system_operation.repair.AddRepairSO;
import rs.ac.bg.fon.nprog.server.system_operation.repair.DeleteRepairSO;
import rs.ac.bg.fon.nprog.server.system_operation.repair.EditRepairSO;
import rs.ac.bg.fon.nprog.server.system_operation.repair.GetRepairsByFKConditionSO;
import rs.ac.bg.fon.nprog.server.system_operation.repair_item.GetRepairItemsByFKConditionSO;
import rs.ac.bg.fon.nprog.server.system_operation.service.AddServiceSO;
import rs.ac.bg.fon.nprog.server.system_operation.service.DeleteServiceSO;
import rs.ac.bg.fon.nprog.server.system_operation.service.GetAllServicesSO;
import rs.ac.bg.fon.nprog.server.system_operation.service.GetServicesByConditionSO;
import rs.ac.bg.fon.nprog.server.system_operation.service_book.*;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Dragon
 */
public class Controller {

    private static Controller instance;

    private ServerThread serverThread;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void startServer() throws IOException {
        if (serverThread == null || !serverThread.isAlive()) {

            serverThread = new ServerThread();
            serverThread.start();
        }
    }

    public void stopServer() throws IOException {
        serverThread.stopThread();
    }

    public Employee login(Employee requestEmployee) throws Exception {
        LoginSO loginSO = new LoginSO();
        loginSO.execute(requestEmployee);

        return (Employee) loginSO.getResult();
    }

    public void addService(Service service) throws Exception {
        AddServiceSO addServiceSO = new AddServiceSO();
        addServiceSO.execute(service);
    }

    public List<Service> getAllServices() throws Exception {
        GetAllServicesSO getAllServicesSO = new GetAllServicesSO();
        getAllServicesSO.execute(null);

        return (List<Service>) getAllServicesSO.getResult();
    }

    public void deleteService(Service service) throws Exception {
        DeleteServiceSO deleteServiceSO = new DeleteServiceSO();
        deleteServiceSO.execute(service);
    }

    public List<Service> getServicesByCondition(Service service) throws Exception {
        GetServicesByConditionSO getServicesByConditionSO = new GetServicesByConditionSO();
        getServicesByConditionSO.execute(service);

        return (List<Service>) getServicesByConditionSO.getResult();
    }

    public void addEmployee(Employee employee) throws Exception {
        AddEmployeeSO addEmployeeSO = new AddEmployeeSO();
        addEmployeeSO.execute(employee);
    }

    public List<Employee> getAllEmployees() throws Exception {
        GetAllEmployeesSO getAllEmployeesSO = new GetAllEmployeesSO();
        getAllEmployeesSO.execute(null);

        return (List<Employee>) getAllEmployeesSO.getResult();
    }

    public List<Employee> getEmployeesByCondition(Employee employee) throws Exception {
        GetEmployeesByConditionSO getEmployeesByConditionSO = new GetEmployeesByConditionSO();
        getEmployeesByConditionSO.execute(employee);

        return (List<Employee>) getEmployeesByConditionSO.getResult();
    }

    public void editEmployee(Employee employee) throws Exception {
        EditEmployeeSO editEmployeeSO = new EditEmployeeSO();
        editEmployeeSO.execute(employee);
    }

    public void deleteEmployee(Employee employee) throws Exception {
        DeleteEmployeeSO deleteEmployeeSO = new DeleteEmployeeSO();
        deleteEmployeeSO.execute(employee);
    }

    public void addServiceBook(ServiceBook serviceBook) throws Exception {
        AddServiceBookSO addServiceBookSO = new AddServiceBookSO();
        addServiceBookSO.execute(serviceBook);
    }

    public List<ServiceBook> getAllServiceBooks() throws Exception {
        GetAllServiceBooksSO getAllServiceBooksSO = new GetAllServiceBooksSO();
        getAllServiceBooksSO.execute(null);

        return (List<ServiceBook>) getAllServiceBooksSO.getResult();
    }

    public List<ServiceBook> getServiceBooksByCondition(ServiceBook serviceBook) throws Exception {
        GetServiceBooksByConditionSO getServiceBooksByConditionSO = new GetServiceBooksByConditionSO();
        getServiceBooksByConditionSO.execute(serviceBook);

        return (List<ServiceBook>) getServiceBooksByConditionSO.getResult();
    }

    public void deleteServiceBook(ServiceBook serviceBook) throws Exception {
        DeleteServiceBookSO deleteServiceBookSO = new DeleteServiceBookSO();
        deleteServiceBookSO.execute(serviceBook);
    }

    public void editServiceBook(ServiceBook serviceBook) throws Exception {
        EditServiceBookSO editServiceBookSO = new EditServiceBookSO();
        editServiceBookSO.execute(serviceBook);
    }

    public List<Repair> getRepairsByFKCondition(Repair repair) throws Exception {
        GetRepairsByFKConditionSO getRepairsByFKConditionSO = new GetRepairsByFKConditionSO();
        getRepairsByFKConditionSO.execute(repair);

        return (List<Repair>) getRepairsByFKConditionSO.getResult();
    }

    public List<RepairItem> getRepairItemsByFKCondition(RepairItem repairItem) throws Exception {
        GetRepairItemsByFKConditionSO getRepairItemsByFKConditionSO = new GetRepairItemsByFKConditionSO();
        getRepairItemsByFKConditionSO.execute(repairItem);

        return (List<RepairItem>) getRepairItemsByFKConditionSO.getResult();
    }

    public Repair addRepair(Repair repair) throws Exception {
        AddRepairSO addRepairSO = new AddRepairSO();
        addRepairSO.execute(repair);

        return (Repair) addRepairSO.getResult();
    }

    public void deleteRepair(Repair repair) throws Exception {
        DeleteRepairSO deleteRepairSO = new DeleteRepairSO();
        deleteRepairSO.execute(repair);
    }

    public void editRepair(Repair repair) throws Exception {
        EditRepairSO editRepairSO = new EditRepairSO();
        editRepairSO.execute(repair);
    }
}
