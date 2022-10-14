package com.sunhp.activiti.we_chat_inter.springboot_enc.sort_test;

/**
 * @author sunhp
 * @Description 手写
 * @Date 2022/10/14 13:40
 **/
public class SortTest {

    /**
     * 单指针快速排序
     * @param arr
     * @param startIndex
     * @param endIndex
     * @return
     */
    private static int singlePoint(int[] arr, int startIndex,int endIndex){
        int pivot = arr[startIndex];
        int markPoint = startIndex;

        for (int i = startIndex + 1; i <= endIndex; i++) {
            if(pivot > arr[i]){
                markPoint++;
                int temp = arr[markPoint];
                arr[markPoint] = arr[i];
                arr[i] = temp;
            }
        }
        arr[startIndex] = arr[markPoint];
        arr[markPoint] = pivot;
        return markPoint;
    }

    private static int doublePoint(int[] arr, int startIndex, int endIndex){
        int pivot = arr[startIndex];
        int leftPoint = startIndex;
        int rightPoint = endIndex;

        while (leftPoint < rightPoint){
            //从右向左寻找小于pivot的值
            while (leftPoint < rightPoint && pivot < arr[rightPoint]){
                rightPoint--;
            }
            //从左向右寻找大于pivot的值
            while (leftPoint < rightPoint && pivot >= arr[leftPoint]){
                leftPoint++;
            }
            //不过界就交换
            if(leftPoint < rightPoint){
                int temp = arr[rightPoint];
                arr[rightPoint] = arr[leftPoint];
                arr[leftPoint] = temp;
            }
        }
        arr[startIndex] = arr[rightPoint];
        arr[rightPoint] = pivot;
        return rightPoint;
    }

    public static void quickSort(int[] arr, int startIndex,int endIndex){
        if (startIndex >= endIndex) {
            return;
        }
        // int singlePoint = singlePoint(arr, startIndex, endIndex);
        // quickSort(arr, startIndex, singlePoint - 1);
        // quickSort(arr, singlePoint + 1, endIndex);

        int doublePoint = doublePoint(arr, startIndex, endIndex);
        quickSort(arr, startIndex, doublePoint - 1);
        quickSort(arr, doublePoint + 1, endIndex);
    }


    public static void main(String[] args) {
        int[] arr3 = new int[] {6, 202, 100, 301, 38, 8, 7, 1};
        quickSort(arr3, 0, arr3.length - 1);
        for (int i = 0; i < arr3.length; i++) {
            System.out.println("===>" + arr3[i]);
        }
    }

}
