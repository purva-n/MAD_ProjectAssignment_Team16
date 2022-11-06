package com.example.atyourservice.users.pojo;

import java.util.ArrayList;
import java.util.List;

public class UserIds {
    private List<Stickers> stickers;

    public UserIds(List<Stickers> stickers) {
        this.stickers = stickers;
    }

    public  UserIds() {
        this.stickers = new ArrayList<>();
    }

    public List<Stickers> getStickers() {
        return stickers;
    }

    public void setStickers(List<Stickers> stickers) {
        this.stickers = stickers;
    }
}
