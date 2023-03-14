package com.ebanma.cloud.sourceCode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author 鹿胜宝
 * @date 2023/03/06
 */
public class MyArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable {

    private static final long serialVersionUID = 1373837418632912607L;

    //初始数组大小
    private static final int DEFAULT_CAPACITY = 10;

    //用于空实例的共享空数组实例
    private static final Object[] EMPTY_ELEMENTDATA = {};

    //用于默认大小的空实例的共享空数组实例。
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private int size;

    transient Object[] elementData;

    protected transient int modCount = 0;

    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = modCount;

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final int size = MyArrayList.this.size;
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size && modCount == expectedModCount) {
                consumer.accept((E) elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
            checkForComodification();
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    //初始化时，创建空数组
    public MyArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    private void ensureCapacityInternal(int minCapacity) {

        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            //虽然默认为10，minCapacity是可能大于10的，因为这个方法不仅在add中被引用，还被addAll引用！
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        ensureExplicitCapacity(minCapacity);
    }

    //确认是否扩容
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;
        if (minCapacity - elementData.length > 0) grow(minCapacity);
    }

    //扩容
    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        //默认策略
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        //如果默认策略的最小容量不够
        if (newCapacity - minCapacity < 0) newCapacity = minCapacity;
        //如果最小容量超出最大数组长度（考虑VM的标题头）
        if (newCapacity - MAX_ARRAY_SIZE > 0) newCapacity = hugeCapacity(minCapacity);
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    //最小容量超出上限的处理
    private static int hugeCapacity(int minCapacity) {
        //如果最小容量为负，此时已经是溢出状态，抛出OOM
        if (minCapacity < 0) throw new OutOfMemoryError();
        //能最大（考虑VM的标题头）就最大（考虑VM的标题头），否则返回最大整型数，等着抛异常吧
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    //根据索引移除对象
    public E remove(int index) {
        rangeCheck(index);
        modCount++;
        E oldValue = elementData(index);
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = null;
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    private void rangeCheck(int index) {
        if (index > size) throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        elementData = EMPTY_ELEMENTDATA;
        stream.defaultReadObject();
        stream.readInt();
        if (size > 0) {
            ensureCapacityInternal(size);
            Object[] a = elementData;
            for (int i = 0; i < size; i++) {
                a[i] = stream.readObject();
            }
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        int expectedModCount = modCount;
        s.defaultWriteObject();
        s.writeInt(size);
        for (int i = 0; i < size; i++) {
            s.writeObject(elementData[i]);
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }


    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
