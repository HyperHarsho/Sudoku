#FAILED PRODUCT
import numpy as np
import random
import pygame

NUMBERS = np.array([1, 2, 3, 4, 5, 6, 7, 8, 9], int)
table = np.zeros((9,9),int)
empty = []


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
    while not check():
        table.fill(0)
        fillDiagonal()
        fillRest(0, 3)
    removeDigit()


def solve():
    box = 0
    if check():
        return True
    for i in range(len(table)):
        for j in range(len(table[i])):
            if table[i][j] != 0:
                continue
            box = generateBoxNumber(i, j)
            for num in NUMBERS:
                if checkUsed(i, j, box, num):
                    table[i][j] = num
                    visualizeGrid()
                    if solve():
                        return True
                    table[i][j] = 0
                    visualizeGrid()
            return False
    return False


def checkUsed(i, j, box, number):
    if checkBox(number, box):
        if checkHorizontal(i, number):
            if checkVertical(j, number):
                return True
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
        table[i][j] = random.choice(NUMBERS)
    for i in range(3):
        for j in range(3):
            if table[i][j] != 0:
                continue
            posNum = []
            for num in NUMBERS:
                if checkUsed(i, j, 1, num):
                    posNum.append(num)
            table[i][j] = random.choice(posNum)
            
    for i in range(3, 6):
        for j in range(3, 6):
            if table[i][j] != 0:
                continue
            posNum = []
            for num in NUMBERS:
                if checkUsed(i, j, 5, num):
                    posNum.append(num)
            table[i][j] = random.choice(posNum)
    for i in range(6, 9):
        for j in range(6, 9):
            if table[i][j] != 0:
                continue
            posNum = []
            for num in NUMBERS:
                if checkUsed(i, j, 9, num):
                    posNum.append(num)
            table[i][j] = random.choice(posNum)


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
        if checkUsed(i, j, box, num):
            table[i][j] = num
            if fillRest(i, j + 1):
                return True
            table[i][j] = 0
    return False


def removeDigit():
    count = int(
        input("Enter how many digits should be missing(max is 81):"))
    while count > 81 and count < 1:
        count = int(input("Enter a number between 1 and 81"))
    k = count
    while k >= 0:
        i = random.randint(1,9)-1
        j = random.randint(1,9)-1
        if table[i][j] != 0:
            table[i][j] = 0
            k-=1
    printTable()
    print("Empty = "+str(count))


initialize()

pygame.font.init()
font = pygame.font.SysFont('Comic Sans MS', 40)
gridDisplay = pygame.display.set_mode((540, 540))
grid_node_width = 60
grid_node_height = 60


def createNumber(x, y, num):
    if num == 0:
        return
    text = font.render(str(num), False, (0, 0, 0))
    gridDisplay.blit(text, (x, y))


def visualizeGrid():
    pygame.event.get()
    pygame.display.get_surface().fill((255, 255, 255))
    y = 0
    for i in range(len(table)):
        x = 0
        for j in range(len(table)):
            if [i, j] in empty:
                text = font.render("_", False, (0, 0, 0))
                gridDisplay.blit(text, (x, y))
            createNumber(x, y, table[i][j])
            x += grid_node_width
        y += grid_node_height
    pygame.display.update()


solving = True
while solving:
    if check():
        solving = False
    visualizeGrid()
    solve()
    visualizeGrid()

printTable()
pygame.quit()
