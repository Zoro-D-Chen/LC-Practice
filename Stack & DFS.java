// 20
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
                continue;
            }
            if (stack.isEmpty()) {
                return false;
            }
            if (ch == ')' && stack.peek() != '(') {
                return false;
            }
            if (ch == ']' && stack.peek() != '[') {
                return false;
            }
            if (ch == '}' && stack.peek() != '{') {
                return false;
            }
            stack.pop();
        }
        return stack.isEmpty();
    }
}

// 1249
class Solution {
    public String minRemoveToMakeValid(String s) {
      StringBuilder sb = new StringBuilder(s);
      Stack<Integer> st = new Stack<>();
      for (int i = 0; i < sb.length(); ++i) {
        if (sb.charAt(i) == '(') st.add(i);
        if (sb.charAt(i) == ')') {
          if (!st.empty()) st.pop();
          else sb.setCharAt(i, '*');
        }
      }
      while (!st.empty())
        sb.setCharAt(st.pop(), '*');
      return sb.toString().replaceAll("\\*", "");
    }
}

// 224
public class Solution {
    public int calculate(String s) {
        int sign = 1;
        Stack<Integer> stack = new Stack<>();
        stack.push(sign);
        int sum = 0;
        int num = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                num = num * 10 + (ch - '0');
            } else if (ch == '+' || ch == '-') {
                sum += sign * num;
                sign = stack.peek() * ((ch == '+') ? 1 : -1);
                num = 0;
            } else if (ch == '(') {
                stack.push(sign);
            } else if (ch == ')') {
                stack.pop();
            }
        }
        sum += sign * num;
        
        return sum;
    }
}

// 394
class Solution {
    public String decodeString(String s) {
        Stack<Integer> intStack = new Stack<>();
        Stack<StringBuilder> strStack = new Stack<>();
        StringBuilder cur = new StringBuilder();
        int k = 0;
        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                k = k * 10 + ch - '0';
            } else if ( ch == '[') {
                intStack.push(k);
                strStack.push(cur);
                cur = new StringBuilder();
                k = 0;
            } else if (ch == ']') {
                StringBuilder tmp = cur;
                cur = strStack.pop();
                for (k = intStack.pop(); k > 0; --k) cur.append(tmp);
            } else cur.append(ch);
        }
        return cur.toString();
    }
}

// 200
class Solution {
    int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    
    public int numIslands(char[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    res++;
                    helper(grid, i , j);
                }
            }
        }
        return res;
    }
    
    private void helper(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        for (int[] dir : dirs) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            helper(grid, newI, newJ);
        }
    }
}

// 17
class Solution {
    public List<String> letterCombinations(String digits) {
        String[] mappings = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        List<String> list = new ArrayList<>();
        
        if (digits == null || digits.length() == 0) {
            return list;
        }
        
        helper(list, mappings, digits, 0, new StringBuilder());
        return list;
    }
    
    private void helper(List<String> list, String[] mappings, String digits, int index, StringBuilder sb) {
        if (index >= digits.length()) {
            list.add(new String(sb));
            return;
        }
        for (char ch : mappings[digits.charAt(index) - '0'].toCharArray()) {
            sb.append(ch);
            helper(list, mappings, digits, index + 1, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}

// 22
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        helper(res, sb, 0, 0, n);
        return res;
    }
    
    private void helper(List<String> res, StringBuilder sb, int left, int right, int n) {
        if (right == n) {
            res.add(sb.toString());
            return;
        }
        if (left < n) {
            sb.append('(');
            helper(res, sb, left + 1, right, n);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (right < left) {
            sb.append(')');
            helper(res, sb, left, right + 1, n);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}

// 46
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        helper(res, list, nums);
        return res;
    }
    
    private void helper(List<List<Integer>> res, List<Integer> list, int[] nums) {
        if (list.size() == nums.length) {
            res.add(new ArrayList(list));
            return;
        }
        for (int num : nums) {
            if (list.contains(num)) {
                continue;
            }
            list.add(num);
            helper(res, list, nums);
            list.remove(list.size() - 1);
        }
    }
}

// 39
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        Arrays.sort(candidates);
        helper(res, list, 0, target, candidates, 0);
        return res;
    }
    
    private void helper(List<List<Integer>> res, List<Integer> list, int sum, int target, int[] candidates, int index) {
        if (sum > target) {
            return;
        }
        if (sum == target) {
            res.add(new ArrayList(list));
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] <= target) {
                list.add(candidates[i]);
                helper(res, list, sum + candidates[i], target, candidates, i);
                list.remove(list.size() - 1);   
            }
        }
    }
}

// 79
class Solution {
    private int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    
    public boolean exist(char[][] board, String word) {
        int row = board.length;
        int col = board[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (helper(board, row, col, i, j, 0, word.toCharArray())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean helper(char[][] board, int row, int col, int x, int y, int index, char[] arr) {
        if (index == arr.length) {
            return true;
        }
        
        if (x < 0 || x >= row || y < 0 || y >= col || arr[index] != board[x][y]) {
            return false;
        }
        
        char temp = board[x][y];
        board[x][y] = '.';
        
        for (int[] dir : dirs) {
            int nextX = x + dir[0];
            int nextY = y + dir[1];
            if (helper(board, row, col, nextX, nextY, index + 1, arr)) {
                return true;
            }
        }
        
        board[x][y] = temp;
        
        return false;
    }
}

// 139
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        return wordBreakMemo(s, new HashSet<>(wordDict), 0, new Boolean[s.length()]);
    }

    private boolean wordBreakMemo(String s, Set<String> wordDict, int start, Boolean[] memo) {
        if (start == s.length()) {
            return true;
        }
        if (memo[start] != null) {
            return memo[start];
        }
        for (int end = start + 1; end <= s.length(); end++) {
            if (wordDict.contains(s.substring(start, end)) && wordBreakMemo(s, wordDict, end, memo)) {
                return memo[start] = true;
            }
        }
        return memo[start] = false;
    }
}

// 140
public class Solution {
    // Using DFS directly will lead to TLE, so I just used HashMap to save the previous results to prune duplicated branches
    public List<String> wordBreak(String s, List<String> wordDict) {
        return DFS(s, wordDict, new HashMap<String, List<String>>());
    }       

    // DFS function returns an array including all substrings derived from s.
    private List<String> DFS(String s, List<String> wordDict, HashMap<String, List<String>>map) {
        if (map.containsKey(s)) {
            return map.get(s);
        }
        List<String> res = new ArrayList<String>();  
        if (s.length() == 0) {
            res.add("");
            return res;
        }
        for (String word : wordDict) {
            if (s.startsWith(word)) { // startsWith，看有没有词在开头
                List<String> sublist = DFS(s.substring(word.length()), wordDict, map);
                for (String sub : sublist) {
                    String temp = (word + " " + sub);
                    res.add(temp.trim());         
                }          
            }
        }    
        map.put(s, res);
        return res;
    }
}

// 329
public class Solution {
    
    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        // 记忆化搜索，省去很大的空间啊
        int[][] cache = new int[matrix.length][matrix[0].length];
        int max = 1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int length = dfs(i, j, matrix, cache, Integer.MIN_VALUE);
                max = Math.max(length, max);
            }
        }
        return max;
    }
    private int dfs(int i, int j, int[][] matrix, int[][] cache, int pre) {
        // if out of bond OR current cell value not bigger than previous cell value.
        if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length || matrix[i][j] <= pre) {
            return 0;
        }
        // if calculated before, no need to do it again，记忆化
        if (cache[i][j] > 0) {
            return cache[i][j];
        }
        int cur = matrix[i][j];
        int tempMax = 1;  // 至少是1啊，自身长度啊
        for (int[] dir : dirs) {
            tempMax = Math.max(dfs(i + dir[0], j + dir[1], matrix, cache, cur) + 1, tempMax);
        }
        cache[i][j] = tempMax; // 四个方向更新完之后，记得保存以这个点出发的最长路径，下次就不用算了啊
        return tempMax;
    }
}
