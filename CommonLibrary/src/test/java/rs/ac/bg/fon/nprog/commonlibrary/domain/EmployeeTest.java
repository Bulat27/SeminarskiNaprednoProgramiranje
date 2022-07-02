package rs.ac.bg.fon.nprog.commonlibrary.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.nprog.commonlibrary.domain.util.EmployeeRole;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeTest {

    private Employee e;

    @Mock
    private ResultSet rs;
    @Mock
    private PreparedStatement ps;

    @BeforeEach
    void setUp() {
        e = new Employee();
    }

    @AfterEach
    void tearDown() {
        e = null;
    }

    @Test
    void testEmployee() {
        assertEquals(null, e.getEmployeeID());
        assertEquals(null, e.getFirstName());
        assertEquals(null, e.getLastName());
        assertEquals(null, e.getEmployeeRole());
        assertEquals(null, e.getHourlyRate());
        assertEquals(null, e.getDateOfEmployment());
        assertEquals(null, e.getUsername());
        assertEquals(null, e.getPassword());
    }

    @Test
    void testEmployeeSviAtributi() {
        e = new Employee(1L, "Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN, LocalDate.of(2022, 1, 1), "bulat", "bulat");

        assertEquals(1L, e.getEmployeeID());
        assertEquals("Nikola", e.getFirstName());
        assertEquals("Bulat", e.getLastName());
        assertEquals(EmployeeRole.ADMIN, e.getEmployeeRole());
        assertEquals(BigDecimal.TEN, e.getHourlyRate());
        assertEquals(LocalDate.of(2022, 1, 1), e.getDateOfEmployment());
        assertEquals("bulat", e.getUsername());
        assertEquals("bulat", e.getPassword());
    }

    @Test
    void testEmployeeSviAtributiSemID() {
        e = new Employee("Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN, LocalDate.of(2022, 1, 1), "bulat", "bulat");

        assertEquals(null, e.getEmployeeID());
        assertEquals("Nikola", e.getFirstName());
        assertEquals("Bulat", e.getLastName());
        assertEquals(EmployeeRole.ADMIN, e.getEmployeeRole());
        assertEquals(BigDecimal.TEN, e.getHourlyRate());
        assertEquals(LocalDate.of(2022, 1, 1), e.getDateOfEmployment());
        assertEquals("bulat", e.getUsername());
        assertEquals("bulat", e.getPassword());
    }

    @Test
    void testEmployeeKredencijali() {
        e = new Employee("bulat", "bulat");

        assertEquals("bulat", e.getUsername());
        assertEquals("bulat", e.getPassword());
        assertEquals(null, e.getEmployeeID());
        assertEquals(null, e.getFirstName());
        assertEquals(null, e.getLastName());
        assertEquals(null, e.getEmployeeRole());
        assertEquals(null, e.getHourlyRate());
        assertEquals(null, e.getDateOfEmployment());
    }

    @ParameterizedTest
    @CsvSource({"1", "10", "13", "4"})
    void testSetEmployeeID(Long employeeID) {
        e.setEmployeeID(employeeID);

        assertEquals(employeeID, e.getEmployeeID());
    }

    @ParameterizedTest
    @CsvSource({"Jovan", "Igor", "Boban", "Zoran"})
    void testSetFirstName(String firstName) {
        e.setFirstName(firstName);

        assertEquals(firstName, e.getFirstName());
    }

    @ParameterizedTest
    @CsvSource({"Jovan", "Igor", "Boban", "Zoran"})
    void testSetLastName(String lastName) {
        e.setLastName(lastName);

        assertEquals(lastName, e.getLastName());
    }

    @ParameterizedTest
    @CsvSource({"ADMIN", "WORKER", "WORKER", "ADMIN"})
    void testSetEmployeeRole(EmployeeRole employeeRole) {
        e.setEmployeeRole(employeeRole);

        assertEquals(employeeRole, e.getEmployeeRole());
    }

    @ParameterizedTest
    @CsvSource({"5", "10", "25", "27"})
    void testSetHourlyRate(BigDecimal hourlyRate) {
        e.setHourlyRate(hourlyRate);

        assertEquals(hourlyRate, e.getHourlyRate());
    }

    @Test
    void testSetDateOfEmployment() {
        e.setDateOfEmployment(LocalDate.of(2022, 1, 1));

        assertEquals(LocalDate.of(2022, 1, 1), e.getDateOfEmployment());
    }

    @ParameterizedTest
    @CsvSource({"bulat", "nb27", "nikola", "bbr"})
    void testSetUserName(String userName) {
        e.setUsername(userName);

        assertEquals(userName, e.getUsername());
    }

    @ParameterizedTest
    @CsvSource({"bulat123", "sifra", "lozinka123", "123lozinka456"})
    void testSetPassword(String password) {
        e.setPassword(password);

        assertEquals(password, e.getPassword());
    }

    @Test
    void testToString() {
        e.setFirstName("Nikola");
        e.setLastName("Bulat");

        String str = e.toString();

        assertTrue(str.contains("Nikola"));
        assertTrue(str.contains("Bulat"));
    }

    @ParameterizedTest
    @CsvSource({"1, 1, true",
            "1, 2, false",
            "2, 2, true",
            "14, 12, false",
            "14, 14, true",})
    void testEquals(Long employeeID, Long employeeID2, boolean result) {
        e.setEmployeeID(employeeID);

        Employee e2 = new Employee();
        e2.setEmployeeID(employeeID2);

        assertEquals(result, e.equals(e2));
    }

    @Test
    void testGetTableName(){
        assertEquals("employee", e.getTableName());
    }

    @Test
    void testGetNewRecord() throws SQLException {
        when(rs.getLong("id")).thenReturn(1l);
        when(rs.getString("first_name")).thenReturn("Nikola");
        when(rs.getString("last_name")).thenReturn("Bulat");
        when(rs.getString("role")).thenReturn("ADMIN");
        when(rs.getBigDecimal("hourly_rate")).thenReturn(BigDecimal.TEN);
        when(rs.getDate("date_of_employment")).thenReturn(Date.valueOf(LocalDate.of(2022, 1, 1)));
        when(rs.getString("username")).thenReturn("nb27");
        when(rs.getString("password")).thenReturn("sifra123");

        Employee resultEmployee = (Employee) e.getNewRecord(rs);

        assertEquals(1L, resultEmployee.getEmployeeID());
        assertEquals("Nikola", resultEmployee.getFirstName());
        assertEquals("Bulat", resultEmployee.getLastName());
        assertEquals(EmployeeRole.ADMIN, resultEmployee.getEmployeeRole());
        assertEquals(BigDecimal.TEN, resultEmployee.getHourlyRate());
        assertEquals(LocalDate.of(2022, 1, 1), resultEmployee.getDateOfEmployment());
        assertEquals("nb27", resultEmployee.getUsername());
        assertEquals("sifra123", resultEmployee.getPassword());
    }

    @Test
    void testGetInsertionColumns(){
        assertEquals("first_name, last_name, role, hourly_rate, date_of_employment, username, password", e.getInsertionColumns());
    }

    @Test
    void testGetAtrPlaceHolders(){
        assertEquals("?, ?, ?, ?, ?, ?, ?", e.getAtrPlaceHolders());
    }

    @Test
    void testSetPreparedStatementParameters() throws SQLException {
        e = new Employee("Nikola", "Bulat", EmployeeRole.ADMIN, BigDecimal.TEN, LocalDate.of(2022, 1, 1), "bulat", "bulat");
        e.setPreparedStatementParameters(ps);

        verify(ps).setString(1 , e.getFirstName());
        verify(ps).setString(2, e.getLastName());
        verify(ps).setString(3, e.getEmployeeRole().toString());
        verify(ps).setBigDecimal(4, e.getHourlyRate());
        verify(ps).setDate(5, Date.valueOf(e.getDateOfEmployment()));
        verify(ps).setString(6, e.getUsername());
        verify(ps).setString(7, e.getPassword());
    }

    @Test
    void testGetPKWhereCondition(){
        e.setEmployeeID(5l);

        assertEquals("id = 5", e.getPKWhereCondition());
    }

    @ParameterizedTest
    @CsvSource({
            "Nikola, Bulat, first_name = 'Nikola' AND last_name = 'Bulat'",
            " '', Bulat, 1 = 1 AND last_name = 'Bulat'",
            "Nikola, '', first_name = 'Nikola' AND 1 = 1",
            " '', '', 1 = 1 AND 1 = 1",})
    void testGetAttributeValuesWhereCondition(String firstName, String lastName, String condition) {
        e.setFirstName(firstName);
        e.setLastName(lastName);

        assertEquals(condition, e.getAttributeValuesWhereCondition());
    }


    @Test
    void testGetUpdateColumnsWithPlaceHolders(){
        assertEquals("first_name = ?, last_name = ?, role = ?, hourly_rate = ?, date_of_employment = ?, username = ?, password = ?",
                e.getUpdateColumnsWithPlaceHolders());
    }

    @Test
    void testGetFKWhereCondition(){
        assertThrows(java.lang.UnsupportedOperationException.class, () -> e.getFKWhereCondition());
    }

    @Test
    void testGetJoinCondition(){
        assertEquals("", e.getJoinCondition());
    }

    @Test
    void testSetAutoGeneratedKey(){
        e.setAutoGeneratedKey(27l);

        assertEquals(27l, e.getEmployeeID());
    }
}