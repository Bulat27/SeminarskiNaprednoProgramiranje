package rs.ac.bg.fon.nprog.client.view.form.model;

import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Dragon
 */
public class TableModelEngagedEmployees extends AbstractTableModel {

    private List<Employee> employees;
    private final String[] columnNames = new String[]{"First and last name", "Hourly rate"};
    private final Class[] columnClass = new Class[]{String.class, BigDecimal.class};

    public TableModelEngagedEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public int getRowCount() {
        return employees != null ? employees.size() : 0;
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
        Employee employee = employees.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return employee.toString();
            case 1:
                return employee.getHourlyRate();
            default:
                return "N/A";
        }
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        //fireTableDataChanged();
        fireTableRowsInserted(employees.size() - 1, employees.size() - 1);
    }

    public void removeEmployee(int index) {
        employees.remove(index);
        fireTableDataChanged();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        fireTableDataChanged();
    }

    public Employee getEmployee(int index) {
        return employees.get(index);
    }

    public void setEmployee(Employee employee, int selectedRow) {
        employees.set(selectedRow, employee);
        fireTableDataChanged();
    }
}
