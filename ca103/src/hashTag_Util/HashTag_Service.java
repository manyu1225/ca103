package hashTag_Util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.forPos.model.Forum_post_Service;
import com.forPos.model.Forum_post_VO;

import redis.clients.jedis.Jedis;

public class HashTag_Service {

	private Jedis jedis;

	public HashTag_Service() {

		jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

	}

	public Jedis addHashTag(String tag, String forPost_ID) {

		jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");

		jedis.sadd(forPost_ID, tag);
		jedis.sadd(tag, forPost_ID);

		return jedis;

	}
	
	
	//取得特定文章的標籤
	public List<String> getPostTag(Integer forPost_ID) {

		jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		List<String> tagDisplay = new LinkedList<String>();
		
		//
		for(String tagEachPost : jedis.smembers("post:" + forPost_ID +  ":" + "tag")){
			
			
			tagDisplay.add(tagEachPost);
			
		}
		return tagDisplay;
	}
	
	
	
	
	public Long getPostTagNum(String tag) {

		jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		

		Long tagNum = jedis.scard("tag:" + tag + ":post");

		
		return tagNum;

	}
	
	
	//取得全部標籤統計
	
	public Set<String> getAllPostTag() {   //為了資料不重複目的

		jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		Map<String,String> errorMsgs = new LinkedHashMap<String, String>();
	
		Set<String> tagAllDisplay = new LinkedHashSet<String>();
		
		//
		try {
			
			Forum_post_Service forPosSvc = new Forum_post_Service();
			List<Forum_post_VO> listAllPos_ID = forPosSvc.getAllForPos();
			
//			System.out.println("listAllPos_ID123" + listAllPos_ID.size());
			
			
//			List<String> listInt = new ArrayList<String>();

			
			
			for(Forum_post_VO forPosVO :listAllPos_ID) {
				System.out.println("-----:" + forPosVO.getForPost_ID());
				
//				listInt.add("post:" + forPosVO.getForPost_ID() +  ":" + "tag");
				
				
				for(String tagEachPost : jedis.sunion("post:" + forPosVO.getForPost_ID() +  ":" + "tag")){
					
					
					tagAllDisplay.add(tagEachPost);
					
					
					
				}
			}
			
			
			System.out.println("tagAllDisplay---" + tagAllDisplay);
			
		}catch(RuntimeException e) {
			e.printStackTrace();

		}
		return tagAllDisplay;
	}
	
	
	//取得質該標籤的貼文
	public Set<String> getPostsByTag(String tag) {
		
		jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		
		//List<String> postListByTag = new LinkedList<String>();  
		
		
		 Set<String> forPost_ID_Str = jedis.smembers("tag:" + tag + ":post");
//		 
//		 for(String list : forPost_ID_Str) {
//			 
//			 postListByTag.add(list);
//		 }
		 
		
		return forPost_ID_Str;
		
		
	}
	
	
	//取得質該標籤的貼文
			public Set<String> getPostsNumByTag(String tag) {
				
				jedis = new Jedis("localhost", 6379);
				jedis.auth("123456");
				
				Set<String> postNumByTag = new LinkedHashSet<String>();  
				
				
				Long tagNum = jedis.scard("tag:" + tag + ":post");
//				 
//				 for(String list : forPost_ID_Str) {
//					 
//					 postListByTag.add(list);
//				 }
				
				return postNumByTag;
			}

	
	
	

}
