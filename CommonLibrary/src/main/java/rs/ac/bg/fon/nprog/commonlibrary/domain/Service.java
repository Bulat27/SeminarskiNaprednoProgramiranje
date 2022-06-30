package rs.ac.bg.fon.nprog.commonlibrary.domain;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Predstavlja uslugu koju moze da pruzi auto servis.
 * Usluga se pruza u okviru stavke servisa koja joj daje dodatan kontekst kroz svoje atribute.
 * Sadrzi atribute idUsluge, cena, naziv, opis i troskoviMaterijala.
 *
 * @author Dragon
 * @version 1.0
 */
public class Service implements GeneralDObject {

    /**
     * Identifikacioni broj usluge koji predstavlja primarni kljuc u bazi podataka, podrazumevana vrednost je null.
     */
    private Long serviceID;

    /**
     * Cena usluge koja je fiksna i zna se unapred, dodatna cena moze da se doda u okviru stavke servisa.
     * Podrazumevana vrednost je null.
     */
    private BigDecimal price;

    /**
     * Naziv usluge, podrazumevana vrednost je null.
     */
    private String name;

    /**
     * Opis usluge koji govore o tome sta ona zapravo podrazumeva, podrazumevana vrednost je null.
     */
    private String description;

    /**
     * Troskovi materijala koji su fiksni i unapred poznati, dodatni troskovi se mogu dodati u okviru stavke servisa.
     * Podrazumevana vrednost je null.
     */
    private BigDecimal materialCost;

    /**
     * Postavlja atribute na njihove podrazumevane vrednosti.
     */
    public Service() {
    }

    /**
     * Postavlja atrbitute idUsluge, cena, naziv, opis i troskoviMaterijala na unete vrednosti.
     *
     * @param serviceID nova vrednost atributa idUsluge
     * @param price nova vrednost atributa cena
     * @param name nova vrednost atributa naziv
     * @param description nova vrednost atributa opis
     * @param materialCost nova vrednost atributa troskoviMaterijala
     */
    public Service(Long serviceID, BigDecimal price, String name, String description, BigDecimal materialCost) {
        this.serviceID = serviceID;
        this.price = price;
        this.name = name;
        this.description = description;
        this.materialCost = materialCost;
    }

    /**
     * Postavlja atrbitute cena, naziv, opis i troskoviMaterijala na unete vrednosti. Atribut idUsluge postavlja
     * na podrazumevanu vrednost.
     *
     * @param price nova vrednost atributa cena
     * @param name nova vrednost atributa naziv
     * @param description nova vrednost atributa opis
     * @param materialCost nova vrednost atributa troskoviMaterijala
     */
    public Service(BigDecimal price, String name, String description, BigDecimal materialCost) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.materialCost = materialCost;
    }

    /**
     * Vraca vrednost atributa idUsluge.
     *
     * @return idUsluge kao Long
     */
    public Long getServiceID() {
        return serviceID;
    }

    /**
     * Postavlja novu vrednost atributa idUsluge.
     *
     * @param serviceID nova vrednost atributa idUsluge
     */
    public void setServiceID(Long serviceID) {
        this.serviceID = serviceID;
    }

    /**
     * Vraca vrednost atributa cena.
     *
     * @return cena kao BigDecimal
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Postavlja novu vrednost atributa cena.
     *
     * @param price nova vrednost atributa cena
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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
     * Vraca vrednost atributa opis.
     *
     * @return opis kao String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Postavlja novu vrednost atributa opis.
     *
     * @param description nova vrednost atributa opis
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Vraca vrednost atributa troskoviMaterijala.
     *
     * @return troskoviMaterijala kao BigDecimal
     */
    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    /**
     * Postavlja novu vrednost atributa troskoviMaterijala.
     *
     * @param materialCost nova vrednost atributa troskoviMaterijala
     */
    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    /**
     * Vraca podatke o usluzi kao jedan String. U ovom slucaju to je samo njen naziv.
     *
     * @return String koji se sastoji od naziva usluge
     */
    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getTableName() {
        return "service";
    }

    @Override
    public GeneralDObject getNewRecord(ResultSet rs) throws SQLException {
        return new Service(rs.getLong("id"), rs.getBigDecimal("price"), rs.getString("name"), rs.getString("description"), rs.getBigDecimal("material_cost"));
    }

    @Override
    public String getInsertionColumns() {
        return "price, name, description, material_cost";
    }

    @Override
    public String getAtrPlaceHolders() {
        return "? , ? , ?, ?";
    }

    @Override
    public void setPreparedStatementParameters(PreparedStatement ps) throws SQLException {
        ps.setBigDecimal(1, price);
        ps.setString(2, name);
        ps.setString(3, description);
        ps.setBigDecimal(4, materialCost);
    }

    @Override
    public String getPKWhereCondition() {
        return "id = " + serviceID;
    }

    @Override
    public String getAttributeValuesWhereCondition() {
        return "name like '%" + name + "%'";
    }

    @Override
    public String getUpdateColumnsWithPlaceHolders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        setServiceID(id);
    }
}
