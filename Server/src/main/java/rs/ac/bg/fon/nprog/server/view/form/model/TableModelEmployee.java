package rs.ac.bg.fon.nprog.server.view.form.model;


import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cartman
 */
public class TableModelEmployee extends AbstractTableModel {

    private final String[] columnNames = new String[]{"Username", "First and last name", "Role"};
    private final Class[] columnClass = new Class[]{String.class, String.class, String.class};
    private List<Employee> employees;

    public TableModelEmployee() {
        this.employees = new ArrayList<>();
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
                return employee.getUsername();
            case 1:
                return employee.toString();
            case 2:
                return employee.getEmployeeRole();
            default:
                return "N/A";
        }
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
        //fireTableDataChanged();
        fireTableRowsInserted(employees.size() - 1, employees.size() - 1);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        fireTableDataChanged();
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
