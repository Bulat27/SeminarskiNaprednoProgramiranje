package rs.ac.bg.fon.nprog.client.view.form.model;

import rs.ac.bg.fon.nprog.client.constant.MyClientConstants;
import rs.ac.bg.fon.nprog.commonlibrary.domain.ServiceBook;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Dragon
 */
public class TableModelServiceBooks extends AbstractTableModel {

    private List<ServiceBook> serviceBooks;
    private final String[] columnNames = new String[]{"Client first and last name", "Vehicle description", "Initial date", "Active"};
    private final Class[] columnClass = new Class[]{String.class, String.class, String.class, String.class};

    public TableModelServiceBooks(List<ServiceBook> serviceBooks) {
        this.serviceBooks = serviceBooks;
    }

    @Override
    public int getRowCount() {
        return serviceBooks != null ? serviceBooks.size() : 0;
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
        ServiceBook serviceBook = serviceBooks.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return serviceBook.toString();
            case 1:
                return serviceBook.getVehicleDescription();
            case 2:
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(MyClientConstants.DATE_PATTERN);
                return serviceBook.getInitialDate().format(dtf);
            case 3:
                return serviceBook.isActive() ? "YES" : "NO";
            default:
                return "N/A";
        }
    }

    public void addServiceBook(ServiceBook serviceBook) {
        this.serviceBooks.add(serviceBook);
        //fireTableDataChanged();
        fireTableRowsInserted(serviceBooks.size() - 1, serviceBooks.size() - 1);
    }

    public void removeServiceBook(int index) {
        serviceBooks.remove(index);
        fireTableDataChanged();
    }

    public List<ServiceBook> getServiceBooks() {
        return serviceBooks;
    }

    public void setServiceBooks(List<ServiceBook> serviceBooks) {
        this.serviceBooks = serviceBooks;
        fireTableDataChanged();
    }

    public ServiceBook getServiceBook(int index) {
        return serviceBooks.get(index);
    }

    public void setServiceBook(ServiceBook serviceBook, int selectedRow) {
        serviceBooks.set(selectedRow, serviceBook);
        fireTableDataChanged();
    }
}
