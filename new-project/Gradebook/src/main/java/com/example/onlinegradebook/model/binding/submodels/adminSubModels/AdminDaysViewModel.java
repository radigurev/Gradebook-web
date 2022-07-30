package com.example.onlinegradebook.model.binding.submodels.adminSubModels;

import java.util.ArrayList;
import java.util.List;

public class AdminDaysViewModel {
    private List<ProgramDayViewModel> monday;
    private List<ProgramDayViewModel> tuesday;
    private List<ProgramDayViewModel> wednesday;
    private List<ProgramDayViewModel> thursday;
    private List<ProgramDayViewModel> friday;

    public AdminDaysViewModel() {
        monday=new ArrayList<>();
        tuesday=new ArrayList<>();
        wednesday=new ArrayList<>();
        thursday=new ArrayList<>();
        friday=new ArrayList<>();
    }

    public List<ProgramDayViewModel> getMonday() {
        return monday;
    }

    public void setMonday(List<ProgramDayViewModel> monday) {
        this.monday = monday;
    }

    public List<ProgramDayViewModel> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<ProgramDayViewModel> tuesday) {
        this.tuesday = tuesday;
    }

    public List<ProgramDayViewModel> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<ProgramDayViewModel> wednesday) {
        this.wednesday = wednesday;
    }

    public List<ProgramDayViewModel> getThursday() {
        return thursday;
    }

    public void setThursday(List<ProgramDayViewModel> thursday) {
        this.thursday = thursday;
    }

    public List<ProgramDayViewModel> getFriday() {
        return friday;
    }

    public void setFriday(List<ProgramDayViewModel> friday) {
        this.friday = friday;
    }

}
