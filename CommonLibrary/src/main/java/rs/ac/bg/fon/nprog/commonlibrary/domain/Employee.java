package rs.ac.bg.fon.nprog.commonlibrary.domain;

import rs.ac.bg.fon.nprog.commonlibrary.domain.util.EmployeeRole;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Predstavlja zaposlenog u auto servisu.
 * <p>
 * Sadrzi atribute neophodne za identifikaciju i koriscenje softvera od strane zaposlenog.
 * Pomenuti atributi su: idZaposlenog, ime, prezime, ulogaZaposlenog, satnica, datumZaposlenja, korisnickoIme i sifra.
 *
 * @author Dragon
 * @version 1.0
 */
public class Employee implements GeneralDObject {

    /**
     * Identifikacioni broj zaposlenog koji predstavlja primarni kljuc u bazi podataka, podrazumevana vrednost je null.
     */
    private Long employeeID;

    /**
     * Ime zaposlenog, podrazumevana vrednost je prazan String.
     */
    private String firstName;

    /**
     * Prezime zaposlenog, podrazumevana vrednost je prazan String.
     */
    private String lastName;

    /**
     * Uloga zaposlenog, zaposleni moze biti administrator ili radnik, podrazumevana vrednost je null.
     */
    private EmployeeRole employeeRole;

    /**
     * Satnica zaposlenog, koristi se za racunanje cene stavke popravke, podrazumevana vrednost je null.
     */
    private BigDecimal hourlyRate;

    /**
     * Datum zaposlenja zaposlenog, podrazumevana vrednost je null.
     */

    private LocalDate dateOfEmployment;
    /**
     * Korisnicko ime koje zaposlenom omogucava kreiranje naloga za koriscenje softvera, podrazumevana vrednost je null.
     */
    private String username;

    /**
     * Lozinka koja zaposlenom omogucava autentikaciju prilikom koriscenja softvera, podrazumevana vrednost je null.
     */
    private String password;

    /**
     * Postavlja atribute na njihove podrazumevane vrednosti.
     */
    public Employee() {
    }

    /**
     * Postavlja atribute idZaposlenog, ime, prezime, ulogaZaposlenog, satnica, datumZaposlenja, korisnickoIme
     * i lozinka na unete vrednosti.
     *
     * @param employeeID       nova vrednost atributa idZaposlenog
     * @param firstName        nova vrednost atributa ime
     * @param lastName         nova vrednost atributa prezime
     * @param employeeRole     nova vrednost atributa ulogaZaposlenog
     * @param hourlyRate       nova vrednost atributa satnica
     * @param dateOfEmployment nova vrednost atributa datumZaposlenja
     * @param username         nova vrednost atributa korisnickoIme
     * @param password         nova vrednost atributa lozinka
     */
    public Employee(Long employeeID, String firstName, String lastName, EmployeeRole employeeRole, BigDecimal hourlyRate,
                    LocalDate dateOfEmployment, String username, String password) {

        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeRole = employeeRole;
        this.hourlyRate = hourlyRate;
        this.dateOfEmployment = dateOfEmployment;
        this.username = username;
        this.password = password;
    }

    /**
     * Postavlja atribute korisnickoIme i lozinka na unete vrednost, ostale atribute postavlja na podrazumevane
     * vrednosti.
     *
     * @param username nova vrednost za korisnicko ime
     * @param password nova vrednost za lozinku
     */
    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Postavlja atribute ime, prezime, ulogaZaposlenog, satnica, datumZaposlenja, korisnickoIme
     * i lozinka na unete vrednosti. Atribut idZaposlenog postavlja na podrazumevanu vrednost.
     *
     * @param firstName        nova vrednost atributa ime
     * @param lastName         nova vrednost atributa prezime
     * @param employeeRole     nova vrednost atributa ulogaZaposlenog
     * @param hourlyRate       nova vrednost atributa satnica
     * @param dateOfEmployment nova vrednost atributa datumZaposlenja
     * @param username         nova vrednost atributa korisnickoIme
     * @param password         nova vrednost atributa lozinka
     */
    public Employee(String firstName, String lastName, EmployeeRole employeeRole, BigDecimal hourlyRate, LocalDate dateOfEmployment, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeRole = employeeRole;
        this.hourlyRate = hourlyRate;
        this.dateOfEmployment = dateOfEmployment;
        this.username = username;
        this.password = password;
    }

    /**
     * Vraca vrednost atributa idZaposlenog.
     *
     * @return id zaposlenog kao Long
     */
    public Long getEmployeeID() {
        return employeeID;
    }

    /**
     * Postavlja novu vrednost atributa idZaposlenog.
     *
     * @param employeeID nova vrednost atributa idZaposlenog
     */
    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    /**
     * Vraca vrednost atributa ime.
     *
     * @return ime zaposlenog kao String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Postavlja novu vrednost atributa ime
     *
     * @param firstName nova vrednost atributa ime
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Vraca vrednost atributa prezime.
     *
     * @return prezime zaposlenog kao String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Postavlja novu vrednost atributa prezime
     *
     * @param lastName nova vrednost atributa prezime
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Vraca vrednost atributa ulogaZaposlenog.
     *
     * @return uloga zaposlenog kao EmployeeRole
     */
    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    /**
     * Postavlja novu vrednost atributa ulogaZaposlenog
     *
     * @param employeeRole nova vrednost atributa ulogaZaposlenog
     */
    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }

    /**
     * Vraca vrednost atributa satnica.
     *
     * @return satnica kao BigDecimal
     */
    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Postavlja novu vrednost atributa satnica.
     *
     * @param hourlyRate nova vrednost atributa satnica.
     */
    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    /**
     * Vraca vrednost atrbiuta datumZaposlenja.
     *
     * @return datum zaposlenja kao LocalDate
     */
    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    /**
     * Postavlja novu vrednost atributa datumZaposlenja
     *
     * @param dateOfEmployment nova vrednost atributa datumZaposlenja
     */
    public void setDateOfEmployment(LocalDate dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    /**
     * Vraca vrednost atrbiuta korisnickoIme
     *
     * @return korisnicko ime kao String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Postavlja novu vrednost atributa korisnickoIme
     *
     * @param username nova vrednost atributa korisnickoIme
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Vraca vrednost atrbiuta lozinka
     *
     * @return lozinka kao String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Postavlja novu vrednost atributa lozinka.
     *
     * @param password nova vrednost atributa lozinka
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Vraca ime i prezime zaposlenog kao jedan String.
     *
     * @return String koji se sastoji od imena i prezimena zaposlenog
     */
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public String getTableName() {
        return "employee";
    }

    @Override
    public GeneralDObject getNewRecord(ResultSet rs) throws SQLException {
        return new Employee(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), EmployeeRole.valueOf(rs.getString("role")), rs.getBigDecimal("hourly_rate"), rs.getDate("date_of_employment").toLocalDate(), rs.getString("username"), rs.getString("password"));
    }

    /**
     * Vraca hashCode zaposlenog koji se koristi u odredjenim algoritmima i strukturama podataka.
     *
     * @return hashCode zaposlenog kao int
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.employeeID);
        return hash;
    }

    /**
     * Poredi dva zaposlena po vrednosti id-a.
     *
     * @return
     * <ul>
     * <li>true - ako su id-evi isti kod oba zaposlena koja se porede</li>
     * <li>false - ako to nije slucaj</li>
     * </ul>
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.employeeID, other.employeeID)) {
            return false;
        }
        return true;
    }

    @Override
    public String getInsertionColumns() {
        return "first_name, last_name, role, hourly_rate, date_of_employment, username, password";
    }

    @Override
    public String getAtrPlaceHolders() {
        return "?, ?, ?, ?, ?, ?, ?";
    }

    @Override
    public void setPreparedStatementParameters(PreparedStatement ps) throws SQLException {
        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, employeeRole.toString());
        ps.setBigDecimal(4, hourlyRate);
        ps.setDate(5, Date.valueOf(dateOfEmployment));
        ps.setString(6, username);
        ps.setString(7, password);
    }

    @Override
    public String getPKWhereCondition() {
        return "id = " + employeeID;
    }

    @Override
    public String getAttributeValuesWhereCondition() {
        String firstNameCondition = firstName.isEmpty() ? "1 = 1" : "first_name = '" + firstName + "'";
        String conjuction = " AND ";
        String lastNameCondition = lastName.isEmpty() ? "1 = 1" : "last_name = '" + lastName + "'";

        return firstNameCondition += conjuction += lastNameCondition;
    }

    @Override
    public String getUpdateColumnsWithPlaceHolders() {
        return "first_name = ?, last_name = ?, role = ?, hourly_rate = ?, date_of_employment = ?, username = ?, password = ?";
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
        setEmployeeID(id);
    }
}
