package controller;

import entity.Plant;
import entity.PlantStateEnum;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class PlantHandler extends Thread{
    ArrayList<Plant> listOfPlants;
    Plant currentPlant;
    Controller controller;
    boolean running;

    public PlantHandler(ArrayList<Plant> listOfPlants, Plant currentPlant, Controller controller)
    {
        this.listOfPlants = listOfPlants;
        this.currentPlant = currentPlant;
        this.controller = controller;
        running = false;
        start();
    }

    public void run()
    {
        while (!Thread.interrupted())
        {
            if (running)
            {
                updateActivePlants();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateActivePlants()
    {
        Plant plant = controller.getCurrentPlant();
        LocalDateTime plantAge = plant.getDateAndTime();
        LocalDateTime currentTime = LocalDateTime.now();
        Duration timeElapsed = Duration.between(plantAge, currentTime);

        System.out.println(timeElapsed.toMinutes());
        int waterlevel = plant.getWaterLevel();

        if (waterlevel > 30 && waterlevel < 130)
        {
            if (timeElapsed.toMinutes() < 120)
            {
                System.out.println("Plant is younger than two hours.");
                plant.setState(PlantStateEnum.small);
            }

            else if (timeElapsed.toMinutes() > 120 && timeElapsed.toMinutes() <= 200)
            {
                System.out.println("Plant is younger than 180 hours.");
                plant.setState(PlantStateEnum.medium);
            }

            else if (timeElapsed.toMinutes() > 200)
            {
                plant.setState(PlantStateEnum.large);
            }
        }

        else
        {
            if (timeElapsed.toMinutes() < 120)
            {
                System.out.println("Plant is younger than two hours.");
                plant.setState(PlantStateEnum.smallDead);
            }

            else if (timeElapsed.toMinutes() > 120 && timeElapsed.toMinutes() <= 180)
            {
                System.out.println("Plant is younger than 180 hours.");
                plant.setState(PlantStateEnum.mediumDead);
            }

            else if (timeElapsed.toMinutes() > 180)
            {
                plant.setState(PlantStateEnum.largeDead);
            }
        }

        controller.updateCurrentPlant();
        controller.updateAge();
    }

    public void updateAllPlants()
    {
        for (Plant plant : listOfPlants)
        {
            LocalDateTime plantAge = plant.getDateAndTime();
            LocalDateTime currentTime = LocalDateTime.now();
            Duration timeElapsed = Duration.between(plantAge, currentTime);

            System.out.println(timeElapsed.toMinutes());
            int waterlevel = plant.getWaterLevel();

            if (waterlevel > 30 && waterlevel < 130)
            {
                if (timeElapsed.toMinutes() < 120)
                {
                    System.out.println("Plant is younger than two hours.");
                    plant.setState(PlantStateEnum.small);
                }

                else if (timeElapsed.toMinutes() > 120 && timeElapsed.toMinutes() <= 180)
                {
                    System.out.println("Plant is younger than 180 hours.");
                    plant.setState(PlantStateEnum.medium);
                }

                else if (timeElapsed.toMinutes() > 180)
                {
                    plant.setState(PlantStateEnum.large);
                }
            }

            else
            {
                if (timeElapsed.toMinutes() < 120)
                {
                    System.out.println("Plant is younger than two hours.");
                    plant.setState(PlantStateEnum.smallDead);
                }

                else if (timeElapsed.toMinutes() > 120 && timeElapsed.toMinutes() <= 180)
                {
                    System.out.println("Plant is younger than 180 hours.");
                    plant.setState(PlantStateEnum.mediumDead);
                }

                else if (timeElapsed.toMinutes() > 180)
                {
                    plant.setState(PlantStateEnum.largeDead);
                }
            }
        }
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    public void setListOfPlants(ArrayList<Plant> allPlants)
    {
        this.listOfPlants = allPlants;
    }
}
