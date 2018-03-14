package cn.leekai.phone.kaidroid.utils;

import java.util.Arrays;

import cn.leekai.phone.kailib.utils.LogUtils;

/**
 * description：
 * created on：2018/3/9 14:25
 * @author likai
 */

public class TestUtil {

	/**
	 * 测试主入口
	 */
	public static void main() {
		// 测试数据
		int[] args = {65, 34, 459, 35, 55, 65, 120, 100, 5, 20, 13, 3000, 2345, 382};

		insertSort(args);
	}

	/**
	 * 排序算法：选择排序（把数值小的数据放到最前，一次增大）
	 * @param args 待排序的数据
	 */
	private static void sort(int[] args) {
		if (args == null || args.length <= 0) {
			return;
		}

		LogUtils.d("likai-test", "原始数据：" + Arrays.toString(args));

		int length = args.length;
		for (int i = 0; i < length; i++) {
			// 在该层中选择最小
			for (int j = i + 1; j < length; j++) {
				// 如果后面的比前面的小，进行数值交换
				if (args[i] > args[j]) {
					int temp = args[i];
					args[i] = args[j];
					args[j] = temp;
				}
			}
		}

		LogUtils.d("likai-test", "排序后的数据:" + Arrays.toString(args));
	}

	/**
	 * 排序算法：冒泡排序
	 */
	private static void sort2(int[] args) {
		if (args == null || args.length <= 0) {
			return;
		}

		LogUtils.d("likai-test", "-原始数据：" + Arrays.toString(args));

		// 从尾部逐渐递减遍历
		for (int i = args.length - 1; i > 0; i--) {
			//这里循环的上界是 i - 1，在这里体现出 “将每一趟排序选出来的最大的数从sorted中移除”
			for (int j = 0; j < i; j++) {
				// 保证在相邻的两个数中比较选出最大的并且进行交换(冒泡过程)
				if (args[j] > args[j + 1]) {
					int temp = args[j];
					args[j] = args[j + 1];
					args[j + 1] = temp;
				}
			}
		}

		LogUtils.d("likai-test", "-排序后的数据:" + Arrays.toString(args));
	}

	/**
	 * 插入排序
	 * @param args 待排序的数据
	 */
	public static void insertSort(int[] args){
		if (args == null || args.length <= 0) {
			return;
		}

		LogUtils.d("likai-test", "-原始数据：" + Arrays.toString(args));

		for (int i = 0; i < args.length; i++){
			// 缓存一下当前数据
			// 当前序列
			int j = i;
			// 当前数值
			int current = args[i];
			// 如果当前值比前一个序列的值小，则进行数值交换（直到序列到达顶端 或 当前值比前一个值大的情况，则退出）
			while (j > 0 && current < args[j - 1]){
				// 把当前值赋值为迁移序列的值（因为前一序列的值比当前值大）
				args[j] = args[j - 1];
				// 序列再迁移一位，进行再前一位的数值比较
				j--;
			}

			// 然后把当前值插入到最后定为的序列
			args[j] = current;
		}

		LogUtils.d("likai-test", "-排序后的数据:" + Arrays.toString(args));
	}

	/**
	 * 划分
	 */
	private static int partition(int[] arr, int left, int right) {
		int pivotKey = arr[left];

		while(left < right) {
			while(arr[right] >= pivotKey) {
				right--;
			}
			//把小的移动到左边
			arr[left] = arr[right];
			while(arr[left] <= pivotKey){
				left++;
			}
			//把大的移动到右边
			arr[right] = arr[left];
		}
		//最后把pivot赋值到中间
		arr[left] = pivotKey;
		return left;
	}

	/**
	 * 递归划分子序列
	 */
	private static void quickSort(int[] arr, int left, int right) {
		if(left >= right){
			return ;
		}
		int pivotPos = partition(arr, left, right);

		quickSort(arr, left, pivotPos-1);

		quickSort(arr, pivotPos+1, right);
	}

	/**
	 * 快速排序
	 * @param arr 待排序的数组
	 */
	private static void quickSort(int[] arr){
		if(arr == null || arr.length == 0) {
			return;
		}
		quickSort(arr, 0, arr.length-1);
	}

	/**
	 * 交换数组的位置
	 * @param arr 待交换的数组
	 * @param left 左侧序列
	 * @param right 右侧序列
	 */
	private static void swap(int[] arr, int left, int right){
		int temp = arr[left];
		arr[left] = arr[right];
		arr[right] = temp;
	}

	private static void shellInsert(int[] arr, int d){

	}

	private static void shellSort(int[] arr){
		if(arr == null || arr.length == 0){
			return;
		}

		int d = arr.length / 2;
		while (d >= 1){
			shellInsert(arr, d);
			d /= 2;
		}
	}


}
