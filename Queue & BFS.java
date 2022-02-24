// 346
class MovingAverage {
    Queue<Integer> q;
    int capacity;
    int currSum;

    /** Initialize your data structure here. */
    public MovingAverage(int size) {
        q = new LinkedList<Integer>();
        capacity = size;
        currSum = 0;
    }
    
    public double next(int val) {
        currSum += val;
        q.offer(val);
        
        if (q.size() > capacity ) {
            currSum -= q.poll();
        }
       
        return currSum*1.0 / q.size() ;
    }
}

// 102
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> temp = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                temp.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            res.add(temp);
        }
        return res;
    }
}

// 127
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) {
            return 0;
        }
        
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> set = new HashSet<>(wordList);
        
        int res = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String temp = queue.poll();
                for (int j = 0; j < temp.length(); j++) {
                    char[] arr = temp.toCharArray();
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        arr[j] = ch;
                        String tempStr = new String(arr);
                        if (tempStr.equals(endWord)) {
                            return res + 1;
                        }
                        if (set.contains(tempStr)) {
                            queue.offer(tempStr);
                            set.remove(tempStr);  
                        }
                    }                    
                }
            }
            res++;
        }
        
        return 0;
    }
}

// 339
public class Solution {
    public int depthSum(List<NestedInteger> nestedList) {
        if (nestedList == null) {
            return 0;
        }

        int sum = 0;
        int depth = 0;

        Queue<NestedInteger> queue = new LinkedList<>(nestedList);
        
        while (!queue.isEmpty()) {
            depth++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                NestedInteger ni = queue.poll();
                if (ni.isInteger()) {
                    sum += ni.getInteger() * depth;
                } else {
                    queue.addAll(ni.getList());
                }
            }
        }

        return sum;
    }
}

// 364
public class Solution {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        Queue<NestedInteger> q = new LinkedList(nestedList);
        int sum = 0;
        int prevSum = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                NestedInteger ni = q.poll();
                if (ni.isInteger()) {
                    prevSum += ni.getInteger();
                } else {
                    q.addAll(ni.getList());
                }
            }
            sum += prevSum;
        }
        return sum;
    }
}

// 207
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if (prerequisites == null || prerequisites.length == 0 || prerequisites[0].length == 0) {
            return true;
        }
        
        Map<Integer, Integer> indegree = new HashMap<>();
        Map<Integer, List<Integer>> relations = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            indegree.put(i, 0);
            relations.put(i, new ArrayList<Integer>());
        }
        
        for (int[] pair : prerequisites) {
            int course = pair[0];
            int preCourse = pair[1];
            relations.get(preCourse).add(course);
            indegree.put(course, indegree.get(course) + 1);
        }
        
        int count = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int course : indegree.keySet()) {
            if (indegree.get(course) == 0) {
                queue.offer(course);
            }
        }
        
        while (!queue.isEmpty()) {
            int course = queue.poll();
            count++;
            for (int next : relations.get(course)) {
                int degree = indegree.get(next);
                degree--;
                indegree.put(next, degree);
                if (degree == 0) {
                    queue.offer(next);
                }
            }
        }
        
        return count == numCourses;
    }
}

// 269
class Solution {
    public String alienOrder(String[] words) {
        Map<Character, Integer> indegree = new HashMap<>();
        Map<Character, List<Character>> relations = new HashMap<>();
        
        for (String word : words) {
            for (char ch : word.toCharArray()) {
                indegree.put(ch, 0);
            }
        }
        
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            int len = Math.min(word1.length(), word2.length());
            for (int j = 0; j < len; j++) {
                char ch1 = word1.charAt(j);
                char ch2 = word2.charAt(j);
                if (ch1 != ch2) {
                    List<Character> list = relations.getOrDefault(ch1, new ArrayList());
                    if (!list.contains(ch2)) {
                        list.add(ch2);
                        relations.put(ch1, list);
                        indegree.put(ch2, indegree.get(ch2) + 1);
                    }
                    break;
                }
                if (j == len - 1 && word2.length() < word1.length()) {
                    return "";
                }
            }
        }
        
        StringBuilder sb = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        
        for (char ch : indegree.keySet()) {
            if (indegree.get(ch) == 0) {
                queue.offer(ch);
            }
        }
        if (queue.isEmpty()) {
            return "";
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                char ch = queue.poll();
                sb.append(ch);
                List<Character> relation = relations.get(ch);
                if (relation == null) {
                    continue;
                }
                for (char tempChar : relation) {
                    indegree.put(tempChar, indegree.get(tempChar) - 1);
                    if (indegree.get(tempChar) == 0) {
                        queue.offer(tempChar);
                    }
                }
            }
        }

        if (sb.length() != indegree.size()) {
            return "";
        }
        
        return sb.toString();
    }
}
