/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.nprog.client.view.controller;

import rs.ac.bg.fon.nprog.client.view.coordinator.Coordinator;
import rs.ac.bg.fon.nprog.client.view.form.EngagedEmployeesForm;
import rs.ac.bg.fon.nprog.client.view.form.model.TableModelEngagedEmployees;
import rs.ac.bg.fon.nprog.commonlibrary.domain.Employee;

import java.util.List;

/**
 *
 * @author Dragon
 */
public class EngagedEmployeesFormController {

    private EngagedEmployeesForm engagedEmployeesForm;
    private List<Employee> employees;

    public EngagedEmployeesFormController(List<Employee> employees) {
        this.employees = employees;
        engagedEmployeesForm = new EngagedEmployeesForm(Coordinator.getInstance().getRepairForm(), true, this);
    }

    public void openForm() {
        prepareTable();
        engagedEmployeesForm.setVisible(true);
    }

    private void prepareTable() {
        TableModelEngagedEmployees tmee = new TableModelEngagedEmployees(employees);

        engagedEmployeesForm.setTableEngagedEmployeesModel(tmee);
    }
}
