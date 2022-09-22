public class ListNode<K, V> {
    private K key;
    private V value;
    private ListNode<K, V> next;
    private int hash;

    public ListNode(K key, V value, int hash, ListNode next) {
        this.key = key;
        this.value = value;
        this.hash = hash;
        this.next = next;
    }

    public boolean hasNext() {
        return next != null;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public ListNode<K, V> getNext() {
        return next;
    }
    public void setNext(ListNode next){
        this.next = next;
    }

    public int getHash() {
        return hash;
    }

    public V setValue(V value) {
        V prevValue = this.value;
        this.value = value;
        return prevValue;
    }
}
