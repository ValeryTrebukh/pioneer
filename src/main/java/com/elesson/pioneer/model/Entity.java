package com.elesson.pioneer.model;

/**
 * The {@code Entity} class represents an abstract entity.
 */
public abstract class Entity {
    protected Integer id;

    /**
     * The default constructor.
     */
    public Entity() {
    }

    /**
     * Instantiates a new Entity.
     *
     * @param id the id
     */
    public Entity(Integer id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Is new boolean.
     *
     * @return true if entity has no Id assigned to it.
     */
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
