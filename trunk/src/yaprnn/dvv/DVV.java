package yaprnn.dvv;

import java.util.Collection;
import java.util.LinkedList;
import yaprnn.mlp.ActivationFunction;

/** DVV is the main data managment and preprocessing class.
 *  It provides methods for reading picture and sound files and for preprocessing loaded files.
 */
public class DVV {

	private Collection<Data> allData;
	private Collection<Data> trainingData;
	private Collection<Data> testData;

	/** Constructs a DVV for holding {@link IdxPicture} data with the specified filenames.
	 *
	 *  @param dataFilename  the name of the file containing the image data
	 *         labelFilename the name of the file containing the labels/targets
	 */
	public DVV(String dataFilename, String labelFilename) throws InvalidFileException {
		//TODO: error handling
		allData = IdxPicture.readFromFile(dataFilename, labelFilename);
	}
	
	/** Returns the whole data set.
	 *
	 *  @return the data set
	 */
	public Collection<Data> getDataSet() {
		return allData;
	}

	/** Returns the training data set.
	 *
	 *  @return the training data
	 */
	public Collection<Data> getTrainingData() {
		if(trainingData == null) selectTrainingData();
		return trainingData;
	}

	/** Returns the test data set.
	 *
	 *  @return the test data
	 */
	public Collection<Data> getTestData() {
		if(testData == null) selectTrainingData();
		return testData;
	}

	/** Preprocesses the whole data set.
	 *
	 *  @param resolution      the data is to be sampled to
	 *  @param overlap         the overlap used when determining the window sizes
	 *  @param scalingFunction the function used to scale the subsampled data
	 */
	public void preprocess(int resolution, double overlap, ActivationFunction scalingFunction) {
		//TODO: error handling
		for(Data data : allData)
			data.subsample(resolution, overlap, scalingFunction);
	}

	/** Selects training and test data and stores them in the appropriate collections. */
	private void selectTrainingData() {
		trainingData = new LinkedList<Data>();
		testData = new LinkedList<Data>();
		for(Data data : allData)
			if(data.isTraining()) 
				trainingData.add(data);
			else if(data.isTest())
				testData.add(data);
	}

}