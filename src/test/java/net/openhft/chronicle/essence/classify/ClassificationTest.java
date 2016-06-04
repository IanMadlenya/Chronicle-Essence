package net.openhft.chronicle.essence.classify;

import net.openhft.chronicle.essence.classify.ProxyFactory;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.SortedMap;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.stream.Collectors;

import static net.openhft.chronicle.essence.classify.ProxyFactory.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Peter on 19/05/2016.
 */
public class ClassificationTest {
    @Test
    public void testClassifyBlockingDeque() {
        ProxyFactory<BlockingDeque> proxyFactory = proxyFactory(BlockingDeque.class);
        BlockingDeque mock = proxyFactory.mock();
        mock.forEach(subscriptionAny());
        lastCall().waitSynchronously();
        when(mock.iterator()).returnProxy();
        assertEquals("public abstract boolean Collection.addAll(Collection)=RequestResponse{method=public abstract boolean Collection.addAll(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public abstract void BlockingDeque.addFirst(Object)=AsyncLambda{method=public abstract void BlockingDeque.addFirst(Object)}\n" +
                        "public abstract void BlockingDeque.addLast(Object)=AsyncLambda{method=public abstract void BlockingDeque.addLast(Object)}\n" +
                        "public abstract boolean BlockingDeque.add(Object)=RequestResponse{method=public abstract boolean BlockingDeque.add(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract void Collection.clear()=AsyncLambda{method=public abstract void Collection.clear()}\n" +
                        "public abstract boolean Collection.containsAll(Collection)=RequestResponse{method=public abstract boolean Collection.containsAll(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean BlockingDeque.contains(Object)=RequestResponse{method=public abstract boolean BlockingDeque.contains(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Iterator Deque.descendingIterator()=RequestSubscription{method=public abstract Iterator Deque.descendingIterator(), callMode=ASYNCHONOUS, argFilters=[]}\n" +
                        "public abstract int BlockingQueue.drainTo(Collection,int)=RequestResponse{method=public abstract int BlockingQueue.drainTo(Collection,int), synchronous=SYNCHRONOUS}\n" +
                        "public abstract int BlockingQueue.drainTo(Collection)=RequestResponse{method=public abstract int BlockingQueue.drainTo(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object BlockingDeque.element()=RequestResponse{method=public abstract Object BlockingDeque.element(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Collection.equals(Object)=RequestResponse{method=public abstract boolean Collection.equals(Object), synchronous=SYNCHRONOUS}\n" +
                        "public default void Iterable.forEach(function.Consumer)=RequestSubscription{method=public default void Iterable.forEach(function.Consumer), callMode=SYNCHRONOUS, argFilters=[SUBSCRIPTION_ANY]}\n" +
                        "public abstract Object Deque.getFirst()=RequestResponse{method=public abstract Object Deque.getFirst(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object Deque.getLast()=RequestResponse{method=public abstract Object Deque.getLast(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract int Collection.hashCode()=RequestResponse{method=public abstract int Collection.hashCode(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Collection.isEmpty()=RequestResponse{method=public abstract boolean Collection.isEmpty(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Iterator BlockingDeque.iterator()=RequestProxy{method=public abstract Iterator BlockingDeque.iterator()}\n" +
                        "public abstract boolean BlockingDeque.offerFirst(Object,long,TimeUnit) throws InterruptedException=RequestResponse{method=public abstract boolean BlockingDeque.offerFirst(Object,long,TimeUnit) throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean BlockingDeque.offerFirst(Object)=RequestResponse{method=public abstract boolean BlockingDeque.offerFirst(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean BlockingDeque.offerLast(Object,long,TimeUnit) throws InterruptedException=RequestResponse{method=public abstract boolean BlockingDeque.offerLast(Object,long,TimeUnit) throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean BlockingDeque.offerLast(Object)=RequestResponse{method=public abstract boolean BlockingDeque.offerLast(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean BlockingDeque.offer(Object,long,TimeUnit) throws InterruptedException=RequestResponse{method=public abstract boolean BlockingDeque.offer(Object,long,TimeUnit) throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean BlockingDeque.offer(Object)=RequestResponse{method=public abstract boolean BlockingDeque.offer(Object), synchronous=SYNCHRONOUS}\n" +
                        "public default stream.Stream Collection.parallelStream()=DefaultCall{method=public default stream.Stream Collection.parallelStream()}\n" +
                        "public abstract Object Deque.peekFirst()=RequestResponse{method=public abstract Object Deque.peekFirst(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object Deque.peekLast()=RequestResponse{method=public abstract Object Deque.peekLast(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object BlockingDeque.peek()=RequestResponse{method=public abstract Object BlockingDeque.peek(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object Deque.pollFirst()=RequestResponse{method=public abstract Object Deque.pollFirst(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object BlockingDeque.pollFirst(long,TimeUnit) throws InterruptedException=RequestResponse{method=public abstract Object BlockingDeque.pollFirst(long,TimeUnit) throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object Deque.pollLast()=RequestResponse{method=public abstract Object Deque.pollLast(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object BlockingDeque.pollLast(long,TimeUnit) throws InterruptedException=RequestResponse{method=public abstract Object BlockingDeque.pollLast(long,TimeUnit) throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object BlockingDeque.poll()=RequestResponse{method=public abstract Object BlockingDeque.poll(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object BlockingDeque.poll(long,TimeUnit) throws InterruptedException=RequestResponse{method=public abstract Object BlockingDeque.poll(long,TimeUnit) throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object Deque.pop()=RequestResponse{method=public abstract Object Deque.pop(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract void BlockingDeque.push(Object)=AsyncLambda{method=public abstract void BlockingDeque.push(Object)}\n" +
                        "public abstract void BlockingDeque.putFirst(Object) throws InterruptedException=AsyncLambda{method=public abstract void BlockingDeque.putFirst(Object) throws InterruptedException}\n" +
                        "public abstract void BlockingDeque.putLast(Object) throws InterruptedException=AsyncLambda{method=public abstract void BlockingDeque.putLast(Object) throws InterruptedException}\n" +
                        "public abstract void BlockingDeque.put(Object) throws InterruptedException=AsyncLambda{method=public abstract void BlockingDeque.put(Object) throws InterruptedException}\n" +
                        "public abstract int BlockingQueue.remainingCapacity()=RequestResponse{method=public abstract int BlockingQueue.remainingCapacity(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Collection.removeAll(Collection)=RequestResponse{method=public abstract boolean Collection.removeAll(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean BlockingDeque.removeFirstOccurrence(Object)=RequestResponse{method=public abstract boolean BlockingDeque.removeFirstOccurrence(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object Deque.removeFirst()=RequestResponse{method=public abstract Object Deque.removeFirst(), synchronous=SYNCHRONOUS}\n" +
                        "public default boolean Collection.removeIf(function.Predicate)=RequestResponse{method=public default boolean Collection.removeIf(function.Predicate), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean BlockingDeque.removeLastOccurrence(Object)=RequestResponse{method=public abstract boolean BlockingDeque.removeLastOccurrence(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object Deque.removeLast()=RequestResponse{method=public abstract Object Deque.removeLast(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object BlockingDeque.remove()=RequestResponse{method=public abstract Object BlockingDeque.remove(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean BlockingDeque.remove(Object)=RequestResponse{method=public abstract boolean BlockingDeque.remove(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Collection.retainAll(Collection)=RequestResponse{method=public abstract boolean Collection.retainAll(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public abstract int BlockingDeque.size()=RequestResponse{method=public abstract int BlockingDeque.size(), synchronous=SYNCHRONOUS}\n" +
                        "public default Spliterator Collection.spliterator()=DefaultCall{method=public default Spliterator Collection.spliterator()}\n" +
                        "public default stream.Stream Collection.stream()=DefaultCall{method=public default stream.Stream Collection.stream()}\n" +
                        "public abstract Object BlockingDeque.takeFirst() throws InterruptedException=RequestResponse{method=public abstract Object BlockingDeque.takeFirst() throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object BlockingDeque.takeLast() throws InterruptedException=RequestResponse{method=public abstract Object BlockingDeque.takeLast() throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object BlockingDeque.take() throws InterruptedException=RequestResponse{method=public abstract Object BlockingDeque.take() throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object[] Collection.toArray()=RequestResponse{method=public abstract Object[] Collection.toArray(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object[] Collection.toArray(Object[])=RequestResponse{method=public abstract Object[] Collection.toArray(Object[]), synchronous=SYNCHRONOUS}"
                , asString(proxyFactory.methodMap()));
    }

    @Test
    public void testClassifyScheduledExecutorService() {
        ProxyFactory<ScheduledExecutorService> proxyFactory = proxyFactory(ScheduledExecutorService.class);
        ScheduledExecutorService mock = proxyFactory.mock();
        when(mock.shutdownNow()).waitSynchronously();
        assertEquals("public abstract boolean ExecutorService.awaitTermination(long,TimeUnit) throws InterruptedException=RequestResponse{method=public abstract boolean ExecutorService.awaitTermination(long,TimeUnit) throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract void Executor.execute(Runnable)=AsyncLambda{method=public abstract void Executor.execute(Runnable)}\n" +
                        "public abstract List ExecutorService.invokeAll(Collection,long,TimeUnit) throws InterruptedException=RequestResponse{method=public abstract List ExecutorService.invokeAll(Collection,long,TimeUnit) throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract List ExecutorService.invokeAll(Collection) throws InterruptedException=RequestResponse{method=public abstract List ExecutorService.invokeAll(Collection) throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object ExecutorService.invokeAny(Collection,long,TimeUnit) throws InterruptedException,ExecutionException,TimeoutException=RequestResponse{method=public abstract Object ExecutorService.invokeAny(Collection,long,TimeUnit) throws InterruptedException,ExecutionException,TimeoutException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object ExecutorService.invokeAny(Collection) throws InterruptedException,ExecutionException=RequestResponse{method=public abstract Object ExecutorService.invokeAny(Collection) throws InterruptedException,ExecutionException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean ExecutorService.isShutdown()=RequestResponse{method=public abstract boolean ExecutorService.isShutdown(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean ExecutorService.isTerminated()=RequestResponse{method=public abstract boolean ExecutorService.isTerminated(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract ScheduledFuture ScheduledExecutorService.scheduleAtFixedRate(Runnable,long,long,TimeUnit)=RequestProxy{method=public abstract ScheduledFuture ScheduledExecutorService.scheduleAtFixedRate(Runnable,long,long,TimeUnit)}\n" +
                        "public abstract ScheduledFuture ScheduledExecutorService.scheduleWithFixedDelay(Runnable,long,long,TimeUnit)=RequestProxy{method=public abstract ScheduledFuture ScheduledExecutorService.scheduleWithFixedDelay(Runnable,long,long,TimeUnit)}\n" +
                        "public abstract ScheduledFuture ScheduledExecutorService.schedule(Runnable,long,TimeUnit)=RequestProxy{method=public abstract ScheduledFuture ScheduledExecutorService.schedule(Runnable,long,TimeUnit)}\n" +
                        "public abstract ScheduledFuture ScheduledExecutorService.schedule(Callable,long,TimeUnit)=RequestProxy{method=public abstract ScheduledFuture ScheduledExecutorService.schedule(Callable,long,TimeUnit)}\n" +
                        "public abstract List ExecutorService.shutdownNow()=RequestResponse{method=public abstract List ExecutorService.shutdownNow(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract void ExecutorService.shutdown()=AsyncLambda{method=public abstract void ExecutorService.shutdown()}\n" +
                        "public abstract Future ExecutorService.submit(Runnable,Object)=RequestProxy{method=public abstract Future ExecutorService.submit(Runnable,Object)}\n" +
                        "public abstract Future ExecutorService.submit(Runnable)=RequestProxy{method=public abstract Future ExecutorService.submit(Runnable)}\n" +
                        "public abstract Future ExecutorService.submit(Callable)=RequestProxy{method=public abstract Future ExecutorService.submit(Callable)}"
                , asString(proxyFactory.methodMap()));
    }

    @Test
    public void testClassifyReadWriteLock() {
        ProxyFactory<ReadWriteLock> proxyFactory = proxyFactory(ReadWriteLock.class);
        ReadWriteLock mock = proxyFactory.mock();
        when(mock.readLock()).returnProxy();
        when(mock.writeLock()).returnProxy();
        assertEquals("public abstract locks.Lock locks.ReadWriteLock.readLock()=RequestProxy{method=public abstract locks.Lock locks.ReadWriteLock.readLock()}\n" +
                        "public abstract locks.Lock locks.ReadWriteLock.writeLock()=RequestProxy{method=public abstract locks.Lock locks.ReadWriteLock.writeLock()}"
                , asString(proxyFactory.methodMap()));
    }

    @Test
    public void testClassifyLock() {
        ProxyFactory<Lock> proxyFactory = proxyFactory(Lock.class);
        Lock mock = proxyFactory.mock();
        when(mock.newCondition()).returnProxy();

        assertEquals("public abstract void locks.Lock.lockInterruptibly() throws InterruptedException=AsyncLambda{method=public abstract void locks.Lock.lockInterruptibly() throws InterruptedException}\n" +
                        "public abstract void locks.Lock.lock()=AsyncLambda{method=public abstract void locks.Lock.lock()}\n" +
                        "public abstract locks.Condition locks.Lock.newCondition()=RequestProxy{method=public abstract locks.Condition locks.Lock.newCondition()}\n" +
                        "public abstract boolean locks.Lock.tryLock()=RequestResponse{method=public abstract boolean locks.Lock.tryLock(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean locks.Lock.tryLock(long,TimeUnit) throws InterruptedException=RequestResponse{method=public abstract boolean locks.Lock.tryLock(long,TimeUnit) throws InterruptedException, synchronous=SYNCHRONOUS}\n" +
                        "public abstract void locks.Lock.unlock()=AsyncLambda{method=public abstract void locks.Lock.unlock()}"
                , asString(proxyFactory.methodMap()));
    }

    @Test
    public void testClassifyList() {
        ProxyFactory<List> proxyFactory = proxyFactory(List.class);
        List mock = proxyFactory.mock();
        mock.clear();
        lastCall().waitSynchronously();
        mock.forEach(subscriptionAny());
        lastCall().waitSynchronously();
//        when(mock.stream()).useDefault();
//        when(mock.parallelStream()).useDefault();

        assertEquals("public abstract boolean List.addAll(int,Collection)=RequestResponse{method=public abstract boolean List.addAll(int,Collection), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean List.addAll(Collection)=RequestResponse{method=public abstract boolean List.addAll(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean List.add(Object)=RequestResponse{method=public abstract boolean List.add(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract void List.add(int,Object)=AsyncLambda{method=public abstract void List.add(int,Object)}\n" +
                        "public abstract void List.clear()=RequestResponse{method=public abstract void List.clear(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean List.containsAll(Collection)=RequestResponse{method=public abstract boolean List.containsAll(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean List.contains(Object)=RequestResponse{method=public abstract boolean List.contains(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean List.equals(Object)=RequestResponse{method=public abstract boolean List.equals(Object), synchronous=SYNCHRONOUS}\n" +
                        "public default void Iterable.forEach(function.Consumer)=RequestSubscription{method=public default void Iterable.forEach(function.Consumer), callMode=SYNCHRONOUS, argFilters=[SUBSCRIPTION_ANY]}\n" +
                        "public abstract Object List.get(int)=RequestResponse{method=public abstract Object List.get(int), synchronous=SYNCHRONOUS}\n" +
                        "public abstract int List.hashCode()=RequestResponse{method=public abstract int List.hashCode(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract int List.indexOf(Object)=RequestResponse{method=public abstract int List.indexOf(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean List.isEmpty()=RequestResponse{method=public abstract boolean List.isEmpty(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Iterator List.iterator()=RequestSubscription{method=public abstract Iterator List.iterator(), callMode=ASYNCHONOUS, argFilters=[]}\n" +
                        "public abstract int List.lastIndexOf(Object)=RequestResponse{method=public abstract int List.lastIndexOf(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract ListIterator List.listIterator()=RequestSubscription{method=public abstract ListIterator List.listIterator(), callMode=ASYNCHONOUS, argFilters=[]}\n" +
                        "public abstract ListIterator List.listIterator(int)=RequestSubscription{method=public abstract ListIterator List.listIterator(int), callMode=ASYNCHONOUS, argFilters=[]}\n" +
                        "public default stream.Stream Collection.parallelStream()=DefaultCall{method=public default stream.Stream Collection.parallelStream()}\n" +
                        "public abstract boolean List.removeAll(Collection)=RequestResponse{method=public abstract boolean List.removeAll(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public default boolean Collection.removeIf(function.Predicate)=RequestResponse{method=public default boolean Collection.removeIf(function.Predicate), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean List.remove(Object)=RequestResponse{method=public abstract boolean List.remove(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object List.remove(int)=RequestResponse{method=public abstract Object List.remove(int), synchronous=SYNCHRONOUS}\n" +
                        "public default void List.replaceAll(function.UnaryOperator)=AsyncLambda{method=public default void List.replaceAll(function.UnaryOperator)}\n" +
                        "public abstract boolean List.retainAll(Collection)=RequestResponse{method=public abstract boolean List.retainAll(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object List.set(int,Object)=RequestResponse{method=public abstract Object List.set(int,Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract int List.size()=RequestResponse{method=public abstract int List.size(), synchronous=SYNCHRONOUS}\n" +
                        "public default void List.sort(Comparator)=AsyncLambda{method=public default void List.sort(Comparator)}\n" +
                        "public default Spliterator List.spliterator()=DefaultCall{method=public default Spliterator List.spliterator()}\n" +
                        "public default stream.Stream Collection.stream()=DefaultCall{method=public default stream.Stream Collection.stream()}\n" +
                        "public abstract List List.subList(int,int)=RequestResponse{method=public abstract List List.subList(int,int), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object[] List.toArray()=RequestResponse{method=public abstract Object[] List.toArray(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object[] List.toArray(Object[])=RequestResponse{method=public abstract Object[] List.toArray(Object[]), synchronous=SYNCHRONOUS}"
                , asString(proxyFactory.methodMap()));
    }

    @Test
    public void testClassifyConcMap() {
        ProxyFactory<ConcurrentNavigableMap> proxyFactory = proxyFactory(ConcurrentNavigableMap.class);
        ConcurrentNavigableMap mock = proxyFactory.mock();
//        when(mock.navigableKeySet()).returnProxy();
//        when(mock.descendingKeySet()).returnProxy();
//        when(mock.descendingMap()).returnProxy();
        when(mock.headMap(any())).returnProxy();
        when(mock.headMap(any(), anyBoolean())).returnProxy();
        when(mock.tailMap(any())).returnProxy();
        when(mock.tailMap(any(), anyBoolean())).returnProxy();
        when(mock.subMap(any(), any())).returnProxy();
        when(mock.subMap(any(), anyBoolean(), any(), anyBoolean())).returnProxy();
//        when(mock.entrySet()).returnProxy();
//        when(mock.keySet()).returnProxy();
//        when(mock.values()).returnProxy();
        mock.forEach(subscriptionAny());
        ProxyFactory.lastCall().waitSynchronously();
        when(mock.getOrDefault(any(), any())).useDefault();
        when(mock.put(any(), any())).passAsynchronously();
        when(mock.remove(any())).passAsynchronously();

        assertEquals("public abstract Map$Entry NavigableMap.ceilingEntry(Object)=RequestResponse{method=public abstract Map$Entry NavigableMap.ceilingEntry(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object NavigableMap.ceilingKey(Object)=RequestResponse{method=public abstract Object NavigableMap.ceilingKey(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract void Map.clear()=AsyncLambda{method=public abstract void Map.clear()}\n" +
                        "public abstract Comparator SortedMap.comparator()=RequestResponse{method=public abstract Comparator SortedMap.comparator(), synchronous=SYNCHRONOUS}\n" +
                        "public default Object ConcurrentMap.computeIfAbsent(Object,function.Function)=RequestResponse{method=public default Object ConcurrentMap.computeIfAbsent(Object,function.Function), synchronous=SYNCHRONOUS}\n" +
                        "public default Object ConcurrentMap.computeIfPresent(Object,function.BiFunction)=RequestResponse{method=public default Object ConcurrentMap.computeIfPresent(Object,function.BiFunction), synchronous=SYNCHRONOUS}\n" +
                        "public default Object ConcurrentMap.compute(Object,function.BiFunction)=RequestResponse{method=public default Object ConcurrentMap.compute(Object,function.BiFunction), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Map.containsKey(Object)=RequestResponse{method=public abstract boolean Map.containsKey(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Map.containsValue(Object)=RequestResponse{method=public abstract boolean Map.containsValue(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract NavigableSet ConcurrentNavigableMap.descendingKeySet()=RequestProxy{method=public abstract NavigableSet ConcurrentNavigableMap.descendingKeySet()}\n" +
                        "public abstract ConcurrentNavigableMap ConcurrentNavigableMap.descendingMap()=RequestProxy{method=public abstract ConcurrentNavigableMap ConcurrentNavigableMap.descendingMap()}\n" +
                        "public default NavigableMap ConcurrentNavigableMap.descendingMap()=DefaultCall{method=public default NavigableMap ConcurrentNavigableMap.descendingMap()}\n" +
                        "public abstract Set Map.entrySet()=RequestProxy{method=public abstract Set Map.entrySet()}\n" +
                        "public abstract boolean Map.equals(Object)=RequestResponse{method=public abstract boolean Map.equals(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Map$Entry NavigableMap.firstEntry()=RequestResponse{method=public abstract Map$Entry NavigableMap.firstEntry(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object SortedMap.firstKey()=RequestResponse{method=public abstract Object SortedMap.firstKey(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Map$Entry NavigableMap.floorEntry(Object)=RequestResponse{method=public abstract Map$Entry NavigableMap.floorEntry(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object NavigableMap.floorKey(Object)=RequestResponse{method=public abstract Object NavigableMap.floorKey(Object), synchronous=SYNCHRONOUS}\n" +
                        "public default void ConcurrentMap.forEach(function.BiConsumer)=RequestSubscription{method=public default void ConcurrentMap.forEach(function.BiConsumer), callMode=SYNCHRONOUS, argFilters=[SUBSCRIPTION_ANY]}\n" +
                        "public default Object ConcurrentMap.getOrDefault(Object,Object)=DefaultCall{method=public default Object ConcurrentMap.getOrDefault(Object,Object)}\n" +
                        "public abstract Object Map.get(Object)=RequestResponse{method=public abstract Object Map.get(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract int Map.hashCode()=RequestResponse{method=public abstract int Map.hashCode(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract ConcurrentNavigableMap ConcurrentNavigableMap.headMap(Object,boolean)=RequestProxy{method=public abstract ConcurrentNavigableMap ConcurrentNavigableMap.headMap(Object,boolean)}\n" +
                        "public default NavigableMap ConcurrentNavigableMap.headMap(Object,boolean)=DefaultCall{method=public default NavigableMap ConcurrentNavigableMap.headMap(Object,boolean)}\n" +
                        "public abstract ConcurrentNavigableMap ConcurrentNavigableMap.headMap(Object)=RequestProxy{method=public abstract ConcurrentNavigableMap ConcurrentNavigableMap.headMap(Object)}\n" +
                        "public default SortedMap ConcurrentNavigableMap.headMap(Object)=DefaultCall{method=public default SortedMap ConcurrentNavigableMap.headMap(Object)}\n" +
                        "public abstract Map$Entry NavigableMap.higherEntry(Object)=RequestResponse{method=public abstract Map$Entry NavigableMap.higherEntry(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object NavigableMap.higherKey(Object)=RequestResponse{method=public abstract Object NavigableMap.higherKey(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Map.isEmpty()=RequestResponse{method=public abstract boolean Map.isEmpty(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract NavigableSet ConcurrentNavigableMap.keySet()=RequestProxy{method=public abstract NavigableSet ConcurrentNavigableMap.keySet()}\n" +
                        "public default Set ConcurrentNavigableMap.keySet()=DefaultCall{method=public default Set ConcurrentNavigableMap.keySet()}\n" +
                        "public abstract Map$Entry NavigableMap.lastEntry()=RequestResponse{method=public abstract Map$Entry NavigableMap.lastEntry(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object SortedMap.lastKey()=RequestResponse{method=public abstract Object SortedMap.lastKey(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Map$Entry NavigableMap.lowerEntry(Object)=RequestResponse{method=public abstract Map$Entry NavigableMap.lowerEntry(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object NavigableMap.lowerKey(Object)=RequestResponse{method=public abstract Object NavigableMap.lowerKey(Object), synchronous=SYNCHRONOUS}\n" +
                        "public default Object ConcurrentMap.merge(Object,Object,function.BiFunction)=RequestResponse{method=public default Object ConcurrentMap.merge(Object,Object,function.BiFunction), synchronous=SYNCHRONOUS}\n" +
                        "public abstract NavigableSet ConcurrentNavigableMap.navigableKeySet()=RequestProxy{method=public abstract NavigableSet ConcurrentNavigableMap.navigableKeySet()}\n" +
                        "public abstract Map$Entry NavigableMap.pollFirstEntry()=RequestResponse{method=public abstract Map$Entry NavigableMap.pollFirstEntry(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Map$Entry NavigableMap.pollLastEntry()=RequestResponse{method=public abstract Map$Entry NavigableMap.pollLastEntry(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract void Map.putAll(Map)=AsyncLambda{method=public abstract void Map.putAll(Map)}\n" +
                        "public abstract Object ConcurrentMap.putIfAbsent(Object,Object)=RequestResponse{method=public abstract Object ConcurrentMap.putIfAbsent(Object,Object), synchronous=SYNCHRONOUS}\n" +
                        "public default Object Map.putIfAbsent(Object,Object)=DefaultCall{method=public default Object Map.putIfAbsent(Object,Object)}\n" +
                        "public abstract Object Map.put(Object,Object)=AsyncLambda{method=public abstract Object Map.put(Object,Object)}\n" +
                        "public abstract boolean ConcurrentMap.remove(Object,Object)=RequestResponse{method=public abstract boolean ConcurrentMap.remove(Object,Object), synchronous=SYNCHRONOUS}\n" +
                        "public default boolean Map.remove(Object,Object)=DefaultCall{method=public default boolean Map.remove(Object,Object)}\n" +
                        "public abstract Object Map.remove(Object)=AsyncLambda{method=public abstract Object Map.remove(Object)}\n" +
                        "public default void ConcurrentMap.replaceAll(function.BiFunction)=AsyncLambda{method=public default void ConcurrentMap.replaceAll(function.BiFunction)}\n" +
                        "public abstract boolean ConcurrentMap.replace(Object,Object,Object)=RequestResponse{method=public abstract boolean ConcurrentMap.replace(Object,Object,Object), synchronous=SYNCHRONOUS}\n" +
                        "public default boolean Map.replace(Object,Object,Object)=DefaultCall{method=public default boolean Map.replace(Object,Object,Object)}\n" +
                        "public abstract Object ConcurrentMap.replace(Object,Object)=RequestResponse{method=public abstract Object ConcurrentMap.replace(Object,Object), synchronous=SYNCHRONOUS}\n" +
                        "public default Object Map.replace(Object,Object)=DefaultCall{method=public default Object Map.replace(Object,Object)}\n" +
                        "public abstract int Map.size()=RequestResponse{method=public abstract int Map.size(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract ConcurrentNavigableMap ConcurrentNavigableMap.subMap(Object,boolean,Object,boolean)=RequestProxy{method=public abstract ConcurrentNavigableMap ConcurrentNavigableMap.subMap(Object,boolean,Object,boolean)}\n" +
                        "public default NavigableMap ConcurrentNavigableMap.subMap(Object,boolean,Object,boolean)=DefaultCall{method=public default NavigableMap ConcurrentNavigableMap.subMap(Object,boolean,Object,boolean)}\n" +
                        "public abstract ConcurrentNavigableMap ConcurrentNavigableMap.subMap(Object,Object)=RequestProxy{method=public abstract ConcurrentNavigableMap ConcurrentNavigableMap.subMap(Object,Object)}\n" +
                        "public default SortedMap ConcurrentNavigableMap.subMap(Object,Object)=DefaultCall{method=public default SortedMap ConcurrentNavigableMap.subMap(Object,Object)}\n" +
                        "public abstract ConcurrentNavigableMap ConcurrentNavigableMap.tailMap(Object,boolean)=RequestProxy{method=public abstract ConcurrentNavigableMap ConcurrentNavigableMap.tailMap(Object,boolean)}\n" +
                        "public default NavigableMap ConcurrentNavigableMap.tailMap(Object,boolean)=DefaultCall{method=public default NavigableMap ConcurrentNavigableMap.tailMap(Object,boolean)}\n" +
                        "public abstract ConcurrentNavigableMap ConcurrentNavigableMap.tailMap(Object)=RequestProxy{method=public abstract ConcurrentNavigableMap ConcurrentNavigableMap.tailMap(Object)}\n" +
                        "public default SortedMap ConcurrentNavigableMap.tailMap(Object)=DefaultCall{method=public default SortedMap ConcurrentNavigableMap.tailMap(Object)}\n" +
                        "public abstract Collection Map.values()=RequestProxy{method=public abstract Collection Map.values()}"
                , asString(proxyFactory.methodMap()));
    }

    @Test
    public void testClassifyNavSet() {
        ProxyFactory<NavigableSet> proxyFactory = proxyFactory(NavigableSet.class);
        NavigableSet mock = proxyFactory.mock();
//        when(mock.iterator()).returnProxy();
//        when(mock.descendingIterator()).returnProxy();
//        when(mock.descendingSet()).returnProxy();
        when(mock.headSet(any())).returnProxy();
        when(mock.headSet(any(), anyBoolean())).returnProxy();
        when(mock.subSet(any(), any())).returnProxy();
        when(mock.subSet(any(), anyBoolean(), any(), anyBoolean())).returnProxy();
        when(mock.tailSet(any())).returnProxy();
        when(mock.tailSet(any(), anyBoolean())).returnProxy();
//        when(mock.spliterator()).useDefault();
//        when(mock.stream()).useDefault();
//        when(mock.parallelStream()).useDefault();
        mock.clear();
        lastCall().waitSynchronously();
        mock.forEach(subscriptionAny());
        ProxyFactory.lastCall().waitSynchronously();

        assertEquals("public abstract boolean Set.addAll(Collection)=RequestResponse{method=public abstract boolean Set.addAll(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Set.add(Object)=RequestResponse{method=public abstract boolean Set.add(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object NavigableSet.ceiling(Object)=RequestResponse{method=public abstract Object NavigableSet.ceiling(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract void Set.clear()=RequestResponse{method=public abstract void Set.clear(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Comparator SortedSet.comparator()=RequestResponse{method=public abstract Comparator SortedSet.comparator(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Set.containsAll(Collection)=RequestResponse{method=public abstract boolean Set.containsAll(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Set.contains(Object)=RequestResponse{method=public abstract boolean Set.contains(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Iterator NavigableSet.descendingIterator()=RequestSubscription{method=public abstract Iterator NavigableSet.descendingIterator(), callMode=ASYNCHONOUS, argFilters=[]}\n" +
                        "public abstract NavigableSet NavigableSet.descendingSet()=RequestProxy{method=public abstract NavigableSet NavigableSet.descendingSet()}\n" +
                        "public abstract boolean Set.equals(Object)=RequestResponse{method=public abstract boolean Set.equals(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object SortedSet.first()=RequestResponse{method=public abstract Object SortedSet.first(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object NavigableSet.floor(Object)=RequestResponse{method=public abstract Object NavigableSet.floor(Object), synchronous=SYNCHRONOUS}\n" +
                        "public default void Iterable.forEach(function.Consumer)=RequestSubscription{method=public default void Iterable.forEach(function.Consumer), callMode=SYNCHRONOUS, argFilters=[SUBSCRIPTION_ANY]}\n" +
                        "public abstract int Set.hashCode()=RequestResponse{method=public abstract int Set.hashCode(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract NavigableSet NavigableSet.headSet(Object,boolean)=RequestProxy{method=public abstract NavigableSet NavigableSet.headSet(Object,boolean)}\n" +
                        "public abstract SortedSet NavigableSet.headSet(Object)=RequestProxy{method=public abstract SortedSet NavigableSet.headSet(Object)}\n" +
                        "public abstract Object NavigableSet.higher(Object)=RequestResponse{method=public abstract Object NavigableSet.higher(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Set.isEmpty()=RequestResponse{method=public abstract boolean Set.isEmpty(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Iterator NavigableSet.iterator()=RequestSubscription{method=public abstract Iterator NavigableSet.iterator(), callMode=ASYNCHONOUS, argFilters=[]}\n" +
                        "public abstract Object SortedSet.last()=RequestResponse{method=public abstract Object SortedSet.last(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object NavigableSet.lower(Object)=RequestResponse{method=public abstract Object NavigableSet.lower(Object), synchronous=SYNCHRONOUS}\n" +
                        "public default stream.Stream Collection.parallelStream()=DefaultCall{method=public default stream.Stream Collection.parallelStream()}\n" +
                        "public abstract Object NavigableSet.pollFirst()=RequestResponse{method=public abstract Object NavigableSet.pollFirst(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object NavigableSet.pollLast()=RequestResponse{method=public abstract Object NavigableSet.pollLast(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Set.removeAll(Collection)=RequestResponse{method=public abstract boolean Set.removeAll(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public default boolean Collection.removeIf(function.Predicate)=RequestResponse{method=public default boolean Collection.removeIf(function.Predicate), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Set.remove(Object)=RequestResponse{method=public abstract boolean Set.remove(Object), synchronous=SYNCHRONOUS}\n" +
                        "public abstract boolean Set.retainAll(Collection)=RequestResponse{method=public abstract boolean Set.retainAll(Collection), synchronous=SYNCHRONOUS}\n" +
                        "public abstract int Set.size()=RequestResponse{method=public abstract int Set.size(), synchronous=SYNCHRONOUS}\n" +
                        "public default Spliterator SortedSet.spliterator()=DefaultCall{method=public default Spliterator SortedSet.spliterator()}\n" +
                        "public default stream.Stream Collection.stream()=DefaultCall{method=public default stream.Stream Collection.stream()}\n" +
                        "public abstract NavigableSet NavigableSet.subSet(Object,boolean,Object,boolean)=RequestProxy{method=public abstract NavigableSet NavigableSet.subSet(Object,boolean,Object,boolean)}\n" +
                        "public abstract SortedSet NavigableSet.subSet(Object,Object)=RequestProxy{method=public abstract SortedSet NavigableSet.subSet(Object,Object)}\n" +
                        "public abstract NavigableSet NavigableSet.tailSet(Object,boolean)=RequestProxy{method=public abstract NavigableSet NavigableSet.tailSet(Object,boolean)}\n" +
                        "public abstract SortedSet NavigableSet.tailSet(Object)=RequestProxy{method=public abstract SortedSet NavigableSet.tailSet(Object)}\n" +
                        "public abstract Object[] Set.toArray()=RequestResponse{method=public abstract Object[] Set.toArray(), synchronous=SYNCHRONOUS}\n" +
                        "public abstract Object[] Set.toArray(Object[])=RequestResponse{method=public abstract Object[] Set.toArray(Object[]), synchronous=SYNCHRONOUS}"
                , asString(proxyFactory.methodMap()));
    }

    @Test
    public void testCallMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method entrySet = Map.class.getMethod("entrySet");
        System.out.println(entrySet);
        SortedMap sortedMap = (SortedMap) Proxy.newProxyInstance(SortedMap.class.getClassLoader(), new Class[]{SortedMap.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method);
                return null;
            }
        });
        entrySet.invoke(sortedMap);
        sortedMap.entrySet();
    }

    private String asString(Map<?, ?> map) {
        return map.entrySet().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"))
                .replace("java.lang.", "")
                .replace("java.util.concurrent.", "")
                .replace("java.util.", "");
    }
}
