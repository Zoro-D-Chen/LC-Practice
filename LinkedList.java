// 2
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry != 0) {
            int num1 = (l1 == null) ? 0 : l1.val;
            int num2 = (l2 == null) ? 0 : l2.val;
            
            l1 = (l1 == null) ? null : l1.next;
            l2 = (l2 == null) ? null : l2.next;
            
            int sum = num1 + num2 + carry;
            head.next = new ListNode(sum % 10);
            head = head.next;
            carry = sum / 10;
        }
        return dummy.next;
    }
}

// 21
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                head.next = l1;
                l1 = l1.next;
            } else {
                head.next = l2;
                l2 = l2.next;
            }
            head = head.next;
        }
        if (l1 != null) {
            head.next = l1;
        }
        if (l2 != null) {
            head.next = l2;
        }
        return dummy.next;
    }
}

// 141
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            if (slow == fast) {
                return true;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }
}

// 206
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode dummy = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = dummy;
            dummy = head;
            head = temp;
        }
        return dummy;
    }
}

// 160
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        
        ListNode currA = headA;
        ListNode currB = headB;
        
        while (currA != null || currB != null) {
            if (currA == currB) {
                return currA;
            }
            currA = (currA == null) ? headB : currA.next;
            currB = (currB == null) ? headA : currB.next;
        }
        
        return null;
    }
}

// 142
public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head, slow = head;
        while(fast != null && fast.next != null){
        fast = fast.next.next;
        slow = slow.next;
        if (fast == slow){
        ListNode slow2 = head;
        while (slow != slow2){
            slow2 = slow2.next;
            slow = slow.next;
             }
            return slow;
            }
        }
        return null;
    }
}

// 143
class Solution {
    public void reorderList(ListNode head) {
        ListNode mid = findMid(head);
        ListNode nextHead = mid.next;
        mid.next = null;
        nextHead = reverse(nextHead);
        head = merge(head, nextHead);
    }
    
    private ListNode findMid(ListNode node) {
        ListNode slow = node;
        ListNode fast = node.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    private ListNode reverse(ListNode node) {
        ListNode dummy = null;
        while (node != null) {
            ListNode next = node.next;
            node.next = dummy;
            dummy = node;
            node = next;
        }
        return dummy;
    }
    
    private ListNode merge(ListNode node1, ListNode node2) {
        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        int index = 0;
        while (node1 != null && node2 != null) {
            if (index % 2 == 0) {
                head.next = node1;
                node1 = node1.next;
            } else {
                head.next = node2;
                node2 = node2.next;
            }
            index++;
            head = head.next;
        }
        if (node1 != null) {
            head.next = node1;
        }
        if (node2 != null) {
            head.next = node2;
        }       
        return dummy.next;
    }
}

// 138
class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        
        Map<Node, Node> map = new HashMap<>();
        Node node = head;
        while (node != null) {
            map.put(node, new Node(node.val));
            node = node.next;
        }
        
        node = head;
        while (node != null) {
            map.get(node).next = map.get(node.next);
            map.get(node).random = map.get(node.random);
            node = node.next;
        }
        
        return map.get(head);
    }
}

// 23
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        return merge(lists, 0, lists.length - 1);
    }
    
    private ListNode merge(ListNode[] lists, int start, int end) {
        if (start > end) {
            return null;
        }
        if (start == end) {
            return lists[start];
        }
        int mid = (start + end) / 2;
        ListNode left = merge(lists, start, mid);
        ListNode right = merge(lists, mid + 1, end);
        return mergeTwo(left, right);
    }
    
    private ListNode mergeTwo(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (head1 != null && head2 != null) {
            if (head1.val <= head2.val) {
                tail.next = head1;
                head1 = head1.next;
            } else {
                tail.next = head2;
                head2 = head2.next;
            }
            tail = tail.next;
        }
        if (head1 != null) {
            tail.next = head1;
        }
        if (head2 != null) {
            tail.next = head2;
        }        
        return dummy.next;
    }
}


// 146
class LRUCache {
    int capacity;
    Map<Integer, Node> map;
    Node head;
    Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        moveToTail(node);
        return node.val;
    }
    
    public void put(int key, int value) {
        if (get(key) != -1) {
            Node node = map.get(key);
            node.val = value;
            return;
        }
        if (map.size() == capacity) {
            map.remove(head.next.key);
            head.next = head.next.next;
            head.next.prev = head;
        }
        Node temp = new Node(key, value);
        map.put(key, temp);
        moveToTail(temp);
    }
    
    private void moveToTail(Node node) {        
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
    }
    
    class Node {
        public int key;
        public int val;
        public Node next;
        public Node prev;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}