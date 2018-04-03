using System.Collections;
using System.Collections.Generic;
using UnityEngine;
public enum tileTypes {
    FLOOR = 0,
    WALL = 1,
    WALLSIDE = 2
}
public class roomGen : MonoBehaviour
{
    public Sprite basicSprite;
    public Sprite[] Spritetypes;
    public GameObject[] tilePrefab;
    private Vector2 spriteSize;
    public int roomWidth = 13, roomHeight = 10;
    private GameObject[,] tiles;
    private bool[,] isBlockRoom;
    private int[,] tileType;
    Vector2 startPos;
    // Use this for initialization
    public Vector3 getStartPosition() {
        return new Vector3(spriteSize.x * 41, spriteSize.y * -41, 0);
    }
    void Start()
    {
        tiles = new GameObject[roomWidth,roomHeight];
        tileType = new int[roomWidth, roomHeight];
        spriteSize = new Vector2(basicSprite.bounds.max.x - basicSprite.bounds.min.x, basicSprite.bounds.max.y - basicSprite.bounds.min.y);
        startPos = new Vector2(spriteSize.x * -40, spriteSize.y * -40);
        spawnStartTile();
        createRooms();
    }
    void createRooms()
    {
        int W = 7, H = 4;
        Room[] rooms = new Room[] {
            new Room(0,0,W-1,H,2,H-1,new int[]
            {
                5,1,1,1,1,5,
                2,0,0,0,0,5,
                2,0,0,0,0,5,
                3,1,4,1,1,3,
            }),
            new Room(6,0,W,H,2,H-1,new int[]
            {
                5,1,1,1,1,1,5,
                2,0,0,0,0,0,5,
                2,0,0,0,0,0,5,
                3,1,4,1,1,1,3,
            }),
            new Room(0,5,W-2,H + 1,2,H-1,new int[]
            {
                5,1,4,1,1,
                5,0,0,0,0,
                2,0,0,0,0,
                2,0,0,0,0,
                3,1,1,1,3,
            }),
            new Room(6,5,W,H + 1,2,H-1,new int[]
            {
                5,1,4,1,1,1,5,
                5,0,0,0,0,0,5,
                2,0,0,0,0,0,5,
                2,0,0,0,0,0,5,
                3,1,1,1,1,1,3,
            }),
            new Room(4,5,W-3,H + 1,2,H-1,new int[]
            {
                5,1,4,1,
                5,0,0,0,
                2,0,0,0,
                2,0,0,0,
                3,1,1,3,
            })
        };
        for (int k = 0; k < rooms.Length; k++)
        {
            for (int i = rooms[k].startX; i < rooms[k].width + rooms[k].startX; i++)
            {
                for (int j = rooms[k].startY; j < rooms[k].height + rooms[k].startY; j++)
                {
                    switch (rooms[k].getBlock(i- rooms[k].startX, j-rooms[k].startY)) {
                       case 5:
                            GameObject tiles = spawnBlock(i, j, 2, false);
                            tiles.transform.localScale = new Vector3((((i- rooms[k].startX) / (rooms[k].width - 1)) * 2 - 1) * -1, 1, 1);
                       break;
                       case 4:
                       break;
                       case 2:
                            GameObject tiless = spawnBlock(i, j, 2, false);
                            tiless.transform.localScale = new Vector3((((i - rooms[k].startX) / (rooms[k].width - 1)) * 2 - 1) * -1, 1, 1);
                            break;
                        case 3:
                            GameObject tile = spawnBlock(i, j, 2, true);
                            //tile.GetComponent<SpriteRenderer>().sprite = Spritetypes[3];
                            tile.GetComponent<BoxCollider2D>().offset = new Vector2(tile.GetComponent<BoxCollider2D>().offset.x, tilePrefab[1].GetComponent<BoxCollider2D>().offset.y);
                            tile.GetComponent<BoxCollider2D>().size = new Vector2(tile.GetComponent<BoxCollider2D>().size.x, tilePrefab[1].GetComponent<BoxCollider2D>().size.y);
                            tile.transform.localScale = new Vector3((((i - rooms[k].startX) / (rooms[k].width - 1)) * 2 - 1) * -1, 1, 1);
                            break;
                        case 0:
                            spawnBlock(i, j, 0, true);
                       break;
                        case 1:
                            spawnBlock(i, j, 1, false);
                            break;

                    }
                }
            }
        }
    }
    GameObject spawnBlock(int i,int j,int blockType,bool isBehind) {
        GameObject tile = (GameObject)Instantiate(tilePrefab[blockType], Vector3.zero, Quaternion.identity);
        tile.transform.position = new Vector3(i * spriteSize.x - startPos.x, -j * spriteSize.y + startPos.y);
        if (isBehind)
        {
            tile.GetComponent<SpriteRenderer>().sortingOrder = Mathf.Abs(-1);
        }
        else {
            tile.GetComponent<SpriteRenderer>().sortingOrder = Mathf.Abs((int)((tile.transform.position.y + spriteSize.y / 2) * 20));
        }
        return tile;
    }
    void spawnStartTile() {
        for (int i = 0; i < roomWidth; i++)
        {
            for (int j = 0; j < roomHeight; j++)
            {
                GameObject tile;
                if ((j == 0 && i != 0 && i != roomWidth - 1) || (j == roomHeight - 1 && i != 0 && i != roomWidth - 1))
                {
                    tile = (GameObject)Instantiate(tilePrefab[1], Vector3.zero, Quaternion.identity);
                    tile.transform.position = new Vector3(i * spriteSize.x - startPos.x, -j * spriteSize.y + startPos.y);
                    tile.GetComponent<SpriteRenderer>().sortingOrder = Mathf.Abs((int)((tile.transform.position.y + spriteSize.y / 2) * 20));
                    tiles[i,j] = tile;
                    tileType[i, j] = 1;
                    continue;
                }
                if (i == 0 || i == roomWidth - 1)
                {
                    tile = (GameObject)Instantiate(tilePrefab[2], Vector3.zero, Quaternion.identity);
                    tile.transform.position = new Vector3(i * spriteSize.x - startPos.x, -j * spriteSize.y + startPos.y);
                    //tile.transform.GetComponent<SpriteRenderer>().sprite = Spritetypes[2];
                    tile.transform.localScale = new Vector3(((i / (roomWidth - 1)) * 2 - 1) * -1, 1, 1);
                    tile.GetComponent<SpriteRenderer>().sortingOrder = Mathf.Abs((int)((tile.transform.position.y + spriteSize.y / 2) * 20));
                    tileType[i, j] = 2;
                    if (j == roomHeight - 1)
                    {
                       // tile.gameObject.GetComponent<SpriteRenderer>().sprite = Spritetypes[3];
                        tileType[i, j] = 3;
                    }
                    tiles[i, j] = tile;
                    continue;
                }
                tile = (GameObject)Instantiate(tilePrefab[0], Vector3.zero, Quaternion.identity);
                tile.transform.position = new Vector3(i * spriteSize.x - startPos.x, -j * spriteSize.y + startPos.y);
                tiles[i, j] = tile;
                tileType[i, j] = 0;
            }
        }
    }
    void spawnRooms(int startX, int startY)
    {
        int RW = 6, RH = Random.Range(4, 6);
        Vector2[] doors = new Vector2[Random.Range(1, 3)];
        for (int i = 0; i < doors.Length; i++) {
            doors[i] = setDoorPosition(startX, startY, RW, RH);
        }
        for (int i = startX; i < RW + startX; i++)
        {
            for (int j = startY; j < RH + startY; j++)
            {
                if (i >= roomWidth || j >= roomHeight)
                    break;
                for (int k = 0; k < doors.Length; k++)
                {
                    if (i == doors[k].x + startX && j == doors[k].y + startY)
                    {
                        Destroy(tiles[i, j]);
                        tiles[i, j] = (GameObject)Instantiate(tilePrefab[0], Vector3.zero, Quaternion.identity);
                        tiles[i, j].transform.position = new Vector3(i * spriteSize.x - startPos.x, -j * spriteSize.y + startPos.y);
                        tiles[i, j].GetComponent<SpriteRenderer>().sortingOrder = -1;
                    }
                }
                if (tileType[i, j] != 2)
                {
                    if (i == RW + startX - 1 || i == startX)
                    {
                        GameObject tile = (GameObject)Instantiate(tilePrefab[2], Vector3.zero, Quaternion.identity);
                        tile.transform.position = new Vector3(i * spriteSize.x - startPos.x, -j * spriteSize.y + startPos.y);
                        for (int k = 0; k < doors.Length; k++)
                        {
                            if (j == RH - 1 + startY || (j + 1 == doors[k].y + startY && i == doors[k].x + startX))
                            {
                                tile.GetComponent<SpriteRenderer>().sprite = Spritetypes[3];
                                tile.GetComponent<BoxCollider2D>().offset = new Vector2(tile.GetComponent<BoxCollider2D>().offset.x, tilePrefab[1].GetComponent<BoxCollider2D>().offset.y);
                                tile.GetComponent<BoxCollider2D>().size = new Vector2(tile.GetComponent<BoxCollider2D>().size.x, tilePrefab[1].GetComponent<BoxCollider2D>().size.y);
                            }
                        }
                        tile.transform.localScale = new Vector3((((i-startX) / (RW - 1)) * 2 - 1) * -1, 1, 1);
                        tile.GetComponent<SpriteRenderer>().sortingOrder = Mathf.Abs((int)((tiles[i, j].transform.position.y + spriteSize.y / 2) * 20) - 1);
                        continue;
                    }
                    if (j == RH - 1 + startY)
                    {
                        Destroy(tiles[i, j]);
                        tiles[i, j] = (GameObject)Instantiate(tilePrefab[1], Vector3.zero, Quaternion.identity);
                        tiles[i, j].transform.position = new Vector3(i * spriteSize.x - startPos.x, -j * spriteSize.y + startPos.y);
                        tiles[i, j].GetComponent<SpriteRenderer>().sortingOrder = Mathf.Abs((int)((tiles[i, j].transform.position.y + spriteSize.y / 2) * 20));
                    }
                }
            }
        }
    }
    private Vector2 setDoorPosition(int startX, int startY, int W,int H) {
        while (true)
        {
            bool XY = Random.Range(0, 10) > 5;
            int side = (int)Mathf.Round(Random.Range(0, 2));
            if (XY)
            {
                int y = Random.Range(1, H - 1);
                if (canPlaceDoor(W, H,startX,startY, side * (W - 1), y, XY))
                {
                    return new Vector2(side * (W - 1), y);
                }
            }
            else
            {
                int x = Random.Range(1, (W - 1));
                if (canPlaceDoor(W, H, startX, startY, x, side * (H - 1), XY))
                {
                    return new Vector2(x, side * (H - 1));
                }
            }
        }
        return new Vector2(0, 0);
    }
    bool canPlaceDoor(int W,int H,int startX, int startY, int x,int y,bool XY) {
        if (XY) {
            if (x-1+startX < 0 || x+1+startX >= roomWidth-1) return false;
            return (tileType[x - 1+startX, y] == 0 && tileType[x + 1+startX, y] == 0);
        }
        else {
            if (y-1+startY < 0 || y +1+startY >= roomHeight-1) return false;
            return (tileType[x, y - 1+startY] == 0 && tileType[x, y + 1+startY] == 0);
        }
    }
    // Update is called once per frame
    void Update()
    {

    }
}
