package com.cy.sort;

import com.cy.util.PrintUtil;
import org.junit.Test;
/**
 * Created by chenying on 2017/10/30.
 */

public class BubbleSortTest {

    private BubbleSort bubbleSort = new BubbleSort();


    @Test
    public void test() {
        int[] arr = {1, 8, 9, 2, 4, 6, 4, 3};
        bubbleSort.sort(arr);
        PrintUtil.print(arr);
    }
    @Test
    public void test2() {
        int[] arr = {1, 8, 9, 2, 4, 6, 4, 3};
        bubbleSort.sort2(arr);
        PrintUtil.print(arr);
    }
}