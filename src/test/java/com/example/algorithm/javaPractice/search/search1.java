package com.example.algorithm.javaPractice.search;

/*
-문제 설명-
수포자는 수학을 포기한 사람의 준말입니다. 수포자 삼인방은 모의고사에 수학 문제를 전부 찍으려 합니다. 수포자는 1번 문제부터 마지막 문제까지 다음과 같이 찍습니다.

1번 수포자가 찍는 방식: 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, ...
2번 수포자가 찍는 방식: 2, 1, 2, 3, 2, 4, 2, 5, 2, 1, 2, 3, 2, 4, 2, 5, ...
3번 수포자가 찍는 방식: 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, 3, 3, 1, 1, 2, 2, 4, 4, 5, 5, ...

1번 문제부터 마지막 문제까지의 정답이 순서대로 들은 배열 answers가 주어졌을 때, 가장 많은 문제를 맞힌 사람이 누구인지 배열에 담아 return 하도록 solution 함수를 작성해주세요.

-제한사항-
시험은 최대 10,000 문제로 구성되어있습니다.
문제의 정답은 1, 2, 3, 4, 5중 하나입니다.
가장 높은 점수를 받은 사람이 여럿일 경우, return하는 값을 오름차순 정렬해주세요.

-입출력 예-
answers 	return
[1,2,3,4,5]	[1]
[1,3,2,4,2]	[1,2,3]
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


@SpringBootTest
public class search1 {

    @Test
    public void searchMain() {
        //int[] answers = new int[]{1, 2, 3, 4, 5, 1, 3, 2, 4, 2};
        int[] answers = new int[]{1, 2, 3, 4, 5};
        //int[] answers = new int[]{1, 3, 2, 4, 2};
        //int[] answers = new int[]{0, 0, 0, 0, 0};

        int[] result = solution(answers);

        for (int i : result) {
            System.out.println(i);
        }
    }

    private int[] solution(int[] answers) {
        People people1 = new People(1, new int[]{1, 2, 3, 4, 5});
        People people2 = new People(2, new int[]{2, 1, 2, 3, 2, 4, 2, 5, 2, 1, 2, 3, 2, 4, 2, 5});
        People people3 = new People(3, new int[]{3, 3, 1, 1, 2, 2, 4, 4, 5, 5});
        People[] people = new People[]{people1, people2, people3};
        int count = 1;
        int[] result;

        for (int i = 0; i < answers.length; i++) {
            for (People p : people) {
                if (answers[i] == p.getPattern()[i % p.getPattern().length]) {
                    p.setCorrect(p.getCorrect() + 1);
                }
            }
        }

        Arrays.sort(people, new Comparator<People>() {
            @Override
            public int compare(People o1, People o2) {
                return o2.getCorrect() - o1.getCorrect();
            }
        });

        if (people[0].getCorrect() == 0) {
            result = new int[people.length];

            for (int i = 0; i < people.length; i++) {
                result[i] = people[i].getIdx();
            }


        } else {
            for (int i = 1; i < people.length; i++) {
                if (people[i-1].getCorrect() == people[i].getCorrect()) {
                    count++;
                } else {
                    break;
                }
            }

            result = new int[count];

            for (int i = 0; i < count; i++) {
                result[i] = people[i].getIdx();
            }
        }

        return result;
    }
}

class People {
    private int idx;
    private int[] pattern;
    private int correct = 0;

    public People(int idx, int[] pattern) {
        this.idx = idx;
        this.pattern = pattern;
    }

    public int getIdx() {
        return idx;
    }

    public int[] getPattern() {
        return pattern;
    }

    public void setPattern(int[] pattern) {
        this.pattern = pattern;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }
}