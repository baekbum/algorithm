package com.example.algorithm.javaPractice.stackAndQueue;

/*
-문제 설명-
트럭 여러 대가 강을 가로지르는 일차선 다리를 정해진 순으로 건너려 합니다. 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 알아내야 합니다. 다리에는 트럭이 최대 bridge_length대 올라갈 수 있으며,
다리는 weight 이하까지의 무게를 견딜 수 있습니다. 단, 다리에 완전히 오르지 않은 트럭의 무게는 무시합니다.

예를 들어, 트럭 2대가 올라갈 수 있고 무게를 10kg까지 견디는 다리가 있습니다. 무게가 [7, 4, 5, 6]kg인 트럭이 순서대로 최단 시간 안에 다리를 건너려면 다음과 같이 건너야 합니다.

경과 시간	다리를 지난 트럭	다리를 건너는 트럭	대기 트럭
0	        []	            []	            [7,4,5,6]
1~2	        []	            [7]	            [4,5,6]
3	        [7]	            [4]	            [5,6]
4	        [7]	            [4,5]	        [6]
5	        [7,4]	        [5]	            [6]
6~7	        [7,4,5]	        [6]	            []
8	        [7,4,5,6]	    []	            []

따라서, 모든 트럭이 다리를 지나려면 최소 8초가 걸립니다.

solution 함수의 매개변수로 다리에 올라갈 수 있는 트럭 수 bridge_length, 다리가 견딜 수 있는 무게 weight, 트럭 별 무게 truck_weights가 주어집니다.
이때 모든 트럭이 다리를 건너려면 최소 몇 초가 걸리는지 return 하도록 solution 함수를 완성하세요.

-제한사항-
bridge_length는 1 이상 10,000 이하입니다.
weight는 1 이상 10,000 이하입니다.
truck_weights의 길이는 1 이상 10,000 이하입니다.
모든 트럭의 무게는 1 이상 weight 이하입니다.

-입출력 예-
bridge_length	weight	truck_weights	                return
2	            10	    [7,4,5,6]	                    8
100	            100	    [10]	                        101
100	            100	    [10,10,10,10,10,10,10,10,10,10]	110
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

@SpringBootTest
public class stackAndQueue3 {

    @Test
    public void searchMain() {
        int bridge_length = 100;
        int weight = 100;
        int[] truck_weights = new int[]{10,10,10,10,10,10,10,10,10,10};

        int answer = solution(bridge_length, weight, truck_weights);

        System.out.println(answer);
    }

    private int solution(int bridge_length, int weight, int[] truck_weights) {
        Bridge bridge = new Bridge(bridge_length, weight);
        Queue<Truck> queue = new LinkedList<>();
        Boolean isRun = true;
        int second = 0;

        for (int i = 0; i < truck_weights.length; i++) {
            Truck truck = new Truck(i, truck_weights[i]);
            queue.offer(truck);
        }

        while (isRun) {
            second++;

            if (bridge.isOnTruck()) {
                if (bridge.removeTargetCheck()) {
                    bridge.removeTruck();
                }
            }

            if (bridge.getOnTruckList().size() < bridge.getLength()) {
                if (!queue.isEmpty()) {
                    int existWeight = 0;

                    if (bridge.isOnTruck()) {
                        existWeight = bridge.sumWeight();
                    }

                    Truck newTruck = queue.peek();

                    if (bridge.getWeight() >= (existWeight + newTruck.getWeight())) {
                        queue.poll();
                        bridge.addTruck(newTruck);
                    }
                }

                bridge.movePosition();
            }

            if (queue.isEmpty() && bridge.getOnTruckList().isEmpty()) {
                isRun = false;
            }
        }

        return second;
    }
}
class Bridge {
    private int length;
    private int weight;
    private Queue<Truck> onTruckList = new LinkedList<>();
    private int count = 0;

    public Bridge(int length, int weight) {
        this.length = length;
        this.weight = weight;
    }

    public Boolean isOnTruck() {
        Boolean isExist = false;

        if (!this.getOnTruckList().isEmpty()) {
            isExist = true;
        }

        return isExist;
    }

    public Boolean removeTargetCheck() {
        Boolean isTarget = false;

        for (Truck truck : this.getOnTruckList()) {
            if (truck.getPosition() >= this.getLength()) {
                isTarget = true;
            }
        }

        return isTarget;
    }

    public int sumWeight() {
        int existWeight = 0;

        for (Truck truck : this.getOnTruckList()) {
            existWeight += truck.getWeight();
        }

        return existWeight;
    }

    public void movePosition() {
        for (Truck truck : this.getOnTruckList()) {
            truck.setPosition(truck.getPosition() + 1);
        }
    }

    public int getLength() {
        return length;
    }

    public int getWeight() {
        return weight;
    }

    public void addTruck(Truck truck) {
        this.onTruckList.offer(truck);
    }

    public void removeTruck() {
        this.onTruckList.poll();
    }

    public Queue<Truck> getOnTruckList() {
        return onTruckList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

class Truck {
    private int idx;
    private int weight;
    private int position = 0;

    public Truck(int idx, int weight) {
        this.idx = idx;
        this.weight = weight;
    }

    public int getIdx() {
        return idx;
    }

    public int getWeight() {
        return weight;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}



