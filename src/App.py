import numpy as np
import random

NUMBERS = np.array([1, 2, 3, 4, 5, 6, 7, 8, 9], int)
table = np.zeros((9, 9), int)


def printTable():
    for i in range(len(table)):
        if i == 0:
            print("-------------------------------")
        for j in range(len(table[i])):
            if j == 0:
                print("|", end="")
            print(" "+(("_" if (table[i][j] == 0)
                  else str(table[i][j])) + " "), end="")
            if (j+1) % 3 == 0:
                print("|", end="")
        print()
        if (i+1) % 3 == 0:
            print("-------------------------------")


def initialize():
    fillDiagonal()
    fillRest(0, 3)
    removeDigit()


def solve():
    box = 0
    for i in range(len(table)):
        for j in range(len(table[i])):
            if table[i][j] != 0:
                continue
            box = generateBoxNumber(i, j)
            posNum = []
            for num in NUMBERS:
                if checkBox(num, box):
                    if checkHorizontal(i, num):
                        if checkVertical(j, num):
                            posNum.append(num)
            if len(posNum) == 1:
                table[i][j] = posNum.pop()
    solve2()
    printTable()


def solve2():
    box = 0
    if check():
        return True
    for i in range(len(table)):
        for j in range(len(table[i])):
            if table[i][j] != 0:
                continue
            box = generateBoxNumber(i, j)
            for num in NUMBERS:
                if checkBox(num, box):
                    if checkHorizontal(i, num):
                        if checkVertical(j, num):
                            table[i][j] = num
                            if solve2():
                                return True
                            table[i][j] = 0
    return False


def check():
    for i in range(len(table)):
        for j in range(len(table[i])):
            if table[i][j] == 0:
                return False
    return True


def checkHorizontal(i, number):
    for j in range(len(table[i])):
        if number == table[i][j]:
            return False
    return True


def checkVertical(j, number):
    for i in range(len(table)):
        if number == table[i][j]:
            return False
    return True


def generateBoxNumber(i, j):
    if i >= 0 and i <= 2:
        if j >= 0 and j <= 2:
            return 1
        if j >= 3 and j <= 5:
            return 2
        return 3
    if i >= 3 and i <= 5:
        if j >= 0 and j <= 2:
            return 4
        if j >= 3 and j <= 5:
            return 5
        return 6
    if j >= 0 and j <= 2:
        return 7
    if j >= 3 and j <= 5:
        return 8
    return 9


def checkBox(number, box):
    match box:
        case 1:
            for i in range(3):
                for j in range(3):
                    if table[i][j] == number:
                        return False
        case 2:
            for i in range(3):
                for j in range(3, 6):
                    if table[i][j] == number:
                        return False
        case 3:
            for i in range(3):
                for j in range(6, 9):
                    if table[i][j] == number:
                        return False
        case 4:
            for i in range(3, 6):
                for j in range(3):
                    if table[i][j] == number:
                        return False
        case 5:
            for i in range(3, 6):
                for j in range(3, 6):
                    if table[i][j] == number:
                        return False
        case 6:
            for i in range(3, 6):
                for j in range(6, 9):
                    if table[i][j] == number:
                        return False
        case 7:
            for i in range(6, 9):
                for j in range(3):
                    if table[i][j] == number:
                        return False
        case 8:
            for i in range(6, 9):
                for j in range(3, 6):
                    if table[i][j] == number:
                        return False
        case 9:
            for i in range(6, 9):
                for j in range(6, 9):
                    if table[i][j] == number:
                        return False
    return True


def fillDiagonal():
    for i in range(len(table)):
        j = i
        posNum = []
        box = generateBoxNumber(i, j)
        for num in NUMBERS:
            if checkBox(num, box):
                if checkHorizontal(i, num):
                    if checkVertical(j, num):
                        posNum.append(num)
        if len(posNum) < 1:
            return
        table[i][j] = random.choice(posNum)
    for i in range(3):
        for j in range(3):
            if table[i][j] != 0:
                continue
            posNum = []
            for num in NUMBERS:
                if checkBox(num, 1):
                    if checkHorizontal(i, num):
                        if checkVertical(j, num):
                            posNum.append(num)
            if len(posNum) >= 1:
                table[i][j] = random.choice(posNum)
            else:
                print("Error")
                printTable()
                return
    for i in range(3, 6):
        for j in range(3, 6):
            if table[i][j] != 0:
                continue
            posNum = []
            for num in NUMBERS:
                if checkBox(num, 5):
                    if checkHorizontal(i, num):
                        if checkVertical(j, num):
                            posNum.append(num)
            if len(posNum) >= 1:
                table[i][j] = random.choice(posNum)
            else:
                print("Error")
                printTable()
                return
    for i in range(6, 9):
        for j in range(6, 9):
            if table[i][j] != 0:
                continue
            posNum = []
            for num in NUMBERS:
                if checkBox(num, 9):
                    if checkHorizontal(i, num):
                        if checkVertical(j, num):
                            posNum.append(num)
            if len(posNum) >= 1:
                table[i][j] = random.choice(posNum)
            else:
                print("Error")
                printTable()
                return


def fillRest(i, j):
    if i == 8 and j == 9:
        return True
    if j == 9:
        i += 1
        j = 0
    if table[i][j] != 0:
        return fillRest(i, j + 1)
    for num in NUMBERS:
        box = generateBoxNumber(i, j)
        if checkBox(num, box):
            if checkHorizontal(i, num):
                if checkVertical(j, num):
                    table[i][j] = num
                    if fillRest(i, j + 1):
                        return True
                    table[i][j] = 0
    return False


def removeDigit():
    count = int(
        input("Enter how many digits should be missing(max is 64):"))
    temp = count
    while count != 0:
        i = random.randint(0, 8)
        j = random.randint(0, 8)
        if table[i][j] != 0:
            count = count-1
            table[i][j] = 0
    printTable()
    print("Empty = "+str(temp))


initialize()
solve()
