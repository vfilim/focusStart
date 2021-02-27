package ru.focus_start.filimonov.merge_sort;

import java.io.*;
import java.util.ArrayList;

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

        ArrayList<File> inputFiles = new ArrayList<>(args.length - i);

        while (i < args.length) {
            File inputFile = new File(args[i]);

            if (inputFile.exists()) {
                inputFiles.add(inputFile);
            } else {
                System.out.println("There is no file on path " + inputFile.getPath());
            }

            i++;
        }

        Sorter sorter = new Sorter(sortMode);

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
            System.out.println("IOException of writing final output file");
        }
    }
}