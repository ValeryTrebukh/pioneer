package com.elesson.pioneer.model;

public class Entity {
    protected Integer id;
    protected String name;

    public Entity() {
    }

    public Entity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean isNew() {
        return this.id == null;
    }

    public int hashCode() {
        return id == null ? 0 : id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity e = (Entity) o;

        return id.equals(e.id);
    }
}
