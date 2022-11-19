package my.asoul.takeout_server.util;

/**
 * @author 4512
 * @date 2022/9/27 2:59
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static Long get() {
        return threadLocal.get();
    }

    public static void set(Long token) {
        threadLocal.set(token);
    }

    public static void remove() {
        threadLocal.remove();
    }
}
