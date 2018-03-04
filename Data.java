public class Data {
	private String label;
	private int tally;

	public Data (String label, int tally) {
		this.label = label;
		this.tally = tally;
	}

	public String getLabel () {
		return label;
	}

	public int getTally () {
		return tally;
	}

	public String toString () {
		String output = "";
		output += "Label: " + label;
		output += "\nTally: " + tally;
		return output;
	}
}