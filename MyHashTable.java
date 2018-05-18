import java.util.ArrayList;

public class MyHashTable<K, V> {
   
   private class Node {
      K key;
      V value;
      int offset;
      
      
      private Node(K key, V value) {
         this.key = key;
         this.value = value;
         offset = 0;
      }
      private void setOffset(int offset) {
         this.offset = offset;
      }
      public int getOffset() {
    	  return this.offset;
      }
      
      @Override
      public String toString() {
         return (key + "=" + value);
      }
      public K getKey() {
         return key;
      }
      public V getValue() {
         return value;
      }
      
   }
   
   int capacity;
   ArrayList<Node> buckets = new ArrayList<Node>();
   
   public MyHashTable (int capacity) {
      this.capacity = capacity;
      buckets.ensureCapacity(capacity);
      
      for(int i = 0; i < capacity; i++) {
         buckets.add(null);
      }
   
   }
   
   public void put(K searchKey, V newValue) {
      Node element = new Node(searchKey, newValue);
      int index = hash(searchKey);
      
      if(buckets.get(index) == null) {
         buckets.set(index, element);
      } else {
         int i = index + 1;
         //linear probing
         int probe = 1;
         while(i != index) {
            if(buckets.get(i) == null) {
               element.setOffset(probe);
               buckets.set(i, element);
               break;
            }
            probe++;
            if(i == capacity) {
               i = 0;
            }
            i++;
         }
         
      
      }
      
   }
   public V get(K key) {
      int hash = hash(key) % capacity;
      int originalhash = hash;
      boolean notFound = false;
      
      while((buckets.get(hash) != null && !buckets.get(hash).getKey().equals(key))) {
         hash = (hash + 1) % capacity;

         if(hash == originalhash) {
            notFound = true;
            break;
         }
      }
      if(notFound || buckets.get(hash) == null) {
         return null;
      }  else {
         return buckets.get(hash).getValue();
      }
   
   }
   public boolean containsKey(K searchKey) {
     // boolean result = false;
      int hash = hash(searchKey) % capacity;
      int originalhash = hash;
   //   System.out.println(originalhash);
      int probe = 0;
      while((buckets.get(hash) != null && buckets.get(hash).getKey() != searchKey) || buckets.get(hash) == null) {
         hash = (hash + 1) % capacity;
         probe++;
         if(hash == originalhash) {
            return false;
         }
      }
    //  System.out.println(hash);
      //System.out.println(buckets.get(hash));
      System.out.println(probe);
      return true;
   
   }
   
   private int hash(K key) {
      return Math.abs(key.hashCode()) % capacity;
   }
   @Override
   public String toString() {
      StringBuilder string = new StringBuilder();
      
      for(int i = 0; i < buckets.size(); i++) {
         if(buckets.get(i) != null) {
           string.append(buckets.get(i));
           string.append(", ");
         }
      }
      
      return string.toString();
   }
   
   public static void main(String[] args) {
      int capacity = 10;
      MyHashTable<Character, String> test = new MyHashTable<Character, String>(capacity);
      
      char key0 = 'a';
      char key1 = 'b';
      char key2 = 'c';
      char key3 = 'f';
      
      test.put(key0, "Hello");
      test.put(key1, "Matt");
      test.put(key2, "hey");
      test.put(key3, "tim");
      
      //System.out.println(test.get(key0).getOffset());
      System.out.println(test);
      
      System.out.println(test.get('p'));
      System.out.println(test.containsKey('k'));
      test.stats();
   }
   int getMaxOffset() {
	   int max = 0;
	   for (int i = 0; i < buckets.size(); i++) {
		   if (buckets.get(i).offset > max) {
			   max = buckets.get(i).offset;
		   }
	   }
	   return max;
   }
   void printHistogram() {
	   ArrayList<Integer> offsets = new ArrayList<Integer>();
	   for (int i = 0; i < buckets.size(); i++) {
		   int sum = 0;
		   for (int j = 0; j < buckets.size(); j++) {
			   System.out.println(buckets.get(j).getOffset());
			   //if (buckets.get(j).getOffset() == i) {
				   sum += j; 
				   { }
		   }
		   System.out.println(sum / i);
	   }
   }

   int getEntries() {
	   //iterate through each bucket
	   //if the bucket is not equal to null
	   //increment a sum.
	   //return the sum.
	   int sum = 0;
	   for (int i = 0; i < buckets.size(); i++) {
		   if (buckets.get(i) != null) {
			   sum++;
		   } 
	   }
	   return sum;
   }
   void stats() {
	   System.out.println("\n\nHash Table Stats");
	   System.out.println("=================");
	   System.out.println("Number of Entries: " + this.getEntries());
	   System.out.println("Number of Buckets: " + capacity);
	  // printHistogram();
	   System.out.println("Fill Percentage: " + (100.0*this.getEntries() 
	   					  / capacity) + "%");
	   //System.out.println("Max Linear Probe: " + getMaxOffset());
	   
	   
   }
}