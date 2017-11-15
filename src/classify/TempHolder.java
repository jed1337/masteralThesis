package classify;

import constants.FileNameConstants;
import constants.FormatConstants;
import format.FormatAsArff;
import format.FormatAsText;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import utils.*;
import weka.core.Attribute;
import weka.core.Instances;

public class TempHolder {
   final HashMap<String, Instances> newInstances;

   final String isAttack = "isAttack";
	final Instances input;
   final String inputPath;

   final ArrayList<Counter> counters;

	public TempHolder(String inputPath) throws IOException{
		this.inputPath    = inputPath;
		this.input        = UtilsInstances.getInstances(inputPath);
		this.counters     = new ArrayList<>();
		this.newInstances = new HashMap<>();
	}

	public void renameMe(String source) throws IOException, Exception{
      int isAttackIndex = UtilsInstances.getAttributeIndex(this.input, this.isAttack);
		HashMap<String, FormatAsArff> singleClass = new HashMap<>();
      
      for (int i = 0; i < this.input.attribute(isAttackIndex).numValues(); i++) {
         String attName = this.input.attribute(isAttackIndex).value(i);
         singleClass.put(attName, new FormatAsArff(this.inputPath));
      }
      
      HashMap<String, Float> splitParam = new HashMap<>();
      splitParam.put(FileNameConstants.TRAIN,      new Float(4.0));
      splitParam.put(FileNameConstants.TEST,       new Float(1.0));
      splitParam.put(FileNameConstants.VALIDATION, new Float(1.0));
      
      for (String path : splitParam.keySet()) {
         this.newInstances.put(path, UtilsInstances.getHeader(source, FormatConstants.FEATURES_TO_REMOVE));
      }

      //Setup limits
      singleClass.entrySet().forEach((scEntry)->{
         String attackType = scEntry.getKey();
         FormatAsArff faa  = scEntry.getValue();

         faa.removeNonMatchingClasses(this.isAttack, attackType);
         splitParam.entrySet().forEach((spEntry)->{
            int limit = Math.round(
               faa.getInstances().numInstances() * ((float)spEntry.getValue()/6)
            );
            this.counters.add(new Counter(attackType, spEntry.getKey(), limit));
         });
      });
      
      int attackIndex = UtilsInstances.getAttributeIndex(this.input, isAttack);
		this.input.forEach((instance)->{
         this.counters.stream()
            .filter((counter)->
            (instance.stringValue(attackIndex).equalsIgnoreCase(counter.getAttackType())))
            .filter((counter)->!counter.isFull())
            .forEach((counter)->{
               counter.increment();
               this.newInstances.get(counter.getFileType()).add(instance);
            });
		});
      for (Map.Entry<String, Instances> entry : newInstances.entrySet()) {
         Utils.writeFile(entry.getKey(), entry.getValue().toString());
         FormatAsText fat = new FormatAsText(entry.getKey());
         fat.addClassCount(isAttack);
      }
	}
}