package layer;

import edge.Edge;
import utils.Activation;

public class FCLayer implements Layer{
	private Edge[] edges;
	private Activation activation;
	private Activation activationP;
	private int prevSize;
	private int nextSize;
	private double[] bias;
	private double dropout;
	
	public FCLayer(int nextSize){
		this.nextSize = nextSize;
		this.activation = Activation.linear;
		this.activationP = Activation.linearP;
		this.bias = new double[nextSize];
		this.dropout = 0.0;
	}
	
	public FCLayer(int nextSize, Activation activation, Activation activationP){
		this.nextSize = nextSize;
		this.activation = activation;
		this.activationP = activationP;
		this.bias = new double[nextSize];
		this.dropout = 0.0;
	}
	
	public FCLayer(int nextSize, Activation activation, Activation activationP, double dropout){
		this.nextSize = nextSize;
		this.activation = activation;
		this.activationP = activationP;
		this.bias = new double[nextSize];
		this.dropout = dropout;
	}
	
	@Override
	public int nextSize(){
		return nextSize;
	}
	
	@Override
	public int prevSize(){
		return prevSize;
	}

	@Override
	public void init(int prevSize){
		this.prevSize = prevSize;
		edges = new Edge[prevSize * nextSize];
		for(int i = 0; i < prevSize; i++){
			for(int j = 0; j < nextSize; j++){
				edges[i * nextSize + j] = new Edge(i, j, prevSize);
			}
		}
	}
	
	@Override
	public void init(int prevSize, double[][] weights, double[] bias){
		this.prevSize = prevSize;
		edges = new Edge[prevSize * nextSize];
		for(int i = 0; i < prevSize; i++){
			for(int j = 0; j < nextSize; j++){
				edges[i * nextSize + j] = new Edge(i, j, weights[i][j]);
			}
		}
		this.bias = bias;
	}
	
	@Override
	public double[] getBias(){
		return bias;
	}
	
	@Override
	public Edge[] edges(){
		return edges;
	}
	
	@Override
	public double[] forwardPropagate(double[] input){
		double[] result = new double[nextSize];
		for(int i = 0; i < edges.length; i++){
			result[edges[i].getNodeB()] += input[edges[i].getNodeA()] * edges[i].getWeight();
		}
		for(int i = 0; i < result.length; i++){
			result[i] += bias[i];
		}
		for(int i = 0; i < result.length; i++){
			result[i] = activation.activate(result[i], result);
		}
		return result;
	}
	
	@Override
	public Activation getActivation(){
		return activation;
	}
	
	@Override
	public Activation getActivationP(){
		return activationP;
	}
	
	@Override
	public double getDropout(){
		return dropout;
	}
}
