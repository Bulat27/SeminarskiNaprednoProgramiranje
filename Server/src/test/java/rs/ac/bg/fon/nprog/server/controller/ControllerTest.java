package rs.ac.bg.fon.nprog.server.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.nprog.commonlibrary.domain.*;
import rs.ac.bg.fon.nprog.commonlibrary.domain.util.EmployeeRole;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    private Controller controllerSpy = spy(Controller.getInstance());

    @Mock
    private LoginSO loginSOMock;
    @Mock
    private AddServiceSO addServiceSOMock;
    @Mock
    private GetAllServicesSO getAllServicesSOMock;
    @Mock
    private DeleteServiceSO deleteServiceSOMock;
    @Mock
    private GetServicesByConditionSO getServicesByConditionSOMock;
    @Mock
    private AddEmployeeSO addEmployeeSOMock;
    @Mock
    private GetAllEmployeesSO getAllEmployeesSOMock;
    @Mock
    private GetEmployeesByConditionSO getEmployeesByConditionSOMock;
    @Mock
    private EditEmployeeSO editEmployeeSOMock;
    @Mock
    private DeleteEmployeeSO deleteEmployeeSOMock;
    @Mock
    private AddServiceBookSO addServiceBookSOMock;
    @Mock
    private GetAllServiceBooksSO getAllServiceBooksSOMock;
    @Mock
    private GetServiceBooksByConditionSO getServiceBooksByConditionSOMock;
    @Mock
    private DeleteServiceBookSO deleteServiceBookSOMock;
    @Mock
    private EditServiceBookSO editServiceBookSOMock;
    @Mock
    private GetRepairsByFKConditionSO getRepairsByFKConditionSOMock;
    @Mock
    private GetRepairItemsByFKConditionSO getRepairItemsByFKConditionSOMock;
    @Mock
    private AddRepairSO addRepairSOMock;
    @Mock
    private DeleteRepairSO deleteRepairSOMock;
    @Mock
    private EditRepairSO editRepairSOMock;

    @Test
    void testGetInstance(){
        Controller controller1 = Controller.getInstance();
        Controller controller2 = Controller.getInstance();

        assertSame(controller1, controller2);
    }

    @Test
    void testLoginUspesan() throws Exception {
        Employee requestEmployee = new Employee("bulat", "bulat");

        Employee resultEmployee = new Employee(1L, "Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN,
                LocalDate.of(2022, 1, 1), "bulat", "bulat");

        when(controllerSpy.createLoginSO()).thenReturn(loginSOMock);

        when(loginSOMock.getResult()).thenReturn(resultEmployee);

        assertSame(loginSOMock.getResult(), controllerSpy.login(requestEmployee));
        verify(loginSOMock).execute(requestEmployee);
    }

    @Test
    void testLoginNeuspesan() throws Exception {
        Employee requestEmployee = new Employee("bulat", "nepostojuci");

        when(controllerSpy.createLoginSO()).thenReturn(loginSOMock);

        doThrow(new Exception("Unknown employee!")).when(loginSOMock).execute(requestEmployee);

        Exception exception = assertThrows(java.lang.Exception.class, () -> controllerSpy.login(requestEmployee));

        String actualMessage = exception.getMessage();

        assertEquals("Unknown employee!", actualMessage);
    }

    @Test
    void testAddServiceUspesan() throws Exception {
        Service s1 = new Service(5l, BigDecimal.TEN, "Zamena filtera", "Promeniti filter goriva",
                BigDecimal.ONE);

        when(controllerSpy.createAddServiceSO()).thenReturn(addServiceSOMock);

        controllerSpy.addService(s1);
        verify(addServiceSOMock).execute(s1);
    }

    @Test
    void testAddServiceNeuspesan() throws Exception {
        Service s1 = new Service(5l, BigDecimal.TEN, "Zamena filtera", "Promeniti filter goriva", BigDecimal.ONE);

        when(controllerSpy.createAddServiceSO()).thenReturn(addServiceSOMock);

        doThrow(new Exception()).when(addServiceSOMock).execute(s1);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.addService(s1));
    }

    @Test
    void testGetAllServicesUspesan() throws Exception {
        Service s1 = new Service(5l, BigDecimal.TEN, "Zamena filtera", "Promeniti filter goriva", BigDecimal.ONE);
        Service s2 = new Service(3l, BigDecimal.ONE, "Zamena ulja", "Promeniti ulje", BigDecimal.ZERO);

        List<Service> services = new ArrayList<>();
        services.add(s1);
        services.add(s2);

        when(controllerSpy.createGetAllServicesSO()).thenReturn(getAllServicesSOMock);

        when(getAllServicesSOMock.getResult()).thenReturn(services);

        assertEquals(getAllServicesSOMock.getResult(), controllerSpy.getAllServices());
        verify(getAllServicesSOMock).execute(null);
    }

    @Test
    void testGetAllServicesNeuspesan() throws Exception {
        when(controllerSpy.createGetAllServicesSO()).thenReturn(getAllServicesSOMock);

        doThrow(new Exception()).when(getAllServicesSOMock).execute(null);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.getAllServices());
    }

    @Test
    void testDeleteServiceUspesan() throws Exception {
        Service s1 = new Service(5l, BigDecimal.TEN, "Zamena filtera", "Promeniti filter goriva",
                BigDecimal.ONE);

        when(controllerSpy.createDeleteServiceSO()).thenReturn(deleteServiceSOMock);

        controllerSpy.deleteService(s1);
        verify(deleteServiceSOMock).execute(s1);
    }

    @Test
    void testDeleteServiceNeuspesan() throws Exception {
        Service s1 = new Service(5l, BigDecimal.TEN, "Zamena filtera", "Promeniti filter goriva", BigDecimal.ONE);

        when(controllerSpy.createDeleteServiceSO()).thenReturn(deleteServiceSOMock);

        doThrow(new Exception()).when(deleteServiceSOMock).execute(s1);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.deleteService(s1));
    }

    @Test
    void testGetServicesByConditionUspesan() throws Exception {
        Service s1 = new Service(5l, BigDecimal.TEN, "Zamena filtera", "Promeniti filter goriva", BigDecimal.ONE);
        Service s2 = new Service(3l, BigDecimal.ONE, "Zamena ulja", "Promeniti ulje", BigDecimal.ZERO);

        List<Service> services = new ArrayList<>();
        services.add(s1);
        services.add(s2);

        when(controllerSpy.createGetServicesByConditionSO()).thenReturn(getServicesByConditionSOMock);

        when(getServicesByConditionSOMock.getResult()).thenReturn(services);

        assertEquals(getServicesByConditionSOMock.getResult(), controllerSpy.getServicesByCondition(s1));
        verify(getServicesByConditionSOMock).execute(s1);
    }

    @Test
    void testGetServicesByConditionNeuspesan() throws Exception {
        Service s1 = new Service(5l, BigDecimal.TEN, "Zamena filtera", "Promeniti filter goriva", BigDecimal.ONE);

        when(controllerSpy.createGetServicesByConditionSO()).thenReturn(getServicesByConditionSOMock);

        doThrow(new Exception()).when(getServicesByConditionSOMock).execute(s1);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.getServicesByCondition(s1));
    }

    @Test
    void testAddEmployeeUspesan() throws Exception {
        Employee e = new Employee(1L, "Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN,
                LocalDate.of(2022, 1, 1), "bulat", "bulat");

        when(controllerSpy.createAddEmployeeSO()).thenReturn(addEmployeeSOMock);

        controllerSpy.addEmployee(e);
        verify(addEmployeeSOMock).execute(e);
    }

    @Test
    void testAddEmployeeNeuspesan() throws Exception {
        Employee e = new Employee(1L, "Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN,
                LocalDate.of(2022, 1, 1), "bulat", "bulat");

        when(controllerSpy.createAddEmployeeSO()).thenReturn(addEmployeeSOMock);

        doThrow(new Exception()).when(addEmployeeSOMock).execute(e);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.addEmployee(e));
    }

    @Test
    void testGetAllEmployeesUspesan() throws Exception {
        Employee e1 = new Employee(1L, "Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN,
                LocalDate.of(2022, 1, 1), "bulat", "bulat");
        Employee e2 = new Employee(2L, "Jovan", "Jovanovic", EmployeeRole.WORKER, BigDecimal.TEN,
                LocalDate.of(2022, 1, 1), "jovan", "jovan");

        List<Employee> employees = new ArrayList<>();
        employees.add(e1);
        employees.add(e2);

        when(controllerSpy.createGetAllEmployeesSO()).thenReturn(getAllEmployeesSOMock);

        when(getAllEmployeesSOMock.getResult()).thenReturn(employees);

        assertEquals(getAllEmployeesSOMock.getResult(), controllerSpy.getAllEmployees());
        verify(getAllEmployeesSOMock).execute(null);
    }

    @Test
    void testGetAllEmployeesNeuspesan() throws Exception {
        when(controllerSpy.createGetAllEmployeesSO()).thenReturn(getAllEmployeesSOMock);

        doThrow(new Exception()).when(getAllEmployeesSOMock).execute(null);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.getAllEmployees());
    }

    @Test
    void testGetEmployeesByConditionUspesan() throws Exception {
        Employee e1 = new Employee(1L, "Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN,
                LocalDate.of(2022, 1, 1), "bulat", "bulat");
        Employee e2 = new Employee(2L, "Jovan", "Jovanovic", EmployeeRole.WORKER, BigDecimal.TEN,
                LocalDate.of(2022, 1, 1), "jovan", "jovan");

        List<Employee> employees = new ArrayList<>();
        employees.add(e1);
        employees.add(e2);

        when(controllerSpy.createGetEmployeesByConditionSO()).thenReturn(getEmployeesByConditionSOMock);

        when(getEmployeesByConditionSOMock.getResult()).thenReturn(employees);

        assertEquals(getEmployeesByConditionSOMock.getResult(), controllerSpy.getEmployeesByCondition(e1));
        verify(getEmployeesByConditionSOMock).execute(e1);
    }

    @Test
    void testGetEmployeesByConditionNeuspesan() throws Exception {
        Employee e1 = new Employee(1L, "Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN,
                LocalDate.of(2022, 1, 1), "bulat", "bulat");

        when(controllerSpy.createGetEmployeesByConditionSO()).thenReturn(getEmployeesByConditionSOMock);

        doThrow(new Exception()).when(getEmployeesByConditionSOMock).execute(e1);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.getEmployeesByCondition(e1));
    }

    @Test
    void testEditEmployeeUspesan() throws Exception {
        Employee e = new Employee(1L, "Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN,
                LocalDate.of(2022, 1, 1), "bulat", "bulat");

        when(controllerSpy.createEditEmployeeSO()).thenReturn(editEmployeeSOMock);

        controllerSpy.editEmployee(e);
        verify(editEmployeeSOMock).execute(e);
    }

    @Test
    void testEditEmployeeNeuspesan() throws Exception {
        Employee e = new Employee(1L, "Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN,
                LocalDate.of(2022, 1, 1), "bulat", "bulat");

        when(controllerSpy.createEditEmployeeSO()).thenReturn(editEmployeeSOMock);

        doThrow(new Exception()).when(editEmployeeSOMock).execute(e);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.editEmployee(e));
    }

    @Test
    void testDeleteEmployeeUspesan() throws Exception {
        Employee e = new Employee(1L, "Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN,
                LocalDate.of(2022, 1, 1), "bulat", "bulat");

        when(controllerSpy.createDeleteEmployeeSO()).thenReturn(deleteEmployeeSOMock);

        controllerSpy.deleteEmployee(e);
        verify(deleteEmployeeSOMock).execute(e);
    }

    @Test
    void testDeleteEmployeeNeuspesan() throws Exception {
        Employee e = new Employee(1L, "Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN,
                LocalDate.of(2022, 1, 1), "bulat", "bulat");

        when(controllerSpy.createDeleteEmployeeSO()).thenReturn(deleteEmployeeSOMock);

        doThrow(new Exception()).when(deleteEmployeeSOMock).execute(e);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.deleteEmployee(e));
    }

    @Test
    void testAddServiceBookUspesan() throws Exception {
        ServiceBook sb = new ServiceBook(5l, "Jovan", "Jovanovic", "Peugeot 508 1.6",
                LocalDate.of(2022, 1, 1), true);

        when(controllerSpy.createAddServiceBookSO()).thenReturn(addServiceBookSOMock);

        controllerSpy.addServiceBook(sb);
        verify(addServiceBookSOMock).execute(sb);
    }

    @Test
    void testAddServiceBookNeuspesan() throws Exception {
        ServiceBook sb = new ServiceBook(5l, "Jovan", "Jovanovic", "Peugeot 508 1.6",
                LocalDate.of(2022, 1, 1), true);

        when(controllerSpy.createAddServiceBookSO()).thenReturn(addServiceBookSOMock);

        doThrow(new Exception()).when(addServiceBookSOMock).execute(sb);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.addServiceBook(sb));
    }

    @Test
    void testGetAllServiceBooksUspesan() throws Exception {
        ServiceBook sb1 = new ServiceBook(5l, "Jovan", "Jovanovic", "Peugeot 508 1.6",
                LocalDate.of(2022, 1, 1), true);
        ServiceBook sb2 = new ServiceBook(6l, "Boban", "Bobanovic", "Lancia Lybra 1.9",
                LocalDate.of(2022, 1, 1), true);

        List<ServiceBook> serviceBooks= new ArrayList<>();
        serviceBooks.add(sb1);
        serviceBooks.add(sb2);

        when(controllerSpy.createGetAllServiceBooksSO()).thenReturn(getAllServiceBooksSOMock);

        when(getAllServiceBooksSOMock.getResult()).thenReturn(serviceBooks);

        assertEquals(getAllServiceBooksSOMock.getResult(), controllerSpy.getAllServiceBooks());
        verify(getAllServiceBooksSOMock).execute(null);
    }

    @Test
    void testGetAllServiceBooksNeuspesan() throws Exception {
        when(controllerSpy.createGetAllServiceBooksSO()).thenReturn(getAllServiceBooksSOMock);

        doThrow(new Exception()).when(getAllServiceBooksSOMock).execute(null);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.getAllServiceBooks());
    }

    @Test
    void testGetServiceBooksByConditionUspesan() throws Exception {
        ServiceBook sb1 = new ServiceBook(5l, "Jovan", "Jovanovic", "Peugeot 508 1.6",
                LocalDate.of(2022, 1, 1), true);
        ServiceBook sb2 = new ServiceBook(6l, "Boban", "Bobanovic", "Lancia Lybra 1.9",
                LocalDate.of(2022, 1, 1), true);

        List<ServiceBook> serviceBooks= new ArrayList<>();
        serviceBooks.add(sb1);
        serviceBooks.add(sb2);

        when(controllerSpy.createGetServiceBooksByConditionSO()).thenReturn(getServiceBooksByConditionSOMock);

        when(getServiceBooksByConditionSOMock.getResult()).thenReturn(serviceBooks);

        assertEquals(getServiceBooksByConditionSOMock.getResult(), controllerSpy.getServiceBooksByCondition(sb1));
        verify(getServiceBooksByConditionSOMock).execute(sb1);
    }

    @Test
    void testGetServiceBooksByConditionNeuspesan() throws Exception {
        ServiceBook sb1 = new ServiceBook(5l, "Jovan", "Jovanovic", "Peugeot 508 1.6",
                LocalDate.of(2022, 1, 1), true);

        when(controllerSpy.createGetServiceBooksByConditionSO()).thenReturn(getServiceBooksByConditionSOMock);

        doThrow(new Exception()).when(getServiceBooksByConditionSOMock).execute(sb1);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.getServiceBooksByCondition(sb1));
    }

    @Test
    void testDeleteServiceBookUspesan() throws Exception {
        ServiceBook sb1 = new ServiceBook(5l, "Jovan", "Jovanovic", "Peugeot 508 1.6",
                LocalDate.of(2022, 1, 1), true);

        when(controllerSpy.createDeleteServiceBookSO()).thenReturn(deleteServiceBookSOMock);

        controllerSpy.deleteServiceBook(sb1);
        verify(deleteServiceBookSOMock).execute(sb1);
    }

    @Test
    void testDeleteServiceBookNeuspesan() throws Exception {
        ServiceBook sb1 = new ServiceBook(5l, "Jovan", "Jovanovic", "Peugeot 508 1.6",
                LocalDate.of(2022, 1, 1), true);

        when(controllerSpy.createDeleteServiceBookSO()).thenReturn(deleteServiceBookSOMock);

        doThrow(new Exception()).when(deleteServiceBookSOMock).execute(sb1);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.deleteServiceBook(sb1));
    }

    @Test
    void testEditServiceBookUspesan() throws Exception {
        ServiceBook sb1 = new ServiceBook(5l, "Jovan", "Jovanovic", "Peugeot 508 1.6",
                LocalDate.of(2022, 1, 1), true);

        when(controllerSpy.createEditServiceBookSO()).thenReturn(editServiceBookSOMock);

        controllerSpy.editServiceBook(sb1);
        verify(editServiceBookSOMock).execute(sb1);
    }

    @Test
    void testEditServiceBookNeuspesan() throws Exception {
        ServiceBook sb1 = new ServiceBook(5l, "Jovan", "Jovanovic", "Peugeot 508 1.6",
                LocalDate.of(2022, 1, 1), true);

        when(controllerSpy.createEditServiceBookSO()).thenReturn(editServiceBookSOMock);

        doThrow(new Exception()).when(editServiceBookSOMock).execute(sb1);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.editServiceBook(sb1));
    }

    @Test
    void testGetRepairsByFKConditionUspesan() throws Exception {
        Repair r1 = new Repair(16l, BigDecimal.TEN, BigDecimal.TEN, "Zamena ulja", LocalDate.of(2022,1 , 1), null);
        Repair r2 = new Repair(1l, BigDecimal.ONE, BigDecimal.TEN, "Zamena filtera", LocalDate.of(2022,4, 4), null);

        List<Repair> repairs = new ArrayList<>();
        repairs.add(r1);
        repairs.add(r2);

        when(controllerSpy.createGetRepairsByFKConditionSO()).thenReturn(getRepairsByFKConditionSOMock);

        when(getRepairsByFKConditionSOMock.getResult()).thenReturn(repairs);

        assertEquals(getRepairsByFKConditionSOMock.getResult(), controllerSpy.getRepairsByFKCondition(r1));
        verify(getRepairsByFKConditionSOMock).execute(r1);
    }

    @Test
    void testGetRepairsByFKConditionNeuspesan() throws Exception {
        Repair r1 = new Repair(16l, BigDecimal.TEN, BigDecimal.TEN, "Zamena ulja", LocalDate.of(2022,1 , 1), null);

        when(controllerSpy.createGetRepairsByFKConditionSO()).thenReturn(getRepairsByFKConditionSOMock);

        doThrow(new Exception()).when(getRepairsByFKConditionSOMock).execute(r1);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.getRepairsByFKCondition(r1));
    }

    @Test
    void testGetRepairItemsByFKConditionUspesan() throws Exception {
       RepairItem ri1 = new RepairItem(null, 5, LocalDate.of(2022, 2, 1), LocalDate.of(2022, 5, 3),
                "Napomena", BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO, null);
        RepairItem ri2 = new RepairItem(null, 3, LocalDate.of(2021, 1, 1), LocalDate.of(2023, 3, 3),
                "Dodatna napomena", BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO, null);

        List<RepairItem> repairItems = new ArrayList<>();
        repairItems.add(ri1);
        repairItems.add(ri2);

        when(controllerSpy.createGetRepairItemsByFKConditionSO()).thenReturn(getRepairItemsByFKConditionSOMock);

        when(getRepairItemsByFKConditionSOMock.getResult()).thenReturn(repairItems);

        assertEquals(getRepairItemsByFKConditionSOMock.getResult(), controllerSpy.getRepairItemsByFKCondition(ri1));
        verify(getRepairItemsByFKConditionSOMock).execute(ri1);
    }

    @Test
    void testGetRepairItemsByFKConditionNeuspesan() throws Exception {
        RepairItem ri1 = new RepairItem(null, 5, LocalDate.of(2022, 2, 1), LocalDate.of(2022, 5, 3),
                "Napomena", BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO, null);

        when(controllerSpy.createGetRepairItemsByFKConditionSO()).thenReturn(getRepairItemsByFKConditionSOMock);

        doThrow(new Exception()).when(getRepairItemsByFKConditionSOMock).execute(ri1);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.getRepairItemsByFKCondition(ri1));
    }

    @Test
    void testAddRepairUspesan() throws Exception {
        Repair r1 = new Repair(16l, BigDecimal.TEN, BigDecimal.TEN, "Zamena ulja", LocalDate.of(2022,1 , 1), null);

        when(controllerSpy.createAddRepairSO()).thenReturn(addRepairSOMock);

        controllerSpy.addRepair(r1);
        verify(addRepairSOMock).execute(r1);
    }

    @Test
    void testAddRepairNeuspesan() throws Exception {
        Repair r1 = new Repair(16l, BigDecimal.TEN, BigDecimal.TEN, "Zamena ulja", LocalDate.of(2022,1 , 1), null);

        when(controllerSpy.createAddRepairSO()).thenReturn(addRepairSOMock);

        doThrow(new Exception()).when(addRepairSOMock).execute(r1);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.addRepair(r1));
    }

    @Test
    void testDeleteRepairUspesan() throws Exception {
        Repair r1 = new Repair(16l, BigDecimal.TEN, BigDecimal.TEN, "Zamena ulja", LocalDate.of(2022,1 , 1), null);

        when(controllerSpy.createDeleteRepairSO()).thenReturn(deleteRepairSOMock);

        controllerSpy.deleteRepair(r1);
        verify(deleteRepairSOMock).execute(r1);
    }

    @Test
    void testDeleteRepairNeuspesan() throws Exception {
        Repair r1 = new Repair(16l, BigDecimal.TEN, BigDecimal.TEN, "Zamena ulja", LocalDate.of(2022,1 , 1), null);

        when(controllerSpy.createDeleteRepairSO()).thenReturn(deleteRepairSOMock);

        doThrow(new Exception()).when(deleteRepairSOMock).execute(r1);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.deleteRepair(r1));
    }

    @Test
    void testEditRepairUspesan() throws Exception {
        Repair r1 = new Repair(16l, BigDecimal.TEN, BigDecimal.TEN, "Zamena ulja", LocalDate.of(2022,1 , 1), null);

        when(controllerSpy.createEditRepairSO()).thenReturn(editRepairSOMock);

        controllerSpy.editRepair(r1);
        verify(editRepairSOMock).execute(r1);
    }

    @Test
    void testEditRepairNeuspesan() throws Exception {
        Repair r1 = new Repair(16l, BigDecimal.TEN, BigDecimal.TEN, "Zamena ulja", LocalDate.of(2022,1 , 1), null);

        when(controllerSpy.createEditRepairSO()).thenReturn(editRepairSOMock);

        doThrow(new Exception()).when(editRepairSOMock).execute(r1);

        assertThrows(java.lang.Exception.class, () -> controllerSpy.editRepair(r1));
    }
}