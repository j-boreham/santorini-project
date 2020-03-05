public class Tile {

    boolean isOccupiedWithRedBuilder;
    boolean isOccupiedWithBlueBuilder;
    boolean isOccupiedWithBuilding;

    public Tile() {

    }
    public Tile(boolean isOccupiedWithRedBuilder, boolean isOccupiedWithBlueBuilder,boolean isOccupiedWithBuilding) {
        this.isOccupiedWithRedBuilder = isOccupiedWithRedBuilder;
        this.isOccupiedWithBlueBuilder = isOccupiedWithBlueBuilder;
        this.isOccupiedWithBuilding = isOccupiedWithBuilding;
    }


    public boolean isOccupiedWithBuilding() {
        return isOccupiedWithBuilding;
    }

    public boolean isOccupiedWithRedBuilder() {
        return isOccupiedWithRedBuilder;
    }

    public boolean isOccupiedWithBlueBuilder(){return isOccupiedWithBlueBuilder;}

    public void setOccupiedWithBuilding(boolean occupied) {
        isOccupiedWithBuilding = occupied;
    }

    public void setOccupiedWithRedBuilder(boolean occupied) {
        isOccupiedWithRedBuilder = occupied;
    }

    public void setOccupiedWithBlueBuilder(boolean occupied){isOccupiedWithBlueBuilder = occupied;}
}

