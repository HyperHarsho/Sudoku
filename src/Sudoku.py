"""
run "pip install numpy" and "pip install pygame --pre"
in terminal before executing the program
"""
import numpy as np
import random
import pygame
import time

table = np.zeros((9, 9), int)
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
    fill()
    removeDigit()


def solve():
    time.sleep(0.25)
    box = 0
    if check():
        return True
    for i in range(len(table)):
        for j in range(len(table[i])):
            if table[i][j] != 0:
                continue
            box = generateBoxNumber(i, j)
            for num in range(9):
                if checkUsed(i, j, box, num+1):
                    table[i][j] = num+1
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


def gensolve():
    box = 0
    if check():
        return True
    for i in range(len(table)):
        for j in range(len(table[i])):
            if table[i][j] != 0:
                continue
            box = generateBoxNumber(i, j)
            for num in range(9):
                if checkUsed(i, j, box, num+1):
                    table[i][j] = num+1
                    if gensolve():
                        return True
                    table[i][j] = 0
            return False
    return False


def fill():
    while check() == False:
        for i in range(len(table)):
            for j in range(len(table[i])):
                if table[i][j] != 0:
                    continue
                table[i][j] = random.randint(1, 9)
                if not gensolve():
                    table[i][j] = 0


def removeDigit():
    count = int(
        input("Enter how many digits should be missing(max is 81):"))
    while count > 81 and count < 1:
        count = int(input("Enter a number between 1 and 81"))
    k = count
    while k >= 0:
        i = random.randint(0, 8)
        j = random.randint(0, 8)
        if table[i][j] != 0:
            table[i][j] = 0
            empty.append((i, j))
            k -= 1
    printTable()
    print("Empty = "+str(count))


initialize()

pygame.font.init()
font = pygame.font.SysFont('Comic Sans MS', 40)
gridDisplay = pygame.display.set_mode((540, 540))
grid_node_width = 60
grid_node_height = 60


def createNumber(x, y, num, color):
    if num == 0:
        return
    text = font.render(str(num), False, color)
    gridDisplay.blit(text, (x, y))


def visualizeGrid():
    pygame.event.get()
    pygame.display.get_surface().fill((255, 255, 255))
    for i in range(60, 540, 60):
        x = i - 5
        y = i
        pygame.draw.line(gridDisplay, (211, 211, 211), (x, 0), (x, 540), 2)
        pygame.draw.line(gridDisplay, (211, 211, 211), (0, y), (540, y), 2)
    for i in range(180, 540, 180):
        x = i - 5
        y = i
        pygame.draw.line(gridDisplay, (0, 0, 0), (x, 0), (x, 540), 5)
        pygame.draw.line(gridDisplay, (0, 0, 0), (0, y), (540, y), 5)
    y = 0
    for i in range(len(table)):
        x = 15
        for j in range(len(table)):
            if (i, j) in empty:
                text = font.render("_", False, (0, 0, 0))
                gridDisplay.blit(text, (x, y))
            if (i, j) in empty:
                createNumber(x, y, table[i][j], (119, 0, 200))
            else:
                createNumber(x, y, table[i][j], (0, 0, 0))
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
