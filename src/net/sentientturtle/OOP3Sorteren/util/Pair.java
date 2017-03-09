package net.sentientturtle.OOP3Sorteren.util;

public class Pair<T, T2> {
    private T object1;
    private T2 object2;

    public Pair() {
        this(null, null);
    }

    public Pair(T Object1, T2 Object2) {
        object1 = Object1;
        object2 = Object2;
    }

    public T getObject1() {
        return object1;
    }

    public T2 getObject2() {
        return object2;
    }

    public boolean contains(Object object) {
        return object.equals(object1) || object.equals(object2);
    }

    public void set(T object1, T2 object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    public void setObject1(T object1) {
        this.object1 = object1;
    }

    public void setObject2(T2 object2) {
        this.object2 = object2;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (object1 != null ? !object1.equals(pair.object1) : pair.object1 != null) return false;
        return object2 != null ? object2.equals(pair.object2) : pair.object2 == null;
    }

    @Override
    public int hashCode() {
        int result = object1 != null ? object1.hashCode() : 0;
        result = 31 * result + (object2 != null ? object2.hashCode() : 0);
        return result;
    }
}
