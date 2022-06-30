package rs.ac.bg.fon.nprog.commonlibrary.domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja stavku servisa (popravke).
 * Svaka stavka servisa je vezana za odredjeni servis, uslugu i sadrzi listu angazovanja radnika.
 * Sadrzi atribute servis, redniBroj, datumPocetka, datumKraja, napomena, troskoviRadnika, dodatniTroskovi
 * dodatnaDobit, usluga i angazovanjaRadnika
 *
 * @author Dragon
 * @version 1.0
 */
public class RepairItem implements GeneralDObject {

    /**
     * Servis za koji je stavka vezana, podrazumevana vrednost je null.
     */
    private Repair repair;

    /**
     * Redni broj stavke u okviru servisa za koji je vezana, podrazumevana vrednost je null.
     */
    private Integer orderNumber;

    /**
     * Datum pocetka pruzanja usluge za koju se vezuje stavka, podrazumevana vrednost je null.
     */
    private LocalDate startDate;

    /**
     * Datum kraja pruzanja usluge za koju se vezuje stavka, podrazumevana vrednost je null.
     */
    private LocalDate endDate;

    /**
     * Dodatna napomena koja se odnosi na posao obavljen u okviru stavke, podrazumevana vrednost je null.
     * Nije neophodno da stavka ima napomenu, njena vrednost moze ostati null i prilikom cuvanja u bazi.
     */
    private String remark;

    /**
     * Troskovi radnika koji su pruzali uslugu u okviru stavke servisa. Dobija se kao proizvod satnice radnika
     * i trajanja njegovog angazovanja, ovi podaci se mogu naci u okviru liste angazovanja radnika. Podrazumevana vrednost je
     * null.
     */
    private BigDecimal employeeExpense;

    /**
     * Dodatni troskovi koji mogu nastati prilikom realizovanja usluge u okviru stavke servisa koji nisu predvidjeni
     * unapred. Podrazumevana vrednost je null.
     */
    private BigDecimal additionalExpense;

    /**
     * Dodatna dobit koja moze nastati prilikom realizovanja usluge okviru stavke servisa koja nije predvidjena unapred.
     * Podrazumevana vrednost je null.
     */
    private BigDecimal additionalRevenue;

    /**
     * Usluga koja se pruza u okviru stavke servisa, podrazumevana vrednost je null.
     */
    private Service service;

    /**
     * Lista angazovanja radnika u okviru stavke servisa, podrazumevana vrednost je prazna lista.
     */
    private List<EmployeeEngagement> employeeEngagements = new ArrayList<>();

    /**
     * Postavlja atribute na njihove podrazumevane vrednosti.
     */
    public RepairItem() {
    }

    /**
     * Postavlja atribute servis, redniBroj, datumPocetka, datumKraja, napomena, troskoviRadnika, dodatniTroskovi
     * dodatnaDobit i usluga na unete vrednosti. Atribut angazovanjaRadnika postavlja na podrazumevanu vrednost.
     *
     * @param repair nova vrednost atributa servis
     * @param orderNumber nova vrednost atributa redniBroj
     * @param startDate nova vrednost atributa datumPocetka
     * @param endDate nova vrednost atributa datumKraja
     * @param remark nova vrednost atributa napomena
     * @param employeeExpense nova vrednost atributa troskoviRadnika
     * @param additionalExpense nova vrednost atributa dodatniTroskovi
     * @param additionalRevenue nova vrednost atributa dodatna dobit
     * @param service nova vrednost atributa usluga
     */
    public RepairItem(Repair repair, Integer orderNumber, LocalDate startDate, LocalDate endDate,
            String remark, BigDecimal employeeExpense, BigDecimal additionalExpense, BigDecimal additionalRevenue, Service service) {

        this.repair = repair;
        this.orderNumber = orderNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remark = remark;
        this.employeeExpense = employeeExpense;
        this.additionalExpense = additionalExpense;
        this.additionalRevenue = additionalRevenue;
        this.service = service;
    }

    /**
     * Vraca vrednost atributa servis.
     *
     * @return servis kao Repair
     */
    public Repair getRepair() {
        return repair;
    }

    /**
     * Postavlja novu vrednost atributa servis.
     *
     * @param repair nova vrednost atributa servis
     */
    public void setRepair(Repair repair) {
        this.repair = repair;
    }

    /**
     * Vraca vrednost atributa redniBroj.
     *
     * @return redni broj stavkeServisa kao Integer
     */
    public Integer getOrderNumber() {
        return orderNumber;
    }

    /**
     * Postavlja novu vrednost atributa redniBroj.
     *
     * @param orderNumber nova vrednost atributa redniBroj
     */
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * Vraca vrednost atributa datumPocetka.
     *
     * @return datumPocetka stavkeServisa kao LocalDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Postavlja novu vrednost atributa datumPocetka.
     *
     * @param startDate nova vrednost atributa datumPocetka
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Vraca vrednost atributa datumKraja.
     *
     * @return datumKraja stavkeServisa kao LocalDate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Postavlja novu vrednost atributa datumKraja.
     *
     * @param endDate nova vrednost atributa datumKraja
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Vraca vrednost atributa napomena.
     *
     * @return napomena kao String
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Postavlja novu vrednost atributa napomena.
     *
     * @param remark nova vrednost atributa napomena
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * Vraca vrednost atributa troskoviRadnika.
     *
     * @return troskoviRadnika kao BigDecimal
     */
    public BigDecimal getEmployeeExpense() {
        return employeeExpense;
    }

    /**
     * Postavlja novu vrednost atributa troskoviRadnika.
     *
     * @param employeeExpense nova vrednost atributa troskoviRadnika
     */
    public void setEmployeeExpense(BigDecimal employeeExpense) {
        this.employeeExpense = employeeExpense;
    }

    /**
     * Vraca vrednost atributa dodatniTroskovi.
     *
     * @return dodatniTroskovi kao BigDecimal
     */
    public BigDecimal getAdditionalExpense() {
        return additionalExpense;
    }

    /**
     * Postavlja novu vrednost atributa dodatniTroskovi.
     *
     * @param additionalExpense nova vrednost atributa dodatniTroskovi
     */
    public void setAdditionalExpense(BigDecimal additionalExpense) {
        this.additionalExpense = additionalExpense;
    }

    /**
     * Vraca vrednost atributa dodatnaDobit.
     *
     * @return dodatnaDobit kao BigDecimal
     */
    public BigDecimal getAdditionalRevenue() {
        return additionalRevenue;
    }

    /**
     * Postavlja novu vrednost atributa dodatnaDobit.
     *
     * @param additionalRevenue nova vrednost atributa dodatnaDobit
     */
    public void setAdditionalRevenue(BigDecimal additionalRevenue) {
        this.additionalRevenue = additionalRevenue;
    }

    /**
     * Vraca vrednost atributa usluga.
     *
     * @return usluga kao Service
     */
    public Service getService() {
        return service;
    }

    /**
     * Postavlja novu vrednost atributa usluga.
     *
     * @param service nova vrednost atributa usluga
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * Vraca vrednost atributa angazovanjaRadnika.
     *
     * @return angazovanja radnika kao listu objekata klase EmployeeEngagement
     */
    public List<EmployeeEngagement> getEmployeeEngagements() {
        return employeeEngagements;
    }

    /**
     * Postavlja novu vrednost atributa angazovanjaRadnika.
     *
     * @param employeeEngagements nova vrednost atributa angazovanjaRadnika
     */
    public void setEmployeeEngagements(List<EmployeeEngagement> employeeEngagements) {
        this.employeeEngagements = employeeEngagements;
    }

    /**
     * Vraca listu svih zaposlenih koji su bili angazovani u okviru ove stavke na osnovu liste angazovanja radnika.
     *
     * @return lista zaposlenih koji su angazovani u okviru ove stavke kao lista objekata klase Employee
     */
    public List<Employee> getEmployeeList() {
        List<Employee> employees = new ArrayList();

        for (EmployeeEngagement employeeEngagement : employeeEngagements) {
            employees.add(employeeEngagement.getEmployee());
        }
        return employees;
    }

    @Override
    public String getTableName() {
        return "repair_item";
    }

    @Override
    public GeneralDObject getNewRecord(ResultSet rs) throws SQLException {
        Service s = new Service(rs.getLong("s.id"), rs.getBigDecimal("s.price"), rs.getString("s.name"),
                rs.getString("s.description"), rs.getBigDecimal("s.material_cost"));

        return new RepairItem(repair, rs.getInt("ri.order_number"), rs.getDate("ri.start_date").toLocalDate(), rs.getDate("ri.end_date").toLocalDate(),
                rs.getString("ri.remark"), rs.getBigDecimal("ri.employee_expense"), rs.getBigDecimal("ri.additional_expense"),
                rs.getBigDecimal("ri.additional_revenue"), s);
    }

    @Override
    public String getInsertionColumns() {
        return "repair_id, order_number, start_date, end_date, remark, employee_expense, additional_expense, additional_revenue, service_id";
    }

    @Override
    public String getAtrPlaceHolders() {
        return "?, ?, ?, ?, ?, ?, ?, ?, ?";
    }

    @Override
    public void setPreparedStatementParameters(PreparedStatement ps) throws SQLException {
        ps.setLong(1, repair.getRepairID());
        ps.setInt(2, orderNumber);
        ps.setDate(3, Date.valueOf(startDate));
        ps.setDate(4, Date.valueOf(endDate));
        ps.setString(5, remark);
        ps.setBigDecimal(6, employeeExpense);
        ps.setBigDecimal(7, additionalExpense);
        ps.setBigDecimal(8, additionalRevenue);
        ps.setLong(9, service.getServiceID());
    }

    @Override
    public String getPKWhereCondition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAttributeValuesWhereCondition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUpdateColumnsWithPlaceHolders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFKWhereCondition() {
        return "ri.repair_id = " + repair.getRepairID();
    }

    @Override
    public String getJoinCondition() {
        return " ri JOIN service s ON ri.service_id = s.id";
    }

    @Override
    public void setAutoGeneratedKey(long id) {

    }
}
