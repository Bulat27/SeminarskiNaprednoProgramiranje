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
 * Predstavlja servis (popravku) koja se sastoji od veceg broja stavki servisa.
 * Svaki servis je vezan za odredjenu servisnu knjizicu.
 * Sadrzi atribute idServisa, naziv, datumPocetka, ukupnaDobit, ukupniTroskovi, servisnaKnjizica istavkeServisa.
 *
 * @author Dragon
 * @version 1.0
 */
public class Repair implements GeneralDObject {

    /**
     * Identifikacioni broj servisa koji predstavlja primarni kljuc u bazi podataka, podrazumevana vrednost je null.
     */
    private Long repairID;

    /**
     * Naziv servisa, podrazumevana vrednost je null.
     */
    private String name;

    /**
     * Datum pocetka servisa koji predstavlja najraniji datum pocetka svih stavki servisa, podrazumevana vrednost je null.
     */
    private LocalDate startDate;

    /**
     * Ukupna dobit servisa, podrazumevana vrednost je BigDecimal.ZERO. Dobija se kao suma cene svih usluga i dodatne dobiti
     * u okviru stavki servisa.
     */
    private BigDecimal totalRevenue = BigDecimal.ZERO;

    /**
     * Ukupni troskovi servisa, podrazumevana vrednost je BigDecimal.ZERO. Dobija se kao suma svih troskova radnika,
     * dodatnih troskova i troskova materijala u okviru stavki servisa.
     */
    private BigDecimal totalExpense = BigDecimal.ZERO;

    /**
     * Servisna knjizica za koju se servis vezuje, podrazumevana vrednost je null.
     */
    private ServiceBook serviceBook;

    /**
     * Lista stavki servisa koje priparadaju datom servisu, podrazumevana vrednost je prazna lista.
     */
    private List<RepairItem> repairItems = new ArrayList<>();

    /**
     * Postavlja atribute na njihove podrazumevane vrednosti.
     */
    public Repair() {
    }

    /**
     * Postavlja atribute idServisa, naziv, datumPocetka, ukupnaDobit, ukupniTroskovi i servisnaKnjizica na unete vrednosti.
     * Atribut stavkeServisa postavlja na podrazumevanu vrednost.
     *
     * @param repairID nova vrednost atributa idServisa
     * @param totalRevenue nova vrednost atributa ukupnaDobit
     * @param totalExpense nova vrednost atributa ukupniTroskovi
     * @param name nova vrednost atributa naziv
     * @param startDate nova vrednost atributa datumPocetka
     * @param serviceBook nova vrednost atributa servisnaKnjizica
     */
    public Repair(Long repairID, BigDecimal totalRevenue, BigDecimal totalExpense, String name, LocalDate startDate, ServiceBook serviceBook) {
        this.repairID = repairID;
        this.totalRevenue = totalRevenue;
        this.totalExpense = totalExpense;
        this.name = name;
        this.startDate = startDate;
        this.serviceBook = serviceBook;
    }

    /**
     * Vraca vrednost atributa idServisa.
     *
     * @return id servisa kao Long
     */
    public Long getRepairID() {
        return repairID;
    }

    /**
     * Postavlja novu vrednost atributa idServisa.
     *
     * @param repairID nova vrednost atributa idServisa
     */
    public void setRepairID(Long repairID) {
        this.repairID = repairID;
    }

    /**
     * Vraca vrednost atributa ukupnaDobit.
     *
     * @return ukupna dobit kao BigDecimal
     */
    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    /**
     * Postavlja novu vrednost atributa ukupnaDobit.
     *
     * @param totalRevenue nova vrednost atributa ukupnaDobit
     */
    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    /**
     * Vraca vrednost atributa ukupniTroskovi.
     *
     * @return ukupni troskovi kao BigDecimal
     */
    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    /**
     * Postavlja novu vrednost atributa ukupniTroskovi.
     *
     * @param totalExpense nova vrednost atributa ukupniTroskovi
     */
    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }

    /**
     * Vraca vrednost atributa naziv.
     *
     * @return naziv kao String
     */
    public String getName() {
        return name;
    }

    /**
     * Postavlja novu vrednost atributa naziv.
     *
     * @param name nova vrednost atributa naziv
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Vraca vrednost atributa datumPocetka.
     *
     * @return datum pocetka kao LocalDate
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
     * Vraca vrednost atributa servisnaKnjizica.
     *
     * @return servisna knjizica kao ServiceBook
     */
    public ServiceBook getServiceBook() {
        return serviceBook;
    }

    /**
     * Postavlja novu vrednost atributa servisnaKnjizica.
     *
     * @param serviceBook nova vrednost atributa servisnaKnjizica
     */
    public void setServiceBook(ServiceBook serviceBook) {
        this.serviceBook = serviceBook;
    }

    /**
     * Vraca vrednost atributa stavkeServisa.
     *
     * @return vraca stavke servisa kao listu objekata RepairItem
     */
    public List<RepairItem> getRepairItems() {
        return repairItems;
    }

    /**
     * Postavlja novu vrednost atributa stavkeServisa, a zatim azurira odgovarajuce podatke.
     *
     * @param repairItems nova vrednost atributa stavkeServisa
     */
    public void setRepairItems(List<RepairItem> repairItems) {
        this.repairItems = repairItems;
        updateDerivedFields();
    }

    /**
     * Azurira finansijske i vremenske podatke u okviru servisa pozivom metoda azurirajFinansijskePodatke() i azurirajDatum().
     */
    private void updateDerivedFields() {
        updateFinancialData();
        updateDate();
    }

    /**
     * Azurira finansijske podatke na osnovu stavki servisa, atribute ukupnaDobit i ukupniTroskovi.
     */
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

    /**
     * Azurira atribut datumPocetka na osnovu stavki servisa.
     */
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
