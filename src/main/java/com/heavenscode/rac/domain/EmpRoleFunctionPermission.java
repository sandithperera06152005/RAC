package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A EmpRoleFunctionPermission.
 */
@Entity
@Table(name = "emp_rolefunctionpermission")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpRoleFunctionPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "roleid")
    private Integer roleId;

    @Column(name = "functionid")
    private Integer functionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpRoleFunctionPermission)) {
            return false;
        }
        return getId() != null && getId().equals(((EmpRoleFunctionPermission) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "EmpRoleFunctionPermission{" + "id=" + getId() + ", roleId=" + getRoleId() + ", functionId=" + getFunctionId() + "}";
    }
}
