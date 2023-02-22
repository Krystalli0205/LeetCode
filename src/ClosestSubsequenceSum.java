// 本题测试链接 : https://leetcode.com/problems/closest-subsequence-sum/

import java.util.Arrays;


// 需要用到分治，因为数组长度不大，而值很大，用动态规划的话，表会爆
public class ClosestSubsequenceSum {

	// 数组左半部分的所有可能累加和都放在这里
	public static int[] l = new int[1 << 20];

	// 数组右半部分的所有可能累加和都放在这里
	public static int[] r = new int[1 << 20];

	public static int minAbsDifference(int[] nums, int goal) {
		if (nums == null || nums.length == 0) {
			return goal;
		}
		// 收集数组左半部分，所有可能的累加和
		// 并且返回一共收集了几个数，就是le
		int le = process(nums, 0, nums.length >> 1, 0, 0, l);
		// 收集数组右半部分，所有可能的累加和
		// 并且返回一共收集了几个数，就是re
		int re = process(nums, nums.length >> 1, nums.length, 0, 0, r);
		// 把左半部分收集到的累加和排序
		Arrays.sort(l, 0, le);
		// 把左半部分收集到的累加和排序
		Arrays.sort(r, 0, re--);
		
		// 下面的代码用来排序，为什么要排序？因为排序之后定位数字可以不回退
		int ans = Math.abs(goal);
		for (int i = 0; i < le; i++) {
			int rest = goal - l[i];
			while (re > 0 && Math.abs(rest - r[re - 1]) <= Math.abs(rest - r[re])) {
				re--;
			}
			ans = Math.min(ans, Math.abs(rest - r[re]));
		}
		return ans;
	}



	public static int process(int[] nums, int index, int end, int sum, int fill, int[] arr) { // fill参数的意思是: 如果出现新的累加和，填写到arr的什么位置
		if (index == end) { // 到了终止为止了，该结束了
			// 把当前的累加和sum
			// 填写到arr[fill]的位置
			// 然后fill++，表示如果后续再填的话
			// 该放在什么位置了
			arr[fill++] = sum;
		} else {
			// 可能性1 : 不要当前的数字
			// 走一个分支，形成多少累加和，都填写到arr里去
			// 同时返回这个分支把arr填到了什么位置
			fill = process(nums, index + 1, end, sum, fill, arr);
			// 可能性2 : 要当前的数字
			// 走一个分支，形成多少累加和，都填写到arr里去
			// 接着可能性1所填到的位置，继续填写到arr里去
			// 这就是为什么要拿到上一个分支填到哪了
			// 因为如果没有这个信息，可能性2的分支不知道往哪填生成的累加和
			fill = process(nums, index + 1, end, sum + nums[index], fill, arr);
		}
		// 可能性1 + 可能性2，总共填了多少都返回
		return fill; // 返回所有生成的累加和，现在填到了arr的什么位置
	}

}
