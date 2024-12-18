package ru.kram.sandbox.features.sparsearray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ConcurrentModificationException;
import java.util.Map;

class SimpleArrayMap<K, V> {
    private static final boolean DEBUG = false;
    private static final String TAG = "ArrayMap";

    private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;

    private static final int BASE_SIZE = 4;

    private static final int CACHE_SIZE = 10;

    static @Nullable Object[] mBaseCache;
    static int mBaseCacheSize;
    static @Nullable Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;

    int[] mHashes;
    Object[] mArray;
    int mSize;

    private static int binarySearchHashes(int[] hashes, int N, int hash) {
        try {
            return ContainerHelpers.binarySearch(hashes, N, hash);
        } catch (ArrayIndexOutOfBoundsException e) {
            if (CONCURRENT_MODIFICATION_EXCEPTIONS) {
                throw new ConcurrentModificationException();
            } else {
                throw e; // the cache is poisoned at this point, there's not much we can do
            }
        }
    }

    int indexOf(Object key, int hash) {
        final int N = mSize;

        // Important fast case: if nothing is in here, nothing to look for.
        if (N == 0) {
            return ~0;
        }

        int index = binarySearchHashes(mHashes, N, hash);

        // If the hash code wasn't found, then we have no entry for this key.
        if (index < 0) {
            return index;
        }

        // If the key at the returned index matches, that's what we want.
        if (key.equals(mArray[index<<1])) {
            return index;
        }

        // Search for a matching key after the index.
        int end;
        for (end = index + 1; end < N && mHashes[end] == hash; end++) {
            if (key.equals(mArray[end << 1])) return end;
        }

        // Search for a matching key before the index.
        for (int i = index - 1; i >= 0 && mHashes[i] == hash; i--) {
            if (key.equals(mArray[i << 1])) return i;
        }
        return ~end;
    }

    int indexOfNull() {
        final int N = mSize;

        // Important fast case: if nothing is in here, nothing to look for.
        if (N == 0) {
            return ~0;
        }

        int index = binarySearchHashes(mHashes, N, 0);

        // If the hash code wasn't found, then we have no entry for this key.
        if (index < 0) {
            return index;
        }

        // If the key at the returned index matches, that's what we want.
        if (null == mArray[index<<1]) {
            return index;
        }

        // Search for a matching key after the index.
        int end;
        for (end = index + 1; end < N && mHashes[end] == 0; end++) {
            if (null == mArray[end << 1]) return end;
        }

        // Search for a matching key before the index.
        for (int i = index - 1; i >= 0 && mHashes[i] == 0; i--) {
            if (null == mArray[i << 1]) return i;
        }

        return ~end;
    }

    @SuppressWarnings("ArrayToString")
    private void allocArrays(final int size) {
        if (size == (BASE_SIZE*2)) {
            synchronized (SimpleArrayMap.class) {
                if (mTwiceBaseCache != null) {
                    final Object[] array = mTwiceBaseCache;
                    mArray = array;
                    mTwiceBaseCache = (Object[])array[0];
                    mHashes = (int[])array[1];
                    array[0] = array[1] = null;
                    mTwiceBaseCacheSize--;
                    if (DEBUG) System.out.println(TAG + " Retrieving 2x cache " + mHashes
                            + " now have " + mTwiceBaseCacheSize + " entries");
                    return;
                }
            }
        } else if (size == BASE_SIZE) {
            synchronized (SimpleArrayMap.class) {
                if (mBaseCache != null) {
                    final Object[] array = mBaseCache;
                    mArray = array;
                    mBaseCache = (Object[])array[0];
                    mHashes = (int[])array[1];
                    array[0] = array[1] = null;
                    mBaseCacheSize--;
                    if (DEBUG) System.out.println(TAG + " Retrieving 1x cache " + mHashes
                            + " now have " + mBaseCacheSize + " entries");
                    return;
                }
            }
        }

        mHashes = new int[size];
        mArray = new Object[size<<1];
    }

    @SuppressWarnings("ArrayToString")
    private static void freeArrays(final int[] hashes, final Object[] array, final int size) {
        if (hashes.length == (BASE_SIZE*2)) {
            synchronized (SimpleArrayMap.class) {
                if (mTwiceBaseCacheSize < CACHE_SIZE) {
                    array[0] = mTwiceBaseCache;
                    array[1] = hashes;
                    for (int i=(size<<1)-1; i>=2; i--) {
                        array[i] = null;
                    }
                    mTwiceBaseCache = array;
                    mTwiceBaseCacheSize++;
                    if (DEBUG) System.out.println(TAG + " Storing 2x cache " + array
                            + " now have " + mTwiceBaseCacheSize + " entries");
                }
            }
        } else if (hashes.length == BASE_SIZE) {
            synchronized (SimpleArrayMap.class) {
                if (mBaseCacheSize < CACHE_SIZE) {
                    array[0] = mBaseCache;
                    array[1] = hashes;
                    for (int i=(size<<1)-1; i>=2; i--) {
                        array[i] = null;
                    }
                    mBaseCache = array;
                    mBaseCacheSize++;
                    if (DEBUG) System.out.println(TAG + " Storing 1x cache " + array
                            + " now have " + mBaseCacheSize + " entries");
                }
            }
        }
    }

    public SimpleArrayMap() {
        mHashes = ContainerHelpers.EMPTY_INTS;
        mArray = ContainerHelpers.EMPTY_OBJECTS;
        mSize = 0;
    }

    @SuppressWarnings("NullAway") // allocArrays initializes mHashes and mArray.
    public SimpleArrayMap(int capacity) {
        if (capacity == 0) {
            mHashes = ContainerHelpers.EMPTY_INTS;
            mArray = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            allocArrays(capacity);
        }
        mSize = 0;
    }

    public SimpleArrayMap(SimpleArrayMap<K, V> map) {
        this();
        if (map != null) {
            putAll(map);
        }
    }

    public void clear() {
        if (mSize > 0) {
            final int[] ohashes = mHashes;
            final Object[] oarray = mArray;
            final int osize = mSize;
            mHashes = ContainerHelpers.EMPTY_INTS;
            mArray = ContainerHelpers.EMPTY_OBJECTS;
            mSize = 0;
            freeArrays(ohashes, oarray, osize);
        }
        if (CONCURRENT_MODIFICATION_EXCEPTIONS && mSize > 0) {
            throw new ConcurrentModificationException();
        }
    }

    public void ensureCapacity(int minimumCapacity) {
        final int osize = mSize;
        if (mHashes.length < minimumCapacity) {
            final int[] ohashes = mHashes;
            final Object[] oarray = mArray;
            allocArrays(minimumCapacity);
            if (mSize > 0) {
                System.arraycopy(ohashes, 0, mHashes, 0, osize);
                System.arraycopy(oarray, 0, mArray, 0, osize<<1);
            }
            freeArrays(ohashes, oarray, osize);
        }
        if (CONCURRENT_MODIFICATION_EXCEPTIONS && mSize != osize) {
            throw new ConcurrentModificationException();
        }
    }

    public boolean containsKey(@Nullable Object key) {
        return indexOfKey(key) >= 0;
    }

    public int indexOfKey(@Nullable Object key) {
        return key == null ? indexOfNull() : indexOf(key, key.hashCode());
    }

    int indexOfValue(Object value) {
        final int N = mSize*2;
        final Object[] array = mArray;
        if (value == null) {
            for (int i=1; i<N; i+=2) {
                if (array[i] == null) {
                    return i>>1;
                }
            }
        } else {
            for (int i=1; i<N; i+=2) {
                if (value.equals(array[i])) {
                    return i>>1;
                }
            }
        }
        return -1;
    }

    public boolean containsValue(Object value) {
        return indexOfValue(value) >= 0;
    }

    @Nullable
    @SuppressWarnings("NullAway") // See inline comment.
    public V get(Object key) {
        return getOrDefault(key, null);
    }

    public V getOrDefault(Object key, V defaultValue) {
        final int index = indexOfKey(key);
        return index >= 0 ? (V) mArray[(index << 1) + 1] : defaultValue;
    }

    public K keyAt(int index) {
        return (K)mArray[index << 1];
    }

    public V valueAt(int index) {
        return (V)mArray[(index << 1) + 1];
    }

    public V setValueAt(int index, V value) {
        index = (index << 1) + 1;
        V old = (V)mArray[index];
        mArray[index] = value;
        return old;
    }

    public boolean isEmpty() {
        return mSize <= 0;
    }

    @Nullable
    public V put(K key, V value) {
        final int osize = mSize;
        final int hash;
        int index;
        if (key == null) {
            hash = 0;
            index = indexOfNull();
        } else {
            hash = key.hashCode();
            index = indexOf(key, hash);
        }
        if (index >= 0) {
            index = (index<<1) + 1;
            final V old = (V)mArray[index];
            mArray[index] = value;
            return old;
        }

        index = ~index;
        if (osize >= mHashes.length) {
            final int n = osize >= (BASE_SIZE*2) ? (osize+(osize>>1))
                    : (osize >= BASE_SIZE ? (BASE_SIZE*2) : BASE_SIZE);

            if (DEBUG) System.out.println(TAG + " put: grow from " + mHashes.length + " to " + n);

            final int[] ohashes = mHashes;
            final Object[] oarray = mArray;
            allocArrays(n);

            if (CONCURRENT_MODIFICATION_EXCEPTIONS && osize != mSize) {
                throw new ConcurrentModificationException();
            }

            if (mHashes.length > 0) {
                if (DEBUG) System.out.println(TAG + " put: copy 0-" + osize + " to 0");
                System.arraycopy(ohashes, 0, mHashes, 0, ohashes.length);
                System.arraycopy(oarray, 0, mArray, 0, oarray.length);
            }

            freeArrays(ohashes, oarray, osize);
        }

        if (index < osize) {
            if (DEBUG) System.out.println(TAG + " put: move " + index + "-" + (osize-index)
                    + " to " + (index+1));
            System.arraycopy(mHashes, index, mHashes, index + 1, osize - index);
            System.arraycopy(mArray, index << 1, mArray, (index + 1) << 1, (mSize - index) << 1);
        }

        if (CONCURRENT_MODIFICATION_EXCEPTIONS) {
            if (osize != mSize || index >= mHashes.length) {
                throw new ConcurrentModificationException();
            }
        }

        mHashes[index] = hash;
        mArray[index<<1] = key;
        mArray[(index<<1)+1] = value;
        mSize++;
        return null;
    }

    public void putAll(@NonNull SimpleArrayMap<? extends K, ? extends V> array) {
        final int N = array.mSize;
        ensureCapacity(mSize + N);
        if (mSize == 0) {
            if (N > 0) {
                System.arraycopy(array.mHashes, 0, mHashes, 0, N);
                System.arraycopy(array.mArray, 0, mArray, 0, N<<1);
                mSize = N;
            }
        } else {
            for (int i=0; i<N; i++) {
                put(array.keyAt(i), array.valueAt(i));
            }
        }
    }

    @Nullable
    public V putIfAbsent(K key, V value) {
        V mapValue = get(key);
        if (mapValue == null) {
            mapValue = put(key, value);
        }
        return mapValue;
    }

    @Nullable
    public V remove(Object key) {
        final int index = indexOfKey(key);
        if (index >= 0) {
            return removeAt(index);
        }

        return null;
    }

    public boolean remove(Object key, Object value) {
        int index = indexOfKey(key);
        if (index >= 0) {
            V mapValue = valueAt(index);
            if (value == mapValue || (value != null && value.equals(mapValue))) {
                removeAt(index);
                return true;
            }
        }
        return false;
    }

    public V removeAt(int index) {
        final Object old = mArray[(index << 1) + 1];
        final int osize = mSize;
        final int nsize;
        if (osize <= 1) {
            // Now empty.
            if (DEBUG) System.out.println(TAG + " remove: shrink from " + mHashes.length + " to 0");
            freeArrays(mHashes, mArray, osize);
            mHashes = ContainerHelpers.EMPTY_INTS;
            mArray = ContainerHelpers.EMPTY_OBJECTS;
            nsize = 0;
        } else {
            nsize = osize - 1;
            if (mHashes.length > (BASE_SIZE*2) && mSize < mHashes.length/3) {
                final int n = osize > (BASE_SIZE*2) ? (osize + (osize>>1)) : (BASE_SIZE*2);

                if (DEBUG) System.out.println(TAG + " remove: shrink from " + mHashes.length + " to " + n);

                final int[] ohashes = mHashes;
                final Object[] oarray = mArray;
                allocArrays(n);

                if (CONCURRENT_MODIFICATION_EXCEPTIONS && osize != mSize) {
                    throw new ConcurrentModificationException();
                }

                if (index > 0) {
                    if (DEBUG) System.out.println(TAG + " remove: copy from 0-" + index + " to 0");
                    System.arraycopy(ohashes, 0, mHashes, 0, index);
                    System.arraycopy(oarray, 0, mArray, 0, index << 1);
                }
                if (index < nsize) {
                    if (DEBUG) System.out.println(TAG + " remove: copy from " + (index+1) + "-" + nsize
                            + " to " + index);
                    System.arraycopy(ohashes, index + 1, mHashes, index, nsize - index);
                    System.arraycopy(oarray, (index + 1) << 1, mArray, index << 1,
                            (nsize - index) << 1);
                }
            } else {
                if (index < nsize) {
                    if (DEBUG) System.out.println(TAG + " remove: move " + (index+1) + "-" + nsize
                            + " to " + index);
                    System.arraycopy(mHashes, index + 1, mHashes, index, nsize - index);
                    System.arraycopy(mArray, (index + 1) << 1, mArray, index << 1,
                            (nsize - index) << 1);
                }
                mArray[nsize << 1] = null;
                mArray[(nsize << 1) + 1] = null;
            }
        }
        if (CONCURRENT_MODIFICATION_EXCEPTIONS && osize != mSize) {
            throw new ConcurrentModificationException();
        }
        mSize = nsize;
        return (V)old;
    }

    @Nullable
    public V replace(K key, V value) {
        int index = indexOfKey(key);
        if (index >= 0) {
            return setValueAt(index, value);
        }
        return null;
    }

    public boolean replace(K key, V oldValue, V newValue) {
        int index = indexOfKey(key);
        if (index >= 0) {
            V mapValue = valueAt(index);
            if (mapValue == oldValue || (oldValue != null && oldValue.equals(mapValue))) {
                setValueAt(index, newValue);
                return true;
            }
        }
        return false;
    }

    public int size() {
        return mSize;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof SimpleArrayMap) {
            SimpleArrayMap<?, ?> map = (SimpleArrayMap<?, ?>) object;
            if (size() != map.size()) {
                return false;
            }

            try {
                for (int i=0; i<mSize; i++) {
                    K key = keyAt(i);
                    V mine = valueAt(i);
                    Object theirs = map.get(key);
                    if (mine == null) {
                        if (theirs != null || !map.containsKey(key)) {
                            return false;
                        }
                    } else if (!mine.equals(theirs)) {
                        return false;
                    }
                }
            } catch (NullPointerException ignored) {
                return false;
            } catch (ClassCastException ignored) {
                return false;
            }
            return true;
        } else if (object instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) object;
            if (size() != map.size()) {
                return false;
            }

            try {
                for (int i=0; i<mSize; i++) {
                    K key = keyAt(i);
                    V mine = valueAt(i);
                    Object theirs = map.get(key);
                    if (mine == null) {
                        if (theirs != null || !map.containsKey(key)) {
                            return false;
                        }
                    } else if (!mine.equals(theirs)) {
                        return false;
                    }
                }
            } catch (NullPointerException ignored) {
                return false;
            } catch (ClassCastException ignored) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int[] hashes = mHashes;
        final Object[] array = mArray;
        int result = 0;
        for (int i = 0, v = 1, s = mSize; i < s; i++, v+=2) {
            Object value = array[v];
            result += hashes[i] ^ (value == null ? 0 : value.hashCode());
        }
        return result;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{}";
        }

        StringBuilder buffer = new StringBuilder(mSize * 28);
        buffer.append('{');
        for (int i=0; i<mSize; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            Object key = keyAt(i);
            if (key != this) {
                buffer.append(key);
            } else {
                buffer.append("(this Map)");
            }
            buffer.append('=');
            Object value = valueAt(i);
            if (value != this) {
                buffer.append(value);
            } else {
                buffer.append("(this Map)");
            }
        }
        buffer.append('}');
        return buffer.toString();
    }
}
