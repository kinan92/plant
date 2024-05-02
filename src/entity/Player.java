package entity;

public class Player {
    private int coins;

    public Player() {
        this.coins = 0;
    }

    public int getCoins(){
        return coins;
    }

    public void addCoins(int amount){
        coins += amount;
    }

    public boolean spendCoins(int amount){
        if (coins >= amount){
            coins -= amount;
            return true;
        }
        System.out.println("Insufficient coins");
        return false;
    }
}
