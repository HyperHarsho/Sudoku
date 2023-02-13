"""
run "pip install numpy" and "pip install pygame --pre"
in terminal before executing the program
"""
import numpy as np
import pygame
from Utils import *

table = np.zeros((9, 9), int)
empty = []
initialize(table,empty)

pygame.font.init()
font = pygame.font.SysFont('Comic Sans MS', 40)
gridDisplay = pygame.display.set_mode((540, 540))
grid_node_width = 60
grid_node_height = 60


solving = True
while solving:
    if check(table):
        solving = False
    visualizeGrid(gridDisplay, table, empty, font, grid_node_width, grid_node_height)
    solve(table,gridDisplay, empty, font, grid_node_width, grid_node_height)
    visualizeGrid(gridDisplay, table, empty, font, grid_node_width, grid_node_height)

printTable(table)
pygame.quit()
