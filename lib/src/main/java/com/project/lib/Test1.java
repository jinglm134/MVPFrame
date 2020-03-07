package com.project.lib;

import java.util.HashMap;

/**
 * @CreateDate 2020/1/10 14:53
 * @Author jaylm
 */
public class Test1 {


    public static void main(String[] args) {
        Test1 test1 = new Test1();
        test1.lengthOfLongestSubstring("abcabcbb");
        System.out.println(test1.twoSum(new int[]{3, 4, 5, 8}, 9)[1]);

    }

//    Given an array of integers, return indices of the two numbers such that they add up to a specific target.
//    You may assume that each input would have exactly one solution, and you may not use the same element twice.
//    Example:
//    Given nums = [2, 7, 11, 15], target = 9,
//    Because nums[0] + nums[1] = 2 + 7 = 9,
//            return [0, 1].

    public int[] twoSum(int[] nums, int target) {

        /*HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        int[] result = new int[2];

        for (int i = 0; i < nums.length; i++) {
            int i1 = target - nums[i];
            Integer index = map.get(i1);
            if (index != null && index != i) {
                result[0] = i;
                result[1] = index;
            }
        }
        return result;*/

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            Integer key = map.get(target - num);
            if (key != null) {
                return new int[]{i, key};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /* You are given two non-empty linked lists representing two non-negative integers.
     The digits are stored in reverse order and each of their nodes contain a single digit.
      Add the two numbers and return it as a linked list.
     You may assume the two numbers do not contain any leading zero, except the number 0 itself.*/
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode mListNode = new ListNode(0);
        addTwoNumbers(mListNode, l1, l2);

        return mListNode;
    }

    public void addTwoNumbers(ListNode result, ListNode l1, ListNode l2) {

        if (l1 == null) {
            l1 = new ListNode(0);
        }
        if (l2 == null) {
            l2 = new ListNode(0);
        }

        result.val += l1.val + l2.val;
        if (result.val >= 10) {
            result.next = new ListNode(result.val / 10);
            result.val = result.val % 10;
        }

        if (l1.next != null || l2.next != null) {
            if (result.next == null) {
                result.next = new ListNode(0);
            }
            addTwoNumbers(result.next, l1.next, l2.next);
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    //    Given a string, find the length of the longest substring without repeating characters.
    public int lengthOfLongestSubstring(String s) {
        StringBuilder str = new StringBuilder();
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            String c = String.valueOf(s.charAt(i));
            if (str.toString().contains(c)) {
                max = Math.max(max, str.length());
                str = new StringBuilder(str.substring(str.indexOf(c) + 1));
            }
            str.append(c);
        }
        return Math.max(max, str.length());
    }

}


