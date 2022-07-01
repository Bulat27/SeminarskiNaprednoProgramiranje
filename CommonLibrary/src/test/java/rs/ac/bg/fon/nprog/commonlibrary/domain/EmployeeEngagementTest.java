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
class EmployeeEngagementTest {

    private EmployeeEngagement eEng;
    private Employee employee;
    private RepairItem repairItem;

    @Mock
    private ResultSet rs;
    @Mock
    private PreparedStatement ps;

    @BeforeEach
    void setUp() {
        eEng = new EmployeeEngagement();
        employee = new Employee();
        repairItem = new RepairItem();
    }

    @AfterEach
    void tearDown() {
        eEng = null;
        employee = null;
        repairItem = null;
    }

    @Test
    void testEmployeeEngagement() {
        assertSame(null, eEng.getEmployee());
        assertSame(null, eEng.getRepairItem());
        assertEquals(0, eEng.getDuration());
    }

    @Test
    void testEmployeeEngagementSviAtributi() {
        eEng = new EmployeeEngagement(employee, repairItem, 550);

        assertSame(employee, eEng.getEmployee());
        assertSame(repairItem, eEng.getRepairItem());
        assertEquals(550, eEng.getDuration());
    }

    @Test
    void testSetEmployee() {
        eEng.setEmployee(employee);

        assertSame(employee, eEng.getEmployee());
    }

    @Test
    void testSetRepairItem() {
        eEng.setRepairItem(repairItem);

        assertSame(repairItem, eEng.getRepairItem());
    }

    @ParameterizedTest
    @CsvSource({"2", "1", "7", "4"})
    void testSetDuration(int duration) {
        eEng.setDuration(duration);

        assertEquals(duration, eEng.getDuration());
    }

    @Test
    void testGetTableName(){
        assertEquals("employee_engagement", eEng.getTableName());
    }

    @Test
    void testGetNewRecord() throws SQLException {
        when(rs.getLong("e.id")).thenReturn(1l);
        when(rs.getString("e.first_name")).thenReturn("Nikola");
        when(rs.getString("e.last_name")).thenReturn("Bulat");
        when(rs.getString("e.role")).thenReturn("ADMIN");
        when(rs.getBigDecimal("e.hourly_rate")).thenReturn(BigDecimal.TEN);
        when(rs.getDate("e.date_of_employment")).thenReturn(Date.valueOf(LocalDate.of(2022, 1, 1)));
        when(rs.getString("e.username")).thenReturn("nb27");
        when(rs.getString("e.password")).thenReturn("sifra123");

        when(rs.getInt("eeng.duration")).thenReturn(5);

        eEng.setRepairItem(repairItem);

        EmployeeEngagement resultEmployeeEngagement = (EmployeeEngagement) eEng.getNewRecord(rs);

        assertEquals(1L, resultEmployeeEngagement.getEmployee().getEmployeeID());
        assertEquals("Nikola", resultEmployeeEngagement.getEmployee().getFirstName());
        assertEquals("Bulat", resultEmployeeEngagement.getEmployee().getLastName());
        assertEquals(EmployeeRole.ADMIN, resultEmployeeEngagement.getEmployee().getEmployeeRole());
        assertEquals(BigDecimal.TEN, resultEmployeeEngagement.getEmployee().getHourlyRate());
        assertEquals(LocalDate.of(2022, 1, 1), resultEmployeeEngagement.getEmployee().getDateOfEmployment());
        assertEquals("nb27", resultEmployeeEngagement.getEmployee().getUsername());
        assertEquals("sifra123", resultEmployeeEngagement.getEmployee().getPassword());

        assertSame(repairItem, resultEmployeeEngagement.getRepairItem());
        assertEquals(5, resultEmployeeEngagement.getDuration());
    }

    @Test
    void testGetInsertionColumns(){
        assertEquals("employee_id, repair_id, order_number, duration", eEng.getInsertionColumns());
    }

    @Test
    void testGetAtrPlaceHolders(){
        assertEquals("?, ?, ?, ?", eEng.getAtrPlaceHolders());
    }

    @Test
    void testSetPreparedStatementParameters() throws SQLException {
        employee.setEmployeeID(27l);
        repairItem.setRepair(new Repair());
        repairItem.getRepair().setRepairID(5l);
        repairItem.setOrderNumber(3);
        eEng = new EmployeeEngagement(employee, repairItem, 550);

        eEng.setPreparedStatementParameters(ps);

        verify(ps).setLong(1, eEng.getEmployee().getEmployeeID());
        verify(ps).setLong(2, eEng.getRepairItem().getRepair().getRepairID());
        verify(ps).setInt(3, eEng.getRepairItem().getOrderNumber());
        verify(ps).setInt(4, eEng.getDuration());
    }

    @Test
    void testGetPKWhereCondition(){
        assertThrows(java.lang.UnsupportedOperationException.class, () -> eEng.getPKWhereCondition());
    }

    @Test
    void testGetAttributeValuesWhereCondition() {
        assertThrows(java.lang.UnsupportedOperationException.class, () -> eEng.getAttributeValuesWhereCondition());
    }

    @Test
    void testGetUpdateColumnsWithPlaceHolders(){
        assertThrows(java.lang.UnsupportedOperationException.class, () -> eEng.getUpdateColumnsWithPlaceHolders());
    }

    @Test
    void testGetFKWhereCondition(){
        repairItem.setRepair(new Repair());
        repairItem.getRepair().setRepairID(5l);
        repairItem.setOrderNumber(3);

        eEng.setRepairItem(repairItem);


        assertEquals( "eeng.repair_id = 5 AND eeng.order_number = 3", eEng.getFKWhereCondition());
    }

    @Test
    void testGetJoinCondition(){
        assertEquals(" eeng JOIN employee e ON eeng.employee_id = e.id", eEng.getJoinCondition());
    }
}