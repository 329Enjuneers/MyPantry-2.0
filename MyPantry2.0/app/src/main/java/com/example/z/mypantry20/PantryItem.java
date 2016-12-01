package com.example.z.mypantry20;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Z on 10-Nov-16.
 */

public class PantryItem implements Serializable
{
    private String name;



    private Drawable photo;
    private float amountRemaining;
    private String amountRemainingUnit;

    // TODO: will be removed once we have the photo feature working. Placed for testing purposes
    // for other feature.
    PantryItem(String name, float amountRemaining, String amountRemainingUnit)
    {
        this.name = name;
        this.amountRemaining = amountRemaining;
        this.amountRemainingUnit = amountRemainingUnit;
    }

    PantryItem(String name, Drawable photo, float amountRemaining, String amountRemainingUnit)
    {
        this.name = name;
        this.photo = photo;
        this.amountRemaining = amountRemaining;
        this.amountRemainingUnit = amountRemainingUnit;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Drawable getPhoto()
    {
        return photo;
    }

    public void setPhoto(Drawable photo)
    {
        this.photo = photo;
    }

    public float getAmountRemaining()
    {
        return amountRemaining;
    }

    public void setAmountRemaining(float amountRemaining)
    {
        this.amountRemaining = amountRemaining;
    }

    public String getAmountRemainingUnit()
    {
        return amountRemainingUnit;
    }

    public void setAmountRemainingUnit(String amountRemainingUnit)
    {
        this.amountRemainingUnit = amountRemainingUnit;
    }

    private void consume(float amountToConsume, String unitToConsume)
    {
        //If the unit the user is trying to consume matches the unit of the PantryItem
        if(unitToConsume.equals(amountRemainingUnit))
        {
            //The user is attempting to consume an amount less than or equal to amount remaining
            if(amountRemaining >= amountToConsume)
            {
                amountRemaining -= amountToConsume;
            }
            //The user is attempting to consume more than they have
            else
            {
                //TODO
            }
        }
        //The unit the user is trying to consume DOES NOT match the unit of the PantryItem
        else
        {
            //TODO make a conversion??
        }
    }


    @Override
    public String toString()
    {
        return name;
        //return "[Name: "  + name + ", Remaining: " + amountRemaining + ", Unit: " + amountRemainingUnit + "]";
    }
}