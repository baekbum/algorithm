package com.example.algorithm.javaPractice.quickSort;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QuickSort {

    @Test
    public void quickSortMain() {
        int[] arr = init();

        quickSort(arr, 0, arr.length - 1);

        //정렬 후 배열
        for (int i = 0; i < arr.length; i++) {
            System.out.println(i + ": " + arr[i]);
        }
    }

    // 배열 초기 값 초기화 함수.
    private int[] init() {
        return new int[]{40, 70, 90, 10 , 60};
    }

    // 재귀 함수
    private void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int pivot = partition(arr, left, right);
            quickSort(arr, left, pivot - 1);
            quickSort(arr, pivot + 1, right);
        }
    }

    // 나누기
    private int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int position = left - 1;

        for (int i = left; i < right; i++) {
            if (arr[i] <= pivot) {
                position++;
                if (i != position) {
                    swap(arr, position, i);
                }
            }
        }

//        System.out.println("expect : 40, 10, 90, 70, 60");
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(i + ": " + arr[i]);
//        }
        swap(arr, position + 1, right);
//        System.out.println("expect : 40, 10, 60, 70, 90");
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println(i + ": " + arr[i]);
//        }
        return position + 1;
    }

    // 배열 스왑
    private void swap(int[] arr, int left, int right) {
        int temp = arr[left];

        arr[left] = arr[right];
        arr[right] = temp;
    }
}
