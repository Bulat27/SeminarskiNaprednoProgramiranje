package rs.ac.bg.fon.nprog.client.view.form.model;

import rs.ac.bg.fon.nprog.client.constant.MyClientConstants;
import rs.ac.bg.fon.nprog.commonlibrary.domain.RepairItem;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Dragon
 */
public class TableModelRepairItems extends AbstractTableModel {

    private List<RepairItem> repairItems;
    private final String[] columnNames = new String[]{"No.", "Service name", "Start date", "End date", "Remark", "Emp. expense", "Extra expense", "Extra revenue", "Serv. price", "Mat. cost"};
    private final Class[] columnClass = new Class[]{Integer.class, String.class, String.class, String.class, String.class, BigDecimal.class, BigDecimal.class, BigDecimal.class, BigDecimal.class, BigDecimal.class};

    public TableModelRepairItems(List<RepairItem> repairItems) {
        this.repairItems = repairItems;
    }

    @Override
    public int getRowCount() {
        return repairItems != null ? repairItems.size() : 0;
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
        RepairItem repairItem = repairItems.get(rowIndex);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(MyClientConstants.DATE_PATTERN);

        switch (columnIndex) {
            case 0:
                return repairItem.getOrderNumber();
            case 1:
                return repairItem.getService().toString();
            case 2:
                return repairItem.getStartDate().format(dtf);
            case 3:
                return repairItem.getEndDate().format(dtf);
            case 4:
                return repairItem.getRemark();
            case 5:
                return repairItem.getEmployeeExpense();
            case 6:
                return repairItem.getAdditionalExpense();
            case 7:
                return repairItem.getAdditionalRevenue();
            case 8:
                return repairItem.getService().getPrice();
            case 9:
                return repairItem.getService().getMaterialCost();
            default:
                return "N/A";
        }
    }

    public void addRepairItem(RepairItem repairItem) {
        this.repairItems.add(repairItem);
        //fireTableDataChanged();
        fireTableRowsInserted(repairItems.size() - 1, repairItems.size() - 1);
    }

    public void removeRepairItem(int index) {
        repairItems.remove(index);
        fireTableDataChanged();
    }

    public List<RepairItem> getRepairItems() {
        return repairItems;
    }

    public void setRepairItems(List<RepairItem> repairItems) {
        this.repairItems = repairItems;
        fireTableDataChanged();
    }

    public RepairItem getRepairItem(int index) {
        return repairItems.get(index);
    }

    public void setRepairItem(RepairItem repairItem, int selectedRow) {
        repairItems.set(selectedRow, repairItem);
        fireTableDataChanged();
    }

    public void resetOrderNumbers() {
        for (int i = 0; i < repairItems.size(); i++) {
            repairItems.get(i).setOrderNumber(i + 1);
        }
    }
}
