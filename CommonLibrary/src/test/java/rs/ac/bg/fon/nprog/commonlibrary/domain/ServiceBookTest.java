package rs.ac.bg.fon.nprog.commonlibrary.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceBookTest {

    private ServiceBook sb;

    @Mock
    private ResultSet rs;
    @Mock
    private PreparedStatement ps;

    @BeforeEach
    void setUp() {
        sb = new ServiceBook();
    }

    @AfterEach
    void tearDown() {
        sb = null;
    }

    @Test
    void testServiceBook() {
        assertEquals(null, sb.getServiceBookID());
        assertEquals(null, sb.getClientFirstName());
        assertEquals(null, sb.getClientLastName());
        assertEquals(null, sb.getVehicleDescription());
        assertEquals(null, sb.getInitialDate());
        assertEquals(false, sb.isActive());
        assertEquals(null, sb.getRepairs());
    }

    @Test
    void testServiceBookSviAtributiOsimServisa() {
        sb = new ServiceBook(5l, "Jovan", "Jovanovic", "Peugeot 508 1.6",
                LocalDate.of(2022, 1, 1), true);

        assertEquals(5l, sb.getServiceBookID());
        assertEquals("Jovan", sb.getClientFirstName());
        assertEquals("Jovanovic", sb.getClientLastName());
        assertEquals("Peugeot 508 1.6", sb.getVehicleDescription());
        assertEquals(LocalDate.of(2022, 1, 1), sb.getInitialDate());
        assertEquals(true, sb.isActive());

        assertEquals(null, sb.getRepairs());
    }

    @Test
    void testServiceBookSviAtributiOsimIDIServisa() {
        sb = new ServiceBook("Jovan", "Jovanovic", "Peugeot 508 1.6",
                LocalDate.of(2022, 1, 1), true);

        assertEquals("Jovan", sb.getClientFirstName());
        assertEquals("Jovanovic", sb.getClientLastName());
        assertEquals("Peugeot 508 1.6", sb.getVehicleDescription());
        assertEquals(LocalDate.of(2022, 1, 1), sb.getInitialDate());
        assertEquals(true, sb.isActive());

        assertEquals(null, sb.getServiceBookID());
        assertEquals(null, sb.getRepairs());
    }

    @ParameterizedTest
    @CsvSource({"1", "10", "13", "4"})
    void testSetServiceBookID(Long serviceBookID) {
        sb.setServiceBookID(serviceBookID);

        assertEquals(serviceBookID, sb.getServiceBookID());
    }

    @ParameterizedTest
    @CsvSource({"Jovan", "Zoran", "Boban", "Ugljesa"})
    void testSetClientFirstName(String clientFirstName) {
        sb.setClientFirstName(clientFirstName);

        assertEquals(clientFirstName, sb.getClientFirstName());
    }

    @ParameterizedTest
    @CsvSource({"Jovanovic", "Miskovic", "Carnojevic", "Nemanjic"})
    void testSetClientLastName(String clientLastName) {
        sb.setClientLastName(clientLastName);

        assertEquals(clientLastName, sb.getClientLastName());
    }

    @ParameterizedTest
    @CsvSource({"Zastava Yugo Koral 1.3i", "Peugeot 508 1.6", "Ford Fiesta 1.4", "Alpha Romeo 147 Q2 1.9"})
    void testSetVehicleDescription(String vehicleDescription) {
        sb.setVehicleDescription(vehicleDescription);

        assertEquals(vehicleDescription, sb.getVehicleDescription());
    }

    @Test
    void testSetInitialDate() {
        sb.setInitialDate(LocalDate.of(2022, 1, 1));

        assertEquals(LocalDate.of(2022, 1, 1), sb.getInitialDate());
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void testSetActive(boolean active) {
        sb.setActive(active);

        assertEquals(active, sb.isActive());
    }

    @Test
    void testSetRepairs() {
        List<Repair> repairs = new ArrayList<>();
        sb.setRepairs(repairs);

        assertSame(repairs, sb.getRepairs());
    }

    @Test
    void testGetTableName(){
        assertEquals("service_book", sb.getTableName());
    }

    @Test
    void testGetNewRecord() throws SQLException {
        when(rs.getLong("id")).thenReturn(5l);
        when(rs.getString("client_first_name")).thenReturn("Jovan");
        when(rs.getString("client_last_name")).thenReturn("Jovanovic");
        when(rs.getString("vehicle_description")).thenReturn("Peugeot 508 1.6");
        when(rs.getDate("initial_date")).thenReturn(Date.valueOf(LocalDate.of(2022, 1, 1)));
        when(rs.getInt("active")).thenReturn(1);

        ServiceBook resultServiceBook = (ServiceBook) sb.getNewRecord(rs);

        assertEquals(5l, resultServiceBook.getServiceBookID());
        assertEquals("Jovan", resultServiceBook.getClientFirstName());
        assertEquals("Jovanovic", resultServiceBook.getClientLastName());
        assertEquals("Peugeot 508 1.6", resultServiceBook.getVehicleDescription());
        assertEquals(LocalDate.of(2022, 1, 1), resultServiceBook.getInitialDate());
        assertEquals(true, resultServiceBook.isActive());

        assertEquals(null, resultServiceBook.getRepairs());
    }

    @Test
    void testGetInsertionColumns(){
        assertEquals("client_first_name, client_last_name, vehicle_description, initial_date, active", sb.getInsertionColumns());
    }

    @Test
    void testGetAtrPlaceHolders(){
        assertEquals("?, ?, ?, ?, ?", sb.getAtrPlaceHolders());
    }

    @Test
    void testSetPreparedStatementParameters() throws SQLException {
        sb = new ServiceBook("Jovan", "Jovanovic", "Peugeot 508 1.6",
                LocalDate.of(2022, 1, 1), true);
        sb.setPreparedStatementParameters(ps);

        verify(ps).setString(1 , sb.getClientFirstName());
        verify(ps).setString(2, sb.getClientLastName());
        verify(ps).setString(3, sb.getVehicleDescription());
        verify(ps).setDate(4, Date.valueOf(sb.getInitialDate()));
        verify(ps).setInt(5, sb.isActive() ? 1 : 0);
    }

    @Test
    void testGetPKWhereCondition(){
        sb.setServiceBookID(5l);

        assertEquals("id = 5", sb.getPKWhereCondition());
    }

    @ParameterizedTest
    @CsvSource({
            "Nikola, Bulat, client_first_name = 'Nikola' AND client_last_name = 'Bulat'",
            " '', Bulat, 1 = 1 AND client_last_name = 'Bulat'",
            "Nikola, '', client_first_name = 'Nikola' AND 1 = 1",
            " '', '', 1 = 1 AND 1 = 1",})
    void testGetAttributeValuesWhereCondition(String firstName, String lastName, String condition) {
        sb.setClientFirstName(firstName);
        sb.setClientLastName(lastName);

        assertEquals(condition, sb.getAttributeValuesWhereCondition());
    }

    @Test
    void testGetUpdateColumnsWithPlaceHolders(){
        assertEquals("client_first_name = ?, client_last_name = ?, vehicle_description = ?, initial_date = ?, active = ?",
                sb.getUpdateColumnsWithPlaceHolders());
    }

    @Test
    void testToString() {
        sb.setClientFirstName("Nikola");
        sb.setClientLastName("Bulat");

        String str = sb.toString();

        assertTrue(str.contains("Nikola"));
        assertTrue(str.contains("Bulat"));
    }

    @Test
    void testGetGeneralData() {
        sb.setClientFirstName("Nikola");
        sb.setClientLastName("Bulat");
        sb.setVehicleDescription("Peugeot 508 1.6");

        String str = sb.getGeneralData();

        assertTrue(str.contains("Nikola"));
        assertTrue(str.contains("Bulat"));
        assertTrue(str.contains("Peugeot 508 1.6"));
    }

    @Test
    void testGetFKWhereCondition(){
        assertThrows(java.lang.UnsupportedOperationException.class, () -> sb.getFKWhereCondition());
    }

    @Test
    void testGetJoinCondition(){
        assertEquals("", sb.getJoinCondition());
    }

    @Test
    void testSetAutoGeneratedKey(){
        sb.setAutoGeneratedKey(27l);

        assertEquals(27l, sb.getServiceBookID());
    }

}