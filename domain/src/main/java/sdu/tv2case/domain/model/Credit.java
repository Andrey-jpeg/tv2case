package sdu.tv2case.domain.model;

import sdu.tv2case.dal.*;

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
