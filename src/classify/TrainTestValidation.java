package classify;

import constants.FileNameConstants;
import format.FormatAsArff;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import utils.Utils;
import utils.UtilsInstances;
import weka.core.Instances;

public class TrainTestValidation extends TrainTest{
   protected final Instances validationSet;

   public TrainTestValidation(String subFolderPath, String trainPath, String testPath, String validationPath) throws IOException, Exception {
      this("results/TestTrainValidation/", subFolderPath, trainPath, testPath, validationPath);
   }

   public TrainTestValidation(String folderPath, String subFolderPath, String trainPath, String testPath, String validationPath) throws IOException, Exception {
      super(folderPath, subFolderPath, trainPath, testPath);

      this.validationSet = UtilsInstances.getInstances(validationPath);
      Utils.writeFile(
         super.fullPath + FileNameConstants.VALIDATION,
         Utils.getFileContents(validationPath)
      );
   }

	/**
	 * Gather all normal, highrate, lowrate into separate Instances file
	 * @param toSplitPath
	 * @throws java.io.IOException
	 */
	public void ttv(String toSplitPath) throws IOException, IllegalArgumentException{
		HashMap<FormatAsArff, String> singleClass = new HashMap<>();
		singleClass.put(new FormatAsArff(toSplitPath), "Normal");
		singleClass.put(new FormatAsArff(toSplitPath), "Highrate");
		singleClass.put(new FormatAsArff(toSplitPath), "Lowrate");

		for (Map.Entry<FormatAsArff, String> entry : singleClass.entrySet()) {
         FormatAsArff faa = entry.getKey();
//         Instances instances = faa.getInstances();
//         instances.get(0).
			String str = entry.getValue();

			faa.removeNonMatchingClasses("isAttack", str);
			Utils.writeFile("Only"+str+".arff", faa.getInstances().toString());

			HashMap<String, Float> splitParam = new HashMap<>();
			splitParam.put("Only"+str+ FileNameConstants.TRAIN,      new Float(4.0));
			splitParam.put("Only"+str+ FileNameConstants.TEST,       new Float(1.0));
			splitParam.put("Only"+str+ FileNameConstants.VALIDATION, new Float(1.0));
         faa.splitFile(6, splitParam);
		}
	}
}