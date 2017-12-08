package preprocessFiles.preprocessEvaluationSet;

import constants.AttributeTypeConstants;
import constants.FormatConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import preprocessFiles.utils.MutableInt;
import utils.*;
import weka.core.Instance;
import weka.core.Instances;

public class SetupTestTrainValidation {
   private final Instances combinedInstances;
   private final String combinePath;

	public SetupTestTrainValidation(String combinedPath) throws IOException{
		this.combinePath    = combinedPath;
		this.combinedInstances= UtilsInstances.getInstances(combinedPath);
	}

   /**
    * Assumes that the different paths are different from one another
    *
    * Currently looks horrible, but works
    * @param evaluationSets
    * @throws IOException
    * @throws Exception
    */
	public void setTrainTestValidationPaths(ArrayList<EvaluationSet> evaluationSets) throws IOException, Exception{
      final ArrayList<SplitFileCounter> splitFileCounters = new ArrayList<>();
      final HashMap<String, MutableInt> nominalValues = UtilsInstances.getClassCount(
         AttributeTypeConstants.ATTRIBUTE_CLASS,
         this.combinedInstances
      );
      
      double totalSplitValue = evaluationSets.stream()
         .mapToDouble((es)->es.getSplitValue())
         .sum();
      
      for (Map.Entry<String, MutableInt> nvEntry : nominalValues.entrySet()) {
         for (EvaluationSet evaluationSet : evaluationSets) {
            splitFileCounters.add(new SplitFileCounter(
               nvEntry.getKey(), 
               evaluationSet.getName(), 
               getLimit(
                  nvEntry.getValue().get(), 
                  evaluationSet.getSplitValue(),
                  totalSplitValue)
               )
            );
         }
      }
      
      addHeaders(evaluationSets, this.combinePath);
      
//TODO cleanup
      int isAttackIndex = UtilsInstances.getAttributeIndex(
         this.combinedInstances, 
         AttributeTypeConstants.ATTRIBUTE_CLASS
      );
      for (Instance instance : this.combinedInstances) {
         String isAttackValue = instance.stringValue(isAttackIndex);
         
         for (SplitFileCounter counter : splitFileCounters) {
            if(isAttackValue.equalsIgnoreCase(counter.getNominalName())){
               if(!counter.isFull()){
                  counter.increment();
                  for (EvaluationSet evaluationSet : evaluationSets) {
                     if(evaluationSet.getName().equalsIgnoreCase(counter.getTestTrainOrValidation())){
                        evaluationSet.addInstance(instance);
                     }
                  }
                  break; // To ensure that only 1 counter gets incremented
               }
            }
         }
      }
      System.out.println("");
   }  
   private int getLimit(int nominalValueCount, float esSplitValue, double totalSplitValue) {
      return (int) Math.round(nominalValueCount * (esSplitValue/totalSplitValue));
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
   private void addHeaders(ArrayList<EvaluationSet> evaluationSets, String source) throws Exception {
      for (EvaluationSet evaluationSet : evaluationSets) {
         evaluationSet.setInstances(UtilsInstances.getHeader(source, FormatConstants.FEATURES_TO_REMOVE));
      }
   }
//      addHeaders(evaluationSets, this.combinePath);
//
//
//      writeFiles();
//
//
//   private void writeFiles() throws IOException {
//      for (Map.Entry<String, Instances> entry : this.newInstancesHM.entrySet()) {
//         Utils.writeStringFile(entry.getKey(), entry.getValue().toString());
//         FormatAsText fat = new FormatAsText(entry.getKey());
//         fat.addClassCount(AttributeTypeConstants.ATTRIBUTE_CLASS);
//      }
//   }
//
//
//
//   private void setupLimits(HashMap<String, FormatAsArff> singleClass, ArrayList<EvaluationSet> evaluationSets) {
//      singleClass.entrySet().forEach((scEntry)->{
//         String attackType = scEntry.getKey();
//         FormatAsArff faa  = scEntry.getValue();
//
//         faa.removeNonMatchingClasses(
//            AttributeTypeConstants.ATTRIBUTE_CLASS,
//            attackType
//         );
//         
//         float totalSplitValue = trainSplit+testSplit+validationSplit;
//         
//         splitParam.entrySet().forEach((spEntry)->{
//            int limit = Math.round(
//                    faa.getInstances().numInstances() * ((float)spEntry.getValue()/totalSplitValue)
//            );
//            this.counters.add(new SplitFileCounter(attackType, spEntry.getKey(), limit));
//         });
//      });
//   }
//
//<editor-fold defaultstate="collapsed" desc="Old ">
//   /**
//    * The reason is because they use the same header variable as the HM's value
//    * If 1 value is edited, all the others are as well.
//    * They aren't technically edited, but since they point to the same object, 1 edit reflects to all
//    *
//    * @param splitParam
//    * @param source Source to get the headers from
//    * @throws Exception
//    */
//   private void addHeaders(HashMap<String, Float> splitParam, String source) throws Exception {
//      for (String path : splitParam.keySet()) {
//          Utils.addToMap(
//            this.newInstancesHM,
//            path,
//            UtilsInstances.getHeader(source, FormatConstants.FEATURES_TO_REMOVE)
//          );
//      }
//   }
//
//   private void setupLimits(HashMap<String, FormatAsArff> singleClass, HashMap<String, Float> splitParam) {
//      singleClass.entrySet().forEach((scEntry)->{
//         String nominalValue = scEntry.getKey();
//         FormatAsArff faa  = scEntry.getValue();
//
//         faa.removeNonMatchingClasses(
//            AttributeTypeConstants.ATTRIBUTE_CLASS,
//            nominalValue
//         );
//
//         float totalSplitValue = trainSplit+testSplit+validationSplit;
//
//         splitParam.entrySet().forEach((spEntry)->{
//            int limit = Math.round(
//                    faa.getInstances().numInstances() * ((float)spEntry.getValue()/totalSplitValue)
//            );
//            this.counters.add(new SplitFileCounter(nominalValue, spEntry.getKey(), limit));
//         });
//      });
//   }

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
//
//   public void setTrainTestValidationPaths(String trainPath, String testPath, String validationPath) throws IOException, Exception{
//      int isAttackIndex = UtilsInstances.getAttributeIndex(
//         this.combinedInstances,
//         AttributeTypeConstants.ATTRIBUTE_CLASS
//      );
//		HashMap<String, FormatAsArff> singleClass = new HashMap<>();
//
//      //To find out what values @isAttack can have
//      for (int i = 0; i < this.combinedInstances.attribute(isAttackIndex).numValues(); i++) {
//         String nominalValue = this.combinedInstances.attribute(isAttackIndex).value(i);
//         Utils.addToMap(singleClass,nominalValue, new FormatAsArff(this.combinePath));
//      }
//
//      HashMap<String, Float> splitParam = new HashMap<>();
//      Utils.addToMap(splitParam, trainPath,      this.trainSplit);
//      Utils.addToMap(splitParam, testPath,       this.testSplit);
//      Utils.addToMap(splitParam, validationPath, this.validationSplit);
//
//      addHeaders(splitParam, this.combinePath);
//
//      setupLimits(singleClass, splitParam);
//
//      for (Instance instance : combinedInstances) {
//         for (SplitFileCounter counter : counters) {
//            if(instance.stringValue(isAttackIndex).equalsIgnoreCase(counter.getAttackType())){
//               if(!counter.isFull()){
//                  counter.increment();
//                  this.newInstancesHM.get(counter.getFileType()).add(instance);
//                  break; // To ensure that only 1 counter gets incremented
//               }
//            }
//         }
//      }
//
//      writeFiles();
//
//	}
//</editor-fold>
   
   /**
    * Doesn't get any instances.
    * <p>
    * Is literally just a counter
    */
   private class SplitFileCounter{
      private final String nominalName;
      private final String testTrainOrValidation;
      private final int limit;
      private int cur;

      public SplitFileCounter(String attackType, String fileType, int limit) {
         this.nominalName = attackType;
         this.testTrainOrValidation = fileType;
         this.limit = limit;
         this.cur = 0;
      }

      public void increment(){
         this.cur++;
      }

      public boolean isFull(){
         return (this.cur == this.limit);
      }

      public String getNominalName() {
         return nominalName;
      }

      public String getTestTrainOrValidation() {
         return testTrainOrValidation;
      }
   }
}