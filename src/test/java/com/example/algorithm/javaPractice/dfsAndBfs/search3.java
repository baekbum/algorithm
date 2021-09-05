package com.example.algorithm.javaPractice.dfsAndBfs;

/*
-문제 설명-
두 개의 단어 begin, target과 단어의 집합 words가 있습니다. 아래와 같은 규칙을 이용하여 begin에서 target으로 변환하는 가장 짧은 변환 과정을 찾으려고 합니다.

1. 한 번에 한 개의 알파벳만 바꿀 수 있습니다.
2. words에 있는 단어로만 변환할 수 있습니다.

예를 들어 begin이 "hit", target가 "cog", words가 ["hot","dot","dog","lot","log","cog"]라면 "hit" -> "hot" -> "dot" -> "dog" -> "cog"와 같이 4단계를 거쳐 변환할 수 있습니다.

두 개의 단어 begin, target과 단어의 집합 words가 매개변수로 주어질 때, 최소 몇 단계의 과정을 거쳐 begin을 target으로 변환할 수 있는지 return 하도록 solution 함수를 작성해주세요.

-제한사항-
각 단어는 알파벳 소문자로만 이루어져 있습니다.
각 단어의 길이는 3 이상 10 이하이며 모든 단어의 길이는 같습니다.
words에는 3개 이상 50개 이하의 단어가 있으며 중복되는 단어는 없습니다.
begin과 target은 같지 않습니다.
변환할 수 없는 경우에는 0를 return 합니다.

-입출력 예-
begin	target	words	                                    return
"hit"	"cog"	["hot", "dot", "dog", "lot", "log", "cog"]	4
"hit"	"cog"	["hot", "dot", "dog", "lot", "log"]	        0
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class search3 {

    @Test
    public void searchMain() {
        String begin = "hit";
        String target = "cog";
        String[] words = {"hot", "dot", "dog", "lot", "log", "cog"};

        int answer = solution(begin, target, words);

        System.out.println(answer);
    }

    private int solution(String begin, String target, String[] words) {
        ArrayList<String> wordList = new ArrayList<>();
        ArrayList<Integer> countList = new ArrayList<>();
        int answer = 0;

        for (String word : words) {
            wordList.add(word);
        }

        dfs(begin, target, wordList, countList, 0);

        if (countList.size() != 0) {
            answer = countList.stream().min((o1, o2) -> o1.compareTo(o2)).orElseThrow();
        }

        return answer;
    }

    private void dfs(String begin, String target, ArrayList<String> wordList, ArrayList<Integer> countList, int depth) {
        // list에 target 문자열이 없는 경우
        if (wordList.stream().filter(s -> s.equals(target)).count() == 0) {
            return;
        }

        // begin과 target 일치 하는지 확인
        if (begin.equals(target)) {
            countList.add(depth);
            return;
        }

        ArrayList<String> copyWordList = new ArrayList<>();
        ArrayList<String> equalWord = new ArrayList<>();

        // 리스트 복사
        for (String word : wordList) {
            copyWordList.add(word);
        }

        // 리스트에 있는 begin 값을 제거
        copyWordList.remove(begin);

        for (int i = 0; i < copyWordList.size(); i++) {
            int unequalCnt = 0;

            unequalCnt = unequal(begin, copyWordList.get(i));

            if (unequalCnt < 2) {
                equalWord.add(copyWordList.get(i));
            }
        }

        // 일치하는 문자열이 없는 경우
        if (equalWord.size() == 0) {
            return;
        }

        for (String word : equalWord) {
            dfs(word, target, copyWordList, countList, depth + 1);
        }
    }

    // 문자열 비교
    private int unequal(String begin, String word) {
        int unequalCnt = 0;

        for (int i = 0; i < begin.length(); i++) {
            if (begin.charAt(i) != word.charAt(i)) {
                unequalCnt++;
            }
        }

        return unequalCnt;
    }
}