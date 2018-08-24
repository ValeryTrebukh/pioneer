package com.elesson.pioneer.model;

import java.sql.ResultSet;
import java.sql.SQLException;

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
     * @param rs the ResultSet object
     * @throws SQLException the sql exception
     */
    public User(ResultSet rs) throws SQLException {
        this(rs.getInt("u.uid"),
                rs.getString("u.name"),
                rs.getString("u.email"),
                rs.getString("u.password"),
                User.Role.valueOf(rs.getString("u.role")));
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

    /**
     * The user roles.
     */
    public enum Role {
        CLIENT,
        ADMIN
    }
}

