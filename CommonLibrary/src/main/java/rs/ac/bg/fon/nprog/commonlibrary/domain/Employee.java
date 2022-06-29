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
 *
 * @author Dragon
 */
public class Employee implements GeneralDObject {

    private Long employeeID;
    private String firstName;
    private String lastName;
    private EmployeeRole employeeRole;
    private BigDecimal hourlyRate;
    private LocalDate dateOfEmployment;
    private String username;
    private String password;

    public Employee() {
    }

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

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Employee(String firstName, String lastName, EmployeeRole employeeRole, BigDecimal hourlyRate, LocalDate dateOfEmployment, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeRole = employeeRole;
        this.hourlyRate = hourlyRate;
        this.dateOfEmployment = dateOfEmployment;
        this.username = username;
        this.password = password;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(LocalDate dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.employeeID);
        return hash;
    }

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
