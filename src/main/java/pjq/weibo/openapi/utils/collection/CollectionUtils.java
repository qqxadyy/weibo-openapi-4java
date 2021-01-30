package pjq.weibo.openapi.utils.collection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pjq.weibo.openapi.utils.CheckUtils;

/**
 * <p>
 * -集合、map、数组工具类
 * <p>
 * Create at 2019年1月17日
 * 
 * @author pengjianqiang
 */
@Slf4j
public final class CollectionUtils {
    private CollectionUtils() {}

    /**
     * -遍历集合，可以在action方法中抛出{@link Break}或{@link Continue}异常进行控制<br/>
     * -如果action里面本身有必须显式捕捉的异常，则可以在catch中根据情况抛出Break或Continue异常<br/>
     * 
     * @param iterable
     * @param action
     *            e->{}
     */
    public static <T> void forEach(Iterable<T> iterable, Consumer<T> action) {
        forEachCommon(iterable, action);
    }

    /**
     * -遍历集合，可以在action方法中抛出{@link Break}或{@link Continue}异常进行控制<br/>
     * -如果action里面本身有必须显式捕捉的异常，则可以在catch中根据情况抛出Break或Continue异常<br/>
     * -当iterable为非有序集合时，action的下标不一定符合预期，慎用
     * 
     * @param iterable
     * @param action
     *            (e,i)->{}
     */
    public static <T> void forEach(Iterable<T> iterable, IndexConsumer<T, Integer> action) {
        forEachCommon(iterable, action);
    }

    public static <K, V> void forEach(Map<K, V> map, Consumer<Entry<K, V>> action) {
        forEachCommon(map.entrySet(), action);
    }

    /**
     * -遍历map，可以在action方法中抛出{@link Break}或{@link Continue}异常进行控制<br/>
     * -如果action里面本身有必须显式捕捉的异常，则可以在catch中根据情况抛出Break或Continue异常<br/>
     * -当map为非有序map时，action的下标不一定符合预期，慎用
     * 
     * @param map
     * @param action
     */
    public static <K, V> void forEach(Map<K, V> map, IndexConsumer<Entry<K, V>, Integer> action) {
        forEachCommon(map.entrySet(), action);
    }

    public static <T> void forEach(T[] array, Consumer<T> action) {
        forEach(Arrays.stream(array), action);
    }

    public static <T> void forEach(T[] array, IndexConsumer<T, Integer> action) {
        forEach(Arrays.stream(array), action);
    }

    public static <T> void forEach(Stream<T> stream, Consumer<T> action) {
        forEach(stream.collect(Collectors.toList()), action);
    }

    public static <T> void forEach(Stream<T> stream, IndexConsumer<T, Integer> action) {
        forEach(stream.collect(Collectors.toList()), action);
    }

    @SuppressWarnings("unchecked")
    private static <T> void forEachCommon(Iterable<T> iterable, Object actionObj) {
        if (CheckUtils.isNull(iterable)) {
            return;
        }

        Iterator<T> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            return;
        }

        boolean isIndexAction = (actionObj instanceof IndexConsumer);
        Consumer<T> action = !isIndexAction ? (Consumer<T>)actionObj : null;
        IndexConsumer<T, Integer> indexAction = isIndexAction ? (IndexConsumer<T, Integer>)actionObj : null;

        int index = 0;
        while (iterator.hasNext()) {
            try {
                T element = iterator.next();
                if (!isIndexAction) {
                    action.accept(element);
                } else {
                    indexAction.accept(element, index++);
                }
            } catch (Break | Continue e) {
                String errMsg = e.getMessage();
                if (CheckUtils.isEmpty(errMsg)) {
                    Throwable cause = e.getCause();
                    if (CheckUtils.isNotNull(cause)) {
                        errMsg = ExceptionUtils.getRootCauseMessage(cause);
                    }
                }
                if (CheckUtils.isNotEmpty(errMsg)) {
                    log.warn("集合遍历出现".concat(e.getClass().getSimpleName()).concat("，原因如下===={}"), errMsg);
                }

                if (e instanceof Break) {
                    break;
                } else {
                    continue;
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    /**
     * -根据条件过滤iterable
     * 
     * @param iterable
     * @param predicate
     *            过滤条件，<code>t->{}</code>
     * @return 过滤结果用List存储，没有符合条件则返回空list
     */
    public static <T> List<T> filter(Iterable<T> iterable, Predicate<T> predicate) {
        if (CheckUtils.isNull(iterable)) {
            return new ArrayList<>();
        }
        return filter(IterableUtils.toList(iterable).stream(), predicate).collect(Collectors.toList());
    }

    /**
     * -根据条件过滤map
     * 
     * @param map
     * @param predicate
     *            过滤条件，<code>entry->{}</code>
     * @return 没有符合条件则返回空map
     */
    public static <K, V> Map<K, V> filter(Map<K, V> map, Predicate<Entry<K, V>> predicate) {
        if (CheckUtils.isEmpty(map)) {
            return new HashMap<>();
        }
        Map<K, V> filteredMap =
            filter(map.entrySet().stream(), predicate).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        return filteredMap;
    }

    /**
     * -根据条件过滤数组
     * 
     * @param array
     * @param predicate
     *            过滤条件，<code>t->{}</code>
     * @return 没有符合条件则返回空数组(注：array==null时返回null)
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] filter(T[] array, Predicate<? super T> predicate) {
        if (CheckUtils.isEmpty(array)) {
            return array;
        }
        List<T> filteredList = filter(Arrays.stream(array), predicate).collect(Collectors.toList());
        return filteredList.stream()
            .toArray(t -> (T[])Array.newInstance(array.getClass().getComponentType(), filteredList.size()));
    }

    /**
     * -Stream的filter不是结束操作，不开放给外部调用，避免外部获取没结束的Stream
     * 
     * @param stream
     * @param predicate
     * @return
     */
    private static <T> Stream<T> filter(Stream<T> stream, Predicate<? super T> predicate) {
        return stream.filter(predicate);
    }

    /**
     * -根据条件过滤iterable，并返回第一个符合条件的对象<br/>
     * -当iterable为非有序iterable时，返回结果不一定符合预期，慎用
     * 
     * @param iterable
     * @param predicate
     * @return 没有符合条件则返回null
     */
    public static <T> T filterOne(Iterable<T> iterable, Predicate<T> predicate) {
        return first(filter(iterable, predicate));
    }

    /**
     * -根据条件过滤map，并返回第一个符合条件的对象<br/>
     * -当map为非有序map时，返回结果不一定符合预期，慎用
     * 
     * @param map
     * @param predicate
     * @return 没有符合条件则返回null
     */
    public static <K, V> Map<K, V> filterOne(Map<K, V> map, Predicate<Entry<K, V>> predicate) {
        return first(filter(map, predicate));
    }

    /**
     * -根据条件过滤数组，并返回第一个符合条件的对象
     * 
     * @param array
     * @param predicate
     * @return 没有符合条件则返回null
     */
    public static <T> T filterOne(T[] array, Predicate<T> predicate) {
        return first(filter(array, predicate));
    }

    /**
     * -获取集合的第一个对象<br/>
     * -当集合为非有序集合时，返回结果不一定符合预期，慎用
     * 
     * @param iterable
     * @return 集合为空则返回null
     */
    public static <T> T first(Iterable<T> iterable) {
        if (CheckUtils.isNull(iterable)) {
            return null;
        }
        Iterator<T> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /**
     * -获取集合的最后一个对象<br/>
     * -当集合为非有序集合时，返回结果不一定符合预期，慎用
     * 
     * @param iterable
     * @return 集合为空则返回null
     */
    public static <T> T last(Iterable<T> iterable) {
        if (CheckUtils.isNull(iterable)) {
            return null;
        }
        T last = null;
        Iterator<T> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            last = iterator.next();
        }
        return last;
    }

    /**
     * -获取map的第一个元素<br/>
     * -当map为非有序map时，返回结果不一定符合预期，慎用
     * 
     * @param iterable
     * @return 集合为空则返回null
     */
    public static <K, V> Map<K, V> first(Map<K, V> map) {
        if (CheckUtils.isEmpty(map)) {
            return null;
        }
        Map<K, V> newMap = new HashMap<>();
        Entry<K, V> entry = map.entrySet().iterator().next();
        newMap.put(entry.getKey(), entry.getValue());
        return newMap;
    }

    /**
     * -获取map的最后一个元素<br/>
     * -当map为非有序map时，返回结果不一定符合预期，慎用
     * 
     * @param iterable
     * @return 集合为空则返回null
     */
    public static <K, V> Map<K, V> last(Map<K, V> map) {
        if (CheckUtils.isEmpty(map)) {
            return null;
        }
        Map<K, V> newMap = new HashMap<>();
        Entry<K, V> entry = null;
        Iterator<Entry<K, V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            entry = iterator.next();
        }
        newMap.put(entry.getKey(), entry.getValue());
        return newMap;
    }

    public static <T> T first(T[] array) {
        return CheckUtils.isEmpty(array) ? null : array[0];
    }

    public static <T> T last(T[] array) {
        return CheckUtils.isEmpty(array) ? null : array[array.length - 1];
    }

    /**
     * <p>
     * -带数组索引的{@link Consumer}
     * <p>
     * Create at 2018年11月28日
     * 
     * @author pengjianqiang
     */
    @FunctionalInterface
    public interface IndexConsumer<T, I> {
        void accept(T t, I i);
    }

    @NoArgsConstructor
    @SuppressWarnings("serial")
    public static class Break extends RuntimeException {
        public Break(String msg) {
            super(msg);
        }

        public Break(Throwable cause) {
            super(cause);
        }
    }

    @NoArgsConstructor
    @SuppressWarnings("serial")
    public static class Continue extends RuntimeException {
        public Continue(String msg) {
            super(msg);
        }

        public Continue(Throwable cause) {
            super(cause);
        }
    }

    /**
     * 把源list根据mapper的处理转成目标list
     * 
     * @param srcList
     * @param mapper
     * @return
     */
    public static <T, S> List<T> transformList(List<S> srcList, Function<S, T> mapper) {
        return srcList.stream().map(mapper).collect(Collectors.toList());
    }
}