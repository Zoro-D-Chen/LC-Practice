// 121
class Solution {
    public int maxProfit(int[] prices) {
        int res = 0;
        int min = prices[0];
        for (int price : prices) {
            res = Math.max(res, price - min);
            min = Math.min(min, price);
        }
        return res;
    }
}

// 55
class Solution {
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        boolean[] result = new boolean[nums.length];
        result[0] = true;
        for (int i = 1; i < nums.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (result[j] == true && j + nums[j] >= i) {
                    result[i] = true;
                    break;
                }
            }
        }
        return result[result.length - 1];
    }
}

// 322
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] res = new int[amount + 1];
        res[0] = 0;
        for (int i = 1; i <= amount; i++) {
            res[i] = -1;
            for (int j = 0; j < coins.length; j++) {
                int remain = i - coins[j];
                if (remain >= 0 && res[remain] != -1) {
                    res[i] = (res[i] == -1) ? (res[remain] + 1) : Math.min(res[i], res[remain] + 1);
                }
            }
        }
        return res[amount];
    }
}

// 62
public class Solution {
    public int uniquePaths(int m, int n) {
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            result[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            result[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                result[i][j] = result[i - 1][j] + result[i][j - 1];
            }
        }
        return result[m - 1][n - 1];
    }
}

// 72
class Solution {
    public int minDistance(String word1, String word2) {
        int[][] res = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            res[i][0] = i;
        }
        for (int i = 0; i <= word2.length(); i++) {
            res[0][i] = i;
        }
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    res[i][j] = res[i - 1][j - 1];
                } else {
                    res[i][j] = Math.min(res[i - 1][j - 1], Math.min(res[i - 1][j], res[i][j - 1])) + 1;
                }
            }
        }
        return res[word1.length()][word2.length()];
    }
}


// 718
class Solution {
    public int findLength(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[][] dp = new int[m + 1][n + 1];
        int max = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        return max;        
    }
}

// 5
class Solution {
    public String longestPalindrome(String s) {
        int len = s.length();
        boolean[][] isPalindrome = new boolean[len][len];
        String res = s.substring(0, 1);
        
        for (int i = 0; i < s.length(); i++) {
            isPalindrome[i][i] = true;
        }
        
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                isPalindrome[i][i + 1] = true;
                if (res.length() < 2) {
                    res = s.substring(i, i + 2);   
                }
            }
        }
        
        for (int i = 3; i <= len; i++) {
            for (int j = 0; j <= len - i; j++) {
                if (s.charAt(j) == s.charAt(j + i - 1) && isPalindrome[j + 1][j + i - 2]) {
                    isPalindrome[j][j + i - 1] = true;
                    if (res.length() < i) {
                        res = s.substring(j, j + i);        
                    }
                }
            }
        }
        
        return res;
    }
}

// 174
public class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
    if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) return 0;

    int m = dungeon.length;
    int n = dungeon[0].length;

    int[][] health = new int[m][n];

    health[m - 1][n - 1] = Math.max(1 - dungeon[m - 1][n - 1], 1);

    for (int i = m - 2; i >= 0; i--) {            
        health[i][n - 1] = Math.max(health[i + 1][n - 1] - dungeon[i][n - 1], 1);
    }

    for (int j = n - 2; j >= 0; j--) {
        health[m - 1][j] = Math.max(health[m - 1][j + 1] - dungeon[m - 1][j], 1);
    }

    for (int i = m - 2; i >= 0; i--) {
        for (int j = n - 2; j >= 0; j--) {
            int down = Math.max(health[i + 1][j] - dungeon[i][j], 1);
            int right = Math.max(health[i][j + 1] - dungeon[i][j], 1);
            health[i][j] = Math.min(right, down);
        }
    }

    return health[0][0];
    }
}

// 1235
class Solution {
    // Sort jobs by the end time, and then do dymanic programming on it. 
    // Current profit sum with starting time t is the sum of maximum profit sum 
    // with end time less than t and current profit.
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int[][] combine = new int [startTime.length][3];
        for (int i=0; i<startTime.length; i++) {
            combine[i] = new int[] {startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(combine, (a,b)->a[1]-b[1]);
        
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int ans = 0;
        
        for (int[] curr: combine) {
            Integer prev = map.floorKey(curr[0]);
            int prevSum = prev==null?0:map.get(prev);
            ans = Math.max(ans, prevSum+curr[2]);
            map.put(curr[1], ans);
        }              
        return ans;        
    }
}