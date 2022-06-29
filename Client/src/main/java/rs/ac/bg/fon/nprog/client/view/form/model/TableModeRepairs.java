package rs.ac.bg.fon.nprog.client.view.form.model;

import rs.ac.bg.fon.nprog.client.constant.MyClientConstants;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Repair;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Dragon
 */
public class TableModeRepairs extends AbstractTableModel {

    private List<Repair> repairs;
    private final String[] columnNames = new String[]{"Name", "Start date", "Total revenue", "Total expense"};
    private final Class[] columnClass = new Class[]{String.class, String.class, BigDecimal.class, BigDecimal.class};

    public TableModeRepairs(List<Repair> repairs) {
        this.repairs = repairs;
    }

    @Override
    public int getRowCount() {
        return repairs != null ? repairs.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return column > columnNames.length ? "N/A" : columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex > columnClass.length ? Object.class : columnClass[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Repair repair = repairs.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return repair.getName();
            case 1:
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(MyClientConstants.DATE_PATTERN);
                return repair.getStartDate() != LocalDate.MAX ? repair.getStartDate().format(dtf) : "";
            case 2:
                return repair.getTotalRevenue();
            case 3:
                return repair.getTotalExpense();
            default:
                return "N/A";
        }
    }

    public void addRepair(Repair repair) {
        this.repairs.add(repair);
        //fireTableDataChanged();
        fireTableRowsInserted(repairs.size() - 1, repairs.size() - 1);
    }

    public void removeRepair(int index) {
        repairs.remove(index);
        fireTableDataChanged();
    }

    public List<Repair> getRepairs() {
        return repairs;
    }

    public void setRepairs(List<Repair> repairs) {
        this.repairs = repairs;
        fireTableDataChanged();
    }

    public Repair getRepair(int index) {
        return repairs.get(index);
    }

    public void setRepair(Repair repair, int selectedRow) {
        repairs.set(selectedRow, repair);
        fireTableDataChanged();
    }
}
