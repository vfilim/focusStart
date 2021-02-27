package ru.focus_start.filimonov.merge_sort;

import java.io.*;
import java.util.LinkedList;

class Sorter {
    boolean isStringType;
    boolean isDescending;

    public Sorter(SortMode sortMode) {
        isStringType = sortMode.hasStringType();
        isDescending = sortMode.isDescending();
    }

    File getSorted(File input0, File input1) {
        LinkedList<String> sortedList = new LinkedList<>();

        try (BufferedReader reader0 = new BufferedReader(new FileReader(input0.getPath()));
             BufferedReader reader1 = new BufferedReader(new FileReader(input1.getPath()))) {

            String[] buffer = new String[2];

            buffer[0] = reader0.readLine();
            buffer[1] = reader1.readLine();

            while (checkEmptyLine(buffer[0], input0)){
                buffer[0] = reader0.readLine();
            }

            while (checkEmptyLine(buffer[0], input1)){
                buffer[1] = reader1.readLine();
            }

            String lastElement0 = buffer[0];
            String lastElement1 = buffer[1];

            while (buffer[0] != null && buffer[1] != null) {
                if (checkLine(buffer[0], lastElement0, input0)) {
                    buffer[0] = reader0.readLine();

                    continue;
                }

                if (checkLine(buffer[1], lastElement1, input1)) {
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
                    if (checkLine(buffer[1], lastElement1, input1)) {
                        buffer[1] = reader0.readLine();

                        continue;
                    }

                    sortedList.add(buffer[1]);

                    lastElement1 = buffer[1];

                    buffer[1] = reader1.readLine();
                }
            } else {
                while (buffer[0] != null) {
                    if (checkLine(buffer[0], lastElement0, input0)) {
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

    private boolean checkType(String string) {
        try {
            if (!isStringType) {
                Integer.parseInt(string);
            }
        } catch (NumberFormatException e) {
            System.out.println("The integer sorting mode is turned on. " + string + " is dropped, because it is not integer");

            return false;
        }
        return true;
    }

    private boolean checkEmptyLine(String element, File inputFile) {
        if (element.equals("")) {
            System.out.println("The file " + inputFile.getName() + " contains empty line, it is skipped");

            return true;
        }

        return false;
    }

    private boolean checkOrder(String element, String lastElement, File inputFile) {
        if (compare(element, lastElement) > 0) {
            System.out.println("The input files must be already sorted. The file " + inputFile.getName() + " is not sorted. The program continued, but "
                    + element + " is dropped");

            return false;
        }

        return true;
    }

    private boolean checkLine(String line, String lastElement , File inputFile){
        return checkEmptyLine(line, inputFile) || !checkType(line) || !checkOrder(line, lastElement, inputFile);
    }

    private int compare(String string1, String string2) {
        if (isDescending) {
            return string1.compareTo(string2);
        } else {
            return string2.compareTo(string1);
        }
    }
}