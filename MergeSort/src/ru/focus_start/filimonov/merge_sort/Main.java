package ru.focus_start.filimonov.merge_sort;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("The program must have at least 3 arguments: data type (strings -s or integers -i), output and input files paths. There is just "
                    + args.length + " arguments now");

            return;
        }

        SortMode sortMode = new SortMode();

        int i = 0;

        try {
            while (i < 2) {
                sortMode.switchMode(args[i]);

                i++;
            }
        } catch (IllegalArgumentException e) {
            if (i == 0) {
                System.out.println("Incorrect first argument. It must be -s, -i, -d or -a, but it is " + args[i] + " now");

                return;
            }

            if (!sortMode.isTypeSet) {
                System.out.println("It is necessary to set data type: -s strings or -i integer");

                return;
            }
        } catch (IllegalStateException e) {
            System.out.println("It is forbidden to set contradicting or same arguments");

            return;
        }

        File outputFile = new File(args[i]);

        i++;

        ArrayList<File> inputFiles = new ArrayList<File>(args.length - i);

        while (i < args.length) {
            File inputFile = new File(args[i]);

            if (inputFile.exists()) {
                inputFiles.add(inputFile);
            } else {
                System.out.println("There is no file on path " + inputFile.getPath());
            }

            i++;
        }

        Sorter sorter;


        Comparator comparator;

        if (sortMode.descending){
            comparator = new Comparator(){
                public int compare (Comparable c1, Comparable c2){
                    return c2.compareTo(c1);
                }
            };
        } else {
            comparator = new Comparator(){
                public int compare (Comparable c1, Comparable c2){
                    return c1.compareTo(c2);
                }
            };
        }

        if (sortMode.isStringType) {
            sorter = new Sorter() {
                public File getSorted(File input1, File input2) {
                    return Main.<String>getSortedUntypezed(input1, input2, comparator);
                }
            };
        } else {
            sorter = new Sorter() {
                public File getSorted(File input1, File input2) {
                    return Main.<Integer>getSortedUntypezed(input1, input2, comparator);
                }
            };
        }

        while (inputFiles.size() != 1) {
            for (int j = 0; j < inputFiles.size() - 1; j++) {
                inputFiles.set(j, sorter.getSorted(inputFiles.get(j), inputFiles.get(j + 1)));

                inputFiles.remove(j + 1);
            }
        }

        try (PrintWriter result = new PrintWriter(outputFile.getPath());
             BufferedReader reader = new BufferedReader(new FileReader(inputFiles.get(0).getPath()))) {
            while (true) {
                String buffer = reader.readLine();

                if (buffer == null) {
                    break;
                }

                result.println(buffer);
            }
        } catch (IOException e) {

        }
    }

    private static <T> File getSortedUntypezed(File input1, File input2, Comparator comparator) {
        LinkedList<T> sortedList = new LinkedList<>();

        try (BufferedReader reader1 = new BufferedReader(new FileReader(input1.getPath()));
             BufferedReader reader2 = new BufferedReader(new FileReader(input2.getPath()))) {

            String[] buffer = new String[2];

            buffer[0] = reader1.readLine();
            buffer[1] = reader2.readLine();

            while (buffer[0] != null && buffer[1] != null) {
                if (comparator.compare(buffer[0], buffer[1]) < 0) {
                    sortedList.add((T) buffer[0]);
                    buffer[0] = reader1.readLine();
                } else {
                    sortedList.add((T) buffer[1]);
                    buffer[1] = reader2.readLine();
                }
            }

            if (buffer[0] == null) {
                while (buffer[1] != null) {
                    sortedList.add((T) buffer[1]);
                    buffer[1] = reader2.readLine();
                }
            } else {
                while (buffer[0] != null) {
                    sortedList.add((T) buffer[0]);
                    buffer[0] = reader1.readLine();
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
        }

        return output;
    }
}
