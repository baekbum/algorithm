package com.example.algorithm.javaPractice.stackAndQueue;

/*
-문제 설명-
초 단위로 기록된 주식가격이 담긴 배열 prices가 매개변수로 주어질 때, 가격이 떨어지지 않은 기간은 몇 초인지를 return 하도록 solution 함수를 완성하세요.

-제한사항-
prices의 각 가격은 1 이상 10,000 이하인 자연수입니다.
prices의 길이는 2 이상 100,000 이하입니다.

-입출력 예-
prices	        return
[1, 2, 3, 2, 3]	[4, 3, 1, 1, 0]
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.Queue;

@SpringBootTest
public class stackAndQueue4 {

    @Test
    public void queueMain() {
        int[] prices = new int[]{1, 2, 3, 2, 3};

        int[] answer = solution(prices);
    }

    private int[] solution(int[] prices) {
        Queue<Stock> queue = new LinkedList<>();
        int[] answer = new int[prices.length];

        for (int i = 0; i < prices.length; i++) {
            Stock stock = new Stock(i, prices[i]);
            queue.offer(stock);
        }

        while (!queue.isEmpty()) {
            int count = 0;
            Stock thisStock = queue.poll();

            if (!queue.isEmpty()) {

                for (Stock nextStock : queue) {
                    count++;

                    if (nextStock.getPrice() < thisStock.getPrice()) {
                        answer[thisStock.getIdx()] = count;
                        thisStock.setDrop(true);
                        break;
                    }
                }

                if (!thisStock.getDrop()) {
                    answer[thisStock.getIdx()] = count;
                }
            }
        }

        return answer;
    }
}

class Stock {
    private int idx;
    private int price;
    private Boolean isDrop;

    public Stock(int idx, int price) {
        this.idx = idx;
        this.price = price;
        this.isDrop = false;
    }

    public int getIdx() {
        return idx;
    }

    public int getPrice() {
        return price;
    }

    public Boolean getDrop() {
        return isDrop;
    }

    public void setDrop(Boolean drop) {
        isDrop = drop;
    }
}
