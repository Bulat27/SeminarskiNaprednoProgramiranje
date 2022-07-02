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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceTest {

    private Service s;

    @Mock
    private ResultSet rs;
    @Mock
    private PreparedStatement ps;

    @BeforeEach
    void setUp() {
        s = new Service();
    }

    @AfterEach
    void tearDown() {
        s = null;
    }

    @Test
    void testService() {
        assertEquals(null, s.getServiceID());
        assertEquals(null, s.getPrice());
        assertEquals(null, s.getName());
        assertEquals(null, s.getDescription());
        assertEquals(null, s.getMaterialCost());
    }

    @Test
    void testServiceSviAtributi() {
        s = new Service(5l, BigDecimal.TEN, "Zamena filtera", "Promeniti filter goriva", BigDecimal.ONE);

        assertEquals(5l, s.getServiceID());
        assertEquals(BigDecimal.TEN, s.getPrice());
        assertEquals("Zamena filtera", s.getName());
        assertEquals("Promeniti filter goriva", s.getDescription());
        assertEquals(BigDecimal.ONE, s.getMaterialCost());
    }

    @Test
    void testServiceSviAtributiOsimID() {
        s = new Service(BigDecimal.TEN, "Zamena filtera", "Promeniti filter goriva", BigDecimal.ONE);

        assertEquals(null, s.getServiceID());

        assertEquals(BigDecimal.TEN, s.getPrice());
        assertEquals("Zamena filtera", s.getName());
        assertEquals("Promeniti filter goriva", s.getDescription());
        assertEquals(BigDecimal.ONE, s.getMaterialCost());
    }

    @ParameterizedTest
    @CsvSource({"1", "10", "13", "4"})
    void testSetServiceID(Long serviceID) {
        s.setServiceID(serviceID);

        assertEquals(serviceID, s.getServiceID());
    }

    @ParameterizedTest
    @CsvSource({"10", "15", "50", "27"})
    void testSetPrice(BigDecimal price) {
        s.setPrice(price);

        assertEquals(price, s.getPrice());
    }

    @ParameterizedTest
    @CsvSource({"Zamena kocnica", "Zamena ulja", "Pregled pomocu dijagnostike", "Remont glave motora"})
    void testSetName(String name) {
        s.setName(name);

        assertEquals(name, s.getName());
    }

    @ParameterizedTest
    @CsvSource({"Zameniti kocnice", "Zameniti ulje", "Pregledati automobil pomocu dijagnostike", "Izvrsiti remont glave motora"})
    void testSetDescription(String description) {
        s.setDescription(description);

        assertEquals(description, s.getDescription());
    }

    @ParameterizedTest
    @CsvSource({"5", "1", "0", "12"})
    void testSetMaterialCost(BigDecimal materialCost) {
        s.setMaterialCost(materialCost);

        assertEquals(materialCost, s.getMaterialCost());
    }

    @Test
    void testToString() {
        s.setName("Remont glave motora");

        String str = s.toString();

        assertTrue(str.contains("Remont glave motora"));
    }

    @Test
    void testGetTableName(){
        assertEquals("service", s.getTableName());
    }

    @Test
    void testGetNewRecord() throws SQLException {
        when(rs.getLong("id")).thenReturn(5l);
        when(rs.getBigDecimal("price")).thenReturn(BigDecimal.TEN);
        when(rs.getString("name")).thenReturn("Zamena filtera");
        when(rs.getString("description")).thenReturn("Promeniti filter goriva");
        when(rs.getBigDecimal("material_cost")).thenReturn(BigDecimal.ONE);

        Service resultService = (Service) s.getNewRecord(rs);

        assertEquals(5l, resultService.getServiceID());
        assertEquals(BigDecimal.TEN, resultService.getPrice());
        assertEquals("Zamena filtera", resultService.getName());
        assertEquals("Promeniti filter goriva", resultService.getDescription());
        assertEquals(BigDecimal.ONE, resultService.getMaterialCost());
    }

    @Test
    void testGetInsertionColumns(){
        assertEquals("price, name, description, material_cost", s.getInsertionColumns());
    }

    @Test
    void testGetAtrPlaceHolders(){
        assertEquals("? , ? , ?, ?", s.getAtrPlaceHolders());
    }

    @Test
    void testSetPreparedStatementParameters() throws SQLException {
        s = new Service(BigDecimal.TEN, "Zamena filtera", "Promeniti filter goriva", BigDecimal.ONE);
        s.setPreparedStatementParameters(ps);

        verify(ps).setBigDecimal(1, s.getPrice());
        verify(ps).setString(2, s.getName());
        verify(ps).setString(3, s.getDescription());
        verify(ps).setBigDecimal(4, s.getMaterialCost());
    }

    @Test
    void testGetPKWhereCondition(){
        s.setServiceID(5l);

        assertEquals("id = 5", s.getPKWhereCondition());
    }

    @Test
    void testGetAttributeValuesWhereCondition() {
        s.setName("Zamena filtera");

        assertEquals("name like '%Zamena filtera%'", s.getAttributeValuesWhereCondition());
    }

    @Test
    void testGetUpdateColumnsWithPlaceHolders(){
        assertThrows(java.lang.UnsupportedOperationException.class, () -> s.getUpdateColumnsWithPlaceHolders());
    }

    @Test
    void testGetFKWhereCondition(){
        assertThrows(java.lang.UnsupportedOperationException.class, () -> s.getFKWhereCondition());
    }

    @Test
    void testGetJoinCondition(){
        assertEquals("", s.getJoinCondition());
    }

    @Test
    void testSetAutoGeneratedKey(){
        s.setAutoGeneratedKey(27l);

        assertEquals(27l, s.getServiceID());
    }
}