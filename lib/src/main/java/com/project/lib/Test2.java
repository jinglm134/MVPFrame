package com.project.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @CreateDate 2020/1/13 12:49
 * @Author jaylm
 */
public class Test2 {
    public static void main(String[] args) {
        Test2 test2 = new Test2();
//        test2.reverse(47483648);
//        String ss = test2.convert("PAYPALISHIRING", 3);
//        System.out.println(test2.getHint("qwer", "qerw"));
//        System.out.println(test2.reverse(648763741));
        System.out.println(test2.countRangeSum(new int[]{-2, 5, -1}, -2, 2));

    }

    //    There are two sorted arrays nums1 and nums2 of size m and n respectively.
//    Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
//    You may assume nums1 and nums2 cannot be both empty.
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int totalLen = nums1.length + nums2.length;
        int flag = totalLen / 2 + 1;
        double secondLast = 0;
        int idx1 = 0;
        int idx2 = 0;
        double tracker = 0;
        for (int i = 0; i < flag; i++) {
            if (idx1 > nums1.length - 1) {
                tracker = nums2[idx2];
                idx2++;
            } else if (idx2 > nums2.length - 1) {
                tracker = nums1[idx1];
                idx1++;
            } else {
                if (nums1[idx1] <= nums2[idx2]) {
                    tracker = nums1[idx1];
                    idx1++;
                } else {
                    tracker = nums2[idx2];
                    idx2++;
                }
            }
            if (i == flag - 2 && totalLen % 2 == 0) {
                secondLast = tracker;
            }
            if (i == flag - 1 && totalLen % 2 == 0) {
                tracker = (tracker + secondLast) / 2;
            }
        }
        return tracker;
    }


    public String convert(String s, int numRows) {
        StringBuilder result = new StringBuilder();
        if (numRows <= 1) {
            return s;
        }
        int model = numRows * 2 - 2;
        List<String> convert = convert(new ArrayList<>(), s, model);
        List<List<Character>> split = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Character> list = new ArrayList<>();
            int x = model - i;
            for (String str : convert) {
                if (str.length() > i) {
                    list.add(str.charAt(i));
                }
                if (str.length() > x && x >= numRows) {
                    list.add(str.charAt(x));
                }
            }
            split.add(list);
        }

        for (List<Character> list1 : split) {
            for (char ss : list1) {
                result.append(ss);
            }
        }

        return result.toString();

    }

    private List<String> convert(List<String> list, String s, int split) {

        if (s.length() > split) {
            list.add(s.substring(0, split));
            return convert(list, s.substring(split), split);
        } else {
            list.add(s);
            return list;
        }
    }

    //    Given a 32-bit signed integer, reverse digits of an integer.
    public int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int i = x % 10;
            if (result > (Integer.MAX_VALUE - Math.abs(i)) / 10 || result < (Integer.MIN_VALUE + Math.abs(i)) / 10) {
                return 0;
            }
            result = result * 10 + i;
            x /= 10;
        }
        return result;
    }

    public String getHint(String secret, String guess) {
        int[] num = new int[100];
        int bull = 0, cow = 0;
        for (int i = 0; i < secret.length(); i++) {
            int s = secret.charAt(i) - '0';
            int g = guess.charAt(i) - '0';
            if (s == g)
                bull += 1;
            else {
                if (num[s]++ < 0)
                    cow++;
                if (num[g]-- > 0)
                    cow++;
            }
        }
        return bull + "A" + cow + "B";
    }

    public boolean wordPattern(String pattern, String str) {

        String[] split = str.split("\\s+");
        if (split.length != pattern.length()) {
            return false;
        }
        HashMap<Character, String> map1 = new HashMap<>();
        HashMap<String, Character> map2 = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            char key = pattern.charAt(i);
            String value = split[i];
            if (map1.containsKey(key)) {
                if (!value.equals(map1.get(key))) {
                    return false;
                }
            } else {
                map1.put(key, value);
            }

            if (map2.containsKey(value)) {
                if (key != map2.get(value)) {
                    return false;
                }
            } else {
                map2.put(value, key);
            }
        }
        return true;
    }

    //    You are given an integer array nums and you have to return a new counts array.
    //    The counts array has the property where counts[i] is the number of smaller elements to the right of nums[i].
    public List<Integer> countSmaller(int[] nums) {
      /*  List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    count++;
                }
            }
            list.add(count);
        }
        return list;*/
        List<Integer> list = new ArrayList<>();
        List<Integer> clone = Arrays.stream(nums.clone()).boxed().sorted().collect(Collectors.toList());
        for (int num : nums) {
            int index = Collections.binarySearch(clone, num);
            for (int i = index - 1; i >= 0; i--) {
                if (clone.get(i) == num) {
                    index--;
                } else {
                    break;
                }
            }
            list.add(index);
            clone.remove(index);
        }
        return list;
    }

//    Given an integer array nums, return the number of range sums that lie in [lower, upper] inclusive.
//    Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j (i ≤ j), inclusive.

    public int countRangeSum(int[] nums, int lower, int upper) {
        int count = 0;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }


        for (int i = 0; i < nums.length; i++) {
            int sum1 = sum - (i == 0 ? 0 : nums[i - 1]);
            for (int j = nums.length - 1; j >= i; j--) {
                sum1 -= (j == nums.length - 1 ? 0 : nums[j + 1]);
                if (sum1 >= lower && sum1 <= upper) {
                    count++;
                }
            }
        }
        return count;
    }
}