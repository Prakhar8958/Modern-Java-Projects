package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.*;

public class ParallelStreamsExample {

    public List<String> transFormNames(List<String> names){
        return names
                //.stream()
                .parallelStream()
                .map(this :: addNameLengthTransform)
                .collect(Collectors.toList());
    }
    public List<String> transFormNamesWithFlag(List<String> names, boolean isParallel){
        Stream<String> stringStream = names.stream();
        if(isParallel){
            stringStream.parallel();
        }
        return stringStream
                .map(this :: addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public static void main(String[] args){
        List<String> namesList = DataSet.namesList();
        startTimer();
        List<String> transFormNames = new ParallelStreamsExample().transFormNames(namesList);
        System.out.println(transFormNames);
        timeTaken();

    }

    private String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }
}
