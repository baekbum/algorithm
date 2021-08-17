package com.example.algorithm.javaPractice.heap;

/*
-문제 설명-
하드디스크는 한 번에 하나의 작업만 수행할 수 있습니다. 디스크 컨트롤러를 구현하는 방법은 여러 가지가 있습니다. 가장 일반적인 방법은 요청이 들어온 순서대로 처리하는 것입니다.

예를들어
- 0ms 시점에 3ms가 소요되는 A작업 요청
- 1ms 시점에 9ms가 소요되는 B작업 요청
- 2ms 시점에 6ms가 소요되는 C작업 요청

- A: 3ms 시점에 작업 완료 (요청에서 종료까지 : 3ms)
- B: 1ms부터 대기하다가, 3ms 시점에 작업을 시작해서 12ms 시점에 작업 완료(요청에서 종료까지 : 11ms)
- C: 2ms부터 대기하다가, 12ms 시점에 작업을 시작해서 18ms 시점에 작업 완료(요청에서 종료까지 : 16ms)

이 때 각 작업의 요청부터 종료까지 걸린 시간의 평균은 10ms(= (3 + 11 + 16) / 3)가 됩니다.
하지만 A → C → B 순서대로 처리하면
- A: 3ms 시점에 작업 완료(요청에서 종료까지 : 3ms)
- C: 2ms부터 대기하다가, 3ms 시점에 작업을 시작해서 9ms 시점에 작업 완료(요청에서 종료까지 : 7ms)
- B: 1ms부터 대기하다가, 9ms 시점에 작업을 시작해서 18ms 시점에 작업 완료(요청에서 종료까지 : 17ms)

이렇게 A → C → B의 순서로 처리하면 각 작업의 요청부터 종료까지 걸린 시간의 평균은 9ms(= (3 + 7 + 17) / 3)가 됩니다.

각 작업에 대해 [작업이 요청되는 시점, 작업의 소요시간]을 담은 2차원 배열 jobs가 매개변수로 주어질 때,
작업의 요청부터 종료까지 걸린 시간의 평균을 가장 줄이는 방법으로 처리하면 평균이 얼마가 되는지 return 하도록 solution 함수를 작성해주세요. (단, 소수점 이하의 수는 버립니다)

-제한사항-
jobs의 길이는 1 이상 500 이하입니다.
jobs의 각 행은 하나의 작업에 대한 [작업이 요청되는 시점, 작업의 소요시간] 입니다.
각 작업에 대해 작업이 요청되는 시간은 0 이상 1,000 이하입니다.
각 작업에 대해 작업의 소요시간은 1 이상 1,000 이하입니다.
하드디스크가 작업을 수행하고 있지 않을 때에는 먼저 요청이 들어온 작업부터 처리합니다.

-입출력 예-
jobs	                    return
[[0, 3], [1, 9], [2, 6]]	9
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class heap2 {
    @Test
    public void heapMain() {
        int[][] jobs = new int[][]{{0, 3}, {1, 9}, {2, 6}};
        //int[][] jobs = new int[][]{{24, 10}, {28, 39}, {43, 20}, {37, 5}, {47, 22}, {20, 47}, {15, 34}, {15, 2}, {35, 43}, {26, 1}};

        int answer = solution(jobs);

        System.out.println(answer);
    }

    private int solution(int[][] jobs) {
        ArrayList<Integer> time = new ArrayList<>();
        PriorityQueue<Work> queueByRequest = sortByRequest();
        PriorityQueue<Work> queueByRequired = sortByRequired();
        Boolean isRun = true;
        int currentTime = 0;
        float answer = 0;

        for (int i = 0; i < jobs.length; i++) {
            queueByRequest.offer(new Work(jobs[i][0], jobs[i][1]));
        };

        while (isRun) {
            if (!queueByRequest.isEmpty()) {
                makeRequiredQueue(queueByRequest, queueByRequired, currentTime);
            }

            if (!queueByRequired.isEmpty()) {
                Work target = queueByRequired.poll();

                if (currentTime < target.getRequestTime()) {
                    currentTime = target.getRequestTime();
                }

                currentTime += target.getRequiredTime();
                time.add(currentTime - target.getRequestTime());
            }

            if (queueByRequest.isEmpty() && queueByRequired.isEmpty()) {
                isRun = false;
            }

        };

        for (int t : time) {
            answer += t;
        }

        return Math.round(answer / jobs.length);
    }

    private PriorityQueue<Work> sortByRequest() {
        PriorityQueue<Work> queue = new PriorityQueue<>(new Comparator<Work>() {
            @Override
            public int compare(Work o1, Work o2) {
                if (o1.getRequestTime() < o2.getRequestTime()) {
                    return -1;
                } else if (o1.getRequestTime() > o2.getRequestTime()) {
                    return 1;
                } else {
                    if (o1.getRequiredTime() < o2.getRequiredTime()) {
                        return -1;
                    } else if (o1.getRequiredTime() > o2.getRequiredTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        });

        return queue;
    }

    private PriorityQueue<Work> sortByRequired() {
        PriorityQueue<Work> queue = new PriorityQueue<>(new Comparator<Work>() {
            @Override
            public int compare(Work o1, Work o2) {
                if (o1.getRequiredTime() < o2.getRequiredTime()) {
                    return -1;
                } else if (o1.getRequiredTime() > o2.getRequiredTime()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        return queue;
    }

    private void makeRequiredQueue(PriorityQueue<Work> queueByRequest, PriorityQueue<Work> queueByRequired, int currentTime) {
        Boolean isRun = true;

        while (isRun) {
            if (!queueByRequest.isEmpty()) {
                Work target = queueByRequest.peek();

                if (currentTime >= target.getRequestTime()) {
                    queueByRequired.offer(queueByRequest.poll());
                } else {
                    if (queueByRequired.isEmpty()) {
                        queueByRequired.offer(queueByRequest.poll());
                    }
                    isRun = false;
                }
            } else {
                isRun = false;
            }

        }
    }
}

class Work {
    private int requestTime;
    private int requiredTime;

    public Work(int requestTime, int requiredTime) {
        this.requestTime = requestTime;
        this.requiredTime = requiredTime;
    }

    public int getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(int requestTime) {
        this.requestTime = requestTime;
    }

    public int getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(int requiredTime) {
        this.requiredTime = requiredTime;
    }

    @Override
    public String toString() {
        return "Work{" +
                "requestTime=" + requestTime +
                ", requiredTime=" + requiredTime +
                '}';
    }
}
