package com.example.algorithm.javaPractice.heap;

/*
-문제 설명-
이중 우선순위 큐는 다음 연산을 할 수 있는 자료구조를 말합니다.

명령어	수신 탑(높이)
I 숫자	큐에 주어진 숫자를 삽입합니다.
D 1 	큐에서 최댓값을 삭제합니다.
D -1	큐에서 최솟값을 삭제합니다.

이중 우선순위 큐가 할 연산 operations가 매개변수로 주어질 때, 모든 연산을 처리한 후 큐가 비어있으면 [0,0] 비어있지 않으면 [최댓값, 최솟값]을 return 하도록 solution 함수를 구현해주세요.

-제한사항-
operations는 길이가 1 이상 1,000,000 이하인 문자열 배열입니다.
operations의 원소는 큐가 수행할 연산을 나타냅니다.
원소는 “명령어 데이터” 형식으로 주어집니다.- 최댓값/최솟값을 삭제하는 연산에서 최댓값/최솟값이 둘 이상인 경우, 하나만 삭제합니다.
빈 큐에 데이터를 삭제하라는 연산이 주어질 경우, 해당 연산은 무시합니다.

-입출력 예-
operations	                return
["I 16","D 1"]	            [0,0]
["I 7","I 5","I -5","D -1"]	[7,5]
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.PriorityQueue;

@SpringBootTest
public class heap3 {
    @Test
    public void heapMain() {
        //String[] operations = new String[]{"I 16","D 1"};
        String[] operations = new String[]{"I 7","I 5","I -5","D -1"};

        int[] answer = solution(operations);

        System.out.println(answer[0] + " , " + answer[1]);
    }

    private int[] solution(String[] operations) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int[] answer;

        for (String s : operations) {
            if (s.equals("D 1")) { // 최댓값
                if (!queue.isEmpty()) {
                    int maxValue = queue.stream().mapToInt(x -> x).max().getAsInt();
                    queue.remove(maxValue);
                }
            } else if (s.equals("D -1")) { // 최솟값
                if (!queue.isEmpty()) {
                    queue.poll();
                }
            } else { // 큐에 추가
                queue.offer(Integer.parseInt(s.substring(2,s.length())));
            }
        }

        if (queue.isEmpty()) {
            answer = new int[]{0, 0};
        } else {
            int max = queue.stream().mapToInt(x -> x).max().getAsInt();
            int min = queue.stream().mapToInt(x -> x).min().getAsInt();

            answer = new int[]{max, min};
        }

        return answer;

    }
}
