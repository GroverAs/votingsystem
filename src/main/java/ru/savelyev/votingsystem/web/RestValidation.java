package ru.savelyev.votingsystem.web;

import lombok.experimental.UtilityClass;
import ru.savelyev.votingsystem.HasId;
import ru.savelyev.votingsystem.error.IllegalRequestDataException;

import java.time.LocalTime;

import static ru.savelyev.votingsystem.util.TimeLimitUtil.TIME_LIMIT_FORMATTER;
import static ru.savelyev.votingsystem.util.TimeLimitUtil.getLimitTime;

@UtilityClass
public class RestValidation {

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }
    public static void assureTimeOver(LocalTime currentTime) {
        if (currentTime.isAfter(getLimitTime())) {
            throw new IllegalRequestDataException("Time limit to change the vote is before:" + getLimitTime().format(TIME_LIMIT_FORMATTER) + ", you try to vote at: " + currentTime.format(TIME_LIMIT_FORMATTER));
        }
    }

}