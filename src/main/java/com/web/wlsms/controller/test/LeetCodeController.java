package com.web.wlsms.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/leet")
public class LeetCodeController {
    @RequestMapping("/test")
    @ResponseBody
    public int test(){
        int[] intArr = new int[]{1,8,6,2,5,4,8,3,7};
        return maxArea(intArr); 
    }

    /**
     * 盛水最多的容器平面面积(双指针的方案)
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int x = 0, y = height.length - 1;//创建左右指针x代表左，z代表右
        int z = 0; //最大面积
        while (x < y) {
            int a = Math.min(height[x], height[y]) * (y - x);//面积
            z = Math.max(z, a);//取出面积队列最大值
            if (height[x] <= height[y]) {
        x++;
    }
            else {
        y--;
    }
}
        return z;
                }

    public int maxArea2(int[] height) {
        int i = 0, j = height.length - 1, res = 0;
        while(i < j){
            res = height[i] < height[j] ?
                    Math.max(res, (j - i) * height[i++]):
                    Math.max(res, (j - i) * height[j--]);
        }
        return res;
    }


    @RequestMapping("/remove")
    @ResponseBody
    public int remove(){
        int[] intArr = new int[]{0,0,1,1,1,2,2,3,3,4};
        return removeDuplicates(intArr);
    }

    /**
     * 删除排序数组中的重复项
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }

}
