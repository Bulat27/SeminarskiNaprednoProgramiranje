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
class RepairTest {

    private Repair r;
    private ServiceBook serviceBook;

    @Mock
    private ResultSet rs;
    @Mock
    private PreparedStatement ps;

    @BeforeEach
    void setUp() {
        r = new Repair();
        serviceBook = new ServiceBook();
    }

    @AfterEach
    void tearDown() {
        r = null;
        serviceBook = null;
    }

    @Test
    void testRepair() {
        assertEquals(null, r.getRepairID());
        assertEquals(null, r.getName());
        assertEquals(null, r.getStartDate());
        assertEquals(BigDecimal.ZERO, r.getTotalRevenue());
        assertEquals(BigDecimal.ZERO, r.getTotalExpense());
        assertSame(null, r.getServiceBook());
        assertEquals(new ArrayList<>(), r.getRepairItems());
    }

    @Test
    void testRepairSviAtributiSemStavkiServisa() {
        r = new Repair(16l, BigDecimal.TEN, BigDecimal.TEN, "Zamena ulja", LocalDate.of(2022,1 , 1), serviceBook);

        assertEquals(16l, r.getRepairID());
        assertEquals(BigDecimal.TEN, r.getTotalRevenue());
        assertEquals(BigDecimal.TEN, r.getTotalExpense());
        assertEquals("Zamena ulja", r.getName());
        assertEquals(LocalDate.of(2022, 1, 1), r.getStartDate());
        assertSame(serviceBook, r.getServiceBook());

        assertEquals(new ArrayList<>(), r.getRepairItems());
    }

    @ParameterizedTest
    @CsvSource({"12", "4", "156", "7"})
    void testSetRepairID(Long repairID) {
        r.setRepairID(repairID);

        assertEquals(repairID, r.getRepairID());
    }

    @ParameterizedTest
    @CsvSource({"125", "400", "100", "250"})
    void testSetTotalRevenue(BigDecimal totalRevenue) {
        r.setTotalRevenue(totalRevenue);

        assertEquals(totalRevenue, r.getTotalRevenue());
    }

    @ParameterizedTest
    @CsvSource({"100", "50", "75", "227"})
    void testSetTotalExpense(BigDecimal totalExpense) {
        r.setTotalExpense(totalExpense);

        assertEquals(totalExpense, r.getTotalExpense());
    }

    @ParameterizedTest
    @CsvSource({"Mali servis", "Generalna popravka", "Veliki servis", "Tehnicki pregled"})
    void testSetName(String name) {
        r.setName(name);

        assertEquals(name, r.getName());
    }

    @Test
    void testSetStartDate() {
        r.setStartDate(LocalDate.of(2022, 1, 1));

        assertEquals(LocalDate.of(2022, 1, 1), r.getStartDate());
    }

    @Test
    void testSetServiceBook() {
        r.setServiceBook(serviceBook);

        assertSame(serviceBook, r.getServiceBook());
    }

    @Test
    void testSetRepairItems() {
        r.setStartDate(LocalDate.of(2022, 1, 1));

        BigDecimal totalRevenueBefore = r.getTotalRevenue();
        BigDecimal totalExpenseBefore = r.getTotalExpense();
        LocalDate startDateBefore = r.getStartDate();

        List<RepairItem> repairItems = new ArrayList<>();

        Service service = new Service(1l, BigDecimal.ONE, "Zamena ulja", "Zameniti ulje motora", BigDecimal.ONE);

        RepairItem repairItem = new RepairItem();
        repairItem.setService(service);
        repairItem.setAdditionalRevenue(BigDecimal.TEN);
        repairItem.setAdditionalExpense(BigDecimal.ONE);
        repairItem.setEmployeeExpense(new BigDecimal("5"));
        repairItem.setStartDate(LocalDate.of(2020, 1, 1));

        repairItems.add(repairItem);

        r.setRepairItems(repairItems);

        BigDecimal totalRevenueAfter = r.getTotalRevenue();
        BigDecimal totalExpenseAfter = r.getTotalExpense();
        LocalDate startDateAfter = r.getStartDate();

        assertSame(repairItems, r.getRepairItems());

        assertEquals(totalRevenueBefore, BigDecimal.ZERO);
        assertEquals(totalRevenueAfter, new BigDecimal("11"));

        assertEquals(totalExpenseBefore, BigDecimal.ZERO);
        assertEquals(totalExpenseAfter, new BigDecimal("7"));

        assertEquals(startDateBefore, LocalDate.of(2022, 1, 1));
        assertEquals(startDateAfter, LocalDate.of(2020, 1, 1));
    }

    @Test
    void testGetTableName(){
        assertEquals("repair", r.getTableName());
    }

    @Test
    void testGetNewRecord() throws SQLException {
        when(rs.getLong("id")).thenReturn(1l);
        when(rs.getBigDecimal("total_revenue")).thenReturn(BigDecimal.TEN);
        when(rs.getBigDecimal("total_expense")).thenReturn(new BigDecimal("2"));
        when(rs.getString("name")).thenReturn("Zamena ulja");
        when(rs.getDate("start_date")).thenReturn(Date.valueOf(LocalDate.of(2022, 1, 1)));

        r.setServiceBook(serviceBook);

        Repair resultRepair = (Repair) r.getNewRecord(rs);

        assertEquals(1l, resultRepair.getRepairID());
        assertEquals(BigDecimal.TEN, resultRepair.getTotalRevenue());
        assertEquals(new BigDecimal("2"), resultRepair.getTotalExpense());
        assertEquals("Zamena ulja", resultRepair.getName());
        assertEquals(LocalDate.of(2022, 1, 1), resultRepair.getStartDate());
        assertSame(r.getServiceBook(), resultRepair.getServiceBook());
    }

    @Test
    void testGetInsertionColumns(){
        assertEquals("name, start_date, total_revenue, total_expense, service_book_id", r.getInsertionColumns());
    }

    @Test
    void testGetAtrPlaceHolders(){
        assertEquals("?, ?, ?, ?, ?", r.getAtrPlaceHolders());
    }

    @Test
    void testSetPreparedStatementParameters() throws SQLException {
        r.setName("Zamena filtera");
        r.setStartDate(LocalDate.of(2022, 1, 1));
        r.setTotalRevenue(BigDecimal.TEN);
        r.setTotalExpense(new BigDecimal("2"));

        serviceBook.setServiceBookID(5l);
        r.setServiceBook(serviceBook);

        r.setPreparedStatementParameters(ps);

        verify(ps).setString(1 , r.getName());
        verify(ps).setDate(2, Date.valueOf(r.getStartDate()));
        verify(ps).setBigDecimal(3, r.getTotalRevenue());
        verify(ps).setBigDecimal(4, r.getTotalExpense());
        verify(ps).setLong(5, r.getServiceBook().getServiceBookID());
    }

    @Test
    void testGetPKWhereCondition(){
        r.setRepairID(6l);

        assertEquals("id = 6", r.getPKWhereCondition());
    }

    @Test
    void testGetAttributeValuesWhereCondition(){
        assertThrows(java.lang.UnsupportedOperationException.class, () -> r.getAttributeValuesWhereCondition());
    }

    @Test
    void testGetUpdateColumnsWithPlaceHolders(){
        assertEquals("name = ?, start_date = ?, total_revenue = ?, total_expense = ?, service_book_id = ?",
                r.getUpdateColumnsWithPlaceHolders());
    }

    @Test
    void testGetFKWhereCondition(){
        serviceBook.setServiceBookID(5l);

        r.setServiceBook(serviceBook);

        assertEquals( "service_book_id = 5", r.getFKWhereCondition());
    }

    @Test
    void testGetJoinCondition(){
        assertEquals("", r.getJoinCondition());
    }

    @Test
    void testSetAutoGeneratedKey(){
        r.setAutoGeneratedKey(27l);

        assertEquals(27l, r.getRepairID());
    }
}