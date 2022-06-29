package rs.ac.bg.fon.nprog.client.view.form.model;

import rs.ac.bg.fon.nprog.commonlibrary.domain.Service;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Dragon
 */
public class TableModelServices extends AbstractTableModel {

    private List<Service> services;
    private final String[] columnNames = new String[]{"Name", "Price", "Description", "Material cost"};
    private final Class[] columnClass = new Class[]{String.class, BigDecimal.class, String.class, BigDecimal.class};

    public TableModelServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public int getRowCount() {
        return services != null ? services.size() : 0;
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

        Service service = services.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return service.getName();
            case 1:
                return service.getPrice();
            case 2:
                return service.getDescription();
            case 3:
                return service.getMaterialCost();
            default:
                return "N/A";
        }
    }

    public void addService(Service service) {
        this.services.add(service);
        //fireTableDataChanged();
        fireTableRowsInserted(services.size() - 1, services.size() - 1);
    }

    public void removeService(int index) {
        services.remove(index);
        fireTableDataChanged();
    }

    public Service getService(int index) {
        return services.get(index);
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
        fireTableDataChanged();
    }
}
