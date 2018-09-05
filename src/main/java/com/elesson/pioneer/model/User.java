package com.elesson.pioneer.model;

import java.util.Objects;

/**
 * Represents a row in the &quot;users&quot; database table,
 * with each column mapped to a property of this class.
 */
public class User extends Entity {
    private String name;
    private String email;
    private String password;
    private Role role;

    /**
     * The default constructor.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param id       the id
     * @param name     the name
     * @param email    the email
     * @param password the password
     * @param role     the role
     */
    public User(Integer id, String name, String email, String password, Role role) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Instantiates a new User.
     *
     * @param name     the name
     * @param email    the email
     * @param password the password
     * @param role     the role
     */
    public User(String name, String email, String password, Role role) {
        this(null, name, email, password, role);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                role == user.role;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, email, password, role);
    }

    /**
     * The user roles.
     */
    public enum Role {
        CLIENT,
        ADMIN
    }
}

