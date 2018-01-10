package preprocessFiles.preprocessEvaluationSet;

import constants.AttributeTypeConstants;
import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import preprocessFiles.utils.MutableInt;
import utils.*;
import weka.core.Instance;
import weka.core.Instances;

public final class SetupTestTrainValidation {
   private final Instances combinedInstances;
   private final String combinePath;

	public SetupTestTrainValidation(String combinedPath) throws IOException{
		this.combinePath    = combinedPath;
		this.combinedInstances= UtilsInstances.getInstances(combinedPath);
	}

	public void setTrainTestValidationPaths(ArrayList<EvaluationSet> evaluationSets) throws IOException, Exception{
      final ArrayList<SplitFileCounter> splitFileCounters = 
         setSplitFileCounters(evaluationSets);
      
      addHeaders(evaluationSets, this.combinePath);
      
      setEvaluationSetInstances(splitFileCounters, evaluationSets);
   }  
   
   /**
    * Sets the SplitFileCounters dynamically based on the values of evaluationSets
    * <p>
    * Assumes that the names in evaluatioSets is all unique
    * @param evaluationSets
    * @return
    * @throws IOException 
    */
   private ArrayList<SplitFileCounter> setSplitFileCounters(ArrayList<EvaluationSet> evaluationSets) throws IOException {
      final ArrayList<SplitFileCounter> splitFileCounters = new ArrayList<>();
      
      final double totalSplitValue = evaluationSets.stream()
              .mapToDouble((es)->es.getSplitValue())
              .sum();
      
      final HashMap<String, MutableInt> nominalNames = UtilsInstances.getClassCount(
         AttributeTypeConstants.ATTRIBUTE_CLASS,
         this.combinedInstances
      );
      
      for (Map.Entry<String, MutableInt> nominalName : nominalNames.entrySet()) {
         for (EvaluationSet evaluationSet : evaluationSets) {
            splitFileCounters.add(new SplitFileCounter(
               nominalName.getKey(),
               evaluationSet.getName(),
               getLimit(
                  nominalName.getValue().get(), 
                  evaluationSet.getSplitValue(),
                  totalSplitValue
               )
            ));
         }
      }
      return splitFileCounters;
   }

   //TODO cleanup
   private void setEvaluationSetInstances(final ArrayList<SplitFileCounter> splitFileCounters, ArrayList<EvaluationSet> evaluationSets) {
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
   }  

   private int getLimit(int nominalValueCount, float esSplitValue, double totalSplitValue) {
      return (int) Math.round(nominalValueCount * (esSplitValue/totalSplitValue));
   }

   /**
    * The reason we repeatedly call UtilsInstances.getHeader(...) <p> 
    * is because if not, they use the same header variable as the AL's value <p>
    * Since they're the same, if 1 value is edited, all the others are as well. <p>
    * They aren't technically edited, but since they point to the same object, it's just that 1 edit reflects to all
    *
    * @param evaluationSets
    * @param source Source to get the headers from
    * @throws Exception
    */
   private void addHeaders(ArrayList<EvaluationSet> evaluationSets, String source) throws Exception {
      for (EvaluationSet evaluationSet : evaluationSets) {
         evaluationSet.setInstances(
            UtilsInstances.getHeader(
               source, GlobalFeatureExtraction.getInstance().getFeaturesToRemove()
            )
         );
      }
   }

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