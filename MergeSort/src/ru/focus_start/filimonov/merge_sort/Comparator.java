package ru.focus_start.filimonov.merge_sort;

interface Comparator<T> {
    int compare(Comparable<T> o1, Comparable<T> o2);
}
