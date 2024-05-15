package controller;

import entity.Plant;

import java.util.ArrayList;

/*TODO: Move all plantlist related methods to this class and refactor so that
*  classes that need to call these methods call them from this class rather than Controller
*  a boundary class should have this class */

public class StorageController
{
    private ArrayList<Plant> plantList;

    public StorageController()
    {
        this.plantList = new ArrayList<>();
    }

    public ArrayList<Plant> getPlantList()
    {
        return plantList;
    }

    public void setPlantList(ArrayList<Plant> plantList)
    {
        this.plantList = plantList;
    }
}
