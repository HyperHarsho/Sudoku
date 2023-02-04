import java.util.ArrayList;
import java.util.List;

public class Solver {
    private int[][] table;
    private List<Integer> NUMBERS;

    Solver() {
        this.table = new int[9][9];
        this.intitalize(this.table);
        this.NUMBERS = new ArrayList<>();
        NUMBERS.add(1);
        NUMBERS.add(2);
        NUMBERS.add(3);
        NUMBERS.add(4);
        NUMBERS.add(5);
        NUMBERS.add(6);
        NUMBERS.add(7);
        NUMBERS.add(8);
        NUMBERS.add(9);
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

    private void solve(int[][] table) {
        int box;
        List<Integer> posNum = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] != 0) {
                    continue;
                }
                box = genrateBoxNumber(i, j);
                posNum.clear();
                for (int num : NUMBERS) {
                    if (checkBox(table, num, box)) {
                        if (checkHorizontal(table, i, num)) {
                            if (checkVertical(table, j, num)) {
                                posNum.add(num);
                            }
                        }
                    }
                }
                if(posNum.size() == 1) {
                    table[i][j] = posNum.get(0);
                }
            }
        }
        solve2(table);
        print(table);
    }

    private boolean solve2(int[][] table) {
        int box;
        if (check(table)) {
            print(table);
            return true;
        }
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j] != 0) {
                    continue;
                }
                box = genrateBoxNumber(i, j);
                for (int num : NUMBERS) {
                    if (checkBox(table, num, box)) {
                        if (checkHorizontal(table, i, num)) {
                            if (checkVertical(table, j, num)) {
                                table[i][j] = num;
                                if (solve2(table)) {
                                    return true;
                                }
                                table[i][j] = 0;
                            }
                        }
                    }
                }
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

    public static void main(String[] args) throws Exception {
        Solver app = new Solver();
        int[][] table = app.table.clone();
        app.solve(table);
    }
}
