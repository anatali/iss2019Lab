package it.unibo.experiments;

public class Person {
    private final String name;
    private int age=0;
    private boolean isMarried = false;
    public Person(String name){ this.name = name; }
    public String getName(){ return name;  }
    public int getAge(){ return age;  }
    public void incAge(){ age++;  }
    public boolean getIsMarried(){ return isMarried;  }
    public void setIsMarried(boolean v){ isMarried=v;  }
}
