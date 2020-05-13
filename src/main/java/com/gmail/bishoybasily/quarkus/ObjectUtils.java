package com.gmail.bishoybasily.quarkus;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ObjectUtils {

    public static boolean isArray(Object obj) {
        return (obj != null && obj.getClass().isArray());
    }

    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof Optional) {
            return !((Optional) obj).isPresent();
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        // else
        return false;
    }

}
