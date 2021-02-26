package ru.focus_start.filimonov.merge_sort;

import java.io.*;
import java.util.LinkedList;

class Sorter<T> {
    File getSorted(File input1, File input2, Comparator comparator) {
        LinkedList<T> sortedList = new LinkedList<>();

        try (BufferedReader reader1 = new BufferedReader(new FileReader(input1.getPath()));
             BufferedReader reader2 = new BufferedReader(new FileReader(input2.getPath()))) {

            T[] buffer = (T[]) new Object[2];

            buffer[0] = (T) reader1.readLine();
            buffer[1] = (T) reader2.readLine();

            while (buffer[0] != null && buffer[1] != null) {
                if (comparator.compare((Comparable) buffer[0], (Comparable)buffer[1]) < 0) {
                    sortedList.add((T) buffer[0]);

                    buffer[0] = (T) reader1.readLine();
                } else {
                    sortedList.add((T) buffer[1]);

                    buffer[1] = (T) reader2.readLine();
                }
            }

            if (buffer[0] == null) {
                while (buffer[1] != null) {
                    sortedList.add((T) buffer[1]);
                    buffer[1] = (T) reader2.readLine();
                }
            } else {
                while (buffer[0] != null) {
                    sortedList.add((T) buffer[0]);
                    buffer[0] = (T) reader1.readLine();
                }
            }
        } catch (IOException e) {
            System.out.println("The file isn't found");
        }

        File output = null;

        try (PrintWriter writer = new PrintWriter(output = File.createTempFile("mergeSort", "b"))) {
            for (T e : sortedList) {
                writer.println(e);
            }

        } catch (IOException e) {
            System.out.println("IOException of writing output temporary file");
        }

        return output;
    }
}
