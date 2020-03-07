package com.project.lib;

/**
 * @CreateDate 2020/1/20 17:59
 * @Author jaylm
 */
public class Test6 {
    boolean[] alive;
    String[] players;
    int[] credibility;
    boolean call = false;
    int left;
    int right;

    public boolean canVillagersWin(String[] players, int[] credibility) {
        alive = new boolean[players.length];
        for (int i = 0; i < players.length; i++) {
            alive[i] = true;
        }
        this.players = players;
        this.credibility = credibility;
        return startGame();
    }

    private boolean startGame() {
        int day = 1;
        while (true) {
            kill(day);
            if (check() != null) {
                return check();
            }
            vote(day);
            if (check() != null) {
                return check();
            }
            day++;
        }
    }

    private void kill(int day) {
        int k = -100;
        int index = -1;
        for (int i = 0; i < players.length; i++) {
            if ((alive[i]) && (!players[i].equals("ww"))) {
                if ((players[i].equals("bear")) && (day != 1)) {
                    index = i;
                    break;
                }
                if (k < credibility[i]) {
                    k = credibility[i];
                    index = i;
                }
            }
        }
        System.out.println("kill index:" + index);

        if (index >= 0) {
            alive[index] = false;
            updateBear();
            know(index, day, true);
        }
    }

    private void vote(int day) {
        int k = 101;
        int index = -1;
        for (int i = 0; i < players.length; i++) {
            if (alive[i]) {
                if (k > credibility[i]) {
                    k = credibility[i];
                    index = i;
                }
            }
        }
        System.out.println("vote index:" + index);

        if (index >= 0) {
            alive[index] = false;
            know(index, day, false);
        }
    }

    private Boolean check() {
        int ww = 0;
        int vil = 0;
        for (int i = 0; i < players.length; i++) {
            if (alive[i]) {
                if (players[i].equals("ww")) {
                    ww++;
                } else {
                    vil++;
                }
            }
        }
        if (ww == 0) {
            return true;
        }
        if (ww >= vil) {
            return false;
        }
        return null;
    }

    private void know(int index, int day, Boolean night) {

        if (players[index].equals("hunter")) {
            credibility[index] = 100;
            if (day != 1) {
                updateCall(index);
            }
            shoot();
        }
        if (players[index].equals("idiot")) {
            if (!night) {
                credibility[index] = 100;
                alive[index] = true;
                updateCall(index);
            }
        }
        if (night) {
            credibility[index] = 100;
            updateCall(index);
        }
        if ((day == 1) && (night)) {
            int bear = 0;
            for (int i = 0; i < players.length; i++) {
                if (players[i].equals("bear")) {
                    bear = i;
                }
            }
            if (alive[bear]) {
                credibility[bear] = 100;
                if (call) {
                    if (credibility[left] == 100) {
                        credibility[right] = 0;
                    } else if (credibility[right] == 100) {
                        credibility[left] = 0;
                    } else {
                        credibility[left] = Math.max(1, credibility[left] / 2);
                        credibility[right] = Math.max(1, credibility[right] / 2);
                    }
                } else {
                    credibility[left] = 100;
                    credibility[right] = 100;
                }
            }
        }

    }

    private void shoot() {
        int k = 101;
        int index = -1;
        for (int i = 0; i < players.length; i++) {
            if (alive[i]) {
                if (k > credibility[i]) {
                    k = credibility[i];
                    index = i;
                }
            }
        }
        if (index >= 0) {
            System.out.println("shoot index:" + index);
            alive[index] = false;
        }
    }

    private void updateCall(int index) {
        if (!call) {
            return;
        }
        if (left == index) {
            credibility[right] = 0;
        }
        if (right == index) {
            credibility[left] = 0;
        }
    }

    private void updateBear() {
        int bear = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i].equals("bear")) {
                bear = i;
            }
        }
        if (alive[bear]) {
            left = (bear - 1 + players.length) % players.length;
            while (!alive[left]) {
                left = (left - 1 + players.length) % players.length;
            }
            right = (bear + 1) % players.length;
            while (!alive[right]) {
                right = (right + 1) % players.length;
            }
            call = ((players[left].equals("ww")) || (players[right].equals("ww")));
        }
        System.out.println("updateBear left:" + left + " right:" + right);
    }

    public static void main(String[] args) {
        Test6 test6 = new Test6();
    }
}