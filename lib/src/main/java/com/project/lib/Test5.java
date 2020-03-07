package com.project.lib;

import java.util.HashMap;

/**
 * @CreateDate 2020/1/19 10:44
 * @Author jaylm
 */
public class Test5 {


    public static void main(String[] args) {
        Test5 test5 = new Test5();
        System.out.println(test5.maxArea(new int[]{1, 1}));
//        System.out.println(test5.fun1(9, 80));
//        System.out.println(test5.fun2(8081));


    }

    //    1.计算m*2^n次方
    public long fun1(int m, int n) {
        return m << n;
    }

    //   2.判断一个数n的奇偶性
    public String fun2(int m) {
        return (m & 1) == 1 ? "奇数" : "偶数";
    }

    //    集合 S 包含从1到 n 的整数。不幸的是，因为数据错误，导致集合里面某一个元素复制了成了集合里面的另外一个元素的值，导致集合丢失了一个整数并且有一个元素重复。
//
//    给定一个数组 nums 代表了集合 S 发生错误后的结果。你的任务是首先寻找到重复出现的整数，再找到丢失的整数，将它们以数组的形式返回。
//
//    示例 1:
//
//    输入: nums = [1,2,2,4]
//    输出: [2,3]
    public int[] findErrorNums(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int x = 0;
        int y = 0;
        for (int i = 0; i < nums.length; i++) {
            int key = nums[i];
            if (map.get(key) != null) {
                y = key;
            } else {
                map.put(key, 0);
            }
            x ^= key ^ (i + 1);
        }
        return new int[]{y, x ^ y};
    }


    //    给定范围 [m, n]，其中 0 <= m <= n <= 2147483647，返回此范围内所有数字的按位与（包含 m, n 两端点）。
    public int rangeBitwiseAnd(int m, int n) {
        while (m < n) {
            n &= n - 1;
        }
        return n;
    }

    //    给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
    public int coinChange(int[] coins, int amount) {
        int[] num = new int[amount + 1];//该数组表示从0到amount的金额需要的最小硬币数量
        num[0] = 0;
        for (int i = 1; i <= amount; i++) {
            int min = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i >= coin) {
                    if (num[i - coin] != Integer.MAX_VALUE) {
                        min = Math.min(min, num[i - coin] + 1);
                    }
                }
            }
            num[i] = min;
        }
        return num[amount] == Integer.MAX_VALUE ? -1 : num[amount];
    }

    //    初始时有 n 个灯泡关闭。 第 1 轮，你打开所有的灯泡。 第 2 轮，每两个灯泡你关闭一次。 第 3 轮，每三个灯泡切换一次开关（如果关闭则开启，如果开启则关闭）。第 i 轮，每 i 个灯泡切换一次开关。 对于第 n 轮，你只切换最后一个灯泡的开关。 找出 n 轮后有多少个亮着的灯泡。
//
//    示例:
//
//    输入: 3
//    输出: 1
//    解释:
//    初始时, 灯泡状态 [关闭, 关闭, 关闭].
//    第一轮后, 灯泡状态 [开启, 开启, 开启].
//    第二轮后, 灯泡状态 [开启, 关闭, 开启].
//    第三轮后, 灯泡状态 [开启, 关闭, 关闭].
//
//    你应该返回 1，因为只有一个灯泡还亮着。
    public int bulbSwitch(int n) {
//        int result = 0;
//        //i为灯泡编号，1————n
//        for (int i = 1; i <= n; i++) {
//            int count = 0;
//            //j为第j轮
//            for (int j = 1; j <= i; j++) {
//                if (i % j == 0) {
//                    count++;
//                }
//            }
//            if (count % 2 == 1) {
//                result++;
//            }
//        }
//        return result;
        return (int) Math.sqrt(n);
    }


//    泰波那契序列 Tn 定义如下： 
//
//    T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
//
//    给你整数 n，请返回第 n 个泰波那契数 Tn 的值。

    public int tribonacci(int n) {
        if (n == 1) {
            return 0;
        }
        if (n == 2) {
            return 1;
        }
        if (n == 3) {
            return 1;
        }
        return fun1(0, 1, 1, 4, n);
    }

    public int fun1(int a, int b, int c, int count, int n) {
        if (count < n) {
            return fun1(b, c, a + b + c, count + 1, n);
        } else {
            return a + b + c;
        }
    }

    //    给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
//
//    说明：你不能倾斜容器，且 n 的值至少为 2。
//
//    输入: [1,8,6,2,5,4,8,3,7]
//    输出: 49
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int max = 0;
        while (left < right) {
            max = Math.max(max, (right - left) * Math.min(height[left], height[right]));
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return max;
    }

//    给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
//
//    如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。
//
//    注意你不能在买入股票前卖出股票。
//
//    示例 1:
//
//    输入: [7,1,5,3,6,4]
//    输出: 5
//    解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
//    注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。

    public int maxProfit(int[] prices) {
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            max = Math.max(max, prices[i] - min);
        }
        return max;

    }

    //    给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
//
//    设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）
//[1,2,3,4,5]
    public int maxProfits(int[] prices) {
//        int max = 0;
//        int min = Integer.MAX_VALUE;
//        int result = 0;
//        for (int i = 0; i < prices.length; i++) {
//            if (prices[i] <= min) {
//                if (max == 0) {
//                    min = prices[i];
//                } else {
//                    result += max - min;
//                    min = prices[i];
//                    max = 0;
//                }
//            } else {
//                if (max == 0) {
//                    max = prices[i];
//                } else if (max > prices[i]) {
//                    result += max - min;
//                    min = prices[i];
//                    max = 0;
//                } else {
//                    max = prices[i];
//                }
//            }
//
//            if (i == prices.length - 1) {
//                if (max > min) {
//                    result += max - min;
//                }
//            }
//
//        }
//        return result;

        int result = 0;
        for (int i = 0; i < prices.length - 2; i++) {
            result += Math.max(prices[i + 1] - prices[i], 0);
        }
        return result;

    }

    /*
    小李的同事们很喜欢玩狼人杀，每次都找他当法官。小李觉得很累，决定写一个程序来跑出狼人杀的结果。

    狼人杀是一个桌游。常见局由 12 名玩家和 1 名法官组成。12 名玩家坐成一个圈。每个玩家在游戏开始时随机抽取一张角色卡牌，只能知道自己的身份。 玩家分为两个阵营，狼人和村民。

    狼人的胜利条件是 狼人阵营的玩家数 >= 村民阵营的玩家数
    村民的胜利条件是 所有狼人出局
    游戏过程

    游戏分为夜晚和白天。

            (夜晚) 拿到狼人身份的玩家睁眼，其他人闭眼。狼人们商量后选择一名玩家击杀；
            (白天) 所有人睁眼；
    法官宣布昨晚出局的玩家，以及熊是否咆哮了；
    出局的玩家离开游戏，发动技能（如果有）；
    法官检查胜利条件，如果任何一个满足则结束游戏；
    在场玩家进行一轮发言，并票选最可疑的玩家出局；
    被选中的玩家离开游戏，发动技能（如果有）；
    法官检查胜利条件，如果任何一个满足则结束游戏；
    重复 1；
    游戏角色
*/

/*【村民阵营】x8

    村民(villager, 下称"vil") x5：没有特殊技能
    猎人("hunter") x1：技能是在出局时（被投票或被狼人击杀）可以向所有人亮出底牌选择带走一名玩家
    白痴("idiot") x1：技能是在白天被投票出局后自动亮出底牌并且不出局
    驯熊人(bear tamer, 下称"bear") x1：（简称熊）每天夜里，如果相邻两名存活玩家有任何一个是狼人，熊会发出咆哮。如果驯熊人已经死亡，则这一局熊不再咆哮。驯熊人如果当晚被杀，熊也不会咆哮。（相邻指向左找第一名存活玩家和向右找第一名存活玩家，当晚被杀的玩家也视为死亡）
            【狼人阵营】x4

    狼人(werewolf, 下称"ww") x4：可以知道同伴，但不知道好人的具体角色。
    对于所有人：

    白天可以投票
    除了发言的环节，玩家不能发言或者交换信息
    我们用 c 来模拟每名玩家在游戏开始时在他人眼中的可信度。0 < c < 100 。越小则越像狼人
    狼人只击杀非狼人，并且在场玩家知晓这点
    在投票环节，c 最低的玩家出局。如果有多个目标，则座位号小号出局。
    狼人优先击杀熊，其他时候击杀 c 最高的好人（村民阵营），如果有多个目标，则击杀座位号小号。
    玩家的 c 变成 0 或者 100 表示已知身份： 铁狼 或者 铁好人
    玩家的 c 会根据大家获取到的信息发生改变，所有人看到的可信度 c 一起更新。注意，改变的时机需要遵守游戏过程：
    猎人出局或白痴被投票出局时一定发动技能，使 ta 的 c 变为 100；猎人会射杀 c 最低的玩家，如果有多个目标，则射杀座位号小号
    第一天发言时，如果驯熊人依然存活，驯熊人会公布身份，使 ta 的 c 变为 100（在模拟器中狼不会假装自己是熊）
    如果熊咆哮了，人们开始怀疑其左右的未知身份玩家，这使他们的 c 变为原来的一半 (向下取整, 如果原来是 1 则不变 )
    如果熊咆哮了且左右的在局一方为铁好人，则另一方成为铁狼；如果其中一方后来被发现是铁好人，人们也会更新另一方的 c
    如果熊在场且没有咆哮，则左右的在场玩家成为铁好人
    如果驯熊人在第一次发言前死亡，则场上玩家不知道其位置，也无法利用熊的咆哮信息
    如果玩家在夜间死亡，则该离场的玩家也被认为是铁好人*/

    /* Input: players = ["bear","vil","vil","ww","vil","vil","idiot","ww","hunter","ww","ww","vil"], credibility = [9,55,62,74,43,70,13,23,15,78,61,66]
     Output: false
     解释：

     第一天夜晚，狼人击杀玩家 5，熊没有咆哮。

     第二天白天，玩家 0 公布自己熊的身份。玩家 1 和玩家 11 成为 铁好人。看上去最可疑的玩家 6 被投出，但是因为是 白痴 身份，并没有出局。玩家 6 成为 铁好人。

     第二天夜晚，狼人击杀玩家 0 驯熊人。熊没有咆哮。

     第三天白天，身份最低的玩家 8 猎人被投票出局。猎人选择带走场上可信度最低的玩家 7。

     第三天夜晚，狼人击杀身份最高的玩家 1 村民。

     第四天白天，身份最低的玩家 4 村民被投票出局。

     第四天夜晚，狼人击杀玩家 6 白痴, 此时场上狼人数量等于好人数量，狼人胜利。*/

//    ["vil", "vil", "vil", "ww", "vil", "ww", "ww", "vil", "ww", "bear", "hunter", "idiot"], credibility = [81, 71, 88, 31, 34, 40, 70, 94, 73, 79, 98, 48]
//    ["vil", "vil", "vil",  "vil", "ww", "ww", "vil", "ww", "bear", "idiot"], credibility = [81, 71, 88,  34, 40, 70, 94, 73, 79, 48]
//    ["vil", "vil", "vil",  "vil", "ww", "ww", "vil", "ww", "bear", "idiot"], credibility = [81, 71, 88,  34, 40, 70, 94, 36.5, 100, 24]
//    ["vil", "vil", "vil",  "vil", "ww", "ww", "vil", "ww", "bear", "idiot"], credibility = [81, 71, 88,  34, 40, 70, 94, 36.5, 100, 100]
//    ["vil", "vil", "vil",  "vil", "ww", "ww", "vil", "ww",  "idiot"], credibility = [81, 71, 88,  34, 40, 70, 94, 36.5,  100]
//    ["vil", "vil", "vil",   "ww", "ww", "vil", "ww",  "idiot"], credibility = [81, 71, 88,  40, 70, 94, 36.5,  100]
//    ["vil", "vil", "vil",   "ww", "ww", "vil", "ww"], credibility = [81, 71, 88,  40, 70, 94, 36.5]
//    ["vil", "vil", "vil",   "ww", "ww", "vil"], credibility = [81, 71, 88,  40, 70, 94]
//    ["vil", "vil", "vil",   "ww", "ww"], credibility = [81, 71, 88,  40, 70]
//    ["vil", "vil", "vil",   "ww"], credibility = [81, 71, 88,  70]
//    ["vil", "vil", "ww"], credibility = [81, 71, 70]
//    ["vil", "vil"], credibility = [81, 71]


//    players = ["vil","ww","bear","hunter","ww","idiot","vil","vil","ww","vil","ww","vil"], credibility = [45,67,32,25,1,27,99,85,3,54,3,25]
//    players = ["vil","ww","bear","hunter","ww","idiot","vil","ww","vil","ww","vil"], credibility = [45,67,32,25,1,27,85,3,54,3,25]
//    players = ["vil","ww","bear","hunter","ww","idiot","vil","ww","vil","ww","vil"], credibility = [45,33.5,100,12.5,1,27,85,3,54,3,25]
//    players = ["vil","ww","bear","hunter","idiot","vil","ww","vil","ww","vil"], credibility = [45,33.5,100,12.5,27,85,3,54,3,25]
//    players = ["vil","ww","hunter","idiot","vil","ww","vil","ww","vil"], credibility = [45,33.5,12.5,27,85,3,54,3,25]
//    players = ["vil","ww","hunter","idiot","vil","vil","ww","vil"], credibility = [45,33.5,12.5,27,85,54,3,25]
//    players = ["vil","ww","hunter","idiot","vil","ww","vil"], credibility = [45,33.5,12.5,27,54,3,25]
//    players = ["vil","ww","hunter","idiot","vil","vil"], credibility = [45,33.5,12.5,27,54,25]
//    players = ["vil","ww","hunter","idiot","vil"], credibility = [45,33.5,12.5,27,25]
//    players = ["vil","ww","idiot","vil"], credibility = [45,0,27,25]
//    players = ["vil","idiot","vil"], credibility = [45,27,25]


//    public boolean canVillagersWin(String[] players, int[] credibility) {
//        int win = win(players);
//        if (win == 1) {
//            return true;
//        } else if (win == 2) {
//            return false;
//        }
//
//
//    }

    /**
     * @param players players
     * @return 1：好人赢 2:狼人赢 3:不能判断胜负
     */
    public int win(String[] players) {
        int ww_num = 0;
        for (String player : players) {
            if ("ww".equals(player)) {
                ww_num++;
            }
        }
        if (ww_num == 0) {
            return 1;
        }
        if (ww_num * 2 >= players.length) {
            return 2;
        }
        return 3;
    }

//    public int atNight(String[] players, int[] credibility) {
//        HashMap<Integer, Integer> map = new HashMap<>();
//        int max = -1;
//        for (int i = credibility.length - 1; i >= 0; i--) {
//            max = Math.max(credibility[i], max);
//        }
//    }
}