package rs.ac.bg.fon.nprog.commonlibrary.domain;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Predstavlja servisnu knjizicu.
 * Servisna knjizica se sastoji od podataka o klijentu i liste servisa (popravki).
 * Sadrzi atribute idServisneKnjizice, imeKlijenta, prezimeKlijenta, opisVozila, datumOtvaranja, aktivna i servisi (popravke).
 *
 * @author Dragon
 * @version 1.0
 */
public class ServiceBook implements GeneralDObject {

    /**
     * Identifikacioni broj servisne knjizice koji predstavlja primarni kljuc u bazi podataka, podrazumevana vrednost
     * je null.
     */
    private Long serviceBookID;

    /**
     * Ime klijenta za kog je otvorena servisna knjizica, podrazumevana vrednost je null.
     */
    private String clientFirstName;

    /**
     * Prezime klijenta za kog je otvorena servisna knjizica, podrazumevana vrednost je null.
     */
    private String clientLastName;

    /**
     * Opis vozila ciji se servisi prate u okviru servisne knjizice, podrazumevana vrednost je null.
     */
    private String vehicleDescription;

    /**
     * Datum otvaranja servisne knjizice, podrazumevana vrednost je null.
     */
    private LocalDate initialDate;

    /**
     * Binarna promenljiva koja ukazuje na to da li je servisna knjizica jos uvek aktivna, odnosno da li se dato vozilo
     * jos uvek odrzava u servisu. Podrazumevana vrednost je false.
     */
    private boolean active;

    /**
     * Servisi zabelezeni u servisnoj knjizici, podrazumevana vrednost je null.
     */
    private List<Repair> repairs;

    /**
     * Postavlja atribute na njihove podrazumevane vrednosti.
     */
    public ServiceBook() {
    }

    /**
     * Postavlja atribute idServisneKnjizice, imeKlijenta, prezimeKlijenta, opisVozila, datumOtvaranja i aktivna
     * na unete vrednosti.
     * Atribut servisi postavlja na podrazumevanu vrednost.
     *
     * @param serviceBookID nova vrednost atributa idServisneKnjizice
     * @param clientFirstName nova vrednost atributa imeKlijenta
     * @param clientLastName nova vrednost atributa prezimeKlijenta
     * @param vehicleDescription nova vrednost atributa opisVozila
     * @param initialDate nova vrednost atributa datumOtvaranja
     * @param active nova vrednost atributa aktivna
     */
    public ServiceBook(Long serviceBookID, String clientFirstName, String clientLastName, String vehicleDescription, LocalDate initialDate, boolean active) {
        this.serviceBookID = serviceBookID;
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.vehicleDescription = vehicleDescription;
        this.initialDate = initialDate;
        this.active = active;
    }

    /**
     * Postavlja atribute imeKlijenta, prezimeKlijenta, opisVozila, datumOtvaranja i aktivna
     * na unete vrednosti.
     * Atribute idServisneKnjizice i servisi postavlja na podrazumevane vrednosti.
     *
     * @param clientFirstName nova vrednost atributa imeKlijenta
     * @param clientLastName nova vrednost atributa prezimeKlijenta
     * @param vehicleDescription nova vrednost atributa opisVozila
     * @param initialDate nova vrednost atributa datumOtvaranja
     * @param active nova vrednost atributa aktivna
     */
    public ServiceBook(String clientFirstName, String clientLastName, String vehicleDescription, LocalDate initialDate, boolean active) {
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.vehicleDescription = vehicleDescription;
        this.initialDate = initialDate;
        this.active = active;
    }

    /**
     * Vraca vrednost atributa idServisneKnjizice.
     *
     * @return id servisne knjizice kao Long
     */
    public Long getServiceBookID() {
        return serviceBookID;
    }

    /**
     * Postavlja novu vrednost atributa idServisneKnjizice.
     *
     * @param serviceBookID nova vrednost atributa idServisneKnjizice
     */
    public void setServiceBookID(Long serviceBookID) {
        this.serviceBookID = serviceBookID;
    }

    /**
     * Vraca vrednost atributa imeKlijenta.
     *
     * @return ime klijenta kao String
     */
    public String getClientFirstName() {
        return clientFirstName;
    }

    /**
     * Postavlja novu vrednost atributa imeKlijenta.
     *
     * @param clientFirstName nova vrednost atributa imeKlijenta
     */
    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    /**
     * Vraca vrednost atributa prezimeKlijenta.
     *
     * @return prezime klijenta kao String
     */
    public String getClientLastName() {
        return clientLastName;
    }

    /**
     * Postavlja novu vrednost atributa prezimeKlijenta.
     *
     * @param clientLastName nova vrednost atributa prezimeKlijenta
     */
    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    /**
     * Vraca vrednost atributa opisVozila.
     *
     * @return opis vozila kao String
     */
    public String getVehicleDescription() {
        return vehicleDescription;
    }
    /**
     * Postavlja novu vrednost atributa opisVozila.
     *
     * @param vehicleDescription nova vrednost atributa opisVozila
     */
    public void setVehicleDescription(String vehicleDescription) {
        this.vehicleDescription = vehicleDescription;
    }

    /**
     * Vraca vrednost atributa datumOtvaranja.
     *
     * @return datum otvaranja servisne knjizice kao LocalDate
     */
    public LocalDate getInitialDate() {
        return initialDate;
    }

    /**
     * Postavlja novu vrednost atributa datumOtvaranja.
     *
     * @param initialDate nova vrednost atributa datumOtvaranja
     */
    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    /**
     * Vraca vrednost atributa aktivna.
     *
     * @return status aktivnosti servisne knjizice kao boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Postavlja novu vrednost atributa aktivna.
     *
     * @param active nova vrednost atributa aktivna
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Vraca vrednost atributa servisi.
     *
     * @return servisi u okviru servise knjizice kao lista objekata klase Repair
     */
    public List<Repair> getRepairs() {
        return repairs;
    }

    /**
     * Postavlja novu vrednost atributa servisi.
     *
     * @param repairs nova vrednost atributa servisi
     */
    public void setRepairs(List<Repair> repairs) {
        this.repairs = repairs;
    }

    @Override
    public String getTableName() {
        return "service_book";
    }

    @Override
    public GeneralDObject getNewRecord(ResultSet rs) throws SQLException {
        return new ServiceBook(rs.getLong("id"), rs.getString("client_first_name"), rs.getString("client_last_name"),
                rs.getString("vehicle_description"), rs.getDate("initial_date").toLocalDate(), rs.getInt("active") == 1);
    }

    @Override
    public String getInsertionColumns() {
        return "client_first_name, client_last_name, vehicle_description, initial_date, active";
    }

    @Override
    public String getAtrPlaceHolders() {
        return "?, ?, ?, ?, ?";
    }

    @Override
    public void setPreparedStatementParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, clientFirstName);
        ps.setString(2, clientLastName);
        ps.setString(3, vehicleDescription);
        ps.setDate(4, Date.valueOf(initialDate));
        ps.setInt(5, active ? 1 : 0);
    }

    @Override
    public String getPKWhereCondition() {
        return "id = " + serviceBookID;
    }

    @Override
    public String getAttributeValuesWhereCondition() {
        String clientFirstNameCondition = clientFirstName.isEmpty() ? "1 = 1" : "client_first_name = '" + clientFirstName + "'";
        String conjuction = " AND ";
        String clientLastNameCondition = clientLastName.isEmpty() ? "1 = 1" : "client_last_name = '" + clientLastName + "'";

        return clientFirstNameCondition += conjuction += clientLastNameCondition;
    }

    @Override
    public String getUpdateColumnsWithPlaceHolders() {
        return "client_first_name = ?, client_last_name = ?, vehicle_description = ?, initial_date = ?, active = ?";
    }

    /**
     * Vraca vrednosti atributa imeKlijenta i prezimeKlijenta kao jedan String.
     *
     * @return String koji se sastoji od imena i prezimena klijenta
     */
    @Override
    public String toString() {
        return clientFirstName + " " + clientLastName;
    }

    /**
     * Vraca opste podatke o servisnoj knjizici koji se sastoje od imena klijenta, prezimena klijenta i opisa vozila.
     *
     * @return String koji se sastoji od imena klijenta, prezimena klijenta i opisa vozila.
     */
    public String getGeneralData() {
        return clientFirstName + " " + clientLastName + ", " + vehicleDescription;
    }

    @Override
    public String getFKWhereCondition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getJoinCondition() {
        return "";
    }

    @Override
    public void setAutoGeneratedKey(long id) {
        setServiceBookID(id);
    }
}
