package com.example.algorithm.javaPractice.heap;

/*
-문제 설명-
매운 것을 좋아하는 Leo는 모든 음식의 스코빌 지수를 K 이상으로 만들고 싶습니다.
모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 Leo는 스코빌 지수가 가장 낮은 두 개의 음식을 아래와 같이 특별한 방법으로 섞어 새로운 음식을 만듭니다.

섞은 음식의 스코빌 지수 = 가장 맵지 않은 음식의 스코빌 지수 + (두 번째로 맵지 않은 음식의 스코빌 지수 * 2)

Leo는 모든 음식의 스코빌 지수가 K 이상이 될 때까지 반복하여 섞습니다.
Leo가 가진 음식의 스코빌 지수를 담은 배열 scoville과 원하는 스코빌 지수 K가 주어질 때,
모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 섞어야 하는 최소 횟수를 return 하도록 solution 함수를 작성해주세요.

-제한사항-
scoville의 길이는 2 이상 1,000,000 이하입니다.
K는 0 이상 1,000,000,000 이하입니다.
scoville의 원소는 각각 0 이상 1,000,000 이하입니다.
모든 음식의 스코빌 지수를 K 이상으로 만들 수 없는 경우에는 -1을 return 합니다.

-입출력 예-
scoville	            K	return
[1, 2, 3, 9, 10, 12]	7	2
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.PriorityQueue;

@SpringBootTest
public class heap1 {
    @Test
    public void heapMain() {
        int[] scoville = new int[]{10, 10, 10, 10, 10};
        int K = 100;

        int answer = solution(scoville, K);

        System.out.println(answer);
    }

    private int solution(int[] scoville, int K) {
        PriorityQueue<Integer> lessList = new PriorityQueue<>();
        PriorityQueue<Integer> moreList = new PriorityQueue<>();
        Boolean isRun = true;
        int answer = 0;

        for (int i : scoville) {
            if (i < K) {
                lessList.add(i);
            } else {
                moreList.add(i);
            }
        }

        if (!lessList.isEmpty()) {
            while (isRun) {
                int thisVal = lessList.poll();

                if (thisVal < K) {
                    if (!lessList.isEmpty()) {
                        int nextVal = lessList.poll();
                        int mixVal = thisVal + (nextVal * 2);

                        lessList.offer(mixVal);
                        answer++;
                    } else {
                        if (!moreList.isEmpty()) {
                            int nextVal = moreList.peek();
                            int mixVal = thisVal + (nextVal * 2);

                            lessList.offer(mixVal);
                            answer++;
                            isRun = false;
                        } else {
                            return -1;
                        }
                    }
                } else {
                    isRun = false;
                }
            }
        }

        return answer;
    }
}
