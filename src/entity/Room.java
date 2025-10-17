package entity;

public class Room {
    private Integer numOfBeds;
    private Integer sizeInSqrM;

    public Room(Integer numOfBeds, Integer sizeInSqrM) {
        this.numOfBeds = numOfBeds;
        this.sizeInSqrM = sizeInSqrM;
    }

    public Integer getNumOfBeds() {
        return numOfBeds;
    }

    public void setNumOfBeds(Integer numOfBeds) {
        this.numOfBeds = numOfBeds;
    }

    public Integer getSizeInSqrM() {
        return sizeInSqrM;
    }

    public void setSizeInSqrM(Integer sizeInSqrM) {
        this.sizeInSqrM = sizeInSqrM;
    }
}
