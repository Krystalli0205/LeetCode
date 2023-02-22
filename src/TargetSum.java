import java.util.HashMap;

// leetcode 494题
public class TargetSum {

	public static int findTargetSumWays1(int[] arr, int s) {
		return process1(arr, 0, s);
	}

	// 可以自由使用arr[index....]所有的数字！
	// 搞出rest这个数，方法数是多少？返回
	// index == 7 rest = 13
	// map "7_13" 256
	public static int process1(int[] arr, int index, int rest) {
		if (index == arr.length) { // 没数了！
			return rest == 0 ? 1 : 0;
		}
		// 还有数！arr[index] arr[index+1 ... ]
		return process1(arr, index + 1, rest - arr[index]) + process1(arr, index + 1, rest + arr[index]);
	}

	public static int findTargetSumWays2(int[] arr, int s) {
		return process2(arr, 0, s, new HashMap<>());
	}

	public static int process2(int[] arr, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> dp) {
		if (dp.containsKey(index) && dp.get(index).containsKey(rest)) {
			return dp.get(index).get(rest);
		}
		// 否则，没命中！
		int ans = 0;
		if (index == arr.length) {
			ans = rest == 0 ? 1 : 0;
		} else {
			ans = process2(arr, index + 1, rest - arr[index], dp) + process2(arr, index + 1, rest + arr[index], dp);
		}
		if (!dp.containsKey(index)) {
			dp.put(index, new HashMap<>());
		}
		dp.get(index).put(rest, ans);
		return ans;
	}

	
	// 二维动态规划的空间压缩技巧
	public static int findTargetSumWays(int[] arr, int target) {
		int sum = 0;
		for (int n : arr) {
			sum += n;
		}
		return sum < target || ((target & 1) ^ (sum & 1)) != 0 ? 0 : subset2(arr, (target + sum) >> 1);
	}

	// 求非负数组nums有多少个子集，累加和是s
	// 二维动态规划
	// 不用空间压缩
	public static int subset1(int[] nums, int s) {
		if (s < 0) {
			return 0;
		}
		int n = nums.length;
		// dp[i][j] : nums前缀长度为i的所有子集，有多少累加和是j？
		int[][] dp = new int[n + 1][s + 1];
		// nums前缀长度为0的所有子集，有多少累加和是0？一个：空集
		dp[0][0] = 1;
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= s; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j - nums[i - 1] >= 0) {
					dp[i][j] += dp[i - 1][j - nums[i - 1]];
				}
			}
		}
		return dp[n][s];
	}

	// 求非负数组nums有多少个子集，累加和是s
	// 二维动态规划
	// 用空间压缩

	public static int subset2(int[] nums, int s) {
		if (s < 0) {
			return 0;
		}
		int[] dp = new int[s + 1];
		dp[0] = 1;
		for (int n : nums) {
			for (int i = s; i >= n; i--) {
				dp[i] += dp[i - n];
			}
		}
		return dp[s];
	}

}