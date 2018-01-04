import pygame
 
# Define some colors
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
GREEN = (0, 255, 0)
RED = (255, 0, 0)
BLUE = (110, 222, 234)
DARKBLUE = (0,0,255)
YELLOW = (255,255,102)
pygame.init()
 
# Set the width and height of the screen [width, height]
WIDTH = 2500
HEIGHT = 1500
size = (2500, 1500)
screen = pygame.display.set_mode(size)
 
pygame.display.set_caption("My Game")
 
# Loop until the user clicks the close button.
done = False
 
# Used to manage how fast the screen updates
clock = pygame.time.Clock()

#stores coordinates of the current polygon
current_polygon = []
#number of islands and the polygons
n_island_polygon = 0
island_polygonlist = []
ISLAND = 1
#number of waves and the polygons
n_waves_polygon = 0
waves_polygonlist = []
WAVES = 2

#stores what type of game object the user is currectly trying to place
TYPE = 1


boatlist = [(-10,20),(10,20),(10,-10),(0,-20),(-10,-10)]

cameraX = 0
cameraY = 0

#dock stuff
dockX = 0
dockY = 0
#--------functions--------
def convertList(posList):
    #convert each position in a list according to the camera position
    newList = []
    for i in range(len(posList)):
        newList.append((posList[i][0] - cameraX, posList[i][1] - cameraY))

    return newList


def printMapList():
    #print the text for all the vertices
    #do this for each polygon
    print("---------------Islands-----------------------")
    for i in range(len(island_polygonlist)):
        currentline = "{\"vertices\":["
        #iterate through the current polygon list and 
        for j in range(len(island_polygonlist[i])):
            currentline += (str(island_polygonlist[i][j][0]) + ",")
            currentline += (str(island_polygonlist[i][j][1]*-1))
            #if not the last element, a comma
            if not j == len(island_polygonlist[i])-1:
                currentline += ","
        currentline += "], \"x\":0,\"y\":0},"
        print(currentline)
    print("-------------------Waves---------------------")
    for i in range(len(waves_polygonlist)):
        currentline = "{\"vertices\":["
        #iterate through the current polygon list and 
        for j in range(len(waves_polygonlist[i])):
            currentline += (str(waves_polygonlist[i][j][0]) + ",")
            currentline += (str(waves_polygonlist[i][j][1]*-1))
            if not j == len(waves_polygonlist[i])-1:
                currentline += ","
        currentline += "], \"x\":0,\"y\":0,\"deltaX\":0,\"deltaY\":0},"
        print(currentline)
    print("\"dock\":{\"x\":" + str(dockX) + ",\"y\":" + str((dockY*-1)-45) + ", \"rotation\" : 0}" )
    
# -------- Main Program Loop -----------
while not done:
    # --- Main event loop
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            done = True
        if event.type == pygame.MOUSEBUTTONUP:
            #on left click
            if event.button == 1:
                current_polygon.append((pygame.mouse.get_pos()[0]+cameraX,pygame.mouse.get_pos()[1]+cameraY))
                
                #Depending on current type, add to the array of polygons
                if TYPE == ISLAND:
                    try:
                        island_polygonlist[n_island_polygon] = current_polygon
                    except:
                        island_polygonlist.append(current_polygon)

                if TYPE == WAVES:
                    try:
                        waves_polygonlist[n_waves_polygon] = current_polygon
                    except:
                        waves_polygonlist.append(current_polygon)
            if event.button == 3:
                dockX = pygame.mouse.get_pos()[0]+cameraX
                dockY = pygame.mouse.get_pos()[1]+cameraY
        
            

        if event.type == pygame.KEYDOWN:
            #control camera

            
            if event.key == pygame.K_ESCAPE:
                if TYPE == ISLAND:
                    n_island_polygon += 1
                    
                if TYPE == WAVES:
                    
                    n_waves_polygon += 1
                    
                current_polygon = []
            if event.key == pygame.K_BACKSPACE:
                current_polygon = []
                
                if TYPE == ISLAND:
                     island_polygonlist.pop()
                    
                if TYPE == WAVES:
                    waves_polygonlist.pop()
                    
                    n_waves_polygon += 1
            if event.key == pygame.K_p:
                printMapList()
            if event.key == pygame.K_1:
                TYPE = ISLAND
            if event.key == pygame.K_2:
                TYPE = WAVES
                
                

                
    #list of pressed keys for controlling the camera
    pressed_keys = pygame.key.get_pressed()
    if pressed_keys[pygame.K_s]:
        cameraY += 3
    if pressed_keys[pygame.K_w]:
        cameraY -= 3
    if pressed_keys[pygame.K_a]:
        cameraX -= 3
    if pressed_keys[pygame.K_d]:
        cameraX += 3
            
 
   
    screen.fill(BLUE)
    #draw center line
    pygame.draw.line(screen,BLACK,(0-cameraX,-HEIGHT-cameraY), (0-cameraX,HEIGHT-cameraY),1)
    pygame.draw.line(screen,BLACK, (-WIDTH-cameraX, 0-cameraY), (WIDTH-cameraX, 0-cameraY),1)
    #draw the wave areas
    for i in range(len(waves_polygonlist)):
        if len(waves_polygonlist[i]) > 2:
            pygame.draw.polygon(screen, DARKBLUE, convertList(waves_polygonlist[i]),0)
    #draw all the islands
    for i in range(len(island_polygonlist)):
        if len(island_polygonlist[i]) > 2:
            pygame.draw.polygon(screen, GREEN, convertList(island_polygonlist[i]),0)
    

    #drawboat
    pygame.draw.rect(screen, YELLOW, (dockX-cameraX, dockY - cameraY, 25,45),0)
    pygame.draw.polygon(screen, RED, convertList(boatlist),0)
    
    # --- Drawing code should go here
 
    # --- Go ahead and update the screen with what we've drawn.
    pygame.display.flip()
 
    # --- Limit to 60 frames per second
    clock.tick(60)
 
# Close the window and quit.
pygame.quit()


        
    
