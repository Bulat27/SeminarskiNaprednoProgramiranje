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
public class Repair implements GeneralDObject {

    private Long repairID;
    private String name;
    private LocalDate startDate;
    private BigDecimal totalRevenue = BigDecimal.ZERO;
    private BigDecimal totalExpense = BigDecimal.ZERO;
    private ServiceBook serviceBook;
    private List<RepairItem> repairItems = new ArrayList<>();

    public Repair() {
    }

    public Repair(Long repairID, BigDecimal totalRevenue, BigDecimal totalExpense, String name, LocalDate startDate, ServiceBook serviceBook) {
        this.repairID = repairID;
        this.totalRevenue = totalRevenue;
        this.totalExpense = totalExpense;
        this.name = name;
        this.startDate = startDate;
        this.serviceBook = serviceBook;
    }

    public Long getRepairID() {
        return repairID;
    }

    public void setRepairID(Long repairID) {
        this.repairID = repairID;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public ServiceBook getServiceBook() {
        return serviceBook;
    }

    public void setServiceBook(ServiceBook serviceBook) {
        this.serviceBook = serviceBook;
    }

    public List<RepairItem> getRepairItems() {
        return repairItems;
    }

    public void setRepairItems(List<RepairItem> repairItems) {
        this.repairItems = repairItems;
        updateDerivedFields();
    }

    private void updateDerivedFields() {
        updateFinancialData();
        updateDate();
    }

    private void updateFinancialData() {
        BigDecimal tr = BigDecimal.ZERO;
        BigDecimal te = BigDecimal.ZERO;

        for (RepairItem repairItem : repairItems) {
            if (repairItem.getService() != null) {
                tr = tr.add(repairItem.getService().getPrice().add(repairItem.getAdditionalRevenue()));
                te = te.add(repairItem.getEmployeeExpense().add(repairItem.getAdditionalExpense().add(repairItem.getService().getMaterialCost())));
            }
        }

        if (tr.compareTo(BigDecimal.ZERO) == 1 && te.compareTo(BigDecimal.ZERO) == 1) {
            totalRevenue = tr;
            totalExpense = te;
        }
        System.out.println(totalRevenue);
        System.out.println(totalExpense);
    }

    private void updateDate() {
        LocalDate earliestDate = LocalDate.MAX;

        for (RepairItem repairItem : repairItems) {
            if (repairItem.getStartDate() != null && repairItem.getStartDate().isBefore(earliestDate)) {
                earliestDate = repairItem.getStartDate();
            }
        }
        startDate = earliestDate;
    }

    @Override
    public String getTableName() {
        return "repair";
    }

    @Override
    public GeneralDObject getNewRecord(ResultSet rs) throws SQLException {
        return new Repair(rs.getLong("id"), rs.getBigDecimal("total_revenue"), rs.getBigDecimal("total_expense"),
                rs.getString("name"), rs.getDate("start_date").toLocalDate(), serviceBook);
    }

    @Override
    public String getInsertionColumns() {
        return "name, start_date, total_revenue, total_expense, service_book_id";
    }

    @Override
    public String getAtrPlaceHolders() {
        return "?, ?, ?, ?, ?";
    }

    @Override
    public void setPreparedStatementParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, name);
        ps.setDate(2, Date.valueOf(startDate));
        ps.setBigDecimal(3, totalRevenue);
        ps.setBigDecimal(4, totalExpense);
        ps.setLong(5, serviceBook.getServiceBookID());
    }

    @Override
    public String getPKWhereCondition() {
        return "id = " + repairID;
    }

    @Override
    public String getAttributeValuesWhereCondition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUpdateColumnsWithPlaceHolders() {
        return "name = ?, start_date = ?, total_revenue = ?, total_expense = ?, service_book_id = ?";
    }

    @Override
    public String getFKWhereCondition() {
//        return "r.service_book_id = " + serviceBook.getServiceBookID();
        return "service_book_id = " + serviceBook.getServiceBookID();
    }

    @Override
    public String getJoinCondition() {
//        return " r JOIN service_book sb ON r.service_book_id = sb.id";
        return "";
    }

    @Override
    public void setAutoGeneratedKey(long id) {
        setRepairID(id);
    }
}
