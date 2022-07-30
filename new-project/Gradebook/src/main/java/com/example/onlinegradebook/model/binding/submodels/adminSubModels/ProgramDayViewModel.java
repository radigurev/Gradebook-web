package com.example.onlinegradebook.model.binding.submodels.adminSubModels;

public class ProgramDayViewModel {
    private String room;
    private String name;

    public ProgramDayViewModel(String room, String name) {
        this.room = room;
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
