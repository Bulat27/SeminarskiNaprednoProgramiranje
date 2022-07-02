package rs.ac.bg.fon.nprog.commonlibrary.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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
class RepairItemTest {

    private RepairItem ri;
    private Service service;
    private Repair repair;

    @Mock
    private ResultSet rs;
    @Mock
    private PreparedStatement ps;

    @BeforeEach
    void setUp() {
        ri = new RepairItem();
        service = new Service();
        repair = new Repair();
    }

    @AfterEach
    void tearDown() {
        ri = null;
        service = null;
        repair = null;
    }

    @Test
    void testRepairItem() {
        assertSame(null, ri.getRepair());
        assertEquals(null, ri.getOrderNumber());
        assertEquals(null, ri.getStartDate());
        assertEquals(null, ri.getEndDate());
        assertEquals(null, ri.getRemark());
        assertEquals(null, ri.getEmployeeExpense());
        assertEquals(null, ri.getAdditionalExpense());
        assertEquals(null, ri.getAdditionalRevenue());
        assertSame(null, ri.getService());
        assertEquals(new ArrayList<>(), ri.getEmployeeEngagements());
    }

    @Test
    void testRepairItemSviAtributiSemAngazovanjaRadnika() {
        ri = new RepairItem(repair, 3, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 3, 3),
                "Dodatna napomena", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, service);

        assertSame(repair, ri.getRepair());
        assertEquals(3, ri.getOrderNumber());
        assertEquals(LocalDate.of(2022, 1, 1), ri.getStartDate());
        assertEquals(LocalDate.of(2022, 3, 3), ri.getEndDate());
        assertEquals("Dodatna napomena", ri.getRemark());
        assertEquals(BigDecimal.TEN, ri.getEmployeeExpense());
        assertEquals(BigDecimal.ONE, ri.getAdditionalExpense());
        assertEquals(BigDecimal.ZERO, ri.getAdditionalRevenue());
        assertSame(service, ri.getService());

        assertEquals(new ArrayList<>(), ri.getEmployeeEngagements());
    }

    @Test
    void testSetRepair() {
        ri.setRepair(repair);

        assertSame(repair, ri.getRepair());
    }

    @ParameterizedTest
    @CsvSource({"2", "1", "7", "4"})
    void testSetOrderNumber(int orderNumber) {
        ri.setOrderNumber(orderNumber);

        assertEquals(orderNumber, ri.getOrderNumber());
    }

    @Test
    void testSetStartDate() {
        ri.setStartDate(LocalDate.of(2022, 1, 1));

        assertEquals(LocalDate.of(2022, 1, 1), ri.getStartDate());
    }

    @Test
    void testSetEndDate() {
        ri.setEndDate(LocalDate.of(2022, 1, 1));

        assertEquals(LocalDate.of(2022, 1, 1), ri.getEndDate());
    }

    @ParameterizedTest
    @CsvSource({"Postojale greske tokom rada", "Tezak posao", "Uzima dosta vremena", "Dosta dodatnih troskova"})
    void testSetRemark(String remark) {
        ri.setRemark(remark);

        assertEquals(remark, ri.getRemark());
    }

    @ParameterizedTest
    @CsvSource({"6", "2", "0", "15"})
    void testSetEmployeeExpense(BigDecimal employeeExpense) {
        ri.setEmployeeExpense(employeeExpense);

        assertEquals(employeeExpense, ri.getEmployeeExpense());
    }

    @ParameterizedTest
    @CsvSource({"2", "5", "0", "18"})
    void testSetAdditionalExpense(BigDecimal additionalExpense) {
        ri.setAdditionalExpense(additionalExpense);

        assertEquals(additionalExpense, ri.getAdditionalExpense());
    }

    @ParameterizedTest
    @CsvSource({"1", "51", "24", "7"})
    void testSetAdditionalRevenue(BigDecimal additionalRevenue) {
        ri.setAdditionalRevenue(additionalRevenue);

        assertEquals(additionalRevenue, ri.getAdditionalRevenue());
    }

    @Test
    void testSetService() {
        ri.setService(service);

        assertSame(service, ri.getService());
    }

    @Test
    void testSetEmployeeEngagements() {
        List<EmployeeEngagement> employeeEngagements = new ArrayList<>();
        ri.setEmployeeEngagements(employeeEngagements);

        assertSame(employeeEngagements, ri.getEmployeeEngagements());
    }

    @Test
    void testGetEmployeeList() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();

        employee1.setEmployeeID(1l);
        employee2.setEmployeeID(2l);

        EmployeeEngagement employeeEngagement1 = new EmployeeEngagement(employee1, ri, 5);
        EmployeeEngagement employeeEngagement2 = new EmployeeEngagement(employee2, ri, 10);

        ri.getEmployeeEngagements().add(employeeEngagement1);
        ri.getEmployeeEngagements().add(employeeEngagement2);

        List<Employee> resultEmployeeList = ri.getEmployeeList();

        assertTrue(resultEmployeeList.contains(employee1));
        assertTrue(resultEmployeeList.contains(employee2));
    }

    @Test
    void testGetTableName(){
        assertEquals("repair_item", ri.getTableName());
    }

    @Test
    void testGetNewRecord() throws SQLException {
        when(rs.getLong("s.id")).thenReturn(1l);
        when(rs.getBigDecimal("s.price")).thenReturn(BigDecimal.TEN);
        when(rs.getString("s.name")).thenReturn("Zamena kocnica");
        when(rs.getString("s.description")).thenReturn("Zameniti kocnice na automobilu");
        when(rs.getBigDecimal("s.material_cost")).thenReturn(BigDecimal.ONE);

        when(rs.getInt("ri.order_number")).thenReturn(3);
        when(rs.getDate("ri.start_date")).thenReturn(Date.valueOf(LocalDate.of(2022, 1, 1)));
        when(rs.getDate("ri.end_date")).thenReturn(Date.valueOf(LocalDate.of(2022, 3, 3)));
        when(rs.getString("ri.remark")).thenReturn("Dodatna napomena");
        when(rs.getBigDecimal("ri.employee_expense")).thenReturn(BigDecimal.TEN);
        when(rs.getBigDecimal("ri.additional_expense")).thenReturn(BigDecimal.ONE);
        when(rs.getBigDecimal("ri.additional_revenue")).thenReturn(BigDecimal.ZERO);

        ri.setRepair(repair);

        RepairItem resultRepairItem = (RepairItem) ri.getNewRecord(rs);

        assertEquals(1l, resultRepairItem.getService().getServiceID());
        assertEquals(BigDecimal.TEN, resultRepairItem.getService().getPrice());
        assertEquals("Zamena kocnica", resultRepairItem.getService().getName());
        assertEquals("Zameniti kocnice na automobilu", resultRepairItem.getService().getDescription());
        assertEquals(BigDecimal.ONE, resultRepairItem.getService().getMaterialCost());

        assertSame(ri.getRepair(), resultRepairItem.getRepair());
        assertEquals(3, resultRepairItem.getOrderNumber());
        assertEquals(LocalDate.of(2022, 1, 1), resultRepairItem.getStartDate());
        assertEquals(LocalDate.of(2022, 3, 3), resultRepairItem.getEndDate());
        assertEquals("Dodatna napomena", resultRepairItem.getRemark());
        assertEquals(BigDecimal.TEN, resultRepairItem.getEmployeeExpense());
        assertEquals(BigDecimal.ONE, resultRepairItem.getAdditionalExpense());
        assertEquals(BigDecimal.ZERO, resultRepairItem.getAdditionalRevenue());

        assertEquals(new ArrayList<>(), resultRepairItem.getEmployeeEngagements());
    }

    @Test
    void testGetInsertionColumns(){
        assertEquals("repair_id, order_number, start_date, end_date, remark, employee_expense, additional_expense, additional_revenue, service_id",
                ri.getInsertionColumns());
    }

    @Test
    void testGetAtrPlaceHolders(){
        assertEquals("?, ?, ?, ?, ?, ?, ?, ?, ?", ri.getAtrPlaceHolders());
    }

    @Test
    void testSetPreparedStatementParameters() throws SQLException {
        repair.setRepairID(3l);
        service.setServiceID(5l);

        ri = new RepairItem(repair, 3, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 3, 3),
                "Dodatna napomena", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, service);

        ri.setPreparedStatementParameters(ps);

        verify(ps).setLong(1, ri.getRepair().getRepairID());
        verify(ps).setInt(2, ri.getOrderNumber());
        verify(ps).setDate(3, Date.valueOf(ri.getStartDate()));
        verify(ps).setDate(4, Date.valueOf(ri.getEndDate()));
        verify(ps).setString(5, ri.getRemark());
        verify(ps).setBigDecimal(6, ri.getEmployeeExpense());
        verify(ps).setBigDecimal(7, ri.getAdditionalExpense());
        verify(ps).setBigDecimal(8, ri.getAdditionalRevenue());
        verify(ps).setLong(9, ri.getService().getServiceID());
    }

    @Test
    void testGetPKWhereCondition(){
        assertThrows(java.lang.UnsupportedOperationException.class, () -> ri.getPKWhereCondition());
    }

    @Test
    void testGetAttributeValuesWhereCondition() {
        assertThrows(java.lang.UnsupportedOperationException.class, () -> ri.getAttributeValuesWhereCondition());
    }

    @Test
    void testGetUpdateColumnsWithPlaceHolders(){
        assertThrows(java.lang.UnsupportedOperationException.class, () -> ri.getUpdateColumnsWithPlaceHolders());
    }

    @Test
    void testGetFKWhereCondition(){
        repair.setRepairID(5l);

        ri.setRepair(repair);

        assertEquals( "ri.repair_id = 5", ri.getFKWhereCondition());
    }

    @Test
    void testGetJoinCondition(){
        assertEquals(" ri JOIN service s ON ri.service_id = s.id", ri.getJoinCondition());
    }
}