package com.abc.tool.mongo;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public class TestDriver {
	
	private DBCollection coll;
	MongoClient mongoClient;
	
	@After
	public void after(){
		if(mongoClient!=null){
			mongoClient.close();
		}
	}
	
	@Before
	public void init() throws Exception{
		String myUserName = "admin";  
    	String myPassword = "admin";  
    	mongoClient = new MongoClient("localhost", 27017);  
    	  
    	// 1.数据库列表  
    	for (String s : mongoClient.getDatabaseNames()) {  
    	    System.out.println("DatabaseName=" + s);  
    	}  
    	  
    	// 2.链接student数据库  
    	DB db = mongoClient.getDB("student");  
    	mongoClient.setWriteConcern(WriteConcern.JOURNALED);  
    	  
    	// 3.用户验证  
//    	boolean auth = db.authenticate(myUserName, myPassword.toCharArray());  
//    	System.out.println("auth=" + auth);  
    	  
    	// 4.集合列表  
    	Set<String> colls = db.getCollectionNames();  
    	for (String s : colls) {  
    	    System.out.println("CollectionName=" + s);  
    	}  
    	  
    	// 5.获取摸个集合对象  
    	coll = db.getCollection("user");
	}
	
	@Test
    public void testSearch() throws Exception{
    	 
    	
		// 2.1查询 - one  
		DBObject myDoc = coll.findOne();  
		System.out.println(myDoc);  
		  
		// 2.2 查询 - 数量  
		System.out.println(coll.getCount());  
		  
		// 2.3查询 - 全部  
		DBCursor cursor = coll.find();  
		while (cursor.hasNext()) {  
		System.out.println("全部--------" + cursor.next());  
		}  
		  
		// 2.4查询 - 过滤 - 等于  
		BasicDBObject query = new BasicDBObject("age", 1);  
		cursor = coll.find(query);  
		while (cursor.hasNext()) {  
		System.out.println("age=1--------" + cursor.next());  
		}  
		  
		// 2.5查询 - 过滤条件 - 不等于  
		query = new BasicDBObject("age", new BasicDBObject("$ne", 1));  
		cursor = coll.find(query);  
		while (cursor.hasNext()) {  
		System.out.println("age!=1" + cursor.next());  
		}  
		  
		// 2.6查询 - 过滤条件 - 20 < i <= 30  
		query = new BasicDBObject("age", new BasicDBObject("$gt", 20).append("$lte", 30));  
		cursor = coll.find(query);  
		while (cursor.hasNext()) {  
		System.out.println("20<age<=30" + cursor.next());  
		} 
    }
	
	@Test
	public void testInsert(){
		//add
    	BasicDBObject doc = new BasicDBObject("_id", "6").append("name", new BasicDBObject("username", "limingnihao").append("nickname", "黎明你好")).append("password", "123456")  
		        .append("password", "123456").append("regionName", "北京").append("works", "5").append("birth", new Date());  
		WriteResult result = coll.insert(doc);  
		  
		System.out.println("insert-result: " + result);
	}
	
	@Test
	public void testUpdate(){
		DBObject search = coll.findOne(new BasicDBObject("_id", "6"));  
		BasicDBObject object = new BasicDBObject().append("$set", new BasicDBObject("password", "1211111")).append("$set", new BasicDBObject("birth", new Date()));  
		WriteResult result = coll.update(search, object, true, true);  
		System.out.println("update-result: " + result); 
		DBObject myDoc = coll.findOne(search);  
		System.out.println(myDoc);
	}
	
	@Test
	public void testDelete(){
		DBObject search = coll.findOne(new BasicDBObject("_id", "6"));  
		WriteResult result = coll.remove(search);  
		System.out.println("remove-result: " + result); 
	}

}
