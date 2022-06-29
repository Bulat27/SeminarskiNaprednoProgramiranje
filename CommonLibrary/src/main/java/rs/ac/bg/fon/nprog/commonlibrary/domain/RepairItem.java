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
 *
 * @author Dragon
 */
public class RepairItem implements GeneralDObject {

    private Repair repair;
    private Integer orderNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private String remark;
    private BigDecimal employeeExpense;
    private BigDecimal additionalExpense;
    private BigDecimal additionalRevenue;
    private Service service;
    private List<EmployeeEngagement> employeeEngagements = new ArrayList<>();

    public RepairItem() {
    }

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

    public Repair getRepair() {
        return repair;
    }

    public void setRepair(Repair repair) {
        this.repair = repair;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getEmployeeExpense() {
        return employeeExpense;
    }

    public void setEmployeeExpense(BigDecimal employeeExpense) {
        this.employeeExpense = employeeExpense;
    }

    public BigDecimal getAdditionalExpense() {
        return additionalExpense;
    }

    public void setAdditionalExpense(BigDecimal additionalExpense) {
        this.additionalExpense = additionalExpense;
    }

    public BigDecimal getAdditionalRevenue() {
        return additionalRevenue;
    }

    public void setAdditionalRevenue(BigDecimal additionalRevenue) {
        this.additionalRevenue = additionalRevenue;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<EmployeeEngagement> getEmployeeEngagements() {
        return employeeEngagements;
    }

    public void setEmployeeEngagements(List<EmployeeEngagement> employeeEngagements) {
        this.employeeEngagements = employeeEngagements;
    }

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
