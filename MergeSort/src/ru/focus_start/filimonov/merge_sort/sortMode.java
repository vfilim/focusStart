package ru.focus_start.filimonov.merge_sort;

class SortMode {
    boolean descending;
    boolean isStringType;

    boolean isOrderSet;
    boolean isTypeSet;

    public SortMode() {
    }

    void setDescending() {
        if (isOrderSet) {
            throw new IllegalStateException("The sorting order is already set");
        }

        descending = true;

        isOrderSet = true;
    }

    void setAscending() {
        if (isOrderSet) {
            throw new IllegalStateException("The sorting order is already set");
        }

        descending = false;

        isOrderSet = true;
    }

    boolean isDescending() {
        return descending;
    }

    void setStringType() {
        if (isTypeSet) {
            throw new IllegalStateException("The data type is already set");
        }

        isStringType = true;

        isTypeSet = true;
    }

    void setIntegerType() {
        if (isTypeSet) {
            throw new IllegalStateException("The data type is already set");
        }

        isStringType = false;

        isTypeSet = true;
    }

    boolean hasStringType() {
        return isStringType;
    }

    void switchMode(String argument) {
        if (argument.equals("-s")) {
            setStringType();
        } else if (argument.equals("-i")) {
            setIntegerType();
        } else if (argument.equals("-d")) {
            setDescending();
        } else if (argument.equals("-a")) {
            setAscending();
        } else {
            throw new IllegalArgumentException("The sort mode argument must be -s, -i, -d or -a, it is " + argument + "now");
        }
    }
}
