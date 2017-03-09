package net.sentientturtle.OOP3Sorteren.sort;

import net.sentientturtle.OOP3Sorteren.util.Pair;

import java.util.Objects;

public class DataSet<E extends Comparable<E>> {
    private E[] data;
    private Pair<Integer, Integer> swappedColumns;
    public DataSet(E[] data) {
        this.data = Objects.requireNonNull(data);
        swappedColumns = new Pair<>(-1, -1);
    }

    public E[] getData() {
        return data;
    }

    public void swap(int index1, int index2) {
        E temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
        swappedColumns.setObject1(index1);
        swappedColumns.setObject2(index2);
    }

    public Pair<Integer, Integer> getSwappedColumns() {
        return swappedColumns;
    }
}
