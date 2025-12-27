package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {

    private List<String> namesList;

    public ForkJoinUsingRecursion(List<String> namesList) {
        this.namesList = namesList;
    }

    public List<String> getNamesList() {
        return namesList;
    }

    public static void main(String[] args) {

        stopWatch.start();
        List<String> resultList = new ArrayList<>();
        List<String> names = DataSet.namesList();
        log("names : "+ names);

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(names);
        resultList = forkJoinPool.invoke(forkJoinUsingRecursion);
//        names.forEach((name)->{
//            String newValue = addNameLengthTransform(name);
//            resultList.add(newValue);
//        });
        stopWatch.stop();
        log("Final Result : "+ resultList);
        log("Total Time Taken : "+ stopWatch.getTime());
    }


    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }

    @Override
    protected List<String> compute() {
        if(namesList.size()<=1){
            List<String> resultList = new ArrayList<>();
            namesList.forEach((name)->{
                String newValue = addNameLengthTransform(name);
                resultList.add(newValue);
            });
            return resultList;
        }
        int mid = namesList.size()/2;
        ForkJoinTask<List<String>> leftInputList = new ForkJoinUsingRecursion(namesList.subList(0, mid)).fork();
        namesList = namesList.subList(mid, namesList.size());
        List<String> rightInputList = compute();
        List<String> leftResult = leftInputList.join();
        leftResult.addAll(rightInputList);
        return leftResult;
    }
}
