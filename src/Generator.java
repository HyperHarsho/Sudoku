import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Generator {
    private int[][] table;
    private List<Integer> NUMBERS;

    Generator() {
        this.table = new int[9][9];
        NUMBERS = new ArrayList<>();
        NUMBERS.add(1);
        NUMBERS.add(2);
        NUMBERS.add(3);
        NUMBERS.add(4);
        NUMBERS.add(5);
        NUMBERS.add(6);
        NUMBERS.add(7);
        NUMBERS.add(8);
        NUMBERS.add(9);
        this.fillDiagonal(table);
        this.fillRest(table, 0, 3);
        this.removeDigits();
    }

    private void print(int[][] table) {
        for (int i = 0; i < table.length; i++) {
            if (i == 0) {
                System.out.println("-------------------------------");
            }
            for (int j = 0; j < table[i].length; j++) {
                if (j == 0) {
                    System.out.print("|");
                }
                System.out.print(" " + ((table[i][j] == 0) ? "_" : table[i][j]) + " ");
                if ((j + 1) % 3 == 0) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if ((i + 1) % 3 == 0) {
                System.out.println("-------------------------------");
            }
        }
    }

    private void fillDiagonal(int[][] table) {
        int box;
        List<Integer> posNum = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < table.length; i++) {
            int j = i;
            posNum.clear();
            box = genrateBoxNumber(i, j);
            for (int num : NUMBERS) {
                if (checkBox(table, num, box)) {
                    if (checkHorizontal(table, i, num)) {
                        if (checkVertical(table, j, num)) {
                            posNum.add(num);
                        }
                    }
                }
            }
            if (posNum.size() < 1) {
                return;
            }
            table[i][j] = posNum.get(rand.nextInt(posNum.size()));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (table[i][j] != 0) {
                    continue;
                }
                posNum.clear();
                for (int num : NUMBERS) {
                    if (checkBox(table, num, 1)) {
                        if (checkHorizontal(table, i, num)) {
                            if (checkVertical(table, j, num)) {
                                posNum.add(num);
                            }
                        }
                    }
                }
                if (posNum.size() >= 1) {
                    table[i][j] = posNum.get(rand.nextInt(posNum.size()));
                } else {
                    System.out.println("Error");
                    print(table);
                    return;
                }
            }
        }
        for (int i = 3; i < 6; i++) {
            for (int j = 3; j < 6; j++) {
                if (table[i][j] != 0) {
                    continue;
                }
                posNum.clear();
                for (int num : NUMBERS) {
                    if (checkBox(table, num, 5)) {
                        if (checkHorizontal(table, i, num)) {
                            if (checkVertical(table, j, num)) {
                                posNum.add(num);
                            }
                        }
                    }
                }
                if (posNum.size() >= 1) {
                    table[i][j] = posNum.get(rand.nextInt(posNum.size()));
                } else {
                    System.out.println("Error");
                    print(table);
                    return;
                }
            }
        }
        for (int i = 6; i < 9; i++) {
            for (int j = 6; j < 9; j++) {
                if (table[i][j] != 0) {
                    continue;
                }
                posNum.clear();
                for (int num : NUMBERS) {
                    if (checkBox(table, num, 9)) {
                        if (checkHorizontal(table, i, num)) {
                            if (checkVertical(table, j, num)) {
                                posNum.add(num);
                            }
                        }
                    }
                }
                if (posNum.size() >= 1) {
                    table[i][j] = posNum.get(rand.nextInt(posNum.size()));
                } else {
                    System.out.println("Error");
                    print(table);
                    return;
                }
            }
        }
    }

    private boolean fillRest(int[][] table, int i, int j) {
        if (j >= 9 && i < 8) {
            i = i + 1;
            j = 0;
        }
        if (i >= 9 && j >= 9) {
            return true;
        }
        if (i < 3) {
            if (j < 3) {
                j = 3;
            }
        } else if (i < 6) {
            if (j == (int) (i / 3) * 3) {
                j = j + 3;
            }
        } else if (j == 6) {
            i = i + 1;
            j = 0;
            if (i >= 9) {
                return true;
            }
        }
        for (int num : NUMBERS) {
            int box = genrateBoxNumber(i, j);
            if (checkBox(table, num, box)) {
                if (checkHorizontal(table, i, num)) {
                    if (checkVertical(table, j, num)) {
                        table[i][j] = num;
                        if (fillRest(table, i, j + 1)) {
                            return true;
                        }
                        table[i][j] = 0;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkHorizontal(int[][] table, int i, int number) {
        for (int j = 0; j < table[i].length; j++) {
            if (number == table[i][j]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkVertical(int[][] table, int j, int number) {
        for (int i = 0; i < table.length; i++) {
            if (number == table[i][j]) {
                return false;
            }
        }
        return true;
    }

    private int genrateBoxNumber(int i, int j) {
        if (i >= 0 && i <= 2) {
            if (j >= 0 && j <= 2) {
                return 1;
            }
            if (j >= 3 && j <= 5) {
                return 2;
            }
            return 3;
        }
        if (i >= 3 && i <= 5) {
            if (j >= 0 && j <= 2) {
                return 4;
            }
            if (j >= 3 && j <= 5) {
                return 5;
            }
            return 6;
        }
        if (j >= 0 && j <= 2) {
            return 7;
        }
        if (j >= 3 && j <= 5) {
            return 8;
        }
        return 9;
    }

    private boolean checkBox(int[][] table, int number, int box) {
        switch (box) {
            case 1:
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < 3; i++) {
                    for (int j = 3; j < 6; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
            case 3:
                for (int i = 0; i < 3; i++) {
                    for (int j = 6; j < table[i].length; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
            case 4:
                for (int i = 3; i < 6; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
            case 5:
                for (int i = 3; i < 6; i++) {
                    for (int j = 3; j < 6; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
            case 6:
                for (int i = 3; i < 6; i++) {
                    for (int j = 6; j < table[i].length; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
            case 7:
                for (int i = 6; i < table.length; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
            case 8:
                for (int i = 6; i < table.length; i++) {
                    for (int j = 3; j < 6; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
            case 9:
                for (int i = 6; i < table.length; i++) {
                    for (int j = 6; j < table[i].length; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
        }
        return true;
    }

    public int[][] exportSudoku() {
        return table;
    }

    private void removeDigits() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter how many digits should be missing(max is 64):");
        int count = in.nextInt();
        int temp = count;
        Random rand = new Random();
        while (count != 0) {
            int i = rand.nextInt(9);
            int j = rand.nextInt(9);
            if (table[i][j] != 0) {
                count--;
                table[i][j] = 0;
            }
        }
        print(table);
        System.out.println("Empty = " + temp);
        in.close();
    }
}
