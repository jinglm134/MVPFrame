package com.project.lib;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @CreateDate 2020/1/15 15:10
 * @Author jaylm
 */
public class Test4 {

    //    给定一个非负整数数组，你最初位于数组的第一个位置。
//
//    数组中的每个元素代表你在该位置可以跳跃的最大长度。
//
//    你的目标是使用最少的跳跃次数到达数组的最后一个位置。
//
//    示例:
//
//    输入: [2,3,1,1,4]
//    输出: 2
//    解释: 跳到最后一个位置的最小跳跃数是 2。
//                 从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
    public static void main(String[] args) {
        Test4 test4 = new Test4();
        System.out.println(test4.jump(new int[]{2, 3, 1, 1, 4}));
    }

    public int jump(int[] nums) {
       /* int count = 0;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            max = Math.max(max, i + nums[i]);
            if (max >= nums.length) {
                return count;
            }
            if (i == max) {
                count++;
                max = 0;
            }
        }
        return count;*/
        int end = 0;
        int maxPosition = 0;
        int steps = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            //找能跳的最远的
            maxPosition = Math.max(maxPosition, nums[i] + i);
            if (i == end) { //遇到边界，就更新边界，并且步数加一
                end = maxPosition;
                steps++;
                if (end >= nums.length - 1) {
                    break;
                }
            }
        }
        return steps;


       /* int count = 0;
        int index = nums.length - 1;
        while (index > 0) {
            for (int i = 0; i < index; i++) {
                if (i + nums[i] >= index) {
                    index = i;
                    nums = Arrays.copyOfRange(nums, 0, index + 1);
                    break;
                }
            }
            count++;
//            throw new IllegalArgumentException("");
        }
        return count;*/
    }

    //给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
    public int trap(int[] height) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int sum = 0;
        int current = 0;
        while (current < height.length) {
            //如果栈不空并且当前指向的高度大于栈顶高度就一直循环
            while (!stack.isEmpty() && height[current] > height[stack.peek()]) {
                int h = height[stack.peek()]; //取出要出栈的元素
                stack.pop(); //出栈
                if (stack.isEmpty()) { // 栈空就出去
                    break;
                }
                int distance = current - stack.peek() - 1; //两堵墙之前的距离。
                int min = Math.min(height[stack.peek()], height[current]);
                sum = sum + distance * (min - h);
            }
            stack.push(current); //当前指向的墙入栈
            current++; //指针后移
        }
        return sum;
    }

    //    给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
    public int singleNumber(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : nums) {
            if (map.containsKey(i)) {
                map.remove(i);
            } else {
                map.put(i, i);
            }
        }
        for (Integer i : map.keySet()) {
            return i;
        }
        return -1;
    }


}
