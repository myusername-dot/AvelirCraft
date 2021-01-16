package com.avelircraft.repositories.news;

import java.util.List;

public interface CustomizedNews<T> {

    List<T> findAll();

}
