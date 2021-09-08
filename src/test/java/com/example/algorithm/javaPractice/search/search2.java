package com.example.algorithm.javaPractice.search;

/*
-문제 설명-
한자리 숫자가 적힌 종이 조각이 흩어져있습니다. 흩어진 종이 조각을 붙여 소수를 몇 개 만들 수 있는지 알아내려 합니다.

각 종이 조각에 적힌 숫자가 적힌 문자열 numbers가 주어졌을 때, 종이 조각으로 만들 수 있는 소수가 몇 개인지 return 하도록 solution 함수를 완성해주세요.

-제한사항-
numbers는 길이 1 이상 7 이하인 문자열입니다.
numbers는 0~9까지 숫자만으로 이루어져 있습니다.
"013"은 0, 1, 3 숫자가 적힌 종이 조각이 흩어져있다는 의미입니다.

-입출력 예-
numbers	return
"17"	3
"011"	2
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;

@SpringBootTest
public class search2 {

    @Test
    public void searchMain() {
        String numbers = "011";
        //String numbers = "17";

        int answer = solution(numbers);

        System.out.println(answer);
    }

    private int solution(String numbers) {
        ArrayList<Number> list = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        int answer = 0;

        for (int i = 0; i < numbers.length(); i++) {
            list.add(new Number(numbers.charAt(i)));
        }

        createNumber(list, set, new StringBuilder());

        for (int number : set) {
            if (number > 1) {
                Boolean isPrime = true;

                for (int i = 2; i < number; i++) {
                    if (number % i == 0) {
                        isPrime = false;
                    }
                }
                if (isPrime) {
                    answer++;
                }
            }
        }

        return answer;
    }

    private void createNumber(ArrayList<Number> list, HashSet<Integer> set, StringBuilder numbers) {
        if (0 == list.stream().filter(number -> number.isUsed() == false).count()) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            ArrayList<Number> copyList = new ArrayList<>();

            // 리스트 복사
            for (Number number : list) {
                try {
                    copyList.add((Number) number.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }

            if (copyList.get(i).isUsed() == false) {
                StringBuilder newBuilder = new StringBuilder();
                newBuilder.append(numbers + copyList.get(i).getNumberToString());
                copyList.get(i).setUsed(true);

                set.add(Integer.parseInt(newBuilder.toString()));
                createNumber(copyList, set, newBuilder);
            }
        }
    }
}

class Number implements Cloneable{
    private char number;
    private boolean isUsed;

    public Number(char number) {
        this.number = number;
        this.isUsed = false;
    }

    public char getNumber() {
        return number;
    }

    public void setNumber(char number) {
        this.number = number;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public String getNumberToString() {
        return String.valueOf(this.getNumber());
    }

    @Override
    public String toString() {
        return "Number{" +
                "number=" + number +
                ", isUsed=" + isUsed +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
