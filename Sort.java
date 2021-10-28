// 56
class Solution {
    public int[][] merge(int[][] intervals) {
        List<int[]> list = new ArrayList<>();
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int[] temp = intervals[0];
        for (int[] interval : intervals) {
            if (interval[0] > temp[1]) {
                int[] arr = new int[2];
                arr[0] = temp[0];
                arr[1] = temp[1];
                list.add(arr);
                temp = interval;
            } else {
                temp[1] = Math.max(temp[1], interval[1]);
            }
        }
        list.add(temp);
        return list.toArray(new int[list.size()][]);
    }
}

// 253
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, (a, b) -> (a[0] - b[0]));
        PriorityQueue<int[]> queue = new PriorityQueue<>(
            (a, b) -> (a[1] - b[1])
        );
        for (int[] interval : intervals) {
            if (!queue.isEmpty() && interval[0] >= queue.peek()[1]) {
                int[] temp = queue.poll();
                interval[0] = temp[0];
                interval[1] = Math.max(interval[1], temp[1]);
            }
            queue.offer(interval);
        }
        return queue.size();        
    }
}

// 215
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int index = nums.length - k;
        return helper(nums, 0, nums.length - 1, index);
    }
    
    private int helper(int[] nums, int start, int end, int index) {
        if (start > end) {
            return -1;
        }
        
        int pivot = nums[end];
        int left = start;
        for (int i = start; i < end; i++) {
            if (nums[i] <= pivot) {
                swap(nums, left, i);
                left++;
            }
        }
        swap(nums, left, end);
        
        if (left == index) {
            return nums[left];
        } else if (left < index) {
            return helper(nums, left + 1, end, index);
        } else {
            return helper(nums, start, left - 1, index);
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;        
    }
}

// 295
class MedianFinder {
    // max queue size is always larger or equal to min queue.
    // min stores larger numbers, max stores smaller numbers.
    Queue<Integer> minFirst = new PriorityQueue();
    Queue<Integer> maxFirst = new PriorityQueue(Collections.reverseOrder());
    // Adds a number into the data structure.
    public void addNum(int num) {
        if (maxFirst.isEmpty() || num <= maxFirst.peek()) {
            maxFirst.offer(num);
        } else {
            minFirst.offer(num);
        }
        if (maxFirst.size() > minFirst.size() + 1) {
            minFirst.offer(maxFirst.poll());
        } 
        if (maxFirst.size() < minFirst.size()) {
            maxFirst.offer(minFirst.poll());
        }
    }

    // Returns the median of current data stream
    public double findMedian() {
        if (maxFirst.size() == minFirst.size()) {
            return (maxFirst.peek() + minFirst.peek()) /  2.0;
        }
        else return maxFirst.peek();
    }
}

// 347
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        List<Integer>[] bucket = new ArrayList[nums.length + 1];
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int n : nums) {
            int frequency = frequencyMap.getOrDefault(n, 0);
            frequencyMap.put(n, frequency + 1);
        }

        for (int key : frequencyMap.keySet()) {
            int frequency = frequencyMap.get(key);
            if (bucket[frequency] == null) {
                bucket[frequency] = new ArrayList<>();
            }
            bucket[frequency].add(key);
        }

        int[] res = new int[k];
        int index = 0;

        for (int pos = bucket.length - 1; pos >= 0 && index < k; pos--) {
            if (bucket[pos] != null) {
                for (int num : bucket[pos]) {
                    res[index++] = num;   
                }
            }
        }
        return res;  
    }
}