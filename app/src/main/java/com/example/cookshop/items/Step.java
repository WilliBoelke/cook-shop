package com.example.cookshop.items;

import android.os.Parcel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * This class describes a step for a {@link Recipe}.
 * Its again a Subclass of {@link Item} because it also has a name and a description.
 * Furthermore a step can provide further assistance while cooking a {@link Recipe}
 * <p>
 * The aim of this class is to provide an interactive step by step guide for a recipe through pictures, timers amd text
 *
 * @author willi
 * @version 1.0
 */
public class Step extends Item implements Serializable, Comparable<Step>
{

    /**
     *
     */
    public static final Creator<Step> CREATOR = new Creator<Step>()
    {
        @Override
        public Step createFromParcel(Parcel in)
        {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size)
        {
            return new Step[size];
        }
    };
    private int timerInSeconds;

    //....Constructor..........
    private int orderPosition;

    /**
     * Standard Constructor
     *
     * @param name
     * @param description
     * @param timerInSeconds
     */
    public Step(String name, String description, int timerInSeconds, int orderPosition)
    {
        this.setName(name);
        this.setDescription(description);
        this.setTimerInSeconds(timerInSeconds);
        this.setOrderPosition(orderPosition);
    }

    /**
     * Constructor for steps without orderPosition,
     * the OrderPosition need to be set later
     *
     * @param name
     * @param description
     * @param timerInSeconds
     */
    public Step(String name, String description, int timerInSeconds)
    {
        this.setName(name);
        this.setDescription(description);
        this.setTimerInSeconds(timerInSeconds);
        this.setOrderPosition(0);
    }

    /**
     * Constructor to create object from a {@link Parcel}
     *
     * @param in
     */
    public Step(Parcel in)
    {
        this(in.readString(), in.readString(), in.readInt(), in.readInt());
    }


    //....Methods..........


    //....Setter..........


    /**
     * Constructor to create object from memento pattern
     */
    public Step(String mementoPattern)
    {
        this.setObjectFromMementoPattern(mementoPattern);
    }

    /**
     * Getter for the timerInSeconds
     *
     * @return
     */
    public int getTimerInSeconds()
    {
        return timerInSeconds;
    }

    //....Getter..........

    /**
     * Setter vor the timerInSeconds
     *
     * @param timerInSeconds
     */
    public void setTimerInSeconds(int timerInSeconds)
    {
        if(timerInSeconds <= Integer.MAX_VALUE  && timerInSeconds >= 0)
        {
            this.timerInSeconds = timerInSeconds;
        }
        else if(timerInSeconds > Integer.MAX_VALUE -1 )
        {
            this.timerInSeconds = Integer.MAX_VALUE;
        }
        else if(timerInSeconds < 0)
        {
            this.timerInSeconds = 0;
        }
    }

    public int getOrderPosition()
    {
        return orderPosition;
    }
    //....Memento.........

    public void setOrderPosition(int position)
    {
        if(position <= Integer.MAX_VALUE  && position >= 0)
        {
            this.orderPosition = position;
        }
        else if(position > Integer.MAX_VALUE -1 )
        {
            this.orderPosition = Integer.MAX_VALUE;
        }
        else if(position < 0)
        {
            this.orderPosition = 0;
        }
    }

    @Override
    public String getMementoPattern()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName() + Memento.DELIMITER_STEPS);
        sb.append(this.getDescription() + Memento.DELIMITER_STEPS);
        sb.append(this.getTimerInSeconds() + Memento.DELIMITER_STEPS);
        return sb.toString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    //....Parcelable..........

    @Override
    public void setObjectFromMementoPattern(String mementoPattern)
    {
        if (mementoPattern.trim().length() > 0)
        {
            StringTokenizer st = new StringTokenizer(mementoPattern, Memento.DELIMITER_STEPS);
            this.setName(st.nextToken(Memento.DELIMITER_STEPS));
            this.setDescription(st.nextToken(Memento.DELIMITER_STEPS));
            this.setTimerInSeconds(Integer.parseInt(st.nextToken(Memento.DELIMITER_STEPS)));
        }
    }


    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(timerInSeconds);
    }


    @Override
    public int compareTo(Step other)
    {
        if (this.getName().compareTo(other.getName()) < 0)
        {
            return 1;
        }
        if (this.getName().compareTo(other.getName()) > 0)
        {
            return -1;
        }
        return 0;
    }

    public static Step deserialize(byte[] bytes) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return   (Step) o.readObject();
    }
}

