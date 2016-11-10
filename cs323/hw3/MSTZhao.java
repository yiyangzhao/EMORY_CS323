/*
 * THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING

	A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - Yiyang Zhao

 */


/**
 * Copyright 2015, Emory University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package graph.span;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
//import java.util.Random;
import java.util.regex.Pattern;
import java.lang.Math;

import graph.Edge;
import graph.Graph;

import graph.span.MSTPrim;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class MSTZhao
{
	private List<String>  words;
	private List<float[]> vectors;
	//a 2*2 matrix to store distance between two words
	float[][] distance = new float[500][500];
	
	public void readVectors(InputStream in) throws Exception
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		Pattern p = Pattern.compile("\t");
		String line, word;
		float[] vector;
		String[] t;
		
		words   = new ArrayList<>();
		vectors = new ArrayList<>(); 
		
		while ((line = reader.readLine()) != null)
		{
			t = p.split(line);
			word = t[0];
			vector = new float[t.length-1];
			
			for (int i=1; i<t.length; i++)
				vector[i-1] = Float.parseFloat(t[i]);
			
			words.add(word);
			vectors.add(vector);
		}
		
		reader.close();
	}
	
	public SpanningTree findMinimumSpanningTree()
	{
		//DONE

		//Generate a graph based on the distance matrix
		Graph wordgraph = new Graph(vectors.size());
		for (int i=0;i<vectors.size();i++){
			for (int j=0;j<i;j++){
				wordgraph.setUndirectedEdge(i, j, distance[i][j]);
			}
		}		
		
		//use MSTPrim to find a MST of a given graph
		MSTPrim mstword = new MSTPrim();
		return  mstword.getMinimumSpanningTree(wordgraph);
		
		 
	}
	
	public float getEuclideanDistance(float[] v1, float[] v2)
	{
		// DONE:
		//sqrt((A-B).*(A-B))
		float dabiange = 0;
		
		
		for (int i=0;i<v1.length;i++){
			dabiange += (v1[i]-v2[i])*(v1[i]-v2[i]);
		}
		dabiange = (float)Math.sqrt((double)dabiange);
			
		return dabiange;
	}
	
	public float getCosineDistance(float[] v1, float[] v2)
	{
		// DONE:
		float ab=0;	// A.*B
		float a =0; // |A|
		float b =0; // |B|
		
		
		for (int i=0;i<v1.length;i++){
			ab+= (v1[i]*v2[i]);
			a += v1[i]*v1[i];
			b += v2[i]*v2[i];
		}
		
		a = (float)Math.sqrt((double)a);
		b = (float)Math.sqrt((double)b);	
		return (1-(ab/(a*b)));
	}
	
	public void printSpanningTree(OutputStream out, SpanningTree tree)
	{
		PrintStream fout = new PrintStream(new BufferedOutputStream(out));
		fout.println("digraph G {");
		
		for (Edge edge : tree.getEdges())
			fout.printf("\"%s\" -> \"%s\"[label=\"%5.4f\"];\n", words.get(edge.getSource()), words.get(edge.getTarget()), edge.getWeight());
		
		fout.println("}");
		fout.close();
	}
	
	public void distancematrix()
	{
		for (int i=0;i<500;i++){
			for (int j=0;j<500;j++){
				distance[i][j] = -1;
			}
		}
		//for (i,j) if (j,i) distance not calculated then use getEuclideanDistance to calcuate distance, else just copy (j,i)
		for (int i=0;i<vectors.size();i++){
			for (int j=0;j<vectors.size();j++){
				distance[i][j]= (distance[j][i] == -1)? getEuclideanDistance(vectors.get(i),vectors.get(j)):distance[j][i];
			}
		}	
	
	}
	
	public void cosdistancematrix()
	{
		for (int i=0;i<500;i++){
			for (int j=0;j<500;j++){
				distance[i][j] = -1;
			}
		}
		for (int i=0;i<vectors.size();i++){
			for (int j=0;j<vectors.size();j++){
				distance[i][j]= (distance[j][i] == -1)? getCosineDistance(vectors.get(i),vectors.get(j)):distance[j][i];
			}
		}	
	
	}
	
	
	
	static public void main(String[] args) throws Exception
	{
		final String INPUT_FILE  = "/Users/Alan/Desktop/CS323/word_vectors.txt";
		final String OUTPUT_FILE = "/Users/Alan/Desktop/CS323/word_vectors.dot";
		final String OUTPUT_FILE2 = "/Users/Alan/Desktop/CS323/word_vectors_cos.dot";
		
		MSTZhao mst = new MSTZhao();
		
		//use Euclidean norm
		mst.readVectors(new FileInputStream(INPUT_FILE));
		mst.distancematrix();
		SpanningTree tree = mst.findMinimumSpanningTree();
		mst.printSpanningTree(new FileOutputStream(OUTPUT_FILE), tree);
		System.out.println(tree.getTotalWeight());
			
		//use Cos similarity
		mst.cosdistancematrix();
		SpanningTree tree2 = mst.findMinimumSpanningTree();
		mst.printSpanningTree(new FileOutputStream(OUTPUT_FILE2), tree2);
		System.out.println(tree2.getTotalWeight());

		//check if all edges have different weights
		boolean unique=true;
		for (Edge edge1 : tree.getEdges()){
			for (Edge edge2 : tree.getEdges()){
				if ((edge1 != edge2)&& edge1.getWeight()==edge2.getWeight()) {System.out.println(edge1.toString()+edge1.toString());unique=false;}
				
			}
				
		}
		System.out.println("Unique MST:"+unique);
	}
}