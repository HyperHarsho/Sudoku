import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Solver {
    private int[][] table;
    private List<Integer> NUMBERS;

    Solver() {
        this.table = new int[9][9];
        this.intitalize(this.table);
        this.NUMBERS = new ArrayList<>();
        this.NUMBERS.add(1);
        this.NUMBERS.add(2);
        this.NUMBERS.add(3);
        this.NUMBERS.add(4);
        this.NUMBERS.add(5);
        this.NUMBERS.add(6);
        this.NUMBERS.add(7);
        this.NUMBERS.add(8);
        this.NUMBERS.add(9);
        Collections.shuffle(this.NUMBERS,new Random(new Random().nextLong()));
    }

    private void intitalize(int[][] table) {
        Generator sudo = new Generator();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = sudo.exportSudoku()[i][j];
            }
        }
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

    private boolean solve(int[][] table) {
        int box;
        if (check(table)) {
            return true;
        }
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] != 0) {
                    continue;
                }
                box = generateBoxNumber(i, j);
                for (int num : NUMBERS) {
                    if (checkUsed(table, i, j, box, num)) {
                        table[i][j] = num;
                        if (solve(table)) {
                            return true;
                        }
                        table[i][j] = 0;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private boolean check(int[][] table) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
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

    private int generateBoxNumber(int i, int j) {
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

    private boolean checkUsed(int[][] table, int i, int j, int box, int num) {
        if (checkBox(table, num, box)) {
            if (checkHorizontal(table, i, num)) {
                if (checkVertical(table, j, num)) {
                    return true;
                }
            }
        }
        return false;
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
                    for (int j = 6; j < 9; j++) {
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
                    for (int j = 6; j < 9; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
            case 7:
                for (int i = 6; i < 9; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
            case 8:
                for (int i = 6; i < 9; i++) {
                    for (int j = 3; j < 6; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
            case 9:
                for (int i = 6; i < 9; i++) {
                    for (int j = 6; j < 9; j++) {
                        if (table[i][j] == number) {
                            return false;
                        }
                    }
                }
                break;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        Solver app = new Solver();
        int[][] table = app.table.clone();
        long start = System.currentTimeMillis();
        app.solve(table);
        long end = System.currentTimeMillis();
        app.print(table);
        long timetaken = end - start;
        System.out.println("Time taken = " + timetaken + "ms");
    }
}
