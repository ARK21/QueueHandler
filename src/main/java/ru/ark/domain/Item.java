package ru.ark.domain;

import java.util.Random;

/**
 * Main class that puts in queue
 */
public class Item {

    /**
     * Item id
     */
    private int ItemId;

    /**
     * group's id from 1 to 4.
     */
    private int groupId;


    /**
     * Constructors
     */
    public Item() {
    }

    public Item(int itemId) {
        ItemId = itemId;
        // random groupId fro each item
        groupId = new Random().nextInt(4) + 1;
    }


    /**
     * Getters and setters
     *
     * @return
     */
    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     *  Return string with current ItemId and groupId
     */
    @Override
    public String toString() {
        synchronized (this) {
            return "Item{" +
                    "ItemId=" + ItemId +
                    ", groupId=" + groupId +
                    '}';
        }
    }
}
