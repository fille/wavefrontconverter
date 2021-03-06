package com.royfat.se;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Inputfile: ");
		Scanner sc = new Scanner(System.in);
        System.out.println("Printing the file passed in:");
        String inputfile = "";
        while(sc.hasNextLine()){
        	inputfile = sc.nextLine();
        	break;
        }
        System.out.println("Reading from: " + inputfile);
        
        try {
        String fromfile =   FileUtils.readFileToString( new File("C:\\Users\\fille\\Documents\\firstobj.obj"));

		WavefontConverter conv = new WavefontConverter();
		conv.ToVertics(fromfile);
		ArrayList<float[]>vertics = conv.meshes;   
		ArrayList<short[]> indices = conv.indices;
		writeToBinary(conv.mesh2,conv.by,"test");
		readfile();
	//	ToJavaclass(vertics,indices,"Monkey");
		
        }catch(Exception e) {
        	e.printStackTrace();
        }
		//conv.ToVertics(Files)
	}

	
	public static void ToJavaclass(ArrayList<float[]>  verts,ArrayList<short[]> indi,String modelname){
		
		StringBuilder builder = new StringBuilder();
	
		builder.append("package se.dungon.crawl;\n");
		
		builder.append("public class " +  modelname + " extends Model {\n");
		int vertsindex = 0;
		int indiceindex = 0;
		int index = 0;
		
		for (float[] vert : verts) {
			
			vertsindex += vert.length;
			
			//1.0, -1.0, -1.0, 1.0, 0.0, -1.0, 0.0, 0.5, 0.74995697,
			builder.append("\t\t\tpublic static final float[] vert"  + index + " = new float[] { ");
				for (int i = 0; i < vert.length; i+=9) {
						
					if(vert.length > i)
						builder.append("\t\t"+vert[i+0]+"f,");
					if(vert.length > i+1)
						builder.append(vert[i+1]+"f,");
					if(vert.length > i+2)
						builder.append(vert[i+2]+"f,");
					if(vert.length > i+3)
						builder.append(vert[i+3]+"f,");
					if(vert.length > i+4)
						builder.append(vert[i+4]+"f,");
					if(vert.length > i+5)
						builder.append(vert[i+5]+"f,");
					if(vert.length > i+6)
						builder.append(vert[i+6]+"f,");
					if(vert.length > i+7)
						builder.append(vert[i+7]+"f,");
					if(vert.length >= i+8)
						builder.append(vert[i+8]+"f,\n");
				
						
					}

			builder.append("};\n\n");
			index++;
		}
		
		builder.append("\tprivate static final float[][] verticsArrays = new float[][] {");
	    
		for (int i = 0; i < verts.size(); i++) {
	    	builder.append("vert"+i+",");
		}
		builder.append("};\n");

		index = 0;
		for (short[] ind : indi) {
			
		 builder.append("\tpublic static final short[] indices"+ index +"= new short[]{\n");
			for (int i = 0; i < ind.length; i+=3) {

				if(ind.length > i)
				builder.append("\t\t" +ind[i+0]+",");
				if(ind.length > i+1)
				builder.append("" +ind[i+1]+",");
				if(ind.length > i+2)
				builder.append("" +ind[i+2]+",\n");
			}
			indiceindex += ind.length;
			builder.append("};\n");
		}
		

		builder.append("\tprivate static final short[][] indiArrays = new short[][] {");
	    
		for (int i = 0; i < indi.size(); i++) {
	    	builder.append("indices"+i+",");
		}
		builder.append("};\n");
		
		
		
		
		builder.append("\tpublic " + modelname +"(MyRenderer render) {\n");
		builder.append("\t\tsuper(render);");
		
		builder.append("\t");
		

	
		
		 builder.append( "\n\tvertexshader=  \"\"+\n");
		 builder.append("\n\t\"varying vec4 vColor;\\n\" +");
	     builder.append("\n\t\"varying vec2 v_TexCoordinate;\"+");
	     builder.append("\n\t\" attribute vec4 vPosition;\\n\"+");
	     builder.append("\n\t\" attribute vec3 in_normals;\\n\"+");
	     builder.append("\n\t\" attribute vec3 a_Normal;\"+");
	     builder.append("\n\t\" attribute vec2 in_TextureCoord;\"+");
	     builder.append("\n\t\" uniform vec4 myposition;\"+");
	     builder.append("\n\t\" uniform mat4 projectionMatrix;\"+");
	     builder.append("\n\t\" uniform mat4 modelMatrix;\"+");
	     builder.append("\n\t\" uniform mat4 viewMatrix;\"+");
	     builder.append("\n\t\" uniform vec3 u_LightPos;\\n\"+");
	     builder.append("\n\t\" void main() {\\n\"+");
		 builder.append("\n\t\" vec4 trans = vPosition -myposition;\"+");
				 
		builder.append("\n\t\"vec3 modelViewVertex = vec3(1,1,1); \\n\"+");
		builder.append("\n\t\"vec3 modelViewNormal = vec3( vec4(a_Normal, 0.0));\\n\"+");
		builder.append("\n\t\"float distance =length(a_Normal- modelViewVertex);  \\n\"+");
		builder.append("\n\t\"vec3 lightVector = normalize(u_LightPos - modelViewVertex);  \\n\"+");
		builder.append("\n\t\"float diffuse = max(dot(modelViewNormal, lightVector), 1.2);\\n\"+");
		builder.append("\n\t\"diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));\"+");
		builder.append("\n\t\"vColor = vec4(1,1,1,1);\"+");
		builder.append("\n\t\" v_TexCoordinate = in_TextureCoord;\"+");
		builder.append("\n\t\"gl_Position =  projectionMatrix *viewMatrix * modelMatrix *trans; \\n\"+");
		builder.append("\n\t\"  }\";");
		
		
		builder.append("\n\n\tfragmentshader=\"\"+\n");
				builder.append("\n\t\"precision mediump float;\\n\"+");
				builder.append("\n\t\"varying vec2 v_TexCoordinate;\\n\"+");
				builder.append("\n\t\"uniform sampler2D u_Texture;\\n\"+");
				builder.append("\n\t\"varying vec4 vColor;\\n\"+");
			
        		builder.append("\n\t\"void main() {\\n\"+");
        	    builder.append("\n\t\"gl_FragColor = texture2D(u_Texture, v_TexCoordinate);\\n\"+");;
        		builder.append("\n\t\"}\";");
        		
        		builder.append("\n}");	
        		
        		builder.append("\n\tpublic void CreateGatherArray(){");
        			
        			 builder.append("\t\t\nint sizevert ="+ vertsindex +";\n"); 	

        			 builder.append("\t\tint sizeindice ="+ indiceindex +";\n"); 	
        			 builder.append("\tint p = 0;\n");
        			 builder.append("\tvertics = new float[sizevert];\t\n");
        			 builder.append("\tindices = new short[sizeindice];\t\n");
        			 builder.append("\tfor(int i=0;i < verticsArrays.length; i++){\t\n");
        			 builder.append("\t\tfor (int j = 0; j < verticsArrays[i].length; j++) {\n");
        			 builder.append("\t\t\tvertics[p]  =  verticsArrays[i][j];");
        			 builder.append("\t\tp++;");
        			 builder.append("}}\n");
        				
        			  
        			 builder.append("\t\tp = 0;\n");
        			 builder.append("\t\tfor(int i =0;i <indiArrays.length;i++ ){\n");
        			 builder.append("\t\tfor (int j = 0; j < indiArrays[i].length; j++) {\n");
        			 builder.append("\t\t\tindices[p] =  indiArrays[i][j];\n\tp++;");
        			 builder.append("\t\t}}\n}\n}");
        				
        			
        		
		System.out.print(builder.toString());;
	try {
		FileUtils.write(new File("C:\\Users\\fille\\Documents\\"+modelname+".java"),builder.toString());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public static void writeToBinary(float[] f,short[] in, String name){
		
		int[] header = new int[]  { f.length,in.length,0 };
		
		ByteBuffer byteBuffer = ByteBuffer.allocate((header.length*4)+(f.length* 4)+(in.length*2));
	
		for (int i = 0; i < header.length; i++) {
			byteBuffer.putInt(header[i]);
		}
		for (int i = 0; i < f.length; i++) {
			
			byteBuffer.putFloat(f[i]);
		}
		
		for (int i = 0; i < in.length; i++) {
			byteBuffer.putShort(in[i]);
		}
		
		byte[] bytearray = byteBuffer.array();
		FileOutputStream output;
		try {
			output = new FileOutputStream(new File("C:\\Users\\fille\\Documents\\"+name+".bin"));

			IOUtils.write(bytearray,output);
			
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readfile(){
		
		try {
			byte[] by = FileUtils.readFileToByteArray(new File("C:\\Users\\fille\\Documents\\test.bin"));
			
			//header
			IntBuffer headerb = ByteBuffer.wrap(by,0,3*4).asIntBuffer();
			final int[] header = new int[headerb.capacity()];
			headerb.get(header);
			//vertics
			FloatBuffer fb = ByteBuffer.wrap(by,3*4, header[0]*4).asFloatBuffer();
			final float[] vertics = new float[fb.capacity()];
			fb.get(vertics);

			//indice
			ShortBuffer sb = ByteBuffer.wrap(by,(header[0]*4)+(header.length*4), header[1]*2).asShortBuffer();
			final short[] indice = new short[sb.capacity()];
			sb.get(indice);
			
			
			System.out.println(Arrays.toString(header));
			System.out.println(Arrays.toString(indice));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

