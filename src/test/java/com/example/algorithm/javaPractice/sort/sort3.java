package com.example.algorithm.javaPractice.sort;

/*
-문제 설명-
H-Index는 과학자의 생산성과 영향력을 나타내는 지표입니다. 어느 과학자의 H-Index를 나타내는 값인 h를 구하려고 합니다. 위키백과1에 따르면, H-Index는 다음과 같이 구합니다.

어떤 과학자가 발표한 논문 n편 중, h번 이상 인용된 논문이 h편 이상이고 나머지 논문이 h번 이하 인용되었다면 h의 최댓값이 이 과학자의 H-Index입니다.

어떤 과학자가 발표한 논문의 인용 횟수를 담은 배열 citations가 매개변수로 주어질 때, 이 과학자의 H-Index를 return 하도록 solution 함수를 작성해주세요.

-제한사항-
과학자가 발표한 논문의 수는 1편 이상 1,000편 이하입니다.
논문별 인용 횟수는 0회 이상 10,000회 이하입니다.

-입출력 예-
citations	    return
[3, 0, 6, 1, 5]	3
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Comparator;


@SpringBootTest
public class sort3 {

    @Test
    public void sortMain() {
        int[] citations = new int[]{3, 0, 6, 1, 5};

        int answer = solution(citations);

        System.out.println(answer);
    }

    private int solution(int[] citations) {
        Integer[] values = Arrays.stream(citations).boxed().toArray(Integer[]::new);
        int count = values.length;
        int hIndex = count;

        Arrays.sort(values, new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return i2 - i1;
            }
        });

        for (int i = 1; i < (count + 1); i++) {
            if (values[i-1] < i) {
                hIndex = i - 1;
                break;
            }
        }

        return hIndex;
    }
}