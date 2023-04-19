package com.gongchang.dsaa.algorithm.classic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 贪心算法应用实践
 * 
 * 贪心算法介绍：在每一步选取中都选择最优的结果，从而导致结果是最优的，但需要注意的是贪心算法得到的是近似最优解的结果，不一定是最优的那个解
 * 
 * 
 * 区域覆盖问题介绍：有一些WIFI信号，每个WIFI信号可以覆盖一个或多个区域，从这些WIFI信号中选取出一个子集去覆盖所有的区域，要求这个子集最小
 * 
 * 区域覆盖问题可以使用贪心算法解决，当然也可以使用穷举算法列举可能的集合，但是性能相当的差
 * 类似的数字区间覆盖问题也可以使用贪心算法解决
 * 
 * @Author	gongchang
 */
public class RegionCoverage {

	public static void main(String[] args) {
		RegionCoverage regionCoverage = new RegionCoverage();
		Set<String> selectWifi = regionCoverage.selectWifi(regionCoverage.getWifiRegion());
		System.out.println("选取出来的最优结果是："+selectWifi);
	}
	
	public Map<String, Set<String>> getWifiRegion(){
		Map<String, Set<String>> regionMap = new HashMap<String, Set<String>>(){{
			put("wifi1", new HashSet(Arrays.asList("1号会议室")));
			put("wifi2", new HashSet(Arrays.asList("2号会议室")));
			put("wifi3", new HashSet(Arrays.asList("3号会议室")));
			put("wifi4", new HashSet(Arrays.asList("楼道")));
			put("wifi5", new HashSet(Arrays.asList("1号会议室","办公区左部")));
			put("wifi6", new HashSet(Arrays.asList("3号会议室","办公区右部")));
		}};
		return regionMap;
	}
	
	public Set<String> selectWifi(Map<String, Set<String>> regionMap){
		Set<String> selectedWifi = new HashSet<String>();
		Set<String> currentWhileNoCoverageRegion =getRegionSet(regionMap);
		Set<String> tempSet = new HashSet<String>();
		
		while(currentWhileNoCoverageRegion.size()>0){
			tempSet.clear();
			
			Integer maxCoverageSize = 0;
			String maxCoverageWifi=null;
			
			for (String wifiName : regionMap.keySet()) {
				
				tempSet.addAll(regionMap.get(wifiName));
				tempSet.retainAll(currentWhileNoCoverageRegion);
				
				if(tempSet.size()>0 && (maxCoverageWifi==null||tempSet.size()>maxCoverageSize)){
					maxCoverageSize = tempSet.size();
					maxCoverageWifi = wifiName;
				}
			}
			
			if(maxCoverageWifi!=null){
				selectedWifi.add(maxCoverageWifi);
				currentWhileNoCoverageRegion.removeAll(regionMap.get(maxCoverageWifi));
			}
		}
		
		return selectedWifi;
	}
	
	private Set<String> getRegionSet(Map<String, Set<String>> regionMap){
		HashSet<String> resultSet = new HashSet<String>();
		for (Set<String> set : regionMap.values()) {
			resultSet.addAll(set);
		}
		return resultSet;
	}
	
}
