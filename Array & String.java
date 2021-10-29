// 252
class Solution {
    public boolean canAttendMeetings(Interval[] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return true;
        }
        
        Arrays.sort(intervals, (i1, i2) -> (i1.start - i2.start));
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].start < intervals[i - 1].end) {
                return false;
            }
        }
        
        return true;
    }
}

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

// 125
class Solution {
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}

// 167
class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int temp = numbers[left] + numbers[right];
            if (temp == target) {
                return new int[] {left + 1, right + 1};
            } else if (temp < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[2];
    }
}

// 15
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length < 3) {
            return res;
        }
        
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            if (i != 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    List<Integer> list = new ArrayList<>(Arrays.asList(nums[i], nums[left], nums[right]));
                    res.add(list);
                }
                if (sum <= 0) {
                    while (left < nums.length - 1 && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    left++;
                } else {
                    while (right > 0 && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    right--;
                }
            }
        }
        
        return res;
    }
}

// 42
public class Solution {
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int sum = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            int leftHigh = height[left];
            int rightHigh = height[right];
            if (leftHigh < rightHigh) { 
                // 知道右边肯定有一个更高的，所以中间可以放心大胆加水，直到
                // 碰到一个比自己高的不能加，但是不用在乎这个和最右的谁更高
                while (left < right && height[left + 1] <= leftHigh) {
                    sum += leftHigh - height[left + 1];
                    left++;
                }
                left++;
            } else {
                while (left < right && height[right - 1] <= rightHigh) {
                    sum += rightHigh - height[right - 1];
                    right--;
                }
                right--;
            }
        }
        return sum;
    }
}

// 11
public class Solution {
    public int maxArea(int[] height) {
        int lo = 0, hi = height.length - 1, max = 0;
        
        while (lo < hi) {    
            int loMax = height[lo], hiMax = height[hi];     
            int candidate = (hi-lo) * (Math.min(loMax, hiMax));
            max = Math.max(candidate, max);

            if (height[lo] <= height[hi])
                while (lo < hi && height[lo] <= loMax) lo++; // 如果左边的柱子比右边的低，而且向右移一直没有遇到更高的，不可能在接下来的几个柱子里找到更大存水量
            else 
                while (hi > lo && height[hi] <= hiMax) hi--;
        }
        
        return max;
    }
}

// 724
class Solution {
    public int pivotIndex(int[] nums) {
        int sum = 0, leftsum = 0;
        for (int x: nums) sum += x;
        for (int i = 0; i < nums.length; ++i) {
            if (leftsum == sum - leftsum - nums[i]) return i;
            leftsum += nums[i];
        }
        return -1;   
    }
}

// 53
class Solution {
    public int maxSubArray(int[] nums) {
        int sum = 0;
        int minSum = 0;
        int res = Integer.MIN_VALUE;

        for (int num : nums) {
            sum += num;
            res = Math.max(res, sum - minSum);
            minSum = Math.min(minSum, sum);
        }
        
        return res;
    }
}

// 560
class Solution {
    public int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int sum = 0;
        int res = 0;
        for (int num : nums) {
            sum += num;
            int remain = sum - k;
            int countRemain = map.getOrDefault(remain, 0);
            int countSum = map.getOrDefault(sum, 0);
            map.put(sum, countSum + 1);
            res += countRemain;
        }
        return res;
    }
}

// 3
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int res = 0;
        int startIndex = 0;
        Map<Character, Integer> map = new HashMap<>();
        
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (map.containsKey(ch)) {
                int prevIndex = map.get(ch);
                startIndex = Math.max(startIndex, prevIndex + 1);
            }
            map.put(ch, i);
            res = Math.max(res, i - startIndex + 1);
        }
        
        return res;
    }
}

// 340
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int res = 0;
        Map<Character, Integer> map = new HashMap<>();
        int tempK = 0;
        int start = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (map.getOrDefault(ch, 0) == 0) {
                tempK++;
            }
            int count = map.getOrDefault(ch, 0);
            map.put(ch, count + 1);
            while (tempK > k) {
                char head = s.charAt(start);
                start++;
                int headCount = map.get(head);
                headCount--;
                map.put(head, headCount);
                if (headCount == 0) {
                    tempK--;
                }
            }
            res = Math.max(res, i - start + 1);
        }
        
        return res;
    }
}

// 239
public class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        int[] result = new int[nums.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<Integer>();
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[i] > deque.peekLast()) {
                deque.pollLast();
            }
            deque.offer(nums[i]);
            if (i >= k && deque.peekFirst() == nums[i - k]) {
                deque.pollFirst();
            }
            if (i >= k - 1) {
                result[i - k + 1] = deque.peekFirst();
            }
        }
        return result;
    }
}

// 76
class Solution {
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }
        
        int[] count = new int[256];
        for (int i = 0; i < t.length(); i++) {
            char ch = t.charAt(i);
            count[ch]++;
        }
        
        int overlap = 0;
        int left = 0;
        int right = 0;
        String res = "";
        
        while (right < s.length()) {
            char ch = s.charAt(right);
            if (count[ch] > 0) {
                overlap++;
            }
            while (overlap >= t.length()) {
                String sub = s.substring(left, right + 1);
                if (res.equals("") || (sub.length() < res.length())) {
                    res = sub;
                }
                char start = s.charAt(left);
                count[start]++;
                left++;
                if (count[start] > 0) {
                    overlap--;
                }
            }
            right++;
            count[ch]--;
        }
        
        return res;
    }
}

// 1
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int remain = target - nums[i];
            if (map.containsKey(remain)) {
                res[0] = map.get(remain);
                res[1] = i;
                break;
            }
            map.put(nums[i], i);
        }
        return res;
    }
}

// 953
class Solution {
  public boolean isAlienSorted(String[] words, String order) {
        int[] dict = new int[26];
        for (int i = 0; i < dict.length; i++) {
            int idx = order.charAt(i) - 'a';
            dict[idx] = i;
        }
        for (int i = 0; i < words.length -1; i++) {
            if(compare(words[i], words[i +1], dict) > 0) {
                return false;
            }
        }

        return true;
    }

    private int compare(String word1, String word2, int[] dict) {
        int L1 = word1.length();
        int L2 = word2.length();
        int min = Math.min(L1,L2);
        for (int i = 0; i < min; i++) {
            int c1 = word1.charAt(i) - 'a';
            int c2 = word2.charAt(i) - 'a';
            if(c1 != c2)
                return dict[c1] - dict[c2];
        }
        return L1 == min ? -1 : 1;
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