package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.util.exception.NotFoundException;

public class ValidationUtil {

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "getID=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "getID=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(BaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (getID=null)");
        }
    }

    public static void checkIdConsistent(BaseEntity entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with getID=" + id);
        }
    }
}
