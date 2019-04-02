package epamtask4;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class SelfDrivenBus {

    private int[] Tree;
    private int N;

    public SelfDrivenBus(final Map<Integer,Set<Integer>> links, final int N) {

        int count = 0;
        Tree = new int[N+1];/* creating tree with N+1 nodes */
        this.N = N;
        Set<Integer> List1= new HashSet<>(N);/* creating HashSets list1,list2 */
        Set<Integer> List2 = new HashSet<>(N);
        List1.add(1);
        Tree[1] = 1;
        while (count < N-1) {
            for (int i : List1) {
                for (int j : links.get(i)) {
                    if (Tree[j] == 0) {
                        Tree[j] = i;/* pushing the subsets into tree */
                        count++;
                        List2.add(j);
                    }
                }
            }
            List1.clear();
            List1.addAll(List2);
            List2.clear();
        
        }
        Tree[1] = 0;
    }

    public int connectedSegments(final int length) {
    	/* this function is used to find weather there is any connected 
    segment with parameter length */
        int count = 0;/* initializing count to 0 because initially connected segments are zero */
        int temp, value;
        for (int i = 1; i <= N-length+1; i++) {
            temp = 0;
            for (int j = i; j < i+length; j++) {
                value = Tree[j];
                if (value < i || value >= i+length) {/* checking weather the given lies in between the length and i */
                    temp++;/* if it satisfies above condition we need to increment the temp */
                    if (temp >= 2) /* if temp>2 we have to break the loop why because we are enough with acquired temp value it means the given length is subset of the tree*/
                    	break;
                }
            }
            if (temp < 2)
            	count++; /* if temp<2 we need to increment count */ 
        }
        return count;/* return count */
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final int N = Integer.parseInt(br.readLine());
        if (N == 1) {/* if N==1 we can print directly as no_of_segemnts=1*/
            System.out.println(1);
            return;
        } else if (N == 2) {/*if N==1 we can print directly as no_of_segemnts=3 */
            System.out.println(3);
            return;
        }
        Map<Integer,Set<Integer>> links = new HashMap<Integer,Set<Integer>>(N);/* creating map for length of N */
        for (int i = 1; i <= N; i++) links.put(i, new HashSet<Integer>());
        String[] edge;
        int node1, node2;
        int No_connected_segments = N+1;
        for (int i = 0; i < N-1; i++) {
            edge = br.readLine().split("\\s");
            node1 = Integer.parseInt(edge[0]);
            node2 = Integer.parseInt(edge[1]);
            links.get(node1).add(node2);
            links.get(node2).add(node1);
            if (Math.abs(node1 - node2) == 1)/* if nodes absolute difference is 1 we need to increment No_connected_segments */
            	No_connected_segments++;
        }
        SelfDrivenBus selfdriven= new SelfDrivenBus(links, N);
        for (int i = 3; i < N; i++) {
            No_connected_segments += selfdriven.connectedSegments(i);/* calling the conectedSegments() */
            	/*  conectedSegments() will return the integer value that value should be added to  No_connected_segments */
        }
        System.out.println(No_connected_segments);/* printing the number of connected segments */
    }
}

