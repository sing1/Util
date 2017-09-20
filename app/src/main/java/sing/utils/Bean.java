package sing.utils;

import java.io.Serializable;

/**
 * Created by Sing on 2017/7/4.
 */

public class Bean implements Serializable {
    public String age;
    public String name;

    public Bean(String age, String name) {
        this.age = age;
        this.name = name;
    }
}
