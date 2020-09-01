import java.util.*;

public class Map {
	private ArrayList<HashNode> BucketArray;
	private static long BucketCapacity;
	private int BucketSize;

	public Map() {
		BucketArray = new ArrayList<HashNode>();
		BucketCapacity = 49157;
		BucketSize = 0;

		for (int i = 0; i < BucketCapacity; i++)
			BucketArray.add(null);
	}

	public class HashNode {
		private long key;
		private Car plateCar;

		private HashNode next;

		public HashNode(String p) {
			next = null;
			key = hashKey(p);
			plateCar.setPlate(p);
		}

		public long ascii(String p) {
			int size = p.length();
			long sum = 0;
			if (size < 6 || size > 12)
				return -1;
			else {
				for (int i = 0; i <= size; i++) {
					sum = (long) (sum + p.charAt(i) * Math.pow(11, i));
				}

			}
			return sum;
		}

		public long hashKey(String p) {
			long key1 = ascii(p);
			key1 = key1 % BucketCapacity;
			return key1;
		}

		private void bucketCheck() {
			double loadFactor = (double) BucketSize / BucketCapacity;
			long[] primeNum = { 49157, 98317, 196613, 393241 };

			if (loadFactor > 0.75) {
				for (int i = 0; i < 4; i++) {
					if (BucketCapacity == primeNum[i]) {
						BucketCapacity = primeNum[i + 1];
						break;
					}
				}
				ArrayList<HashNode> temp = BucketArray;
				BucketArray = new ArrayList<HashNode>((int) BucketCapacity);
				for (int j = 0; j < BucketCapacity; j++)
					BucketArray.add(null);
				for (HashNode headNode : temp) {
					while (headNode != null) {
						add(headNode.plateCar);
						headNode = headNode.next;
					}
				}
			}
		}

		public boolean add(Car c) {

			long KEY = hashKey(c.getPlate());
			HashNode head = BucketArray.get((int) KEY);

			// Checking if the plate is already present
			while (head != null) {
				if (head.plateCar.getPlate().equals(c.getPlate())) {
					head.plateCar.setHistory(c.getPlate());
					return false;
				}
				head = head.next;
			}
			// Adding elements
			BucketSize++;
			head = BucketArray.get((int) KEY);
			HashNode t = new HashNode(c.getPlate());
			t.next = head;
			BucketArray.set((int) KEY, t);
			bucketCheck();
			return true;

		}

		public boolean remove(String p) {
			long index = hashKey(p);
			HashNode head = BucketArray.get((int) index);
			HashNode prev = null;
			while (head != null) {
				if (head.plateCar.getPlate().equals(p)) {
					break;
				}
				prev = head;
				head = head.next;
			}

			if (head == null)
				return false;

			if (prev != null)
				prev.next = head.next;
			else
				BucketArray.set((int) index, head.next);

			BucketSize--;

			return true;

		}

		private HashNode find(String p) {

			long index = hashKey(p);
			HashNode head = BucketArray.get((int) index);

			while (head != null) {
				if (head.plateCar.getPlate().equals(p)) {
					return head;
				}

				head = head.next;
			}

			return null;

		}

		public Car getKeys(String p) {
			if (find(p) == null) {
				return null;
			} else {
				return find(p).plateCar;
			}
		}

		public String nextKey(String p) {
			return find(p).next.plateCar.getPlate();
		}

		public String prevKey(String p) {
			long index = hashKey(p);
			HashNode head = BucketArray.get((int) index);
			HashNode prev = null;
			while (head != null) {
				if (head.plateCar.getPlate().equals(p)) {
					break;
				}
				prev = head;
				head = head.next;

			}
			if (head == null) {
				return null;
			} else
				return prev.plateCar.getPlate();

		}

		public ArrayList<String> previousCars(String p) {

			if (find(p) == null)
				return null;

			else {
				return find(p).plateCar.getReverseChronological();
			}
		}
		
		

	}

}
