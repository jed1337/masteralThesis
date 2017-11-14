package classify;

import format.FormatAsArff;
import formatkddarff.Thesis;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Utils;
import weka.core.Instances;

public class TrainTestValidation{
//   private final Instances validationSet;
	/**
	 * Gather all normal, highrate, lowrate into sepate Instances file
	 * @param toSplitPath
	 * @throws java.io.IOException
	 */
	public void ttv(String toSplitPath) throws IOException, IllegalArgumentException{
		HashMap<FormatAsArff, String> singleClass = new HashMap<>();
		singleClass.put(new FormatAsArff(toSplitPath), "Normal");
		singleClass.put(new FormatAsArff(toSplitPath), "Highrates");
		singleClass.put(new FormatAsArff(toSplitPath), "Lowrate");

		for (Map.Entry<FormatAsArff, String> entry : singleClass.entrySet()) {
         FormatAsArff faa = entry.getKey();
			String str = entry.getValue();

			faa.removeNonMatchingClasses("isAttack", str);
			Utils.writeFile("Only"+str+".arff", faa.getInstances().toString(), false);

			HashMap<String, Float> splitParam = new HashMap<>();
			splitParam.put("Only"+str+" Train.arff",      new Float(4.0));
			splitParam.put("Only"+str+" Test.arff",       new Float(1.0));
			splitParam.put("Only"+str+" Validation.arff", new Float(1.0));
         faa.splitFile(6, splitParam);
		}
	}
}