public class HashDict<K, V> {
    private ListNode<K, V>[] buckets;
    private double loadFactor;
    private int bucketsCount;
    private int size = 0;
    private int threshold;
    private final static double DEFAULT_LOADFACTOR = 0.75;
    private final static int DEFAULT_BUCKETS_COUNT = 16;

    public HashDict() {
        this(DEFAULT_LOADFACTOR, DEFAULT_BUCKETS_COUNT);

    }

    public HashDict(double loadFactor, int bucketsCount) {
        this.loadFactor = loadFactor;
        this.bucketsCount = bucketsCount;
        threshold = calcThreshold();
        buckets = new ListNode[bucketsCount];
    }

    public int getThreshold() {
        return threshold;
    }

    public HashDict(double initialLoadFactor) {
        this(initialLoadFactor, DEFAULT_BUCKETS_COUNT);
    }

    public HashDict(int initialBucketsCount) {
        this(DEFAULT_LOADFACTOR, calcBucketsCount(initialBucketsCount));
    }

    private static int calcBucketsCount(int bucketsCount) {
        int count = 1;
        while (count < bucketsCount) {
            count *= 2;
        }
        return count;
    }

    private int getBucketNumber(K key) {
        return key.hashCode() % bucketsCount;
    }

    public int getSize() {
        return size;
    }

    public V put(K key, V value) {
        ListNode<K, V> node = findNodeByKey(key);

        if (node != null) {
            if (key.hashCode() == node.getHash() && key.equals(node.getKey())) {
                return node.setValue(value);
            }
            node.setNext(new ListNode<>(key, value, key.hashCode(), null));
            return null;
        }
        buckets[getBucketNumber(key)] = new ListNode<>(key, value, key.hashCode(), null);
        size++;
        if (size > threshold) {
            resizeHashDict();
        }
        return null;
    }

    public V get(K key) {
        ListNode<K, V> node = findNodeByKey(key);
        if (node == null) {
            return null;
        }
        return node.getValue();
    }

    public V remove(K key) {
        ListNode<K, V> node = findNodeByKey(key);
        if (node != null) {
            ListNode<K, V> previousNode = findPreviousNode(node);
            if (previousNode == null) {
                buckets[getBucketNumber(key)] = null;
            } else {
                previousNode.setNext(null);
            }
            size--;
            return node.getValue();
        }
        return null;
    }

    private ListNode<K, V> findNodeByKey(K key) {
        int bucketNumber = getBucketNumber(key);
        int hash = key.hashCode();
        ListNode<K, V> current = buckets[bucketNumber];
        if (current != null) {
            do {
                if (hash == current.getHash() && key.equals(current.getKey())) {
                    return current;
                }
                current = current.getNext();
            }
            while (current != null);
        }
        return null;
    }

    private ListNode<K, V> findPreviousNode(ListNode<K, V> node) {
        ListNode<K, V> current = buckets[getBucketNumber(node.getKey())];
        while (current != null) {
            if (current.getNext() != null) {
                if (current.getNext().equals(node)) {
                    return current;
                }
            }
            current = current.getNext();
        }
        return null;
    }

    private void resizeHashDict(){
        HashDict<K, V> tempDict = new HashDict(loadFactor, bucketsCount * 2);
        for (int i = 0; i < bucketsCount; i++) {
            ListNode<K, V> node = buckets[i];
            while (node != null) {
                tempDict.put(node.getKey(), node.getValue());
                node = node.getNext();
            }
        }
        bucketsCount *= 2;
        threshold = calcThreshold();
        buckets = tempDict.buckets;
    }
    private int calcThreshold(){
        return (int) (loadFactor * bucketsCount);
    }


}
