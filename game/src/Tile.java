public class Tile {

    boolean isOccupiedWithBuilder;
    boolean isOccupiedWithBuilding;

    public Tile() {

    }
    public Tile(boolean isOccupiedWithBuilder,boolean isOccupiedWithBuilding) {
        this.isOccupiedWithBuilder = isOccupiedWithBuilder;
        this.isOccupiedWithBuilding = isOccupiedWithBuilding;
    }


    public boolean isOccupiedWithBuilding() {
        return isOccupiedWithBuilding;
    }

    public boolean isOccupiedWithBuilder() {
        return isOccupiedWithBuilder;
    }

    public void setOccupiedWithBuilding(boolean occupied) {
        isOccupiedWithBuilding = occupied;
    }

    public void setOccupiedWithBuilder(boolean occupied) {
        isOccupiedWithBuilder = occupied;
    }
}

