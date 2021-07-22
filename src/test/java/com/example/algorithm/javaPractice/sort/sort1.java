package com.example.algorithm.javaPractice.sort;

/*
-문제 설명-
0 또는 양의 정수가 주어졌을 때, 정수를 이어 붙여 만들 수 있는 가장 큰 수를 알아내 주세요.
예를 들어, 주어진 정수가 [6, 10, 2]라면 [6102, 6210, 1062, 1026, 2610, 2106]를 만들 수 있고, 이중 가장 큰 수는 6210입니다.
0 또는 양의 정수가 담긴 배열 numbers가 매개변수로 주어질 때, 순서를 재배치하여 만들 수 있는 가장 큰 수를 문자열로 바꾸어 return 하도록 solution 함수를 작성해주세요.

-제한사항-
numbers의 길이는 1 이상 100,000 이하입니다.
numbers의 원소는 0 이상 1,000 이하입니다.
정답이 너무 클 수 있으니 문자열로 바꾸어 return 합니다.

-입출력 예-
numbers	            return
[6, 10, 2]	        "6210"
[3, 30, 34, 5, 9]	"9534330"
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
public class sort1 {

    @Test
    public void sortMain() {
        int[] numbers = new int[]{3, 30, 34, 5, 9};

        String result = solution(numbers);

        System.out.println("가장 큰 수의 문자열은 : " + result);
    }

    private String solution(int[] numbers) {
        Numbers number = new Numbers();
        String str = new String();

        // numbers의 값을 문자열로 바꾼 후 Numbers 객체의 알맞은 프로퍼티에 값을 추가한다.
        for (int i = 0; i < numbers.length; i++) {
            str = String.valueOf(numbers[i]);
            number.add(str);
        }

        // 해당 객체의 각각의 프로퍼티 내부 값을 내림차순 정렬한다.
        number.eachSort();
        String anwser = String.valueOf(number.getResult());

        return anwser;
    }
}

class Numbers {
    private ArrayList<ArrayList<String>> total = new ArrayList<>();
    private StringBuilder result = new StringBuilder();
    private ArrayList<String> zero = new ArrayList<>();
    private ArrayList<String> one = new ArrayList<>();
    private ArrayList<String> two = new ArrayList<>();
    private ArrayList<String> three = new ArrayList<>();
    private ArrayList<String> four = new ArrayList<>();
    private ArrayList<String> five = new ArrayList<>();
    private ArrayList<String> six = new ArrayList<>();
    private ArrayList<String> seven = new ArrayList<>();
    private ArrayList<String> eight = new ArrayList<>();
    private ArrayList<String> nine = new ArrayList<>();

    public Numbers() {
        this.total.add(this.nine);
        this.total.add(this.eight);
        this.total.add(this.seven);
        this.total.add(this.six);
        this.total.add(this.five);
        this.total.add(this.four);
        this.total.add(this.three);
        this.total.add(this.two);
        this.total.add(this.one);
        this.total.add(this.zero);
    }

    public void add(String str) {
        switch (str.charAt(0)) {
            case '0':
                zero.add(str);
                break;
            case '1':
                one.add(str);
                break;
            case '2':
                two.add(str);
                break;
            case '3':
                three.add(str);
                break;
            case '4':
                four.add(str);
                break;
            case '5':
                five.add(str);
                break;
            case '6':
                six.add(str);
                break;
            case '7':
                seven.add(str);
                break;
            case '8':
                eight.add(str);
                break;
            case '9':
                nine.add(str);
                break;
            default:
                System.out.println("error");
                break;
        }
    }

    public void eachSort () {
        for (ArrayList<String> p : this.total) {
            if (p.size() > 0) {
                p.sort((o1, o2) -> (o2 + o1).compareTo(o1 + o2));

                for (String s : p) {
                    result.append(s);
                }
            }
        }
    }

    public StringBuilder getResult() {
        return result;
    }
}
