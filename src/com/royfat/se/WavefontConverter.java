package com.royfat.se;


	import java.lang.reflect.Array;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.Collections;
	import java.util.HashMap;
	import java.util.List;

	import org.apache.commons.lang3.ArrayUtils;

	public class WavefontConverter {

		
		public float[] v;  
		public float[] vt;  
		public short[] f; 
		public   short[] by;
		public float[] textureMap;
		public float[] normals;
		float[] workingtexture;
		public float[] workingtextureMap;
		public int[] frontsTexture;
		public int indicesCount;
		public ArrayList<float[]> meshes = null;

		public ArrayList<short[]> indices  = null;
		
		
		public  float[]  ToVertics(String str)  {
				
		   String[] divided = str.split("\n");
		
		  ArrayList<String> v = new ArrayList<String>();
		  ArrayList<String> vt = new ArrayList<String>();
		  ArrayList<String> f = new ArrayList<String>();
		  ArrayList<String> vn = new ArrayList<String>();
		  
		  
		  HashMap<String, float[]> map = new HashMap<String, float[]>();
		   
		   for (String str1 : divided) {
			
			   if(str1.startsWith("v ")){
				  v.add(str1.substring(2));
			   }else if(str1.startsWith("vt ")){
				   vt.add(str1.substring(3));
			   }else if(str1.startsWith("f ")){
				   f.add(str1.substring(2));
			   }else if(str1.startsWith("vn "))  {
				   vn.add(str1.substring(3));
			   }
			}
		   float[] Textures =null;
		   if(vt.size() > 0){
		   Textures = new float[vt.size()*2];
		   }else {
			   Textures  = new float[]{
					   
					   0f,0f
			   };
		   }
		   int[] fronts = new int[f.size()*3];
		   
		   int p =0;
		   
		   //Texture Setup;
		   for(int i =0;i < vt.size();i++){
			   
			   p = i*2;
			   String[] uv = vt.get(i).split(" ");
			   Textures[p]   =  Float.parseFloat(uv[0]);
			   Textures[p+1] = 1- Float.parseFloat(uv[1]);
		   }
		   
		  //set the xyz
		   ArrayList<Float> xyz = new ArrayList<Float>();
		   for(int i =0;i < v.size();i++){
			  String[] xyzstr =   v.get(i).split(" ");
			  	xyz.add(Float.parseFloat(xyzstr[0]));
			  	xyz.add(Float.parseFloat(xyzstr[1]));
			  	xyz.add(Float.parseFloat(xyzstr[2]));
		  }
		   
		   
		   //Set up normals
		   
		   ArrayList<Float> normals = new ArrayList<Float>();
		   for(int i =0;i < vn.size();i++){
			  String[] normalstr =   vn.get(i).split(" ");
			  normals.add(Float.parseFloat(normalstr[0]));
			  normals.add(Float.parseFloat(normalstr[1]));
			  normals.add(Float.parseFloat(normalstr[2]));
		  }
		   
		   
		
		  String[] keyporder = new String[3*f.size()];
		
		  int pointindex = 0;
		  for(int i =0;i< f.size();i++){
			  
			    
			  String[] verts =   f.get(i).split(" ");

			  for(int x =0;x< verts.length;x++){

				  int uv =0;
				  keyporder[pointindex] =  verts[x];
				  String[] points =   verts[x].split("/");
				  fronts[pointindex] = Integer.parseInt(points[0]);
				  int xyzp   = Integer.parseInt(points[0])-1;   
				  
				  
				  if(points[1].length() > 0){
					  uv   = Integer.parseInt(points[1])-1;  
				  }
				  
				  int normal   = Integer.parseInt(points[2])-1;  
				  
				  xyzp = xyzp*3;
				  uv = uv*2;
				  normal = normal *3;
				  float[] result = new float[]{
					  
					  (float) xyz.get(xyzp),
					  (float) xyz.get(xyzp+1),
					  (float) xyz.get(xyzp+2),
					  1,
					  (float)  normals.get(normal),
					 (float)  normals.get(normal+1),
					 (float)  normals.get(normal+2),
					  Textures[uv],
					  Textures[uv+1],
				
				  };
				  pointindex++;
				  map.put(verts[x],result);
				  
			  }
			  
			  
			  
		  }
		   




			HashMap<String, Integer>hashMap  = new HashMap<String, Integer>();
		   float[] mesh2 = new float[9* map.keySet().size()];
		 
		   int index =0;
		   List<String> list =  new ArrayList<String>();
		   list.addAll(map.keySet());
		   Collections.sort(list);
		   int newindex = 0;
		   
		   
		   for(String key :  list){

			   p = index *9;
			   float[] result =    map.get(key);
			   mesh2[p]    =result[0];
			   mesh2[p+1]  =result[1];
			   mesh2[p+2]  =result[2];
			   mesh2[p+3]  =result[3];
			   mesh2[p+4]  =result[4];
			   mesh2[p+5]  =result[5];
			   mesh2[p+6]  =result[6];
			   mesh2[p+7]  =result[7];
			   mesh2[p+8]  =result[8];
			   hashMap.put(key, index);
			   index++;
		   }
		   int size = mesh2.length/1024;
		   meshes = new ArrayList<float[]>(size);
		   
		   p  = 0;
		   for(int i = 0;i < size;i++){

				  float[] newa = Arrays.copyOfRange(mesh2, p,p+1024);
				  
				  meshes.add(newa);
				  p+= 1024;
		   }
		  
		   by = new short[keyporder.length];
			for(int i=0;i <by.length;i++) {
				by[i] =   Short.parseShort(""+hashMap.get(keyporder[i]));
				   
			}
			
			     p  = 0;
			   	indices = new ArrayList<short[]>(size);
			    size = Math.round(by.length/1024);
			   for(int i = 0;i < size;i++){

				   short[] newa = Arrays.copyOfRange(by, p,p+1024);
					  
				   indices.add(newa);
					  p+= 1024;
			   }
			return mesh2;
		}
	}
 
	

