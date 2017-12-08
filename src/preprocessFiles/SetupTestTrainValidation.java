package preprocessFiles;

import constants.AttributeTypeConstants;
import constants.FormatConstants;
import preprocessFiles.preprocessAs.FormatAsArff;
import preprocessFiles.preprocessAs.FormatAsText;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import utils.*;
import weka.core.Instance;
import weka.core.Instances;

public class SetupTestTrainValidation {
   private final HashMap<String, Instances> newInstancesHM;

   private final Instances combinedInstances;
   private final String combinePath;

   private final ArrayList<SplitFileCounter> counters;

   private final float trainSplit      = 4.0f;
   private final float testSplit       = 1.0f;
   private final float validationSplit = 1.0f;

	public SetupTestTrainValidation(String combinedPath) throws IOException{
		this.combinePath    = combinedPath;
		this.combinedInstances= UtilsInstances.getInstances(combinedPath);
		this.counters     = new ArrayList<>();
		this.newInstancesHM = new HashMap<>();
	}

   /**
    * Assumes that the different paths are different from one another
    *
    * Currently looks horrible, but works
    * @param trainPath
    * @param testPath
    * @param validationPath
    * @throws IOException
    * @throws Exception
    */
	public void setTrainTestValidationPaths(String trainPath, String testPath, String validationPath) throws IOException, Exception{
      int isAttackIndex = UtilsInstances.getAttributeIndex(
         this.combinedInstances, 
         AttributeTypeConstants.ATTRIBUTE_CLASS
      );
		HashMap<String, FormatAsArff> singleClass = new HashMap<>();

      //To find out what values @isAttack can have
      for (int i = 0; i < this.combinedInstances.attribute(isAttackIndex).numValues(); i++) {
         String nominalValue = this.combinedInstances.attribute(isAttackIndex).value(i);
         Utils.addToMap(singleClass,nominalValue, new FormatAsArff(this.combinePath));
      }

      HashMap<String, Float> splitParam = new HashMap<>();
      Utils.addToMap(splitParam, trainPath,      new Float(this.trainSplit));
      Utils.addToMap(splitParam, testPath,       new Float(this.testSplit));
      Utils.addToMap(splitParam, validationPath, new Float(this.validationSplit));

      addHeaders(splitParam, this.combinePath);

      setupLimits(singleClass, splitParam);

      for (Instance instance : combinedInstances) {
         for (SplitFileCounter counter : counters) {
            if(instance.stringValue(isAttackIndex).equalsIgnoreCase(counter.getAttackType())){
               if(!counter.isFull()){
                  counter.increment();
                  this.newInstancesHM.get(counter.getFileType()).add(instance);
                  break; // To ensure that only 1 counter gets incremented
               }
            }
         }
      }

      writeFiles();
	}

   private void writeFiles() throws IOException {
      for (Map.Entry<String, Instances> entry : this.newInstancesHM.entrySet()) {
         Utils.writeStringFile(entry.getKey(), entry.getValue().toString());
         FormatAsText fat = new FormatAsText(entry.getKey());
         fat.addClassCount(AttributeTypeConstants.ATTRIBUTE_CLASS);
      }
   }


   /**
    * The reason is because they use the same header variable as the HM's value
    * If 1 value is edited, all the others are as well.
    * They aren't technically edited, but since they point to the same object, 1 edit reflects to all
    *
    * @param splitParam
    * @param source Source to get the headers from
    * @throws Exception
    */
   private void addHeaders(HashMap<String, Float> splitParam, String source) throws Exception {
      for (String path : splitParam.keySet()) {
          Utils.addToMap(
            this.newInstancesHM, 
            path, 
            UtilsInstances.getHeader(source, FormatConstants.FEATURES_TO_REMOVE)
          );
      }
   }

   private void setupLimits(HashMap<String, FormatAsArff> singleClass, HashMap<String, Float> splitParam) {
      singleClass.entrySet().forEach((scEntry)->{
         String attackType = scEntry.getKey();
         FormatAsArff faa  = scEntry.getValue();

         faa.removeNonMatchingClasses(
            AttributeTypeConstants.ATTRIBUTE_CLASS,
            attackType
         );
         
         float totalSplitValue = trainSplit+testSplit+validationSplit;
         
         splitParam.entrySet().forEach((spEntry)->{
            int limit = Math.round(
                    faa.getInstances().numInstances() * ((float)spEntry.getValue()/totalSplitValue)
            );
            this.counters.add(new SplitFileCounter(attackType, spEntry.getKey(), limit));
         });
      });
   }

   /**
    * Doesn't get any instances. Is literally just a counter
    */
   private class SplitFileCounter{
      private final String attackType;
      private final String fileType;
      private final int limit;
      private int cur;

      public SplitFileCounter(String attackType, String fileType, int limit) {
         this.attackType = attackType;
         this.fileType = fileType;
         this.limit = limit;
         this.cur = 0;
      }

      public void increment(){
         this.cur++;
      }

      public boolean isFull(){
         return (this.cur == this.limit);
      }

      public String getAttackType() {
         return attackType;
      }

      public String getFileType() {
         return fileType;
      }
   }
}