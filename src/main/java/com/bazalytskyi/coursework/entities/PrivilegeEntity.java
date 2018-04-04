package com.bazalytskyi.coursework.entities;



import javax.persistence.*;
import java.util.Objects;

@Entity
public class PrivilegeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "role_fk")
    private RoleEntity role;

    public PrivilegeEntity() {

    }

    public PrivilegeEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleEntity getRoles() {
        return role;
    }

    public void setRoles(RoleEntity role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "PrivilegeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roles=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivilegeEntity that = (PrivilegeEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, role);
    }
}
