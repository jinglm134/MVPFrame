package com.project.lib;

/**
 * @CreateDate 2020/1/15 9:41
 * @Author jaylm
 */
public class Test3 {

    public static void main(String[] args) {
        Test3 test3 = new Test3();
        System.out.println(test3.findTheDifference("aacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccdd", "aaccacddaacccaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddaacccddddaacccddaacccddaacccddaacccddaacccddaacccddaacccdd"));
    }

    //Given an integer (signed 32 bits), write a function to check whether it is a power of 4.
    public boolean isPowerOfFour(int num) {
        return Math.log(num) / Math.log(4) % 1.0 == 0;
    }

    /* The guess API is defined in the parent class GuessGame.
   @param num, your guess
   @return -1 if my number is lower, 1 if my number is higher, otherwise return 0
    int guess(int num); */


    public int getSum(int a, int b) {
        return Math.addExact(a, b);
    }

    public boolean isPerfectSquare(int num) {
        return Math.sqrt(num) % 1.0 == 0;
    }

    public char findTheDifference(String s, String t) {
//        List<Character> list = new ArrayList<>();
//        for (int i = 0; i < t.length(); i++) {
//            list.add(t.charAt(i));
//        }
//        for (int i = 0; i < s.length(); i++) {
//            list.remove(Character.valueOf(s.charAt(i)));
//        }
//        return list.get(0);


       /* StringBuilder sb = new StringBuilder(t);
        for(int i=0; i<s.length(); i++) {
            int index = sb.indexOf(String.valueOf(s.charAt(i)));
            sb.deleteCharAt(index);
        }
        return sb.charAt(0);*/

        int ch = t.charAt(t.length() - 1);
        for (int i = 0; i < s.length(); i++) {
            ch += s.charAt(i) - t.charAt(i);
        }
        return (char) ch;
    }
}
