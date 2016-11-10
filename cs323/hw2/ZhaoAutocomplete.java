package trie;

/*THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - YIYANG ZHAO

The two required methods are done but not the ec
*/

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import utils.StringUtils;

public class ZhaoAutocomplete extends Trie<List<String>> implements IAutocomplete<List<String>>{
		
	@Override
	public List<String> getCandidates(String prefix)
	{
		prefix=prefix.replaceAll("\\s+","");
		
		/*
		List<String> list = new ArrayList<>();
		
		list.add("These");
		list.add("are");
		list.add("dummy");
		list.add("candidates");
		*/
		
		//use TrieNode to store words
		TrieNode<List<String>> node = find(prefix);
		//check if there is matching entry in the dictionary
		if(node==null) return new ArrayList<String>();
		
		List<String> list = node.getValue();
		if (list==null) list = new ArrayList<String>();
		
		Deque<Entry<String,TrieNode<List<String>>>> currentlist = new ArrayDeque<>();
		currentlist.add(new SimpleEntry<String,TrieNode<List<String>>>(prefix, node));
		
		do{
			Entry<String,TrieNode<List<String>>> word = currentlist.remove();
			node = word.getValue();
			
			if (node.isEndState() && (!list.contains(word.getKey())))
			{
				list.add(word.getKey());
				truncate(list);
			}
			List<Entry<Character,TrieNode<List<String>>>> children = new ArrayList<>(node.getChildrenMap().entrySet());
			Collections.sort(children, Entry.comparingByKey());
			
			for (Entry<Character,TrieNode<List<String>>> chi : children)
			{
				currentlist.add(new SimpleEntry<String,TrieNode<List<String>>>(word.getKey()+chi.getKey(), chi.getValue()));
			}
		} while(!currentlist.isEmpty());
		return list;
	}
	
	//cap the list with a max of 20
	public void truncate(List<String> list){
		if (list.size() >= 20){
			while (list.size()>20) list.remove(list.size()-1);		
		}		
	}
			
	@Override
	public void pickCandidate(String prefix, String candidate)
	{
		prefix=prefix.replaceAll("\\s+","");
		TrieNode<List<String>> node = find(prefix);	
		// if the prefix is not in the dic then create one
		if (node == null)
    	{
    		char[] chararray = prefix.toCharArray();
    		node = getRoot();    		
    		for (int i=0; i<prefix.length(); i++){
    			node = node.addChild(chararray[i]);
    		}
    	}
		// current goes to the top
    	List<String> list = node.getValue();
    	if (list == null)
    	{
    		list = new ArrayList<>();
    		node.setValue(list);    		
    	}
    	//check special case
    	//make sure each candidate only appear once
    	if (list.contains(candidate)) list.remove(candidate);
    	list.add(0,candidate);    	
    	if (!contains(candidate)) put(candidate, null);
	}	
}
