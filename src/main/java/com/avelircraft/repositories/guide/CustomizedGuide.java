package com.avelircraft.repositories.guide;

import java.util.List;

public interface CustomizedGuide<T> {

    List<T> findGuidesLikeTags(String[] tags);
}
