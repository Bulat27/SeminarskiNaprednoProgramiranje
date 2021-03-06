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
import rs.ac.bg.fon.nprog.server.thread.ServerThread;

import java.io.IOException;
import java.util.List;

/**
 * Predstavlja klasu u okviru koje se pozivaju sve sistemske operacije.
 * Controller je zaduzen da izabere i kreira odgovarajucu sistemsku operaciju za zahtev koji je stigao.
 * Nakon toga, Controller prihvata i vraca rezultat te sistemske operacije.
 * Implementiran je pomocu Singleton paterna kako bi se osiguralo kreiranje samo jedne instance Controller-a.
 *
 * @author Dragon
 * @version 1.0
 */
public class Controller {

    /**
     * Instanca klase Controller koja ce se kreirati samo jednom prilikom prvog zahteva za nju. Svaki sledeci
     * put kada je potreban Controller bice vracena prvobitno kreirana instanca.
     */
    private static Controller instance;

    /**
     * Referenca ka serverskoj niti za cije je pokretanje zaduzen Controller.
     */
    private ServerThread serverThread;

    /**
     * Privatni konstruktor koji osigurava da se instanca klase Controller moze napraviti jedino pozivom
     * metode getInstance().
     */
    private Controller() {
    }

    /**
     * Kreira novu instancu klase Controller samo ukoliko ona vec ne postoji, a zatim vraca postojecu ili novokreiranu
     * instancu.
     *
     * @return postojeca ili novokreirana instanca klase Controller
     */
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Kreira i pokrece novu serversku nit u okviru koje ce se osluskuju zahtevi klijenata.
     *
     * @throws IOException ukoliko nastane greska vezana za ulazno-izlaznu operaciju (kreiranje i rad sa socket-om) prilikom kreiranja serverske niti
     */
    public void startServer() throws IOException {
        if (serverThread == null || !serverThread.isAlive()) {

            serverThread = new ServerThread();
            serverThread.start();
        }
    }

    /**
     * Zaustavlja serversku nit cime se prekida osluskivanje zahteva klijenata.
     *
     * @throws IOException ukoliko nastane greska vezana za ulazno-izlaznu operaciju (zatvaranje socket-a) prilikom zaustavljanja serverske niti
     */
    public void stopServer() throws IOException {
        serverThread.stopThread();
    }

    /**
     * Poziva sistemsku operaciju za prijavu korisnika.
     * Ukoliko je prijava uspesna vraca odgovarajuci objekat koji predstavlja prijavljenog korisnika, a u slucaju
     * da nije baca izuzetak.
     *
     * @param requestEmployee objekat klase Employee sa kredencijalima potrebnim za prijavu korisnika
     * @return vraca ulogovanog korisnika kao Employee
     * @throws Exception ukoliko prijava korisnika nije uspesna
     */
    public Employee login(Employee requestEmployee) throws Exception {
        LoginSO loginSO = createLoginSO();
        loginSO.execute(requestEmployee);

        return (Employee) loginSO.getResult();
    }

    /**
     * Poziva sistemsku operaciju za cuvanje nove usluge u bazi podataka.
     * Ukoliko nastane greske prilikom cuvanja, metoda baca izuzetak.
     *
     * @param service usluga koju je potrebno sacuvati u bazi podataka
     * @throws Exception ukoliko nastane greske prilikom cuvanja nove usluge
     */
    public void addService(Service service) throws Exception {
        AddServiceSO addServiceSO = createAddServiceSO();
        addServiceSO.execute(service);
    }

    /**
     * Poziva sistemsku operaciju koja vraca sve usluge koje vec postoje u bazi podataka.
     * Ukoliko nastane greska prilikom ucitavanja usluga, metoda baca izuzetak.
     *
     * @return usluge iz baze podataka kao listu objekata klase Service
     * @throws Exception ukoliko nastane greska prilikom ucitavanja usluga
     */
    public List<Service> getAllServices() throws Exception {
        GetAllServicesSO getAllServicesSO = createGetAllServicesSO();
        getAllServicesSO.execute(null);

        return (List<Service>) getAllServicesSO.getResult();
    }

    /**
     * Poziva sistemsku operaciju za brisanje usluge iz baze podataka.
     * Ukoliko nastane greska prilikom brisanja, metoda baca izuzetak.
     *
     * @param service usluga koju je potrebno obrisati iz baze podataka
     * @throws Exception ukoliko nastane greska prilikom brisanja usluge
     */
    public void deleteService(Service service) throws Exception {
        DeleteServiceSO deleteServiceSO = createDeleteServiceSO();
        deleteServiceSO.execute(service);
    }

    /**
     * Poziva sistemsku operaciju koja filtrira i vraca usluge iz baze podataka.
     * Filtriranje se sprovodi na osnovu naziva usluge.
     * Ukoliko nastane greska prilikom filtriranja ili ucitavanja usluga, metoda baca izuzetak.
     *
     * @param service usluga sa postavljen vrednoscu naziva na osnovu kojeg se filtriraju usluge
     * @return filtrirana lista usluga kao lista objekata klase Service
     * @throws Exception ukoliko nastane greska prilikom filtriranja ili ucitavanja usluga
     */
    public List<Service> getServicesByCondition(Service service) throws Exception {
        GetServicesByConditionSO getServicesByConditionSO = createGetServicesByConditionSO();
        getServicesByConditionSO.execute(service);

        return (List<Service>) getServicesByConditionSO.getResult();
    }

    /**
     * Poziva sistemsku operaciju za cuvanje novog zaposlenog u bazi podataka.
     * Ukoliko nastane greska prilikom cuvanja, metoda baca izuzetak.
     *
     * @param employee zaposleni kojeg je potrebno sacuvati u bazi podataka
     * @throws Exception ukoliko nastane greska prilikom cuvanja zaposlenog
     */
    public void addEmployee(Employee employee) throws Exception {
        AddEmployeeSO addEmployeeSO = createAddEmployeeSO();
        addEmployeeSO.execute(employee);
    }

    /**
     * Poziva sistemsku operaciju koja vraca sve zaposlene koji vec postoje u bazi podataka.
     * Ukoliko nastane greska prilikom ucitavanja zaposlenih, metoda baca izuzetak.
     *
     * @return zaposlene iz baze podataka kao listu objekata klase Employee
     * @throws Exception ukoliko nastane greska prilikom ucitavanja zaposlenih
     */
    public List<Employee> getAllEmployees() throws Exception {
        GetAllEmployeesSO getAllEmployeesSO = createGetAllEmployeesSO();
        getAllEmployeesSO.execute(null);

        return (List<Employee>) getAllEmployeesSO.getResult();
    }

    /**
     * Poziva sistemsku operaciju koja filtrira i vraca zaposlene iz baze podataka.
     * Filtriranje se sprovodi na osnovu imena ili prezimena zaposlenog.
     * Ukoliko nastane greska prilikom filtriranja ili ucitavanja zaposlenih, metoda baca izuzetak.
     *
     * @param employee zaposleni sa postavljim vrednostima imena i prezimena na osnovu kojih se filtriraju usluge
     * @return filtrirana lista zaposlenih kao lista objekata klase Employee
     * @throws Exception ukoliko nastane greska prilikom filtriranja ili ucitavanja zaposlenih
     */
    public List<Employee> getEmployeesByCondition(Employee employee) throws Exception {
        GetEmployeesByConditionSO getEmployeesByConditionSO = createGetEmployeesByConditionSO();
        getEmployeesByConditionSO.execute(employee);

        return (List<Employee>) getEmployeesByConditionSO.getResult();
    }

    /**
     * Poziva sistemsku operaciju koja azurira vrednosti zaposlenog koji vec postoji u bazi podataka.
     * Ukoliko nastane greska prilikom azuriranja vrednosti zaposlenog, metoda baca izuzetak.
     *
     * @param employee zaposleni sa novim vrednostima koje treba postaviti u bazi podataka
     * @throws Exception ukoliko nastane greska prilikom azuriranja vrednosti zaposlenog
     */
    public void editEmployee(Employee employee) throws Exception {
        EditEmployeeSO editEmployeeSO = createEditEmployeeSO();
        editEmployeeSO.execute(employee);
    }

    /**
     * Poziva sistemsku operaciju za brisanje zaposlenog iz baze podataka.
     * Ukoliko nastane greska prilikom brisanja, metoda baca izuzetak.
     *
     * @param employee zaposleni kojeg je potrebno obrisati iz baze podataka
     * @throws Exception ukoliko nastane greska prilikom brisanja zaposlenog iz baze podataka
     */
    public void deleteEmployee(Employee employee) throws Exception {
        DeleteEmployeeSO deleteEmployeeSO = createDeleteEmployeeSO();
        deleteEmployeeSO.execute(employee);
    }

    /**
     * Poziva sistemsku operaciju za cuvanje nove servisne knjizice u bazi podataka.
     * Podrazumeva otvaranja nove servisne knjizice bez ijednog servisa koji se naknadno dodaju.
     * Ukoliko nastane greska prilikom cuvanja, metoda baca izuzetak.
     *
     * @param serviceBook servisna knjizica koju je potrebno sacuvati u bazi podataka
     * @throws Exception ukoliko nastane greska prilikom cuvanja servisne knjizice
     */
    public void addServiceBook(ServiceBook serviceBook) throws Exception {
        AddServiceBookSO addServiceBookSO = createAddServiceBookSO();
        addServiceBookSO.execute(serviceBook);
    }

    /**
     * Poziva sistemsku operaciju koja vraca sve servisne knjizice koje vec postoje u bazi podataka.
     * Ukoliko nastane greska prilikom ucitavanja servisnih knjizica, metoda baca izuzetak.
     *
     * @return servisne knjizice iz baze podataka kao listu objekata klase ServiceBook
     * @throws Exception ukoliko nastane greska prilikom ucitavanja servisnih knjizica
     */
    public List<ServiceBook> getAllServiceBooks() throws Exception {
        GetAllServiceBooksSO getAllServiceBooksSO = createGetAllServiceBooksSO();
        getAllServiceBooksSO.execute(null);

        return (List<ServiceBook>) getAllServiceBooksSO.getResult();
    }

    /**
     * Poziva sistemsku operaciju koja filtrira i vraca servisne knjizice iz baze podataka.
     * Filtriranje se sprovodi na osnovu imena ili prezimena klijenta.
     * Ukoliko nastane greska prilikom filtriranja ili ucitavanja servisnih knjizica, metoda baca izuzetak.
     *
     * @param serviceBook servisna knjizica sa postavljim vrednostima imena i prezimena klijenta na osnovu kojih se filtriraju servisne knjizice
     * @return filtrirana lista servisnih knjizica kao lista objekata klase ServiceBook
     * @throws Exception ukoliko nastane greska prilikom filtriranja ili ucitavanja servisnih knjizica
     */
    public List<ServiceBook> getServiceBooksByCondition(ServiceBook serviceBook) throws Exception {
        GetServiceBooksByConditionSO getServiceBooksByConditionSO = createGetServiceBooksByConditionSO();
        getServiceBooksByConditionSO.execute(serviceBook);

        return (List<ServiceBook>) getServiceBooksByConditionSO.getResult();
    }

    /**
     * Poziva sistemsku operaciju za brisanje servisne knjizice iz baze podataka.
     * Ukoliko nastane greska prilikom brisanja, metoda baca izuzetak.
     *
     * @param serviceBook servisna knjizica koju je potrebno obrisati iz baze podataka
     * @throws Exception ukoliko nastane greska prilikom brisanja servisne knjizice iz baze podataka
     */
    public void deleteServiceBook(ServiceBook serviceBook) throws Exception {
        DeleteServiceBookSO deleteServiceBookSO = createDeleteServiceBookSO();
        deleteServiceBookSO.execute(serviceBook);
    }

    /**
     * Poziva sistemsku operaciju koja azurira vrednosti servisne knjizice koja vec postoji u bazi podataka.
     * Ukoliko nastane greska prilikom azuriranja vrednosti servisne knjizice, metoda baca izuzetak.
     *
     * @param serviceBook servisna knjizica sa novim vrednostima koje treba postaviti u bazi podataka
     * @throws Exception ukoliko nastane greska prilikom azuriranja vrednosti servisne knjizice
     */
    public void editServiceBook(ServiceBook serviceBook) throws Exception {
        EditServiceBookSO editServiceBookSO = createEditServiceBookSO();
        editServiceBookSO.execute(serviceBook);
    }

    /**
     * Poziva sistemsku operaciju koja filtrira i vraca servise koji vec postoje u bazi podataka.
     * Filtriranje se vrsi na osnovu servisne knjizice kojoj servis pripada.
     * Ukoliko nastane greska prilikom filtriranja ili ucitavanja servisa, metoda baca izuzetak.
     *
     * @param repair servis sa postavljenom vrednoscu servisne knjizice na osnovu koje se filtriraju servisi
     * @return filtrirana lista servisa kao lista objekata klase Repair
     * @throws Exception ukoliko nastane greska prilikom filtriranja ili ucitavanja servisa
     */
    public List<Repair> getRepairsByFKCondition(Repair repair) throws Exception {
        GetRepairsByFKConditionSO getRepairsByFKConditionSO = createGetRepairsByFKConditionSO();
        getRepairsByFKConditionSO.execute(repair);

        return (List<Repair>) getRepairsByFKConditionSO.getResult();
    }

    /**
     * Poziva sistemsku operaciju koja filtrira i vraca stavke servisa koje vec postoje u bazi podataka.
     * Filtriranje se vrsi na osnovu servisa kom stavke servisa pripadaju.
     * Ukoliko nastane greska prilikom filtriranja ili ucitavanja stavki servisa, metoda baca izuzetak.
     *
     * @param repairItem stavka servisa sa postavljenom vrednoscu servisa na osnovu koje se filtriraju stavke servisa
     * @return filtrirana lista stavki servisa kao lista objekata klase RepairItem
     * @throws Exception ukoliko nastane greska prilikom filtriranja ili ucitavanja stavki servisa
     */
    public List<RepairItem> getRepairItemsByFKCondition(RepairItem repairItem) throws Exception {
        GetRepairItemsByFKConditionSO getRepairItemsByFKConditionSO = createGetRepairItemsByFKConditionSO();
        getRepairItemsByFKConditionSO.execute(repairItem);

        return (List<RepairItem>) getRepairItemsByFKConditionSO.getResult();
    }

    /**
     * Poziva sistemsku operaciju za cuvanje novog servisa u bazi podataka.
     * Ukoliko nastane greska prilikom cuvanja, metoda baca izuzetak, a u suprotnom vraca sacuvani servis.
     *
     * @param repair servis koji je potrebno sacuvati u bazi podataka
     * @return servis koji je sacuvan u bazi podataka kao objekat klase Repair
     * @throws Exception ukoliko nastane greska prilikom cuvanja servisa
     */
    public Repair addRepair(Repair repair) throws Exception {
        AddRepairSO addRepairSO = createAddRepairSO();
        addRepairSO.execute(repair);

        return (Repair) addRepairSO.getResult();
    }

    /**
     * Poziva sistemsku operaciju za brisanje servisa iz baze podataka.
     * Ukoliko nastane greska prilikom brisanja, metoda baca izuzetak.
     *
     * @param repair servis koji je potrebno obrisati iz baze podataka
     * @throws Exception ukoliko nastane greska prilikom brisanja servisa iz baze podataka
     */
    public void deleteRepair(Repair repair) throws Exception {
        DeleteRepairSO deleteRepairSO = createDeleteRepairSO();
        deleteRepairSO.execute(repair);
    }

    /**
     * Poziva sistemsku operaciju koja azurira vrednosti servisa koji vec postoji u bazi podataka.
     * Ukoliko nastane greska prilikom azuriranja vrednosti servisa, metoda baca izuzetak.
     *
     * @param repair servis sa novim vrednostima koje treba postaviti u bazi podataka
     * @throws Exception ukoliko nastane greska prilikom azuriranja vrednosti servisa
     */
    public void editRepair(Repair repair) throws Exception {
        EditRepairSO editRepairSO = createEditRepairSO();
        editRepairSO.execute(repair);
    }

    /**
     * Vraca novu instancu sistemske operacije LoginSO.
     *
     * @return nova instanca sistemske operacije LoginSO
     */
    protected LoginSO createLoginSO(){
        return new LoginSO();
    }

    /**
     * Vraca novu instancu sistemske operacije AddServiceSO.
     *
     * @return nova instanca sistemske operacije AddServiceSO
     */
    protected AddServiceSO createAddServiceSO(){
        return new AddServiceSO();
    }

    /**
     * Vraca novu instancu sistemske operacije GetAllServicesSO.
     *
     * @return nova instanca sistemske operacije GetAllServicesSO
     */
    protected GetAllServicesSO createGetAllServicesSO() {
        return new GetAllServicesSO();
    }

    /**
     * Vraca novu instancu sistemske operacije DeleteServiceSO.
     *
     * @return nova instanca sistemske operacije DeleteServiceSO
     */
    protected DeleteServiceSO createDeleteServiceSO(){
        return new DeleteServiceSO();
    }

    /**
     * Vraca novu instancu sistemske operacije GetServicesByConditionSO.
     *
     * @return nova instanca sistemske operacije GetServicesByConditionSO
     */
    protected GetServicesByConditionSO createGetServicesByConditionSO(){
        return new GetServicesByConditionSO();
    }

    /**
     * Vraca novu instancu sistemske operacije AddEmployeeSO.
     *
     * @return nova instanca sistemske operacije AddEmployeeSO
     */
    protected AddEmployeeSO createAddEmployeeSO(){
        return new AddEmployeeSO();
    }

    /**
     * Vraca novu instancu sistemske operacije GetAllEmployeesSO .
     *
     * @return nova instanca sistemske operacije GetAllEmployeesSO
     */
    protected GetAllEmployeesSO createGetAllEmployeesSO(){
        return new GetAllEmployeesSO();
    }

    /**
     * Vraca novu instancu sistemske operacije GetEmployeesByConditionSO.
     *
     * @return nova instanca sistemske operacije GetEmployeesByConditionSO
     */
    protected GetEmployeesByConditionSO createGetEmployeesByConditionSO(){
        return new GetEmployeesByConditionSO();
    }

    /**
     * Vraca novu instancu sistemske operacije EditEmployeeSO.
     *
     * @return nova instanca sistemske operacije EditEmployeeSO
     */
    protected EditEmployeeSO createEditEmployeeSO(){
        return new EditEmployeeSO();
    }

    /**
     * Vraca novu instancu sistemske operacije DeleteEmployeeSO.
     *
     * @return nova instanca sistemske operacije DeleteEmployeeSO
     */
    protected DeleteEmployeeSO createDeleteEmployeeSO(){
        return new DeleteEmployeeSO();
    }

    /**
     * Vraca novu instancu sistemske operacije AddServiceBookSO.
     *
     * @return nova instanca sistemske operacije AddServiceBookSO
     */
    protected AddServiceBookSO createAddServiceBookSO(){
        return new AddServiceBookSO();
    }

    /**
     * Vraca novu instancu sistemske operacije GetAllServiceBooksSO.
     *
     * @return nova instanca sistemske operacije GetAllServiceBooksSO
     */
    protected GetAllServiceBooksSO createGetAllServiceBooksSO(){
        return new GetAllServiceBooksSO();
    }

    /**
     * Vraca novu instancu sistemske operacije GetServiceBooksByConditionSO.
     *
     * @return nova instanca sistemske operacije GetServiceBooksByConditionSO
     */
    protected GetServiceBooksByConditionSO createGetServiceBooksByConditionSO(){
        return new GetServiceBooksByConditionSO();
    }

    /**
     * Vraca novu instancu sistemske operacije DeleteServiceBookSO.
     *
     * @return nova instanca sistemske operacije DeleteServiceBookSO
     */
    protected DeleteServiceBookSO createDeleteServiceBookSO(){
        return new DeleteServiceBookSO();
    }

    /**
     * Vraca novu instancu sistemske operacije EditServiceBookSO.
     *
     * @return nova instanca sistemske operacije EditServiceBookSO
     */
    protected EditServiceBookSO createEditServiceBookSO(){
        return new EditServiceBookSO();
    }

    /**
     * Vraca novu instancu sistemske operacije GetRepairsByFKConditionSO.
     *
     * @return nova instanca sistemske operacije GetRepairsByFKConditionSO
     */
    protected GetRepairsByFKConditionSO createGetRepairsByFKConditionSO(){
        return new GetRepairsByFKConditionSO();
    }

    /**
     * Vraca novu instancu sistemske operacije GetRepairItemsByFKConditionSO.
     *
     * @return nova instanca sistemske operacije GetRepairItemsByFKConditionSO
     */
    protected GetRepairItemsByFKConditionSO createGetRepairItemsByFKConditionSO(){
        return new GetRepairItemsByFKConditionSO();
    }

    /**
     * Vraca novu instancu sistemske operacije AddRepairSO.
     *
     * @return nova instanca sistemske operacije AddRepairSO
     */
    protected AddRepairSO createAddRepairSO(){
        return new AddRepairSO();
    }

    /**
     * Vraca novu instancu sistemske operacije DeleteRepairSO.
     *
     * @return nova instanca sistemske operacije DeleteRepairSO
     */
    protected DeleteRepairSO createDeleteRepairSO(){
        return new DeleteRepairSO();
    }

    /**
     * Vraca novu instancu sistemske operacije EditRepairSO.
     *
     * @return nova instanca sistemske operacije EditRepairSO
     */
    protected EditRepairSO createEditRepairSO(){
        return new EditRepairSO();
    }
}
