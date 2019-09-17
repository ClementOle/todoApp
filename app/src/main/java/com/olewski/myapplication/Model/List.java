package com.olewski.myapplication.Model;

public class List {

    private Integer id;

    private String name;

    public List(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public List(String name) {
        this.id = (int) (Math.random() * 1000);
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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        List list = (List) o;

        if (getId() != null ? !getId().equals(list.getId()) : list.getId() != null) return false;
        return getName() != null ? getName().equals(list.getName()) : list.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "List{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
