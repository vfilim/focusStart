package ru.focus_start.filimonov.merge_sort;

import java.io.*;
import java.util.LinkedList;

class Sorter {
    boolean isStringType;
    boolean isDescending;

    public Sorter(SortMode sortMode) {
        isStringType = sortMode.isStringType;
        isDescending = sortMode.descending;
    }

    File getSorted(File input0, File input1) {
        LinkedList<String> sortedList = new LinkedList<>();

        try (BufferedReader reader0 = new BufferedReader(new FileReader(input0.getPath()));
             BufferedReader reader1 = new BufferedReader(new FileReader(input1.getPath()))) {

            String[] buffer = new String[2];

            buffer[0] = reader0.readLine();
            buffer[1] = reader1.readLine();

            checkType(buffer[0]);
            checkType(buffer[1]);

            String lastElement0 = buffer[0];
            String lastElement1 = buffer[1];

            while (buffer[0] != null && buffer[1] != null) {
                if (!checkOrder(buffer[0], lastElement0, input0)){
                    buffer[0] = reader0.readLine();

                    continue;
                }

                if (!checkOrder(buffer[1], lastElement1, input1)){
                    buffer[1] = reader1.readLine();

                    continue;
                }

                try {
                    checkType(buffer[0]);
                } catch (NumberFormatException e) {
                    System.out.println("The integer sorting mode is turned on. " + buffer[0] + " is dropped, because it is not integer");

                    buffer[0] = reader0.readLine();
                    continue;
                }

                try {
                    checkType((buffer[1]));
                } catch (NumberFormatException e) {
                    System.out.println("The integer sorting mode is turned on. " + buffer[1] + " is dropped, because it is not integer");

                    buffer[1] = reader1.readLine();
                    continue;
                }

                if (compare(buffer[0], buffer[1]) > 0) {
                    sortedList.add(buffer[0]);

                    lastElement0 = buffer[0];

                    buffer[0] = reader0.readLine();
                } else {
                    sortedList.add(buffer[1]);

                    lastElement1 = buffer[1];

                    buffer[1] = reader1.readLine();
                }
            }

            if (buffer[0] == null) {
                while (buffer[1] != null) {
                    if (buffer[1].compareTo(lastElement1) < 0) {
                        System.out.println("The input files must be already sorted. The file " + input1.getName() + " is not sorted. The program continued, but "
                                + buffer[1] + " is dropped");

                        buffer[1] = reader1.readLine();
                        continue;
                    }

                    try {
                        checkType(buffer[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("The integer sorting mode is turned on. " + buffer[1] + " is dropped, because it is not integer");

                        buffer[1] = reader1.readLine();
                        continue;
                    }

                    sortedList.add(buffer[1]);

                    lastElement1 = buffer[1];

                    buffer[1] = reader1.readLine();
                }
            } else {
                while (buffer[0] != null) {
                    if (buffer[0].compareTo(lastElement0) < 0) {
                        System.out.println("The input files must be already sorted. The file " + input1.getName() + "  is not sorted. The program continued, but "
                                + buffer[0] + " is dropped");

                        buffer[0] = reader0.readLine();
                        continue;
                    }

                    try {
                        checkType(buffer[0]);
                    } catch (NumberFormatException e) {
                        System.out.println("The integer sorting mode is turned on. " + buffer[0] + " is dropped, because it is not integer");

                        buffer[0] = reader0.readLine();
                        continue;
                    }

                    sortedList.add(buffer[0]);

                    lastElement0 = buffer[0];

                    buffer[0] = reader0.readLine();
                }
            }
        } catch (IOException e) {
            System.out.println("The file isn't found");
        }

        File output = null;

        try (PrintWriter writer = new PrintWriter(output = File.createTempFile("mergeSort", "b"))) {
            for (String e : sortedList) {
                writer.println(e);
            }

        } catch (IOException e) {
            System.out.println("IOException of writing output temporary file");
        }

        return output;
    }

    private void checkType(String string) {
        if (!isStringType) {
            Integer.parseInt(string);
        }
    }

    private boolean checkOrder(String element, String lastElement, File inputFile){
        if(compare(element, lastElement) > 0){
            System.out.println("The input files must be already sorted. The file " + inputFile.getName() + " is not sorted. The program continued, but "
                    + element + " is dropped");

            return false;
        }

        return true;
    }

    private int compare(String string1, String string2) {
        if (isDescending) {
            return string1.compareTo(string2);
        } else {
            return string2.compareTo(string1);
        }
    }
}