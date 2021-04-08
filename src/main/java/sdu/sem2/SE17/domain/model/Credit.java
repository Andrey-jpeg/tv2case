package sdu.sem2.SE17.domain.model;

import sdu.sem2.SE17.dal.*;

public class Credit {
    int i;

    public Credit(int i) {
        this.i = i;
    }

    public int multiplyNumber(int x){
        return x * i;
    }

    public Object sample(){
        return new Sample(1);
    }
}
