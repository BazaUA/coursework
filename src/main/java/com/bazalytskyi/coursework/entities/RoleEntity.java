package com.bazalytskyi.coursework.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @OneToMany(mappedBy = "role")
    private Collection<UserEntity> users;

    @OneToMany(mappedBy = "role")
    private Collection<PrivilegeEntity> privileges;

    public RoleEntity(){

    }

    public RoleEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", privileges=" + privileges +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(users, that.users) &&
                Objects.equals(privileges, that.privileges);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, users, privileges);
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

    public Collection<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserEntity> users) {
        this.users = users;
    }

    public Collection<PrivilegeEntity> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<PrivilegeEntity> privileges) {
        this.privileges = privileges;
    }
}
