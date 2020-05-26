package utils;

import java.util.Comparator;

public class RecordsByNameComparator implements Comparator<Record> {

    @Override
    public int compare(Record o1, Record o2) {
        return o1.getName().compareTo(o2.getName());
    }

}
