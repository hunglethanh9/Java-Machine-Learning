package utils;

import java.text.DecimalFormat;
import java.util.Arrays;

import edge.Edge;
import layer.Layer;
import network.NeuralNetwork;

public class UtilMethods{
	private static final DecimalFormat numFormat1 = new DecimalFormat("###,###.###");
	private static final DecimalFormat numFormat2 = new DecimalFormat("###,###.##");
	
	public static String format(double num){
		return numFormat1.format(num);
	}
	
	public static String shorterFormat(double num){
		return numFormat2.format(num);
	}
	
	public static void printArray(double[][] arr){
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				System.out.print(numFormat1.format(arr[i][j]) + " ");
			}
			System.out.println();
		}
	}
	
	public static void printArray(double[] arr){
		for(int i = 0; i < arr.length; i++){
			System.out.print(numFormat1.format(arr[i]) + " ");
		}
		System.out.println();
	}
	
	public static void printArray(double[] arr, int count){
		for(int i = 0; i < count; i++){
			System.out.print(numFormat1.format(arr[i]) + " ");
		}
		System.out.println();
	}
	
	public static void printNN(NeuralNetwork nn){
		System.out.println("Neural Network:");
		int max1 = nn.getInputSize();
		for(int i = 0; i < nn.size(); i++){
			max1 = Math.max(max1, nn.layers().get(i).nextSize());
		}
		char[][] chars = new char[max1][nn.size() + 1];
		for(int i = 0; i < max1; i++){
			for(int j = 0; j < nn.size() + 1; j++){
				chars[i][j] = ' ';
			}
		}
		for(int i = 0; i < nn.size() + 1; i++){
			Layer l = i == 0 ? null : nn.layers().get(i - 1);
			for(int j = 0; j < (i == 0 ? nn.getInputSize() : l.nextSize()); j++){
				chars[j + ((max1 - (i == 0 ? nn.getInputSize() : l.nextSize())) / 2)][i] = 'O';
			}
		}
		for(int i = 0; i < max1; i++){
			for(int j = 0; j < nn.size() + 1; j++){
				System.out.print(chars[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("Edge Weights:");
		int max2 = 0;
		for(int i = 0; i < nn.size(); i++){
			max2 = Math.max(max2, nn.layers().get(i).edges().length);
		}
		String[][] strings = new String[max2][nn.size()];
		for(int i = 0; i < max2; i++){
			for(int j = 0; j < nn.size(); j++){
				strings[i][j] = makeStr(' ', 15);
			}
		}
		for(int i = 0; i < nn.size(); i++){
			Layer l = nn.layers().get(i);
			for(int j = 0; j < l.edges().length; j++){
				Edge e = l.edges()[j];
				strings[j + ((max2 - l.edges().length) / 2)][i] = e.getNodeA() + "-" + e.getNodeB() + ": " + numFormat1.format(e.getWeight());
			}
		}
		for(int i = 0; i < max2; i++){
			for(int j = 0; j < nn.size(); j++){
				System.out.print(strings[i][j] + makeStr(' ', 16 - strings[i][j].length()));
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("Bias Weights:");
		strings = new String[max1][nn.size()];
		for(int i = 0; i < max1; i++){
			for(int j = 0; j < nn.size(); j++){
				strings[i][j] = makeStr(' ', 15);
			}
		}
		for(int i = 0; i < nn.size(); i++){
			Layer l = nn.layers().get(i);
			for(int j = 0; j < l.nextSize(); j++){
				strings[j + ((max1 - l.nextSize()) / 2)][i] = "B-" + j + ": " + numFormat1.format(l.getBias()[j]);
			}
		}
		for(int i = 0; i < max1; i++){
			for(int j = 0; j < nn.size(); j++){
				System.out.print(strings[i][j] + makeStr(' ', 16 - strings[i][j].length()));
			}
			System.out.println();
		}
	}
	
	public static String makeStr(char c, int n){
		char[] result = new char[n];
		Arrays.fill(result, c);
		return new String(result);
	}
	
	public static double[] oneHotEncode(int index, int size){
		double[] result = new double[size];
		result[index] = 1.0;
		return result;
	}
	
	public static int argMax(double[] arr){
		double max = 0.0;
		int maxIndex = -1;
		for(int i = 0; i < arr.length; i++){
			if(arr[i] > max){
				max = arr[i];
				maxIndex = i;
			}
		}
		return maxIndex;
	}
}
