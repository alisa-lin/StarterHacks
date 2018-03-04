import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class DataManager {
	public static final String COLOUR_DATA = "colours.txt";
	public static final int MAX_DATA = 5;
	public static final int NUM_TRIANGLES = 72;
	private ArrayList<Data> dataList;
	private ArrayList<Integer> triangleCount;

	public DataManager () {
		dataList = new ArrayList<>();
	}

	public DataManager (ArrayList<Data> dataList) {
		this.dataList = dataList;
	}

	public DataManager (String fileName) {
		dataList = new ArrayList<>();
		loadData(fileName);
	}

	private void loadData (String fileName) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));

			int numDataSets = Integer.parseInt(in.readLine());

			for (int i = 0; i < numDataSets; i++) {
				in.readLine();

				int numData = Integer.parseInt(in.readLine());
				for (int j = 0; j < numData; j++) {
					Data data = new Data (in.readLine(), Integer.parseInt(in.readLine()));
					dataList.add(data);
				}
			}
			in.close();
		} catch (IOException iox) {
			System.out.println("error");
		}
	}

	public boolean addData (Data data) {
		if (dataList.size() < MAX_DATA) {
			dataList.add(data);
			return true;
		}
		return false;
	}

	public void clear () {
		dataList.clear();
	}

	public ArrayList<String> getLabelList () {
		ArrayList<String> labels = new ArrayList<>();
		for (Data data : dataList) {
			labels.add(data.getLabel());
		}
		return labels;
	}

	public ArrayList<Integer> getTriangleCount () {
		calculateTriangleCount();
		return triangleCount;
	}

	private void calculateTriangleCount () {
		triangleCount = new ArrayList<>();
		int totalTally = calculateTallySum();
		int triangleSum = 0;

		for (Data data : dataList) {
			double percentage = (double) data.getTally() / totalTally;
			int numTriangles = (int)(Math.round(percentage * NUM_TRIANGLES));
			triangleSum += numTriangles;
			triangleCount.add(numTriangles);
		}

		while (triangleSum > NUM_TRIANGLES) {
			int temp = triangleCount.get(triangleCount.size()-1);
			triangleCount.remove(triangleCount.size()-1);
			triangleCount.add(temp-1);
			triangleSum--;
		}

		while (triangleSum < NUM_TRIANGLES) {
			int temp = triangleCount.get(triangleCount.size()-1);
			triangleCount.remove(triangleCount.size()-1);
			triangleCount.add(temp+1);
			triangleSum++;
		}
	}

	private int calculateTallySum () {
		int sum = 0;
		for (Data data : dataList) {
			sum += data.getTally();
		}
		return sum;
	}
}