package com.sunhp.activiti.we_chat_inter.springboot_enc;

/**
 * @author sunhp
 * @Description
 * @Date 2022/10/13 10:54
 **/
public class MerageSortTest {
    /**
     * 两个有序数组归并
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static int[] sort(int[] arr1, int[] arr2) {
        int[] newArr = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                newArr[k] = arr1[i];
                i++;
                k++;
            } else {
                newArr[k] = arr2[j];
                j++;
                k++;
            }
        }
        while (i < arr1.length) {
            newArr[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            newArr[k++] = arr2[j++];
        }
        return newArr;
    }

    /**
     * 归并排序
     *
     * @param arr 原数组
     * @param left 左下标
     * @param right 右下标
     * @return
     */
    public static int[] sort(int[] arr, int left, int right) {
        if (left == right) {
            return new int[] {arr[left]};
        }
        int mid = left + (right - left) / 2;
        //左有序数组
        int[] leftArr = sort(arr, left, mid);
        //右有序数组
        int[] rightArr = sort(arr, mid + 1, right);
        //新有序数组
        int[] newArr = new int[leftArr.length + rightArr.length];
        int i = 0, j = 0, k = 0;
        while (i < leftArr.length && j < rightArr.length) {
            newArr[k++] = leftArr[i] < rightArr[j] ? leftArr[i++] : rightArr[j++];
        }
        while (i < leftArr.length) {
            newArr[k++] = leftArr[i++];
        }
        while (j < rightArr.length) {
            newArr[k++] = rightArr[j++];
        }
        return newArr;
    }

    /**
     * 快速排序
     *
     * @param arr
     * @param startIndex
     * @param endIndex
     */
    public static void quickSort(int[] arr, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }
        //双指针
        // int pivotIndex = doublePointSwap(arr, startIndex, endIndex);
        //单指针
        int pivotIndex = singlePoint(arr, startIndex, endIndex);
        quickSort(arr, startIndex, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, endIndex);

    }

    public static void main(String[] args) {
        //两个有序数组合并排序
        // int[] arr1 = new int[] {2, 3, 6, 8};
        // int[] arr2 = new int[] {1, 4, 5, 7, 9, 15, 200};
        // int[] newArr = sort(arr1, arr2);
        // for (int i = 0; i < newArr.length; i++) {
        //     System.out.println("===>" + newArr[i]);
        // }
        int[] arr3 = new int[] {6, 202, 100, 301, 38, 8, 7, 1};
        //归并排序
        // int[] sort = sort(arr3, 0, arr3.length - 1);
        // for (int i = 0; i < sort.length; i++) {
        //     System.out.println("===>" + sort[i]);
        // }
        quickSort(arr3, 0, arr3.length - 1);
        for (int i = 0; i < arr3.length; i++) {
            System.out.println("===>" + arr3[i]);
        }
    }

    /**
     * 双边指针（交换法）
     * 思路：
     * 记录分界值 pivot，创建左右指针（记录下标）。
     * （分界值选择方式有：首元素，随机选取，三数取中法）
     *
     * 首先从右向左找出比pivot小的数据，
     * 然后从左向右找出比pivot大的数据，
     * 左右指针数据交换，进入下次循环。
     *
     * 结束循环后将当前指针数据与分界值互换，
     * 返回当前指针下标（即分界值下标）
     */
    private static int doublePointSwap(int[] arr, int startIndex, int endIndex) {
        int pivot = arr[startIndex];
        int leftPoint = startIndex;
        int rightPoint = endIndex;
        while (leftPoint < rightPoint) {
            //从右向左找出比pivot小的数据
            while (leftPoint < rightPoint && arr[rightPoint] > pivot) {
                rightPoint--;
            }
            //从左向右找出比pivot大的数据
            while (leftPoint < rightPoint && arr[leftPoint] <= pivot) {
                leftPoint++;
            }
            //没有过界则交换
            if (leftPoint < rightPoint) {
                int temp = arr[leftPoint];
                arr[leftPoint] = arr[rightPoint];
                arr[rightPoint] = temp;
            }
        }
        //最终分界值与当前指针互换
        arr[startIndex] = arr[rightPoint];
        arr[rightPoint] = pivot;
        return rightPoint;
    }

    /**
     * 单边指针
     * 思路：
     * 记录首元素为分界值 pivot, 创建标记 mark 指针。
     * 循环遍历与分界值对比。
     * 比分界值小，则 mark++ 后与之互换。
     * 结束循环后，将首元素分界值与当前mark互换。
     * 返回 mark 下标为分界值下标。
     */
    private static int singlePoint(int[] arr, int startIndex, int endIndex) {
        int pivot = arr[startIndex];
        int markPoint = startIndex;
        for (int i = startIndex + 1; i <= endIndex; i++) {
            //如果比分界值小，则markPoint++后互换
            if (pivot > arr[i]) {
                markPoint++;
                int temp = arr[markPoint];
                arr[markPoint] = arr[i];
                arr[i] = temp;
            }
        }
        //将首元素分界值与当前mark互换
        arr[startIndex] = arr[markPoint];
        arr[markPoint] = pivot;
        return markPoint;
    }
}
