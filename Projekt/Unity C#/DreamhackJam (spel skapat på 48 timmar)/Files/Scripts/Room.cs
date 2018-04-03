using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Room {
    public int width;
    public int height;
    public int startX;
    public int startY;
    public int doorX, doorY;
    public int roomType;

    public int[] roomLayout;
    public Room(int startX,int startY,int width,int height,int doorX,int doorY,int[] layout) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.doorX = doorX;
        this.doorY = doorY;
        this.roomLayout = layout;
    }
    public int getBlock(int x, int y) {
        return roomLayout[x + y * width];
    }
}
