package com.juliano.pet_shopp.Util;

import java.util.ArrayList;

public class Racas extends ArrayList<Raca> {

    public Raca getById(int id) {
        for (Raca raca : this) {
            if (raca.getId() == id) {
                return raca;
            }
        }

        return null;
    }


}