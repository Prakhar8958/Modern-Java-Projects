package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamsExampleTest {

    ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();
    @Test
    void transFormNames() {
        //given
        List<String> inputList = DataSet.namesList();
        //when
        startTimer();
        List<String> resultList = parallelStreamsExample.transFormNames(inputList);
        timeTaken();
        //then
        assertEquals(inputList.size(), resultList.size());
        resultList.forEach(str -> {assertTrue(str.contains("-"));
        });
    }
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void transFormNamesWithFlag(boolean isParallel) {
        //given
        List<String> inputList = DataSet.namesList();
        //when
        startTimer();
        List<String> resultList = parallelStreamsExample.transFormNamesWithFlag(inputList, isParallel);
        timeTaken();
        //then
        assertEquals(inputList.size(), resultList.size());
        resultList.forEach(str -> {assertTrue(str.contains("-"));
        });
    }
}