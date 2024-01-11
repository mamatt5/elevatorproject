package com.fdmgroup.ElevatorProject;

public class UserToggleOptions {
    private int srcFloor;
    private int dstFloor;
    private boolean setSrcOn;
    private boolean setDstOn;
    private boolean generateCommands;

    UserToggleOptions(int srcFloor, int dstFloor, boolean setSrcOn, boolean setDstOn,
                      boolean generateCommands) {
        this.srcFloor = srcFloor;
        this.dstFloor = dstFloor;
        this.setSrcOn = setSrcOn;
        this.setDstOn = setDstOn;
        this.generateCommands = generateCommands;
    }

    // Setter Methods
    public void setSrcFloor(int srcFloor) {
        this.srcFloor = srcFloor;
    }

    public void setDstFloor(int dstFloor) {
        this.dstFloor = dstFloor;
    }

    public void setSetSrcOn(boolean setSrcOn) {
        this.setSrcOn = setSrcOn;
    }

    public void setSetDstOn(boolean setDstOn) {
        this.setDstOn = setDstOn;
    }

    public void setGenerateCommands(boolean generateCommands) {
        this.generateCommands = generateCommands;
    }

    public int getSrcFloor() {
        return srcFloor;
    }

    public int getDstFloor() {
        return dstFloor;
    }

    public boolean isGenerateCommands() {
        return generateCommands;
    }

    public boolean isSetSrcOn() {
        return setSrcOn;
    }

    public boolean isSetDstOn() {
        return setDstOn;
    }
}
