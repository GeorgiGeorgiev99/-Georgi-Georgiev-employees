package com.company.model;

import java.util.Objects;

public class PairEmployees {

    private Long employeeOne;
    private Long employeeTwo;

    public PairEmployees(Long employeeOne, Long employeeTwo) {
        this.employeeOne = employeeOne;
        this.employeeTwo = employeeTwo;
    }

    public Long getEmployeeOne() {
        return employeeOne;
    }

    public void setEmployeeOne(Long employeeOne) {
        this.employeeOne = employeeOne;
    }

    public Long getEmployeeTwo() {
        return employeeTwo;
    }

    public void setEmployeeTwo(Long employeeTwo) {
        this.employeeTwo = employeeTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairEmployees that = (PairEmployees) o;
        return Objects.equals(employeeOne, that.employeeOne) && Objects.equals(employeeTwo, that.employeeTwo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeOne, employeeTwo);
    }
}
